package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Mana.ManaSystem;

public class SetManaCommand extends ICommand {

    @CommandDescription(description = "<html>Sets the amount of mana of the player</html>",
            argnames = {"player", "amount"}, name = "SetMana", parameters = {ParameterType.Player, ParameterType.Number})
    public SetManaCommand() {
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
                    ManaSystem.setManaByUUID(p.getUniqueId(), (int) ((Number) ca.getParams().get(1)).doubleValue());
                }
                return true;
            }
        }
        return false;
    }
}