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
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Number, ParameterType.Boolean};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 3) {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number && ca.params.get(2) instanceof Boolean) {
                for (Player e : (Player[]) ca.params.get(0)) {
                    if (e == null) {
                        continue;
                    }
                    if (PlayerData.getPlayerData(e.getName()).getManasystem().getCurmana() - (int) ((Number) ca.params.get(1)).doubleValue() < 0 && (Boolean) ca.params.get(2)) {
                        return false;
                    }
                    ManaSystem.removeManaByName(e.getName(), ((Number) ca.params.get(1)).intValue());
                }
                return true;
            }
        }
        return false;
    }
}
