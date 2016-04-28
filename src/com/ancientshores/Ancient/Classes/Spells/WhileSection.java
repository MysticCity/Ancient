package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WhileSection
  extends ICodeSection
{
  Condition c;
  final ICodeSection parentSection;
  
  public WhileSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent)
    throws IOException
  {
    super("endwhile", "while", p);
    this.parentSection = parent;
    parseRaw(curline, bf, linenumber);
    try
    {
      String condString = this.firstline.substring(this.firstline.indexOf(',') + 1);
      this.c = new Condition(condString, this.mSpell);
    }
    catch (Exception e)
    {
      Bukkit.getLogger().log(Level.SEVERE, "Error in while command which starts with " + this.firstline + " in spell " + p.name);
    }
  }
  
  public void executeCommand(final Player mPlayer, final SpellInformationObject so)
  {
    if (((so.canceled) || (this.sections.size() == 0)) && (this.parentSection != null))
    {
      this.parentSection.executeCommand(mPlayer, so);
      return;
    }
    if (!this.playersindexes.containsKey(so)) {
      this.playersindexes.put(so, Integer.valueOf(0));
    }
    if ((((Integer)this.playersindexes.get(so)).intValue() >= this.sections.size()) && (this.c.conditionFulfilled(mPlayer, so)))
    {
      this.playersindexes.remove(so);
      this.playersindexes.put(so, Integer.valueOf(0));
    }
    else if (((Integer)this.playersindexes.get(so)).intValue() >= this.sections.size())
    {
      this.playersindexes.remove(so);
      if (this.parentSection != null)
      {
        this.parentSection.executeCommand(mPlayer, so);
      }
      else
      {
        so.finished = true;
        AncientClass.executedSpells.remove(so);
      }
      return;
    }
    if ((((Integer)this.playersindexes.get(so)).intValue() == 0) && (!this.c.conditionFulfilled(mPlayer, so)))
    {
      this.playersindexes.remove(so);
      if ((!so.canceled) && (this.parentSection != null)) {
        this.parentSection.executeCommand(mPlayer, so);
      }
      return;
    }
    if (this.sections.size() == 0) {
      return;
    }
    final ICodeSection cs = (ICodeSection)this.sections.get(((Integer)this.playersindexes.get(so)).intValue());
    this.playersindexes.put(so, Integer.valueOf(((Integer)this.playersindexes.get(so)).intValue() + 1));
    try
    {
      int t = so.waittime;
      so.waittime = 0;
      Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
      {
        public void run()
        {
          cs.executeCommand(mPlayer, so);
          if (so.canceled) {
            so.canceled = true;
          }
        }
      }, Math.round(t / 50));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      so.canceled = true;
      if (this.parentSection != null) {
        this.parentSection.executeCommand(mPlayer, so);
      }
    }
  }
}
