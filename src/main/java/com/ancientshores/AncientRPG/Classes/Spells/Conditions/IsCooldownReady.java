package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.CooldownTimer;
import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class IsCooldownReady extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the cooldown with the name is ready",
            parameterdescription = {"cooldownname"}, returntype = ParameterType.Boolean, rparams = {ParameterType.String})
    public IsCooldownReady() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.String};
        this.name = "iscooldownready";
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length < 1 || !(obj[0] instanceof String)) {
            return null;
        }

        UUID uuid = Bukkit.getServer().getPlayer((String) obj[0]).getUniqueId();
        PlayerData pd = PlayerData.getPlayerData(so.buffcaster);
        for (CooldownTimer cd : pd.getCooldownTimer()) {
            if (cd.uuid.compareTo(uuid) == 0) {
            	return cd.ready;
            }
        }
        return true;
    }
}