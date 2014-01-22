package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class RemoveManaCommand extends ICommand {

    @CommandDescription(description = "<html>Removes the amount of mana from the player's mana system</html>",
            argnames = {"player", "amount", "cancelonfail"}, name = "RemoveMana", parameters = {ParameterType.Player, ParameterType.Number, ParameterType.Boolean})
    public RemoveManaCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number, ParameterType.Boolean};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 3) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number && ca.getParams().get(2) instanceof Boolean) {
                for (Player e : (Player[]) ca.getParams().get(0)) {
                    if (e == null) {
                        continue;
                    }
                    if (PlayerData.getPlayerData(e.getName()).getManasystem().getCurmana() - (int) ((Number) ca.getParams().get(1)).doubleValue() < 0 && (Boolean) ca.getParams().get(2)) {
                        return false;
                    }
                    ManaSystem.removeManaByName(e.getName(), ((Number) ca.getParams().get(1)).intValue());
                }
                return true;
            }
        }
        return false;
    }
}