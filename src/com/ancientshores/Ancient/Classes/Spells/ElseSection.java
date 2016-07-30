package com.ancientshores.Ancient.Classes.Spells;

import java.io.BufferedReader;
import java.io.IOException;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;

public class ElseSection extends ICodeSection {
    final ICodeSection parentSection;

    public ElseSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent) throws IOException {
        super("endelse", "else", p);
        parentSection = parent;
        parseRaw(curline, bf, linenumber);
    }

    public void executeElse(final Player mPlayer, final SpellInformationObject so) {
        if ((so.canceled || sections.size() == 0) && parentSection != null) {
            parentSection.executeCommand(mPlayer, so);
            return;
        }
        if (!playersindexes.containsKey(so)) {
            playersindexes.put(so, 0);
        }
        if (playersindexes.get(so) >= sections.size()) {
            playersindexes.remove(so);
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
            playersindexes.remove(so);
            so.canceled = true;
            if (parentSection != null) {
                parentSection.executeCommand(mPlayer, so);
            }
        }
    }

    public void executeCommand(final Player mPlayer, final SpellInformationObject so) {
        if (playersindexes.containsKey(so)) {
            executeElse(mPlayer, so);
        } else {
            parentSection.executeCommand(mPlayer, so);
        }
    }
}