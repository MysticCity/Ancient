package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class AddGroupCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the player to the permission group with the specified name</html>",
            argnames = {"the player", "the group"}, name = "AddGroup", parameters = {ParameterType.Player, ParameterType.String})
    public AddGroupCommand() {
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
                        try {
                            AncientRPG.permissionHandler.playerAddGroup(p, node);
                        } catch (Exception ignored) {

                        }
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