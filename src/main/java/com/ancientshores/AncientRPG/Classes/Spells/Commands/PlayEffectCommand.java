package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Effect;
import org.bukkit.Location;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class PlayEffectCommand extends ICommand {
    @CommandDescription(description = "<html>Play effect at the location</html>",
            argnames = {"location", "effectname"}, name = "PlayEffect", parameters = {ParameterType.Location, ParameterType.String})
    public PlayEffectCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2 && ca.getParams().get(1) instanceof String && ca.getParams().get(0) instanceof Location[]) {
            final Location[] loc = (Location[]) ca.getParams().get(0);
            final String name = (String) ca.getParams().get(1);
            Effect effect = getParticleEffectByName(name);
            if (effect == null) {
                return false;
            }
            if (loc != null && loc.length > 0) {
                for (Location l : loc) {
                    if (l == null) {
                        continue;
                    }
                    l.getWorld().playEffect(l, effect, 0);
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