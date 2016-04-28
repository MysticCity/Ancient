package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import java.util.regex.Pattern;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Variable
  extends ICodeSection
{
  public static final HashMap<String, Variable> globVars = new HashMap();
  public static final HashMap<UUID, HashMap<String, Variable>> playerVars = new HashMap();
  public String name;
  IParameter param;
  public Object obj;
  String[] subParams = new String[0];
  ICodeSection parent;
  Operator operator;
  ArgumentInformationObject aio;
  String line;
  
  public Variable(String line, ICodeSection parent, Spell p)
  {
    super(null, null, p);
    this.parent = parent;
    this.line = line;
    parseLine(line, p);
  }
  
  public Variable(String name)
  {
    super(null, null, null);
    this.name = name;
  }
  
  void parseLine(String line, Spell mSpell)
  {
    String[] sides = null;
    if (line.contains("+="))
    {
      sides = line.split("\\+=");
      this.operator = Operator.ADD;
    }
    else if (line.contains("-="))
    {
      sides = line.split("\\-=");
      this.operator = Operator.SUBTRACT;
    }
    else if (line.contains("*="))
    {
      sides = line.split("\\*=");
      this.operator = Operator.MULTIPLY;
    }
    else if (line.contains("/="))
    {
      sides = line.split("/=");
      this.operator = Operator.DIVIDE;
    }
    else if (line.contains("="))
    {
      sides = line.split("=");
      this.operator = Operator.ASSIGN;
    }
    this.name = sides[0].trim();
    if (sides.length == 1) {
      return;
    }
    String[] ps = sides[1].split(":");
    if (Parameter.registeredParameters.size() <= 10) {
      Parameter.registerDefaultParameters();
    }
    for (IParameter p : Parameter.registeredParameters) {
      if (p.getName().toLowerCase().startsWith(ps[0].trim().toLowerCase()))
      {
        this.param = p;
        break;
      }
    }
    ps = sides;
    if (this.param == null)
    {
      this.aio = IArgument.parseArgumentByString(sides[1], mSpell);
      if (this.aio == null) {
        try
        {
          int i = Integer.parseInt(ps[0].trim());
          this.obj = Integer.valueOf(i);
        }
        catch (Exception e)
        {
          try
          {
            float i = Float.parseFloat(ps[0].trim());
            this.obj = Float.valueOf(i);
          }
          catch (Exception e1)
          {
            this.obj = ps[0].trim();
          }
        }
      }
      if (!mSpell.variables.contains(this.name)) {
        mSpell.variables.add(this.name);
      }
      return;
    }
    if (ps.length > 1)
    {
      String[] sp = ps[1].trim().split(Pattern.quote(":"));
      this.subParams = new String[sp.length - 1];
      System.arraycopy(sp, 1, this.subParams, 0, this.subParams.length);
    }
    if (!mSpell.variables.contains(this.name)) {
      mSpell.variables.add(this.name);
    }
  }
  
  public void saveParameter(Player mPlayer, SpellInformationObject so)
  {
    parseLine(this.line, this.mSpell);
    if (this.obj == null)
    {
      if (this.param != null) {
        this.obj = this.param.parseParameter(mPlayer, this.subParams, so);
      }
      if (this.aio != null) {
        this.obj = this.aio.getArgument(so, mPlayer);
      }
    }
    if ((this.obj instanceof Player)) {
      this.obj = new Player[] { (Player)this.obj };
    }
    if ((this.obj instanceof Entity)) {
      this.obj = new Entity[] { (Entity)this.obj };
    }
    if ((this.obj instanceof Location)) {
      this.obj = new Location[] { (Location)this.obj };
    }
  }
  
  public Object getVariableObject()
  {
    if ((this.obj instanceof Player)) {
      this.obj = new Player[] { (Player)this.obj };
    }
    if ((this.obj instanceof Entity)) {
      this.obj = new Entity[] { (Entity)this.obj };
    }
    if ((this.obj instanceof Location)) {
      this.obj = new Location[] { (Location)this.obj };
    }
    return this.obj;
  }
  
  public void AutoCast(ParameterType pt, EffectArgs ea)
  {
    switch (pt)
    {
    case Number: 
      if ((this.obj instanceof Number)) {
        ea.getParams().addLast(Double.valueOf(((Number)this.obj).doubleValue()));
      }
      break;
    case String: 
      if ((this.obj instanceof String)) {
        ea.getParams().addLast(this.obj);
      } else if ((this.obj instanceof Number)) {
        ea.getParams().addLast("" + ((Number)this.obj).doubleValue());
      }
      break;
    case Entity: 
      if ((this.obj instanceof Entity[]))
      {
        ea.getParams().addLast(this.obj);
        return;
      }
      if ((this.obj instanceof Entity))
      {
        Entity[] e = new Entity[1];
        e[0] = ((Entity)this.obj);
        ea.getParams().addLast(e);
      }
      else if ((this.obj instanceof Player[]))
      {
        Entity[] e = (Player[])this.obj;
        ea.getParams().addLast(e);
      }
      break;
    case Player: 
      if ((this.obj instanceof Player[]))
      {
        ea.getParams().addLast(this.obj);
      }
      else if ((this.obj instanceof Player))
      {
        Player[] p = new Player[1];
        p[0] = ((Player)this.obj);
        ea.getParams().addLast(p);
      }
      break;
    case Material: 
      if ((this.obj instanceof Number))
      {
        Material m = Material.getMaterial((int)((Number)this.obj).doubleValue());
        ea.getParams().addLast(m);
      }
    case Boolean: 
      if ((this.obj instanceof Boolean)) {
        ea.getParams().addLast(this.obj);
      }
      break;
    case Location: 
      if ((this.obj instanceof Location[]))
      {
        ea.getParams().addLast(this.obj);
      }
      else if ((this.obj instanceof Entity[]))
      {
        Entity[] arr = (Entity[])this.obj;
        Location[] l = new Location[arr.length];
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] != null) {
            l[i] = arr[i].getLocation();
          }
        }
        ea.getParams().addLast(l);
      }
      else if ((this.obj instanceof Entity))
      {
        Location[] arr = new Location[1];
        arr[0] = ((Entity)this.obj).getLocation();
        ea.getParams().addLast(arr);
      }
      else if ((this.obj instanceof Player[]))
      {
        Player[] arr = (Player[])this.obj;
        Location[] l = new Location[arr.length];
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] != null) {
            l[i] = arr[i].getLocation();
          }
        }
        ea.getParams().addLast(l);
      }
      break;
    case Locx: 
      break;
    case Locy: 
      break;
    case Locz: 
      break;
    case Void: 
      break;
    }
  }
  
  public void reAssign(String line, Spell mSpell)
  {
    parseLine(line, mSpell);
  }
  
  public void executeCommand(Player mPlayer, SpellInformationObject so)
  {
    this.obj = null;
    saveParameter(mPlayer, so);
    if ((this.operator == Operator.ADD) && 
      (so.variables.containsKey(this.name.toLowerCase())))
    {
      Object s = ((Variable)so.variables.get(this.name.toLowerCase())).obj;
      if (((s instanceof Number)) && ((this.obj instanceof Number)))
      {
        Number n = (Number)s;
        Number no = (Number)this.obj;
        this.obj = Double.valueOf(n.doubleValue() + no.doubleValue());
        so.variables.put(this.name.toLowerCase(), this);
      }
      if ((s instanceof String))
      {
        String st = (String)s;
        this.obj = (st + this.obj);
        so.variables.put(this.name.toLowerCase(), this);
      }
    }
    if ((this.operator == Operator.SUBTRACT) && 
      (so.variables.containsKey(this.name.toLowerCase())))
    {
      Object s = ((Variable)so.variables.get(this.name.toLowerCase())).obj;
      if (((s instanceof Number)) && ((this.obj instanceof Number)))
      {
        Number n = (Number)s;
        Number no = (Number)this.obj;
        this.obj = Double.valueOf(n.doubleValue() - no.doubleValue());
        so.variables.put(this.name.toLowerCase(), this);
      }
    }
    if ((this.operator == Operator.DIVIDE) && 
      (so.variables.containsKey(this.name.toLowerCase())))
    {
      Object s = ((Variable)so.variables.get(this.name.toLowerCase())).obj;
      if (((s instanceof Number)) && ((this.obj instanceof Number)))
      {
        Number n = (Number)s;
        Number no = (Number)this.obj;
        this.obj = Double.valueOf(n.doubleValue() / no.doubleValue());
        so.variables.put(this.name.toLowerCase(), this);
      }
    }
    if (this.operator == Operator.MULTIPLY)
    {
      if (so.variables.containsKey(this.name.toLowerCase()))
      {
        Object s = ((Variable)so.variables.get(this.name.toLowerCase())).obj;
        if (((s instanceof Number)) && ((this.obj instanceof Number)))
        {
          Number n = (Number)s;
          Number no = (Number)this.obj;
          this.obj = Double.valueOf(n.doubleValue() * no.doubleValue());
          so.variables.put(this.name.toLowerCase(), this);
        }
      }
    }
    else if (this.operator == Operator.ASSIGN) {
      so.variables.put(this.name.toLowerCase(), this);
    }
    if (globVars.containsKey(this.name)) {
      ((Variable)globVars.get(this.name)).obj = this.obj;
    }
    if ((playerVars.containsKey(so.buffcaster)) && (((HashMap)playerVars.get(so.buffcaster)).containsKey(this.name.toLowerCase())))
    {
      ((Variable)((HashMap)playerVars.get(so.buffcaster)).get(this.name.toLowerCase())).obj = this.obj;
      so.variables.put(this.name.toLowerCase(), ((HashMap)playerVars.get(so.buffcaster)).get(this.name.toLowerCase()));
    }
    if ((this.obj instanceof Player)) {
      this.obj = new Player[] { (Player)this.obj };
    }
    if ((this.obj instanceof Entity)) {
      this.obj = new Entity[] { (Entity)this.obj };
    }
    if ((this.obj instanceof Location)) {
      this.obj = new Location[] { (Location)this.obj };
    }
    if (this.parent != null) {
      this.parent.executeCommand(mPlayer, so);
    }
  }
  
  static enum Operator
  {
    ASSIGN,  ADD,  SUBTRACT,  MULTIPLY,  DIVIDE;
    
    private Operator() {}
  }
}
