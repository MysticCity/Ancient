package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class SetFoodLevelCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the foodlevel of the player</html>",
            argnames = {"player", "amount"}, name = "SetFoodLevel", parameters = {ParameterType.Player, ParameterType.Number})
    public SetFoodLevelCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 2) {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number) {
                for (Player e : (Player[]) ca.params.get(0)) {
                    if (e == null) {
                        continue;
                    }
                    e.setFoodLevel((int) ((Number) ca.params.get(1)).doubleValue());
                }
                return true;
            }
        }
        return false;
    }

}
