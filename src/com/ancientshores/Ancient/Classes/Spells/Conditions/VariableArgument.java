package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.entity.Entity;

public class VariableArgument
  extends IArgument
{
  final String name;
  
  public VariableArgument(String name)
  {
    this.name = name;
  }
  
  public Object getArgument(Object[] params, SpellInformationObject so)
  {
    if ((Variable.playerVars.containsKey(so.buffcaster)) && (((HashMap)Variable.playerVars.get(so.buffcaster)).containsKey(this.name.toLowerCase()))) {
      return ((Variable)((HashMap)Variable.playerVars.get(so.buffcaster)).get(this.name.toLowerCase())).getVariableObject();
    }
    if (so.variables.containsKey(this.name.toLowerCase())) {
      return ((Variable)so.variables.get(this.name.toLowerCase())).getVariableObject();
    }
    if (so.mSpell.variables.contains(this.name.toLowerCase())) {
      try
      {
        return Integer.valueOf(so.parseVariable(so.buffcaster.getUniqueId(), this.name.toLowerCase()));
      }
      catch (Exception ignored) {}
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\VariableArgument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */