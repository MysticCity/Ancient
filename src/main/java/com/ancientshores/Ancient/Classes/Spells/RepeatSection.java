package com.ancientshores.Ancient.Classes.Spells;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;

public class RepeatSection extends ICodeSection {
    final ICodeSection parentSection;
    public final HashMap<SpellInformationObject, Integer> playersrepeats = new HashMap<SpellInformationObject, Integer>();
    boolean variable;
    String varname = "";

    public RepeatSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent) throws IOException {
        super("endrepeat", "repeat", p);
        parentSection = parent;
        parseRaw(curline, bf, linenumber);
        String amount = firstline.substring(firstline.indexOf(',') + 1);
        try {
            repeatamount = Integer.parseInt(amount.trim());
        } catch (Exception e) {
            if (mSpell.variables.contains(amount.trim())) {
                variable = true;
                varname = amount.trim();
            }
        }
    }

    int repeatamount = 0;

    public void executeCommand(final Player mPlayer, final SpellInformationObject so) {
        if ((so.canceled || sections.size() == 0) && parentSection != null) {
            removeAll(so);
            parentSection.executeCommand(mPlayer, so);
            return;
        }
        int repeatamount = this.repeatamount;
        if (variable) {
            repeatamount = (int) ((Number) so.variables.get(varname.trim()).obj).doubleValue();
        }
        if (!playersindexes.containsKey(so)) {
            playersindexes.put(so, 0);
        }
        if (!playersrepeats.containsKey(so)) {
            playersrepeats.put(so, 1);
        }
        if (playersrepeats.get(so) < repeatamount && playersindexes.get(so) >= sections.size()) {
            playersindexes.remove(so);
            playersindexes.put(so, 0);
            playersrepeats.put(so, playersrepeats.get(so) + 1);
        } else if (playersindexes.get(so) >= sections.size()) {
            removeAll(so);
            if (parentSection != null) {
                parentSection.executeCommand(mPlayer, so);
            } else {
                so.finished = true;
                AncientClass.executedSpells.remove(so);
            }
            return;
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
            e.printStackTrace();
            removeAll(so);
            so.canceled = true;
        }
    }

    public void removeAll(SpellInformationObject so) {
        this.playersrepeats.remove(so);
        this.playersindexes.remove(so);
    }
}