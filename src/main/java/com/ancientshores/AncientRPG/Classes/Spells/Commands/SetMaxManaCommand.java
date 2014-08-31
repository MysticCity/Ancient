package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class SetMaxManaCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the maximum amount of mana the player can have</html>",
            argnames = {"player", "amount"}, name = "SetMaxMana", parameters = {ParameterType.Player, ParameterType.Number})

    public SetMaxManaCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                for (Player e : (Player[]) ca.getParams().get(0)) {
                    if (e == null) {
                        continue;
                    }
                    PlayerData.getPlayerData(e.getUniqueId()).getManasystem().maxmana = (int) ((Number) ca.getParams().get(1)).doubleValue();
                }
                return true;
            }
        }
        return false;
    }
}