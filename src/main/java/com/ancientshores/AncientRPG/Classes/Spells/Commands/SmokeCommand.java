package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Effect;
import org.bukkit.Location;

public class SmokeCommand extends ICommand {
    @CommandDescription(description = "<html>Creates smoke particles at the location</html>",
            argnames = {"location"}, name = "Smoke", parameters = {ParameterType.Location})
    public SmokeCommand() {
        ParameterType[] buffer = {ParameterType.Location};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.params.get(0);
                if (loc != null && loc.length > 0 && loc[0] != null) {
                    AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {

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
