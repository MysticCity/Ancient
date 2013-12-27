package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class SetFlyCommand extends ICommand {

    @CommandDescription(description = "<html>Sets if the player can fly or not</html>",
            argnames = {"player", "canfly"}, name = "SetFly", parameters = {ParameterType.Player, ParameterType.Boolean})
    public SetFlyCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Boolean};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Boolean) {
                Player[] players = (Player[]) ca.params.get(0);
                final boolean on = (Boolean) ca.params.get(1);
                for (Player p : players) {
                    p.setAllowFlight(on);
                    p.setFlying(on);
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }

}
