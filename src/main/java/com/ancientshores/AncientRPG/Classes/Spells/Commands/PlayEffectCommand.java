package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Effect;
import org.bukkit.Location;

public class PlayEffectCommand extends ICommand {
    @CommandDescription(description = "<html>Play effect at the location</html>",
            argnames = {"location", "effectname"}, name = "PlayEffect", parameters = {ParameterType.Location, ParameterType.String})
    public PlayEffectCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 2 && ca.params.get(1) instanceof String && ca.params.get(0) instanceof Location[]) {
            final Location[] loc = (Location[]) ca.params.get(0);
            final String name = (String) ca.params.get(1);
            Effect effect = getParticleEffectByName(name);
            if (effect == null) {
                return false;
            }
            if (loc != null && loc.length > 0 && loc[0] != null) {
                for (Location l : loc) {
                    if (l == null) {
                        continue;
                    }
                    for (int i = 0; i < 20; i++) {
                        l.getWorld().playEffect(l, effect, 0);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static Effect getParticleEffectByName(String s) {
        s.replace("_", "");
        for (Effect e : Effect.values()) {
            String ename = e.name().replace("_", "");
            if (ename.equalsIgnoreCase(s)) {
                return e;
            }
        }
        return null;
    }
}