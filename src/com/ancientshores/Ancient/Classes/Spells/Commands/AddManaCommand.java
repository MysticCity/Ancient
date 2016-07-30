package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Mana.ManaSystem;

public class AddManaCommand extends ICommand {
    @CommandDescription(description = "<html>Adds the specified amount of mana to the players mana system</html>",
            argnames = {"the player", "amount"}, name = "AddMana", parameters = {ParameterType.Player, ParameterType.Number})

    public AddManaCommand() {
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
                    ManaSystem.addManaByUUID(p.getUniqueId(), (int) ((Number) ca.getParams().get(1)).doubleValue());
                }
                return true;
            }
        }
        return false;
    }
}