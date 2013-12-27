package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;

public class PlayEntityEffect extends ICommand {
    @CommandDescription(description = "<html>Play entity effect at the location</html>",
            argnames = {"entity", "effectname"}, name = "PlayEntityEffect", parameters = {ParameterType.Entity, ParameterType.String})
    public PlayEntityEffect() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 2 && ca.params.get(1) instanceof String && ca.params.get(0) instanceof Entity[]) {
            final Entity[] e = (Entity[]) ca.params.get(0);
            final String name = (String) ca.params.get(1);
            EntityEffect effect = getEntityEffectByName(name);
            if (effect == null) {
                return false;
            }
            if (e != null && e.length > 0 && e[0] != null) {
                for (Entity ent : e) {
                    if (ent == null) {
                        continue;
                    }
                    for (int i = 0; i < 20; i++) {
                        ent.playEffect(effect);
                    }
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
