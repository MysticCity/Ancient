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

public class IfSection
  extends ICodeSection
{
  Condition c;
  final ICodeSection parentSection;
  
  public IfSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent)
    throws IOException
  {
    super("endif", "if", p);
    this.parentSection = parent;
    parseRaw(curline, bf, linenumber);
    try
    {
      String condString = this.firstline.substring(this.firstline.indexOf(',') + 1);
      this.c = new Condition(condString, this.mSpell);
    }
    catch (Exception e)
    {
      Bukkit.getLogger().log(Level.SEVERE, "Error in if command which starts with " + this.firstline + " in spell " + p.name);
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
    if (((Integer)this.playersindexes.get(so)).intValue() >= this.sections.size())
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
      if ((!so.canceled) && 
        (this.parentSection != null))
      {
        if (((Integer)this.parentSection.playersindexes.get(so)).intValue() < this.parentSection.sections.size())
        {
          if ((this.parentSection.sections.get(((Integer)this.parentSection.playersindexes.get(so)).intValue()) instanceof ElseSection))
          {
            ElseSection es = (ElseSection)this.parentSection.sections.get(((Integer)this.parentSection.playersindexes.get(so)).intValue());
            this.parentSection.playersindexes.put(so, Integer.valueOf(((Integer)this.parentSection.playersindexes.get(so)).intValue() + 1));
            es.executeElse(mPlayer, so);
            return;
          }
          if ((this.parentSection.sections.get(((Integer)this.parentSection.playersindexes.get(so)).intValue()) instanceof ElseIfSection))
          {
            ElseIfSection es = (ElseIfSection)this.parentSection.sections.get(((Integer)this.parentSection.playersindexes.get(so)).intValue());
            this.parentSection.playersindexes.put(so, Integer.valueOf(((Integer)this.parentSection.playersindexes.get(so)).intValue() + 1));
            es.executeElseIf(mPlayer, so);
            return;
          }
        }
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
      so.canceled = true;
      this.playersindexes.remove(so);
      if (this.parentSection != null) {
        this.parentSection.executeCommand(mPlayer, so);
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\IfSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */