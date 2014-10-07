package com.ancientshores.AncientRPG.Classes.Spells;

import java.io.BufferedReader;
import java.io.IOException;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;

public class CodeSection extends ICodeSection {
    final Spell mSpell;
    final ICodeSection parentSection;

    public CodeSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parentsection) throws IOException {
        super(null, null, p);
        this.mSpell = p;
        this.parentSection = parentsection;
        parseRaw(curline, bf, linenumber);
    }

    public void executeCommand(final Player mPlayer, final SpellInformationObject so) {
        if (so.canceled) {
            return;
        }
        if (!playersindexes.containsKey(so)) {
            playersindexes.put(so, 0);
        }
        if (playersindexes.get(so) >= sections.size()) {
            playersindexes.remove(so);
            if (!so.canceled && parentSection != null) {
                parentSection.executeCommand(mPlayer, so);
            } else if (parentSection == null) {
                so.finished = true;
                AncientRPGClass.executedSpells.remove(so);
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
            playersindexes.remove(so);
            e.printStackTrace();
            so.canceled = true;
        }
    }
}