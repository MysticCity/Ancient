package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class RepeatSection
  extends ICodeSection
{
  final ICodeSection parentSection;
  public final HashMap<SpellInformationObject, Integer> playersrepeats = new HashMap();
  boolean variable;
  String varname = "";
  
  public RepeatSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent)
    throws IOException
  {
    super("endrepeat", "repeat", p);
    this.parentSection = parent;
    parseRaw(curline, bf, linenumber);
    String amount = this.firstline.substring(this.firstline.indexOf(',') + 1);
    try
    {
      this.repeatamount = Integer.parseInt(amount.trim());
    }
    catch (Exception e)
    {
      if (this.mSpell.variables.contains(amount.trim()))
      {
        this.variable = true;
        this.varname = amount.trim();
      }
    }
  }
  
  int repeatamount = 0;
  
  public void executeCommand(final Player mPlayer, final SpellInformationObject so)
  {
    if (((so.canceled) || (this.sections.size() == 0)) && (this.parentSection != null))
    {
      removeAll(so);
      this.parentSection.executeCommand(mPlayer, so);
      return;
    }
    int repeatamount = this.repeatamount;
    if (this.variable) {
      repeatamount = (int)((Number)((Variable)so.variables.get(this.varname.trim())).obj).doubleValue();
    }
    if (!this.playersindexes.containsKey(so)) {
      this.playersindexes.put(so, Integer.valueOf(0));
    }
    if (!this.playersrepeats.containsKey(so)) {
      this.playersrepeats.put(so, Integer.valueOf(1));
    }
    if ((((Integer)this.playersrepeats.get(so)).intValue() < repeatamount) && (((Integer)this.playersindexes.get(so)).intValue() >= this.sections.size()))
    {
      this.playersindexes.remove(so);
      this.playersindexes.put(so, Integer.valueOf(0));
      this.playersrepeats.put(so, Integer.valueOf(((Integer)this.playersrepeats.get(so)).intValue() + 1));
    }
    else if (((Integer)this.playersindexes.get(so)).intValue() >= this.sections.size())
    {
      removeAll(so);
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
      removeAll(so);
      so.canceled = true;
    }
  }
  
  public void removeAll(SpellInformationObject so)
  {
    this.playersrepeats.remove(so);
    this.playersindexes.remove(so);
  }
}
