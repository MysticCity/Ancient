package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class RemovePermissionCommand extends ICommand {
    @CommandDescription(description = "<html>Removes the permission from the player</html>",
            argnames = {"player", "permission"}, name = "RemovePermission", parameters = {ParameterType.Player, ParameterType.String})
    public RemovePermissionCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final String node = (String) ca.getParams().get(1);
                if (node != null) {
                    for (Player p : players) {
                        if (p == null) {
                            continue;
                        }
                        Ancient.permissionHandler.playerRemove(p, node);
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