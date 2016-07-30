package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Experience.AncientExperience;

public class AddExperienceCommand extends ICommand {
    @CommandDescription(description = "<html>Adds the specific amount of experience to the player<br>Parameter 1: The player who receives the xp<br>Parameter 2: The amount of experience the player receives</html>",
            argnames = {"the player", "the number"}, name = "AddExperience", parameters = {ParameterType.Player, ParameterType.Number})
    public AddExperienceCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                Player[] players = (Player[]) ca.getParams().get(0);
                int amount = (int) ((Number) ca.getParams().get(1)).doubleValue();
                for (Player p : players) {
                    if (AncientExperience.isWorldEnabled(p.getWorld())) {
                        PlayerData.getPlayerData(p.getUniqueId()).getXpSystem().addXP(amount, false);
                    }
                }
                return true;
            }
        }
        return false;
    }
}