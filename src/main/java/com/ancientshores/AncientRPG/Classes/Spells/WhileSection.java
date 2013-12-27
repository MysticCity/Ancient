package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;

public class WhileSection extends ICodeSection {
    Condition c;
    final ICodeSection parentSection;

    public WhileSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent) throws IOException {
        super("endwhile", "while", p);
        parentSection = parent;
        parseRaw(curline, bf, linenumber);
        try {
            String condString = firstline.substring(firstline.indexOf(',') + 1);
            c = new Condition(condString, mSpell);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error in while command which starts with " + this.firstline + " in spell " + p.name);
        }
    }

	/*
     * private void parseRaw(String curline, BufferedReader bf, int linenumber)
	 * throws IOException { if
	 * (!curline.trim().toLowerCase().startsWith("while")) return; String
	 * condString = curline.substring(curline.indexOf(',') + 1); c = new
	 * Condition(condString, mSpell); curline = bf.readLine();
	 * 
	 * while (curline != null && !curline.equalsIgnoreCase("")) { linenumber++;
	 * if (curline.trim().equalsIgnoreCase("endwhile")) { return; } if
	 * (curline.startsWith("//")) { curline = bf.readLine(); continue; } if
	 * (curline.toLowerCase().trim().startsWith("if")) { sections.add(new
	 * IfSection(curline, bf, linenumber, mSpell, this)); } else if
	 * (curline.toLowerCase().trim().startsWith("while")) { sections.add(new
	 * WhileSection(curline, bf, linenumber, mSpell, this)); } else if
	 * (curline.toLowerCase().trim().startsWith("foreach")) { sections.add(new
	 * ForeachSection(curline, bf, linenumber, mSpell, this)); } else if
	 * (curline.toLowerCase().trim().startsWith("try")) { sections.add(new
	 * TrySection(curline, bf, linenumber, mSpell, this)); } else if
	 * (curline.toLowerCase().trim().startsWith("repeat")) { sections.add(new
	 * RepeatSection(curline, bf, linenumber, mSpell, this)); } else if
	 * (curline.toLowerCase().trim().startsWith("var")) { sections.add(new
	 * Variable(curline.substring(3), this, mSpell)); } else { sections.add(new
	 * Command(curline, mSpell, linenumber, this)); } curline = bf.readLine(); }
	 * }
	 */

    public void executeCommand(final Player mPlayer, final SpellInformationObject so) {
        if ((so.canceled || sections.size() == 0) && parentSection != null) {
            parentSection.executeCommand(mPlayer, so);
            return;
        }
        if (!playersindexes.containsKey(so)) {
            playersindexes.put(so, 0);
        }
        if (playersindexes.get(so) >= sections.size() && c.conditionFulfilled(mPlayer, so)) {
            playersindexes.remove(so);
            playersindexes.put(so, 0);
        } else if (playersindexes.get(so) >= sections.size()) {
            playersindexes.remove(so);
            if (parentSection != null) {
                parentSection.executeCommand(mPlayer, so);
            } else if (parentSection == null) {
                so.finished = true;
                AncientRPGClass.executedSpells.remove(so);
            }
            return;
        }
        // TODO: FIX every execution check
        if (playersindexes.get(so) == 0 && !c.conditionFulfilled(mPlayer, so)) {
            playersindexes.remove(so);
            if (!so.canceled && parentSection != null) {
                parentSection.executeCommand(mPlayer, so);
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
            AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                public void run() {
                    cs.executeCommand(mPlayer, so);
                    if (so.canceled) {
                        so.canceled = true;
                    }
                }
            }, Math.round(t / 50));
        } catch (Exception e) {
            e.printStackTrace();
            so.canceled = true;
            parentSection.executeCommand(mPlayer, so);
        }
    }
}
