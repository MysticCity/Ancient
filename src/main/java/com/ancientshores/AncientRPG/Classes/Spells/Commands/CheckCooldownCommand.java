package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CheckCooldownCommand extends ICommand {
    @CommandDescription(description = "<html>Checks if the cooldown with the specified name is ready, cancels if not</html>",
            argnames = {"cdname"}, name = "CheckCooldown", parameters = {ParameterType.String})

    private final HashMap<Player, Long> lastcdcheck = new HashMap<Player, Long>();

    public CheckCooldownCommand() {
        ParameterType[] buffer = {ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof String) {
                String name = (String) ca.params.get(0);
                PlayerData pd = PlayerData.getPlayerData(ca.caster.getName());
                if (pd.isCastReady(name)) {
                    return true;
                } else if (ca.p.active) {
                    if (!lastcdcheck.containsKey(ca.caster)) {
                        lastcdcheck.put(ca.caster, System.currentTimeMillis());
                    } else if (Math.abs(System.currentTimeMillis() - lastcdcheck.get(ca.caster)) > 1000) {
                        ca.caster.sendMessage("This spell is not ready");
                        ca.caster.sendMessage("Time remaining: " + ((double) pd.getRemainingTime(name) / 1000));
                        lastcdcheck.put(ca.caster, System.currentTimeMillis());
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}