package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class BroadcastCommand extends ICommand {
    @CommandDescription(description = "<html>Broadcasts the message</html>",
            argnames = {"message"}, name = "Broadcast", parameters = {ParameterType.String})
    public BroadcastCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof String) {
                final String message = (String) ca.getParams().get(0);
                if (message != null) {
                    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {

                        @Override
                        public void run() {
                            Ancient.plugin.getServer().broadcastMessage(HelpList.replaceChatColor(message));
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