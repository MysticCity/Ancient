package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class GetMaxHealth extends IArgument {
    @ArgumentDescription(
            description = "Returns the maximum health of the given entity",
            parameterdescription = {"entity"}, returntype = ParameterType.Number, rparams = {ParameterType.Entity})
    public GetMaxHealth() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "getmaxhealth";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Entity[])) {
            return 0;
        }
        LivingEntity e = (LivingEntity) ((Entity[]) obj[0])[0];
        if (e instanceof Player) {
            Player mPlayer = (Player) e;
            PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
            if (DamageConverter.isEnabled() && DamageConverter.isWorldEnabled(mPlayer)) {
                return pd.getHpsystem().maxhp;
            }
        } else if (e instanceof Creature && CreatureHp.isEnabled(e.getWorld())) {
            return e.getMaxHealth();
        }
        return e.getMaxHealth();
    }
}