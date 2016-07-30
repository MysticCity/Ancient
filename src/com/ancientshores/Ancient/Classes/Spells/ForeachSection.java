package com.ancientshores.Ancient.Classes.Spells;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import com.ancientshores.Ancient.Util.GlobalMethods;

public class ForeachSection extends ICodeSection {
    final ICodeSection parentSection;
    String varname;
    public final HashMap<SpellInformationObject, Integer> playersarrayindexes = new HashMap<SpellInformationObject, Integer>();
    public final HashMap<SpellInformationObject, Object> playersarraysobjects = new HashMap<SpellInformationObject, Object>();
    IParameter p;
    String[] subparams;
    Variable v;
    ArgumentInformationObject aio;

    public Object getObject(SpellInformationObject so, Player mPlayer) {
        if (aio != null) {
            return aio.getArgument(so, mPlayer);
        }
        if (v != null) {
            v.executeCommand(mPlayer, so);
            return v.obj;
        }
        if (p != null) {
            return p.parseParameter(mPlayer, subparams, so);
        }
        return null;
    }

    public ForeachSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent) throws IOException {
        super("endforeach", "foreach", p);
        parentSection = parent;
        parseRaw(curline, bf, linenumber);
        try {
            String foreachstring = firstline.substring(firstline.indexOf(',') + 1);
            String[] args = foreachstring.split(" as ");
            parseForeachObject(args);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error in foreach command which starts with " + this.firstline + " in spell " + p.name);
        }
    }

    private void parseForeachObject(String[] args) {
        if (args.length != 2) {
            return;
        }
        args[0] = args[0].trim();
        varname = args[1].trim();
        mSpell.variables.add(varname.trim());
        for (IParameter p : Parameter.registeredParameters) {
            if (args[0].toLowerCase().startsWith(p.getName().toLowerCase())) {
                String[] subs = args[0].split(":");
                this.p = p;
                if (subs.length > 1) {
                    this.subparams = new String[subs.length - 1];
                    System.arraycopy(subs, 1, this.subparams, 0, subs.length - 1);
                }
                return;
            }
        }
        aio = IArgument.parseArgumentByString(args[0], this.mSpell);
    }

    public void executeCommand(final Player mPlayer, final SpellInformationObject so) {
        if ((so.canceled || sections.size() == 0) && parentSection != null) {
            parentSection.executeCommand(mPlayer, so);
            removeObjects(so);
            return;
        }
        if (!playersarraysobjects.containsKey(so)) {
            Object o = getObject(so, mPlayer);
            if (o == null || !(o instanceof Object[]) || Array.getLength(o) <= 0) {
                removeObjects(so);
                if (parentSection != null) {
                    parentSection.executeCommand(mPlayer, so);
                }
                return;
            }
            o = GlobalMethods.removeNullArrayCells((Object[]) o);
            if (Array.getLength(o) <= 0) {
                removeObjects(so);
                if (parentSection != null) {
                    parentSection.executeCommand(mPlayer, so);
                }
                return;
            }
            Variable v = new Variable(this.varname.trim());
            so.variables.put(varname.trim(), v);
            v.obj = Array.get(o, 0);
            playersarrayindexes.put(so, 1);
            playersarraysobjects.put(so, o);
        }
        if (!playersindexes.containsKey(so)) {
            playersindexes.put(so, 0);
        }
        if (playersindexes.get(so) >= sections.size()) {
            Object o = playersarraysobjects.get(so);
            int index = playersarrayindexes.get(so);
            if (index < Array.getLength(o)) {
                so.variables.get(varname).obj = Array.get(o, index);
                this.playersindexes.put(so, 0);
                playersarrayindexes.put(so, index + 1);
            } else {
                removeObjects(so);
                if (parentSection != null) {
                    parentSection.executeCommand(mPlayer, so);
                } else {
                    so.finished = true;
                    AncientClass.executedSpells.remove(so);
                }
                return;
            }
        }
        if (sections.size() == 0) {
            return;
        }
        final ICodeSection cs = sections.get(playersindexes.get(so));
        playersindexes.put(so, playersindexes.get(so) + 1);
        try {
            int t = so.waittime;
            so.waittime = 0;
            Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                public void run() {
                    cs.executeCommand(mPlayer, so);
                    if (so.canceled) {
                        so.canceled = true;
                    }
                }
            }, Math.round(t / 50));
        } catch (Exception e) {
            so.canceled = true;
            removeObjects(so);
            if (parentSection != null) {
                parentSection.executeCommand(mPlayer, so);
            }
        }
    }

    public void removeObjects(SpellInformationObject so) {
        this.playersarrayindexes.remove(so);
        this.playersarraysobjects.remove(so);
        this.playersindexes.remove(so);
        if (so.variables.containsKey(varname)) {
            so.variables.remove(varname);
        }
    }
}