package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class TrySection
  extends ICodeSection
{
  Condition c;
  final ICodeSection parentSection;
  
  public TrySection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent)
    throws IOException
  {
    super("try", "endtry", p);
    this.parentSection = parent;
    parseRaw(curline, bf, linenumber);
  }
  
  public void executeCommand(final Player mPlayer, final SpellInformationObject so)
  {
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
      else if (this.parentSection == null)
      {
        so.finished = true;
        AncientClass.executedSpells.remove(so);
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
          if (so.canceled) {
            so.canceled = false;
          }
          cs.executeCommand(mPlayer, so);
          if (so.canceled) {
            so.canceled = false;
          }
        }
      }, Math.round(t / 50));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      so.canceled = true;
    }
  }
}
