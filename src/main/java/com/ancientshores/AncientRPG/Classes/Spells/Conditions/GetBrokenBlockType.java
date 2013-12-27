package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.event.block.BlockBreakEvent;

public class GetBrokenBlockType extends IArgument {
    @ArgumentDescription(
            description = "Returns the id of the broken block, only usable in block break event",
            parameterdescription = {}, returntype = ParameterType.Material, rparams = {})
    public GetBrokenBlockType() {
        this.pt = ParameterType.Material;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getbrokenblocktype";
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        if (so.mEvent instanceof BlockBreakEvent) {
            return ((BlockBreakEvent) so.mEvent).getBlock().getTypeId();
        }
        return "";
    }
}
