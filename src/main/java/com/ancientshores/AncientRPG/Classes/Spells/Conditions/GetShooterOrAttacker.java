package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Util.GlobalMethods;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GetShooterOrAttacker extends IArgument {
    @ArgumentDescription(
            description = "Returns the damager, for example if a player hits you with an arrow the player is returned.",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetShooterOrAttacker() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getshooterorattacker";
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) so.mEvent;
            if (damageEvent.getDamager() instanceof Projectile) {
                return GlobalMethods.getStringByEntity(((Projectile) damageEvent.getDamager()).getShooter());
            } else {
                return GlobalMethods.getStringByEntity(damageEvent.getDamager());
            }
        }
        return "";
    }
}