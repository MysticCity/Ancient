package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class AddExperienceCommand extends ICommand {
    @CommandDescription(description = "<html>Adds the specific amount of experience to the player<br>Parameter 1: The player who receives the xp<br>Parameter 2: The amount of experience the player receives</html>",
            argnames = {"the player", "the number"}, name = "AddExperience", parameters = {ParameterType.Player, ParameterType.Number})
    public AddExperienceCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 2) {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number) {
                Player[] players = (Player[]) ca.params.get(0);
                int amount = (int) ((Number) ca.params.get(1)).doubleValue();
                for (Player p : players) {
                    if (AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(p)) {
                        PlayerData.getPlayerData(p.getName()).getXpSystem().addXP(amount, false);
                    }
                }
                return true;
            }
        }
        return false;
    }

}
