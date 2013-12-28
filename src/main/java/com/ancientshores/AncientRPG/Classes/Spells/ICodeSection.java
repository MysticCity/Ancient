package com.ancientshores.AncientRPG.Classes.Spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;

public abstract class ICodeSection {
    protected final LinkedList<ICodeSection> sections = new LinkedList<ICodeSection>();
    final String endstatement;
    final String startstatement;
    protected String firstline;
    public final Spell mSpell;
    public final HashMap<SpellInformationObject, Integer> playersindexes = new HashMap<SpellInformationObject, Integer>();

    public ICodeSection(String endstatement, String startstatement, Spell mSpell) {
        this.endstatement = endstatement;
        this.startstatement = startstatement;
        this.mSpell = mSpell;
    }

    public abstract void executeCommand(Player mPlayer, SpellInformationObject so);

    protected void parseRaw(String curline, BufferedReader bf, int linenumber) throws IOException {
        firstline = curline;
        if (startstatement != null) {
            String start = curline;
            if (start.contains(",")) {
                start = start.substring(0, start.indexOf(",")).trim();
            }
            if (!start.equalsIgnoreCase(startstatement)) {
                return;
            }
            curline = bf.readLine();
        }
        while (curline != null && !curline.equalsIgnoreCase("")) {
            linenumber++;
            if (endstatement != null && curline.trim().equalsIgnoreCase(endstatement.trim())) {
                return;
            }
            if (curline.startsWith("//")) {
                curline = bf.readLine();
                continue;
            }
            String start = curline;
            if (start.contains(",")) {
                start = start.substring(0, start.indexOf(",")).trim();
            }
            if (start.startsWith("if")) {
                sections.add(new IfSection(curline, bf, linenumber, mSpell, this));
            } else if (start.startsWith("while")) {
                sections.add(new WhileSection(curline, bf, linenumber, mSpell, this));
            } else if (start.startsWith("foreach")) {
                sections.add(new ForeachSection(curline, bf, linenumber, mSpell, this));
            } else if (start.equalsIgnoreCase("else")) {
                if (sections.size() == 0 || !(sections.getLast() instanceof IfSection || sections.getLast() instanceof ElseIfSection)) {
                    Bukkit.getLogger().log(Level.SEVERE, "Error in spell " + mSpell.name + " can't have an else statement without a preceding if statement");
                }
                sections.add(new ElseSection(curline, bf, linenumber, mSpell, this));
            } else if (start.equalsIgnoreCase("elseif")) {
                if (sections.size() == 0 || !(sections.getLast() instanceof IfSection || sections.getLast() instanceof ElseIfSection)) {
                    Bukkit.getLogger().log(Level.SEVERE, "Error in spell " + mSpell.name + " can't have an else statement without a preceding if statement");
                }
                sections.add(new ElseIfSection(curline, bf, linenumber, mSpell, this));
            } else if (start.startsWith("try")) {
                sections.add(new TrySection(curline, bf, linenumber, mSpell, this));
            } else if (start.startsWith("repeat")) {
                sections.add(new RepeatSection(curline, bf, linenumber, mSpell, this));
            } else if (start.startsWith("var")) {
                sections.add(new Variable(curline.substring(3), this, mSpell));
            } else {
                sections.add(new Command(curline, mSpell, linenumber, this));
            }
            curline = bf.readLine();
        }
    }
}