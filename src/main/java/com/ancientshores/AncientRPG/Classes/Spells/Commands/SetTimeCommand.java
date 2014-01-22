package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;

public class SetTimeCommand extends ICommand {

    @CommandDescription(description = "<html>Sets the time of the world</html>",
            argnames = {"world", "time"}, name = "SetTime", parameters = {ParameterType.Location, ParameterType.Number})
    public SetTimeCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Location[] && ca.getParams().get(1) instanceof Number) {
                for (Location e : (Location[]) ca.getParams().get(0)) {
                    if (e == null) {
                        continue;
                    }
                    e.getWorld().setTime((long) ((Number) ca.getParams().get(1)).doubleValue());
                }
                return true;
            }
        }
        return false;
    }
}