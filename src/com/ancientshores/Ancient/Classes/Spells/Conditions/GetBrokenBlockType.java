package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;

public class GetBrokenBlockType
  extends IArgument
{
  @ArgumentDescription(description="Returns the id of the broken block, only usable in block break event", parameterdescription={}, returntype=ParameterType.Material, rparams={})
  public GetBrokenBlockType()
  {
    this.returnType = ParameterType.Material;
    this.requiredTypes = new ParameterType[0];
    this.name = "getbrokenblocktype";
  }
  
  public Object getArgument(Object[] mPlayer, SpellInformationObject so)
  {
    if ((so.mEvent instanceof BlockBreakEvent)) {
      return Integer.valueOf(((BlockBreakEvent)so.mEvent).getBlock().getTypeId());
    }
    return "";
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetBrokenBlockType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */