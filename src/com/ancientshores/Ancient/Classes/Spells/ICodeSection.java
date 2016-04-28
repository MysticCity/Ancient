package com.ancientshores.Ancient.Classes.Spells;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class ICodeSection
{
  protected final LinkedList<ICodeSection> sections = new LinkedList();
  final String endstatement;
  final String startstatement;
  protected String firstline;
  public final Spell mSpell;
  public final HashMap<SpellInformationObject, Integer> playersindexes = new HashMap();
  
  public ICodeSection(String endstatement, String startstatement, Spell mSpell)
  {
    this.endstatement = endstatement;
    this.startstatement = startstatement;
    this.mSpell = mSpell;
  }
  
  public abstract void executeCommand(Player paramPlayer, SpellInformationObject paramSpellInformationObject);
  
  protected void parseRaw(String curline, BufferedReader bf, int linenumber)
    throws IOException
  {
    this.firstline = curline;
    if (this.startstatement != null)
    {
      String start = curline;
      if (start.contains(",")) {
        start = start.substring(0, start.indexOf(",")).trim();
      }
      if (!start.equalsIgnoreCase(this.startstatement)) {
        return;
      }
      curline = bf.readLine();
    }
    while ((curline != null) && (!curline.equalsIgnoreCase("")))
    {
      linenumber++;
      if ((this.endstatement != null) && (curline.trim().equalsIgnoreCase(this.endstatement.trim()))) {
        return;
      }
      if (curline.startsWith("//"))
      {
        curline = bf.readLine();
      }
      else
      {
        String start = curline;
        if (start.contains(",")) {
          start = start.substring(0, start.indexOf(",")).trim();
        }
        if (start.startsWith("if"))
        {
          this.sections.add(new IfSection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.startsWith("while"))
        {
          this.sections.add(new WhileSection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.startsWith("foreach"))
        {
          this.sections.add(new ForeachSection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.equalsIgnoreCase("else"))
        {
          if ((this.sections.size() == 0) || ((!(this.sections.getLast() instanceof IfSection)) && (!(this.sections.getLast() instanceof ElseIfSection)))) {
            Bukkit.getLogger().log(Level.SEVERE, "Error in spell " + this.mSpell.name + " can't have an else statement without a preceding if statement");
          }
          this.sections.add(new ElseSection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.equalsIgnoreCase("elseif"))
        {
          if ((this.sections.size() == 0) || ((!(this.sections.getLast() instanceof IfSection)) && (!(this.sections.getLast() instanceof ElseIfSection)))) {
            Bukkit.getLogger().log(Level.SEVERE, "Error in spell " + this.mSpell.name + " can't have an else statement without a preceding if statement");
          }
          this.sections.add(new ElseIfSection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.startsWith("try"))
        {
          this.sections.add(new TrySection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.startsWith("repeat"))
        {
          this.sections.add(new RepeatSection(curline, bf, linenumber, this.mSpell, this));
        }
        else if (start.startsWith("var"))
        {
          this.sections.add(new Variable(curline.substring(3), this, this.mSpell));
        }
        else
        {
          this.sections.add(new Command(curline, this.mSpell, linenumber, this));
        }
        curline = bf.readLine();
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\ICodeSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */