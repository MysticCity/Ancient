package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.UUID;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class CooldownCommand extends ICommand {

    @CommandDescription(description = "<html>Triggers the cooldown with the specified name for the given amount of time</html>",
            argnames = {"cooldownname", "duration"}, name = "Cooldown", parameters = {ParameterType.UUID, ParameterType.Number})
    public CooldownCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.UUID, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof String && ca.getParams().get(1) instanceof Number) {
                UUID uuid = (UUID) ca.getParams().get(0);
                int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
                PlayerData pd = PlayerData.getPlayerData(ca.getCaster().getUniqueId());

                pd.addTimer(uuid, time);
                pd.startTimer(uuid);

                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}