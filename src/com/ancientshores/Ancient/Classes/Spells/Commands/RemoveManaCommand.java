package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Mana.ManaSystem;

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
                for (Player p : (Player[]) ca.getParams().get(0)) {
                    if (p == null) {
                        continue;
                    }

                    if (PlayerData.getPlayerData(p.getUniqueId()).getManasystem().getCurrentMana() - (int) ((Number) ca.getParams().get(1)).doubleValue() < 0 && (Boolean) ca.getParams().get(2)) {
                        return false;
                    }
                    ManaSystem.removeManaByUUID(p.getUniqueId(), ((Number) ca.getParams().get(1)).intValue());
                }
                return true;
            }
        }
        return false;
    }
}