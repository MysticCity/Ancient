package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import com.ancientshores.Ancient.Util.GlobalMethods;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForeachSection
  extends ICodeSection
{
  final ICodeSection parentSection;
  String varname;
  public final HashMap<SpellInformationObject, Integer> playersarrayindexes = new HashMap();
  public final HashMap<SpellInformationObject, Object> playersarraysobjects = new HashMap();
  IParameter p;
  String[] subparams;
  Variable v;
  ArgumentInformationObject aio;
  
  public Object getObject(SpellInformationObject so, Player mPlayer)
  {
    if (this.aio != null) {
      return this.aio.getArgument(so, mPlayer);
    }
    if (this.v != null)
    {
      this.v.executeCommand(mPlayer, so);
      return this.v.obj;
    }
    if (this.p != null) {
      return this.p.parseParameter(mPlayer, this.subparams, so);
    }
    return null;
  }
  
  public ForeachSection(String curline, BufferedReader bf, int linenumber, Spell p, ICodeSection parent)
    throws IOException
  {
    super("endforeach", "foreach", p);
    this.parentSection = parent;
    parseRaw(curline, bf, linenumber);
    try
    {
      String foreachstring = this.firstline.substring(this.firstline.indexOf(',') + 1);
      String[] args = foreachstring.split(" as ");
      parseForeachObject(args);
    }
    catch (Exception e)
    {
      Bukkit.getLogger().log(Level.SEVERE, "Error in foreach command which starts with " + this.firstline + " in spell " + p.name);
    }
  }
  
  private void parseForeachObject(String[] args)
  {
    if (args.length != 2) {
      return;
    }
    args[0] = args[0].trim();
    this.varname = args[1].trim();
    this.mSpell.variables.add(this.varname.trim());
    for (IParameter p : Parameter.registeredParameters) {
      if (args[0].toLowerCase().startsWith(p.getName().toLowerCase()))
      {
        String[] subs = args[0].split(":");
        this.p = p;
        if (subs.length > 1)
        {
          this.subparams = new String[subs.length - 1];
          System.arraycopy(subs, 1, this.subparams, 0, subs.length - 1);
        }
        return;
      }
    }
    this.aio = IArgument.parseArgumentByString(args[0], this.mSpell);
  }
  
  public void executeCommand(final Player mPlayer, final SpellInformationObject so)
  {
    if (((so.canceled) || (this.sections.size() == 0)) && (this.parentSection != null))
    {
      this.parentSection.executeCommand(mPlayer, so);
      removeObjects(so);
      return;
    }
    if (!this.playersarraysobjects.containsKey(so))
    {
      Object o = getObject(so, mPlayer);
      if ((o == null) || (!(o instanceof Object[])) || (Array.getLength(o) <= 0))
      {
        removeObjects(so);
        if (this.parentSection != null) {
          this.parentSection.executeCommand(mPlayer, so);
        }
        return;
      }
      o = GlobalMethods.removeNullArrayCells((Object[])o);
      if (Array.getLength(o) <= 0)
      {
        removeObjects(so);
        if (this.parentSection != null) {
          this.parentSection.executeCommand(mPlayer, so);
        }
        return;
      }
      Variable v = new Variable(this.varname.trim());
      so.variables.put(this.varname.trim(), v);
      v.obj = Array.get(o, 0);
      this.playersarrayindexes.put(so, Integer.valueOf(1));
      this.playersarraysobjects.put(so, o);
    }
    if (!this.playersindexes.containsKey(so)) {
      this.playersindexes.put(so, Integer.valueOf(0));
    }
    if (((Integer)this.playersindexes.get(so)).intValue() >= this.sections.size())
    {
      Object o = this.playersarraysobjects.get(so);
      int index = ((Integer)this.playersarrayindexes.get(so)).intValue();
      if (index < Array.getLength(o))
      {
        ((Variable)so.variables.get(this.varname)).obj = Array.get(o, index);
        this.playersindexes.put(so, Integer.valueOf(0));
        this.playersarrayindexes.put(so, Integer.valueOf(index + 1));
      }
      else
      {
        removeObjects(so);
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
      removeObjects(so);
      if (this.parentSection != null) {
        this.parentSection.executeCommand(mPlayer, so);
      }
    }
  }
  
  public void removeObjects(SpellInformationObject so)
  {
    this.playersarrayindexes.remove(so);
    this.playersarraysobjects.remove(so);
    this.playersindexes.remove(so);
    if (so.variables.containsKey(this.varname)) {
      so.variables.remove(this.varname);
    }
  }
}
