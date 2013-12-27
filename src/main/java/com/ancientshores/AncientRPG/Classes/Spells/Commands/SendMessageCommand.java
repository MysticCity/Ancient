package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HelpList;
import org.bukkit.entity.Player;

public class SendMessageCommand extends ICommand {
    @CommandDescription(description = "<html>Sends the message to the specified player</html>",
            argnames = {"player", "message"}, name = "SendMessage", parameters = {ParameterType.Player, ParameterType.String})
    public SendMessageCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String) {
                final Player[] target = (Player[]) ca.params.get(0);
                final String message = (String) ca.params.get(1);
                for (final Player p : target) {
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
