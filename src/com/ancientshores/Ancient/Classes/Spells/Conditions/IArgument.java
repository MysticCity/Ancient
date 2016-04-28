package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.StringArgument;
import java.util.HashSet;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class IArgument
{
  public ParameterType returnType;
  public ParameterType[] requiredTypes;
  public String name;
  public static HashSet<IArgument> registeredArguments = new HashSet();
  
  public abstract Object getArgument(Object[] paramArrayOfObject, SpellInformationObject paramSpellInformationObject);
  
  public static ArgumentInformationObject parseArgumentByString(String line, Spell spell)
  {
    line.trim();
    ArgumentInformationObject aio = new ArgumentInformationObject();
    
    LinkedList<String> args = new LinkedList();
    int curpos = 0;
    int openbrackets = 0;
    int last = 0;
    boolean openq = false;
    line = line.trim();
    if (line.endsWith(")")) {
      line = line.substring(0, line.length() - 1);
    }
    if (line.startsWith("(")) {
      line = line.substring(1);
    }
    while (curpos < line.length())
    {
      if ((line.charAt(curpos) == '(') && (!openq)) {
        openbrackets++;
      } else if ((line.charAt(curpos) == ')') && (!openq)) {
        openbrackets--;
      } else if ((line.charAt(curpos) == ',') && (openbrackets == 0) && (!openq)) {
        if (line.charAt(curpos) == ')')
        {
          args.add(line.substring(last, curpos - 1));
          
          last = curpos;
        }
        else
        {
          args.add(line.substring(last, curpos));
          
          last = curpos + 1;
        }
      }
      if (line.charAt(curpos) == '"') {
        openq = !openq;
      }
      curpos++;
    }
    if (curpos != last) {
      if (line.charAt(curpos - 1) == ')') {
        args.add(line.substring(last, curpos - 1));
      } else {
        args.add(line.substring(last, curpos));
      }
    }
    aio.argument = getArgumentByString(((String)args.get(0)).trim(), spell);
    ArgumentParameter[] params = new ArgumentParameter[args.size() - 1];
    for (int i = 1; i < args.size(); i++) {
      params[(i - 1)] = new ArgumentParameter((String)args.get(i), spell);
    }
    aio.parameters = params;
    return aio;
  }
  
  public boolean isValidArgument(Object o, Class<?> c)
  {
    if ((o != null) && (o.getClass() == c) && (((Object[])o).length > 0)) {
      return true;
    }
    return false;
  }
  
  public static void addDefaults()
  {
    registeredArguments.add(new GetLeggings());
    registeredArguments.add(new GetBoots());
    registeredArguments.add(new GetChestplate());
    registeredArguments.add(new GetHelmet());
    registeredArguments.add(new GetItemInHands());
    registeredArguments.add(new GetAttackedEntity());
    registeredArguments.add(new GetAttacker());
    registeredArguments.add(new GetLevel());
    registeredArguments.add(new GetRandom());
    registeredArguments.add(new GetHealth());
    registeredArguments.add(new GetMaxHealth());
    registeredArguments.add(new GetClassName());
    registeredArguments.add(new GetFoodLevel());
    registeredArguments.add(new BlockExistsInRange());
    registeredArguments.add(new IsInZone());
    registeredArguments.add(new IsInvisible());
    registeredArguments.add(new GetShooterOrAttacker());
    registeredArguments.add(new GetLightLevel());
    registeredArguments.add(new GetDamageCause());
    registeredArguments.add(new GetFoodLevel());
    registeredArguments.add(new GetDayTime());
    registeredArguments.add(new GetProjectileHitLocation());
    registeredArguments.add(new GetProjectileName());
    registeredArguments.add(new GetProjectileNameCustomName());
    registeredArguments.add(new GetItemCount());
    registeredArguments.add(new GetBlockRelative());
    registeredArguments.add(new GetBrokenBlockType());
    registeredArguments.add(new GetPlayerName());
    registeredArguments.add(new PlayersInRange());
    registeredArguments.add(new EntitiesInRage());
    registeredArguments.add(new GetCube());
    registeredArguments.add(new GetBlockRelative());
    registeredArguments.add(new GetCuboid());
    registeredArguments.add(new GetWall());
    registeredArguments.add(new PlayerExists());
    registeredArguments.add(new GetPlayerByUUID());
    registeredArguments.add(new GetPlayerByName());
    registeredArguments.add(new GetChatArgument());
    registeredArguments.add(new GetChatArgumentLength());
    registeredArguments.add(new GetMana());
    registeredArguments.add(new GetMaxMana());
    registeredArguments.add(new ParseNumber());
    registeredArguments.add(new Replace());
    registeredArguments.add(new GetGuildName());
    registeredArguments.add(new GetHealthPercentage());
    registeredArguments.add(new GetDistance());
    registeredArguments.add(new GetEntityName());
    registeredArguments.add(new GetUniqueEntityId());
    registeredArguments.add(new GetDamage());
    registeredArguments.add(new Round());
    registeredArguments.add(new GetAmountOf());
    registeredArguments.add(new GetTime());
    registeredArguments.add(new GetTimeMillis());
    registeredArguments.add(new IsCooldownReady());
    registeredArguments.add(new FirstGapAbove());
    registeredArguments.add(new FirstGapBelow());
    registeredArguments.add(new TopBlockAt());
    registeredArguments.add(new HasPermission());
    registeredArguments.add(new GetLocationAt());
    registeredArguments.add(new GetYaw());
    registeredArguments.add(new GetPitch());
    registeredArguments.add(new GetXCoordinate());
    registeredArguments.add(new GetYCoordinate());
    registeredArguments.add(new GetZCoordinate());
    registeredArguments.add(new IsInWater());
    registeredArguments.add(new IsSneaking());
    registeredArguments.add(new GetEventProperty());
    registeredArguments.add(new IsRightClicked());
    registeredArguments.add(new GetHeldSlotNumber());
    registeredArguments.add(new AreEntities());
    registeredArguments.add(new AreLocations());
    registeredArguments.add(new ArePlayers());
    registeredArguments.add(new GetNearbyEntities());
    registeredArguments.add(new GetRandomBetween());
    registeredArguments.add(new GetItemAmountInItemSlot());
    registeredArguments.add(new GetItemInItemSlot());
    registeredArguments.add(new IsBurning());
    registeredArguments.add(new HasPotionEffect());
    registeredArguments.add(new HasBuff());
    registeredArguments.add(new IsInAir());
    registeredArguments.add(new IsInSameGuild());
    registeredArguments.add(new IsInSameParty());
    registeredArguments.add(new GetWorld());
    registeredArguments.add(new IsBehind());
    registeredArguments.add(new IsLookingAt());
    registeredArguments.add(new GetGainedExperience());
    registeredArguments.add(new GetItemname());
  }
  
  public static void AutoCast(Object obj, ParameterType pt, EffectArgs ea)
  {
    switch (pt)
    {
    case Number: 
      if ((obj instanceof Number)) {
        ea.getParams().addLast(Double.valueOf(((Number)obj).doubleValue()));
      }
      break;
    case String: 
      if ((obj instanceof String)) {
        ea.getParams().addLast(obj);
      } else if ((obj instanceof Integer)) {
        ea.getParams().addLast("" + obj);
      } else if ((obj instanceof Float)) {
        ea.getParams().addLast("" + obj);
      }
      break;
    case Entity: 
      if ((obj instanceof Entity[]))
      {
        ea.getParams().addLast(obj);
        return;
      }
      if ((obj instanceof Player[]))
      {
        Player[] arr = (Player[])obj;
        Entity[] e = new Entity[arr.length];
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] != null) {
            e[i] = arr[i];
          }
        }
        ea.getParams().addLast(e);
      }
      break;
    case Player: 
      if ((obj instanceof Player[])) {
        ea.getParams().addLast(obj);
      }
      break;
    case Material: 
      if ((obj instanceof Number))
      {
        Material m = Material.getMaterial((int)((Number)obj).doubleValue());
        ea.getParams().addLast(m);
      }
    case Boolean: 
      if ((obj instanceof Boolean)) {
        ea.getParams().addLast(obj);
      }
      break;
    case Location: 
      if ((obj instanceof Location[]))
      {
        ea.getParams().addLast(obj);
      }
      else if ((obj instanceof Entity[]))
      {
        Entity[] arr = (Entity[])obj;
        Location[] l = new Location[arr.length];
        for (int i = 0; i < arr.length; i++) {
          if (arr[i] != null) {
            l[i] = arr[i].getLocation();
          }
        }
        ea.getParams().addLast(l);
      }
      else if ((obj instanceof Player[]))
      {
        Player[] arr = (Player[])obj;
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
  
  public static IArgument getArgumentByString(String name, Spell mSpell)
  {
    name = name.trim();
    name = name.replace("(", "");
    name = name.replace(")", "");
    if (mSpell.variables.contains(name)) {
      return new VariableArgument(name);
    }
    if (mSpell.variables.contains(name)) {
      return new VariableArgument(name);
    }
    for (IArgument argument : registeredArguments) {
      if (name.equalsIgnoreCase(argument.name)) {
        return argument;
      }
    }
    try
    {
      Double.parseDouble(name);
      return new NumberArgument(name);
    }
    catch (Exception e)
    {
      if (name.equalsIgnoreCase("true")) {
        return new BooleanArgument(Boolean.valueOf(true));
      }
      if (name.equalsIgnoreCase("false")) {
        return new BooleanArgument(Boolean.valueOf(false));
      }
    }
    return new StringArgument(name);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IArgument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */