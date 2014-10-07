package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.HelpList;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class AddPrefixCommand extends ICommand {
    @CommandDescription(description = "<html>Adds a prefix to the players name if it doesn't already exist</html>",
            argnames = {"player", "prefix"}, name = "AddPrefix", parameters = {ParameterType.Player, ParameterType.String})
    public AddPrefixCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                String node = HelpList.replaceChatColor((String) ca.getParams().get(1));
                if (node != null) {
                    for (Player p : players) {
                        if (p == null) {
                            continue;
                        }
                        if (!p.getDisplayName().startsWith(node)) {
                            p.setDisplayName(node + p.getDisplayName());
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