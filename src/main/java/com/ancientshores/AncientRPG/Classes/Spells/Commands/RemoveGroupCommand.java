package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class RemoveGroupCommand extends ICommand {
    @CommandDescription(description = "<html>Removes the permission group from the player</html>",
            argnames = {"player", "group"}, name = "RemoveGroup", parameters = {ParameterType.Player, ParameterType.String})
    public RemoveGroupCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2 && ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
            final Player[] players = (Player[]) ca.getParams().get(0);
            final String node = (String) ca.getParams().get(1);
            if (node != null) {
                for (Player p : players) {
                    if (p == null) {
                        continue;
                    }
                    try {
                        AncientRPG.permissionHandler.playerRemoveGroup(p, node);
                    } catch (Exception ignored) {
                    }
                }
                return true;
            }
        }
        return false;
    }
}