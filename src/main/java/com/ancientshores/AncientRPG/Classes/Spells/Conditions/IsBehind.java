package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Util.Plane;

public class IsBehind extends IArgument {
    public IsBehind() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Entity};
        this.name = "isbehind";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 2 && obj[0] instanceof Location[] && ((Location[]) obj[0]).length > 0 && obj[1] instanceof Entity[] && ((Entity[]) obj[1]).length > 0) {
            Entity e = ((Entity[]) obj[1])[0];
            Location l = ((Location[]) obj[0])[0];
            if (e != null && l != null && e instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) e;
                Vector viewdirection = e.getLocation().getDirection();
                viewdirection.setY(0);
                Plane plane = new Plane(e.getLocation().getDirection(), le.getEyeLocation());
                double angle = viewdirection.angle(le.getEyeLocation().toVector().multiply(-1));
                double distance = plane.distance(l);
                return (distance < 0 && Math.abs(angle) > Math.PI / 2) || (distance > 0 && Math.abs(angle) < Math.PI / 2);
            }
        }
        return false;
    }
}