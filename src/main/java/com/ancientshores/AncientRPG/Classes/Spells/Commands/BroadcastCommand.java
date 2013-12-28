package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HelpList;

public class BroadcastCommand extends ICommand {
    @CommandDescription(description = "<html>Broadcasts the message</html>",
            argnames = {"message"}, name = "Broadcast", parameters = {ParameterType.String})
    public BroadcastCommand() {
        ParameterType[] buffer = {ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof String) {
                final String message = (String) ca.params.get(0);
                if (message != null) {
                    AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {

                        @Override
                        public void run() {
                            AncientRPG.plugin.getServer().broadcastMessage(HelpList.replaceChatColor(message));
                        }
                    });
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}