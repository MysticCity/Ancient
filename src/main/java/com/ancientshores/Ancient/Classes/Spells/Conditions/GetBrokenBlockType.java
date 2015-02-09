package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.event.block.BlockBreakEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetBrokenBlockType extends IArgument {
    @ArgumentDescription(
            description = "Returns the id of the broken block, only usable in block break event",
            parameterdescription = {}, returntype = ParameterType.Material, rparams = {})
    public GetBrokenBlockType() {
        this.returnType = ParameterType.Material;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getbrokenblocktype";
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        if (so.mEvent instanceof BlockBreakEvent) {
            return ((BlockBreakEvent) so.mEvent).getBlock().getTypeId();
        }
        return "";
    }
}