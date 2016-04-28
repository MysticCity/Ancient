package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import com.ancientshores.Ancient.Classes.Spells.Parameters.ArgumentParameterWrapper;
import com.ancientshores.Ancient.Classes.Spells.Parameters.AttackedEntityParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.AttackerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.BlockInSightParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.ChangedBlockParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestEntitiesParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestEntityInSightParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestEntityParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostileEntitiesParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostileEntityInSight;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostileEntityParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostilePlayerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostilePlayersParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestPlayerInSightParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestPlayerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestPlayersParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.PartyMembersParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.PlayerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.TargetInSight;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Parameter
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static HashSet<IParameter> registeredParameters = new HashSet();
  public final String s;
  public ParameterType pt;
  public IParameter parameter;
  public final Command mCommand;
  public final String[] subparam;
  
  public Parameter(Command c, String s, int number, ICommand effect)
  {
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
    for (IParameter p : registeredParameters) {
      if (p.getName().toLowerCase().equalsIgnoreCase(this.s))
      {
        this.parameter = p;
        break;
      }
    }
    if (this.parameter == null) {
      this.parameter = new ArgumentParameterWrapper(IArgument.parseArgumentByString(s, c.mSpell));
    }
    this.mCommand = c;
    try
    {
      this.pt = effect.paramTypes[number];
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Wrong number of parameters in command: " + c.commandString);
      this.pt = ParameterType.Void;
    }
  }
  
  public synchronized void parseParameter()
  {
    for (IParameter p : registeredParameters) {
      if (p.getName().toLowerCase().startsWith(this.s.toLowerCase()))
      {
        this.parameter = p;
        return;
      }
    }
  }
  
  public static synchronized void registerDefaultParameters()
  {
    registeredParameters.add(new AttackedEntityParameter());
    registeredParameters.add(new AttackerParameter());
    registeredParameters.add(new BlockInSightParameter());
    registeredParameters.add(new ChangedBlockParameter());
    registeredParameters.add(new NearestEntitiesParameter());
    registeredParameters.add(new NearestEntityInSightParameter());
    registeredParameters.add(new NearestEntityParameter());
    registeredParameters.add(new NearestHostileEntityParameter());
    registeredParameters.add(new NearestHostileEntitiesParameter());
    registeredParameters.add(new NearestHostilePlayerParameter());
    registeredParameters.add(new NearestHostileEntityInSight());
    registeredParameters.add(new NearestHostilePlayersParameter());
    registeredParameters.add(new NearestPlayerInSightParameter());
    registeredParameters.add(new NearestPlayerParameter());
    registeredParameters.add(new NearestPlayersParameter());
    registeredParameters.add(new PartyMembersParameter());
    registeredParameters.add(new PlayerParameter());
    registeredParameters.add(new TargetInSight());
    registeredParameters.add(new NearestHostileEntityInSight());
  }
  
  public Variable getVariable(SpellInformationObject so, String name)
  {
    if ((Variable.playerVars.containsKey(so.buffcaster)) && (((HashMap)Variable.playerVars.get(so.buffcaster)).containsKey(name.toLowerCase()))) {
      return (Variable)((HashMap)Variable.playerVars.get(so.buffcaster)).get(name.toLowerCase());
    }
    if (so.variables.containsKey(name.toLowerCase())) {
      return (Variable)so.variables.get(name.toLowerCase());
    }
    return null;
  }
  
  public void parseParameter(EffectArgs ea, Player mPlayer)
  {
    if (ea.getSpellInfo().variables.containsKey(this.s.toLowerCase()))
    {
      Variable v = getVariable(ea.getSpellInfo(), this.s.toLowerCase());
      v.AutoCast(this.pt, ea);
      return;
    }
    if ((ea.getSpell() != null) && (ea.getSpell().variables.contains(this.s.toLowerCase())))
    {
      parseVariable(ea, mPlayer, this.s.toLowerCase());
      return;
    }
    if (this.parameter != null) {
      this.parameter.parseParameter(ea, mPlayer, this.subparam, this.pt);
    } else if ((this.pt == ParameterType.String) || (this.pt == ParameterType.Number) || (this.pt == ParameterType.Material) || (this.pt == ParameterType.Boolean)) {
      parsePrimitive(ea);
    } else {
      Bukkit.getLogger().log(Level.WARNING, "Parameter " + this.s + " not found");
    }
  }
  
  void parseVariable(EffectArgs ea, Player mPlayer, String var)
  {
    switch (this.pt)
    {
    case Number: 
      int value = Variables.getParameterIntByString(ea.getSpellInfo().buffcaster.getUniqueId(), var, ea.getSpell().newConfigFile);
      ea.getParams().addLast(Integer.valueOf(value));
      break;
    case String: 
      int v = Variables.getParameterIntByString(ea.getSpellInfo().buffcaster.getUniqueId(), var, ea.getSpell().newConfigFile);
      ea.getParams().addLast("" + v);
    }
  }
  
  void parsePrimitive(EffectArgs ea)
  {
    switch (this.pt)
    {
    case String: 
      ea.getParams().addLast(this.s);
      return;
    case Material: 
      try
      {
        int t = Integer.parseInt(this.s);
        ea.getParams().addLast(Material.getMaterial(t));
        return;
      }
      catch (Exception ignored) {}
    case Number: 
      try
      {
        double t = Double.parseDouble(this.s);
        ea.getParams().addLast(Double.valueOf(t));
        return;
      }
      catch (Exception ignored) {}
    case Boolean: 
      try
      {
        boolean b = Boolean.parseBoolean(this.s);
        ea.getParams().addLast(Boolean.valueOf(b));
      }
      catch (Exception ignored) {}
    default: 
      Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + this.mCommand.commandString);
    }
  }
  
  public EffectArgs getArgs(EffectArgs ea, Player mPlayer)
  {
    parseParameter(ea, mPlayer);
    return ea;
  }
}
