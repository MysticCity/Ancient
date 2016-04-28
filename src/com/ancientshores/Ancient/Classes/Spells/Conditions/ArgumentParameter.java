package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Parameters.ArgumentParameterWrapper;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ArgumentParameter
{
  IParameter parameter;
  Object c;
  boolean variable;
  public final String s;
  public final String[] subparam;
  final String varname = "";
  
  public ArgumentParameter(String s, Spell mSpell)
  {
    s = s.trim();
    if ((s.contains(":")) && (!s.startsWith("(")))
    {
      String[] buffer = s.split(":");
      this.s = buffer[0];
      this.subparam = new String[buffer.length - 1];
      System.arraycopy(buffer, 1, this.subparam, 0, buffer.length - 1);
    }
    else
    {
      this.s = s;
      this.subparam = null;
    }
    for (IParameter p : Parameter.registeredParameters) {
      if (p.getName().toLowerCase().equalsIgnoreCase(this.s))
      {
        this.parameter = p;
        break;
      }
    }
    if (this.parameter == null) {
      this.parameter = new ArgumentParameterWrapper(IArgument.parseArgumentByString(s, mSpell));
    }
  }
  
  public Object parseParameter(ParameterType pt, SpellInformationObject so, Player mPlayer, Spell spell)
  {
    if (this.variable) {
      try
      {
        this.c = ((Variable)so.variables.get("")).obj;
      }
      catch (Exception ignored) {}
    }
    if (this.parameter != null) {
      this.c = this.parameter.parseParameter(mPlayer, this.subparam, so);
    }
    if ((this.c != null) && (this.parameter == null))
    {
      switch (pt)
      {
      case Player: 
        if ((this.c instanceof Player[]))
        {
          Player[] players = (Player[])this.c;
          return players[0];
        }
      case Location: 
        if ((this.c instanceof Location[]))
        {
          Location[] locs = (Location[])this.c;
          return locs[0];
        }
      case Entity: 
        if ((this.c instanceof Entity[]))
        {
          Entity[] locs = (Entity[])this.c;
          return locs[0];
        }
      case String: 
        return this.c.toString();
      }
      return this.c;
    }
    EffectArgs ea = new EffectArgs(mPlayer, spell, so, null);
    if ((pt == ParameterType.Void) || (this.parameter == null))
    {
      Player[] p = { mPlayer };
      ea.getParams().addLast(p);
      switch (pt)
      {
      case Player: 
        return ea.getParams().get(0);
      case Location: 
        return ea.getParams().get(0);
      case Entity: 
        return ea.getParams().get(0);
      }
      return mPlayer;
    }
    this.parameter.parseParameter(ea, mPlayer, this.subparam, pt);
    try
    {
      if (ea.getParams().size() == 1) {
        switch (pt)
        {
        case Player: 
          return (Player[])ea.getParams().get(0);
        case Location: 
          return (Location[])ea.getParams().get(0);
        case Entity: 
          return (Entity[])ea.getParams().get(0);
        }
      }
    }
    catch (Exception ignored) {}
    return this.c;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\ArgumentParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */