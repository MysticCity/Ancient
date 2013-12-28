package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;

public class SetWeatherCommand extends ICommand {
    @CommandDescription(description = "<html>Changes the weather in the world for atleast the specified time</html>",
            argnames = {"world", "duration", "raining", "thundering"}, name = "SetWeather", parameters = {ParameterType.Location, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean})
    public SetWeatherCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 4 && ca.params.get(0) instanceof Location[] && ca.params.get(1) instanceof Number && ca.params.get(2) instanceof Boolean && ca.params.get(3) instanceof Boolean) {
            Location[] locs = (Location[]) ca.params.get(0);
            boolean raining = (Boolean) ca.params.get(2);
            boolean thundering = (Boolean) ca.params.get(3);
            int time = (int) ((Number) ca.params.get(1)).doubleValue();
            for (Location l : locs) {
                if (l == null) {
                    continue;
                }
                int t = Math.round(time / 50);
                if (t == 0) {
                    t = Integer.MAX_VALUE;
                }
                l.getWorld().setWeatherDuration(t);
                l.getWorld().setThunderDuration(t);
                l.getWorld().setThundering(thundering);
                l.getWorld().setStorm(raining);
                l.getWorld().setWeatherDuration(t);
                l.getWorld().setThunderDuration(t);
            }
            return true;
        }
        return false;
    }
}