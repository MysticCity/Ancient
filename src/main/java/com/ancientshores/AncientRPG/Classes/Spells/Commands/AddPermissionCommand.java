package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class AddPermissionCommand extends ICommand {
    @CommandDescription(description = "<html>Adds the specified permission to the player</html>",
            argnames = {"player", "permission"}, name = "AddPermission", parameters = {ParameterType.Player, ParameterType.String})
    public AddPermissionCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String) {
                final Player[] players = (Player[]) ca.params.get(0);
                final String node = (String) ca.params.get(1);
                if (node != null) {
                    for (Player p : players) {
                        if (p == null) {
                            continue;
                        }
                        AncientRPG.permissionHandler.playerAdd(p, node);
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}
