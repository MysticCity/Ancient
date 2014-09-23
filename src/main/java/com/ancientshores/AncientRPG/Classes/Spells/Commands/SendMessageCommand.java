package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.HelpList;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class SendMessageCommand extends ICommand {
    @CommandDescription(description = "<html>Sends the message to the specified players</html>",
            argnames = {"player", "message"}, name = "SendMessage", parameters = {ParameterType.Player, ParameterType.String})
    public SendMessageCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
                final Player[] target = (Player[]) ca.getParams().get(0);
                final String message = (String) ca.getParams().get(1);
                for (Player p : target) {
                    if (p == null) {
                        continue;
                    }
                    p.sendMessage(HelpList.replaceChatColor(message));
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}