package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

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
                for (Player p : (Player[]) ca.getParams().get(0)) {
                    if (p == null) {
                        continue;
                    }
                    PlayerData.getPlayerData(p.getUniqueId()).getManasystem().maxmana = (int) ((Number) ca.getParams().get(1)).doubleValue();
                }
                return true;
            }
        }
        return false;
    }
}