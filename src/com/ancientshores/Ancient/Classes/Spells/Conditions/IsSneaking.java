package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class IsSneaking
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the player is sneaking, false otherwise", parameterdescription={"player"}, returntype=ParameterType.Boolean, rparams={ParameterType.Player})
  public IsSneaking()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "issneaking";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Player[])) && (((Player[])obj[0]).length > 0))
    {
      Player p = ((Player[])(Player[])obj[0])[0];
      if (p != null) {
        return Boolean.valueOf(p.isSneaking());
      }
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsSneaking.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */