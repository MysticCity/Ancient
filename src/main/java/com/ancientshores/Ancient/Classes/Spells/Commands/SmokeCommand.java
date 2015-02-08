package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Effect;
import org.bukkit.Location;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class SmokeCommand extends ICommand {
    @CommandDescription(description = "<html>Creates smoke particles at the location</html>",
            argnames = {"location"}, name = "Smoke", parameters = {ParameterType.Location})
    public SmokeCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.getParams().get(0);
                if (loc != null && loc.length > 0 && loc[0] != null) {
                    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {

                        @Override
                        public void run() {
                            for (Location l : loc) {
                                if (l == null) {
                                    continue;
                                }
                                for (int i = 0; i < 20; i++) {
                                    l.getWorld().playEffect(l, Effect.SMOKE, 1);
                                }
                            }
                        }
                    });
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}