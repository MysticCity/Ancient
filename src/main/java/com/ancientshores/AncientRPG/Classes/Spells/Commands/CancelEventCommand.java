package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.event.Cancellable;

public class CancelEventCommand extends ICommand {
    @CommandDescription(description = "<html>Cancels the event, can only be used in a passive spell</html>",
            argnames = {}, name = "CancelEvent", parameters = {})
    public CancelEventCommand() {
        ParameterType[] buffer = {ParameterType.Void};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.so.mEvent instanceof Cancellable) {
            ((Cancellable) ca.so.mEvent).setCancelled(true);
        }
        return true;
    }
}
