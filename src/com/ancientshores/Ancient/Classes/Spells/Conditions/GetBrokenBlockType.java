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
