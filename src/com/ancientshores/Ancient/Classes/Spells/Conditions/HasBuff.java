package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

public class HasBuff
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the player has the specified buff activated, false otherwise", parameterdescription={"player", "buffname"}, returntype=ParameterType.Boolean, rparams={ParameterType.Player, ParameterType.String})
  public HasBuff()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
    this.name = "hasbuff";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length != 2) || (!(obj[0] instanceof Player[])) || (!(obj[1] instanceof String))) {
      return Boolean.valueOf(false);
    }
    Player p = ((Player[])(Player[])obj[0])[0];
    String s = (String)obj[1];
    if (p != null) {
      for (Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : AncientSpellListener.getAllBuffs().entrySet()) {
        if ((((Spell)e.getKey()).name != null) && (((Spell)e.getKey()).name.equalsIgnoreCase(s))) {
          for (Map.Entry<UUID[], Integer> entry : ((ConcurrentHashMap)e.getValue()).entrySet()) {
            if ((((UUID[])entry.getKey()).length == 2) && (((UUID[])entry.getKey())[0].compareTo(p.getUniqueId()) == 0)) {
              return Boolean.valueOf(true);
            }
          }
        }
      }
    }
    return Boolean.valueOf(false);
  }
}
