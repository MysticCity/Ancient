package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.event.Cancellable;

public class CancelEventCommand extends ICommand {
    @CommandDescription(description = "<html>Cancels the event, can only be used in a passive spell</html>",
            argnames = {}, name = "CancelEvent", parameters = {})
    public CancelEventCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Void};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getSpellInfo().mEvent instanceof Cancellable) {
            ((Cancellable) ca.getSpellInfo().mEvent).setCancelled(true);
        }
        return true;
    }
}