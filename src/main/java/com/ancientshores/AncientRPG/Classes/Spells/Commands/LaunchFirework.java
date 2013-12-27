package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class LaunchFirework extends ICommand {
    @CommandDescription(description = "<html>Launches a firework at the location with the effect type and the color (must be specified in 'r, g, b')</html>",
            argnames = {"location", "effectname", "color", "flickering", "trail"}, name = "LaunchFirework", parameters = {ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Boolean})
    public LaunchFirework() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Boolean};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 5 && ca.params.get(1) instanceof String && ca.params.get(2) instanceof String && ca.params.get(3) instanceof Boolean && ca.params.get(4) instanceof Boolean
                && ca.params.get(0) instanceof Location[]) {
            final Location[] locs = (Location[]) ca.params.get(0);
            final String name = (String) ca.params.get(1);
            final String colorname = (String) ca.params.get(2);
            final boolean flickering = (Boolean) ca.params.get(3);
            final boolean trail = (Boolean) ca.params.get(4);
            Color c = getColorByString(colorname);
            FireworkEffect.Type effect = getEntityEffectByName(name);
            if (effect == null) {
                return false;
            }
            if (locs != null && locs.length > 0 && locs[0] != null) {
                for (Location l : locs) {
                    if (l == null) {
                        continue;
                    }
                    Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    Builder builder = FireworkEffect.builder();
                    builder.with(effect);
                    builder.flicker(flickering);
                    builder.trail(trail);
                    builder.withColor(c);
                    fwm.setPower(2);
                    fwm.addEffect(builder.build());
                    fw.setFireworkMeta(fwm);
                }
                return true;
            }
        }
        return false;
    }

    public static FireworkEffect.Type getEntityEffectByName(String s) {
        s = s.replace("_", "");
        for (FireworkEffect.Type e : FireworkEffect.Type.values()) {
            String ename = e.name().replace("_", "");
            if (ename.equalsIgnoreCase(s)) {
                return e;
            }
        }
        return null;
    }

    public static Color getColorByString(String s) {
        try {
            String[] args = s.split(",");
            int r = Integer.parseInt(args[0].trim());
            int g = Integer.parseInt(args[1].trim());
            int b = Integer.parseInt(args[2].trim());
            return Color.fromBGR(b, g, r);
        } catch (Exception e) {
            return null;
        }
    }
}
