package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.ExecutableSpellItem;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.stuff.Splitter;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public abstract class Parameterizable
  extends ExecutableSpellItem
{
  protected Parameter[] parameterTypes;
  protected Object[] parameterValues;
  
  public Parameterizable(int line, String description, Parameter[] parameter)
  {
    super(line, description);
    
    this.parameterTypes = parameter;
    this.parameterValues = new Object[this.parameterTypes.length];
  }
  
  public boolean validValues()
  {
    if (this.parameterValues.length != this.parameterTypes.length) {
      return false;
    }
    for (int i = 0; i < this.parameterTypes.length; i++)
    {
      if (this.parameterValues[i] == null) {
        return false;
      }
      if (!(this.parameterValues[i] instanceof Object[])) {}
    }
    for (int i = 0; i < this.parameterTypes.length; i++)
    {
      if (this.parameterValues[i] == null) {
        return false;
      }
      if (!this.parameterValues[i].getClass().equals(this.parameterTypes[i].getType().getClassType())) {
        return false;
      }
      if (this.parameterTypes[i].isArray())
      {
        if (!(this.parameterValues[i] instanceof Object[])) {
          return false;
        }
        for (Object o : this.parameterValues) {
          if (o == null) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public boolean validParameters(String s)
  {
    ArrayList<Object> args = new ArrayList();
    String[] arguments = Splitter.splitByArgument(s);
    if (arguments.length != this.parameterTypes.length) {
      return false;
    }
    for (int i = 0; i < arguments.length; i++)
    {
      String argument = arguments[i].trim();
      String[] subArguments;
      if (argument.startsWith("("))
      {
        if (argument.indexOf(":") == -1) {}
        SpellItem item = SpellParser.parseLine(argument, this);
        if (!(item instanceof Returnable)) {
          return false;
        }
        Parameter returning = ((Returnable)item).getReturnType();
        if ((this.parameterTypes[i].isArray()) && (returning.isArray()) && (this.parameterTypes[i].getType().compareTo(returning.getType()) == 0)) {
          args.add(i, item);
        } else {
          return false;
        }
      }
      else if (argument.startsWith("["))
      {
        subArguments = Splitter.splitArray(argument);
      }
      if (!this.parameterTypes[i].isArray())
      {
        switch (this.parameterTypes[i].getType())
        {
        case BOOLEAN: 
          if ((argument.equals("true")) || (argument.equals("false"))) {
            args.add(i, Boolean.valueOf(Boolean.parseBoolean(argument)));
          } else {
            return false;
          }
          break;
        case ENTITY: 
        case PLAYER: 
          UUID uuid;
          try
          {
            uuid = UUID.fromString(argument);
          }
          catch (IllegalArgumentException ex)
          {
            return false;
          }
          args.add(i, uuid);
          break;
        case LOCATION: 
          String[] subargs = argument.split(",");
          if (subargs.length != 4) {
            return false;
          }
          World w;
          if ((w = Bukkit.getWorld(subargs[0].trim())) == null) {
            return false;
          }
          double x;
          double y;
          double z;
          try
          {
            x = Double.parseDouble(subargs[1]);
            y = Double.parseDouble(subargs[2]);
            z = Double.parseDouble(subargs[3]);
          }
          catch (NumberFormatException ex)
          {
            return false;
          }
          catch (NullPointerException ex)
          {
            return false;
          }
          args.add(i, new Location(w, x, y, z));
          break;
        case MATERIAL: 
          Material mat = Material.getMaterial(argument);
          if (mat == null) {
            return false;
          }
          args.add(i, mat);
          break;
        case NUMBER: 
          try
          {
            Double.parseDouble(argument);
          }
          catch (NumberFormatException ex)
          {
            return false;
          }
          catch (NullPointerException ex)
          {
            return false;
          }
          args.add(i, Double.valueOf(Double.parseDouble(argument)));
          break;
        case STRING: 
          args.add(i, argument);
        }
      }
      else
      {
        String[] subargs = Splitter.splitByArgument(argument);
        switch (this.parameterTypes[i].getType())
        {
        case BOOLEAN: 
          boolean[] booleans = new boolean[subargs.length];
          for (int j = 0; j < subargs.length; i++)
          {
            String subarg = subargs[j];
            if ((!subarg.equals("true")) && (!subarg.equals("false"))) {
              return false;
            }
            booleans[j] = Boolean.parseBoolean(subarg);
          }
          args.add(i, booleans);
          break;
        case ENTITY: 
        case PLAYER: 
          UUID[] uuids = new UUID[subargs.length];
          for (int j = 0; j < subargs.length; i++)
          {
            String subarg = subargs[j];
            try
            {
              uuids[j] = UUID.fromString(subarg);
            }
            catch (IllegalArgumentException ex)
            {
              return false;
            }
          }
          args.add(i, uuids);
          break;
        case LOCATION: 
          Location[] locations = new Location[subargs.length];
          for (int j = 0; j < subargs.length; i++)
          {
            String subarg = subargs[j];
            String[] locargs = Splitter.splitByArgument(subarg);
            if (locargs.length != 4) {
              return false;
            }
            World w;
            if ((w = Bukkit.getWorld(locargs[0].trim())) == null) {
              return false;
            }
            double x;
            double y;
            double z;
            try
            {
              x = Double.parseDouble(locargs[1]);
              y = Double.parseDouble(locargs[2]);
              z = Double.parseDouble(locargs[3]);
            }
            catch (NumberFormatException ex)
            {
              return false;
            }
            catch (NullPointerException ex)
            {
              return false;
            }
            locations[j] = new Location(w, x, y, z);
          }
          args.add(i, locations);
          break;
        case MATERIAL: 
          Material[] materials = new Material[subargs.length];
          for (int j = 0; j < subargs.length; i++)
          {
            String subarg = subargs[j];
            Material mat = Material.getMaterial(subarg);
            if (mat == null) {
              return false;
            }
            materials[i] = mat;
          }
          args.add(i, materials);
          break;
        case NUMBER: 
          Double[] doubles = new Double[subargs.length];
          for (int j = 0; j < subargs.length; i++)
          {
            String subarg = subargs[j];
            try
            {
              doubles[j] = Double.valueOf(Double.parseDouble(subarg));
            }
            catch (NumberFormatException ex)
            {
              return false;
            }
            catch (NullPointerException ex)
            {
              return false;
            }
          }
          args.add(i, doubles);
          break;
        case STRING: 
          args.add(i, subargs);
        }
      }
    }
    this.parameterValues = args.toArray();
    return true;
  }
}
