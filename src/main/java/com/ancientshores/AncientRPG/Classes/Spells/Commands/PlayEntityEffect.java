package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class PlayEntityEffect extends ICommand {
    @CommandDescription(description = "<html>Play entity effect at the location</html>",
            argnames = {"entity", "effectname"}, name = "PlayEntityEffect", parameters = {ParameterType.Entity, ParameterType.String})
    public PlayEntityEffect() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2 && ca.getParams().get(1) instanceof String && ca.getParams().get(0) instanceof Entity[]) {
            final Entity[] e = (Entity[]) ca.getParams().get(0);
            final String name = (String) ca.getParams().get(1);
            EntityEffect effect = getEntityEffectByName(name);
            if (effect == null) {
                return false;
            }
            if (e != null && e.length > 0) {
                for (Entity ent : e) {
                    if (ent == null) {
                        continue;
                    }
                    ent.playEffect(effect);
                }
                return true;
            }
        }
        return false;
    }

    public static EntityEffect getEntityEffectByName(String s) {
        s.replace("_", "");
        for (EntityEffect e : EntityEffect.values()) {
            String ename = e.name().replace("_", "");
            if (ename.equalsIgnoreCase(s)) {
                return e;
            }
        }
        return null;
    }
}