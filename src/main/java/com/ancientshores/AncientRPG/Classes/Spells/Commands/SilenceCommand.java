package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Commands.ClassCastCommand;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

import java.util.Map;

public class SilenceCommand extends ICommand {
    @CommandDescription(description = "<html>Silences the player for the specified amount of time</html>",
            argnames = {"player", "duration"}, name = "Silence", parameters = {ParameterType.Player, ParameterType.Number})

    public SilenceCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 2) {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number) {
                final Player[] p = (Player[]) ca.params.get(0);
                final Integer i = (int) ((Number) ca.params.get(1)).doubleValue();
                AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        for (Player pl : p) {
                            if (pl == null) {
                                continue;
                            }
                            ClassCastCommand.silencedPlayers.put(ca.so, pl);
                            for (Map.Entry<SpellInformationObject, Player> entry : AncientRPGClass.executedSpells.entrySet()) {
                                if (entry.getValue() == pl) {
                                    entry.getKey().canceled = true;
                                }
                            }
                        }
                        AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                            public void run() {
                                for (Player pl : p) {
                                    if (pl == null) {
                                        continue;
                                    }
                                    ClassCastCommand.silencedPlayers.remove(ca.so);
                                }
                            }
                        }, Math.round(i / 50));
                    }
                });
                return true;
            }
        }
        return false;
    }
}