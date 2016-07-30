package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;

public class GetShooterOrAttacker extends IArgument {
    @ArgumentDescription(
            description = "Returns the damager, for example if a player hits you with an arrow the player is returned.",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetShooterOrAttacker() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getshooterorattacker";
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) so.mEvent;
            if (damageEvent.getDamager() instanceof Projectile) {
                return GlobalMethods.getStringByEntity((Entity) ((Projectile) damageEvent.getDamager()).getShooter());
            } else {
                return GlobalMethods.getStringByEntity(damageEvent.getDamager());
            }
        }
        return "";
    }
}