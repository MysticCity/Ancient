package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class RemoveGroupCommand extends ICommand {
    @CommandDescription(description = "<html>Removes the permission group from the player</html>",
            argnames = {"player", "group"}, name = "RemoveGroup", parameters = {ParameterType.Player, ParameterType.String})
    public RemoveGroupCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 2 && ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String) {
            final Player[] players = (Player[]) ca.params.get(0);
            final String node = (String) ca.params.get(1);
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
