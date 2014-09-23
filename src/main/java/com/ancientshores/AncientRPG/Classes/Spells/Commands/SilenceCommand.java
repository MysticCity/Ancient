package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Commands.ClassCastCommand;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class SilenceCommand extends ICommand {
    @CommandDescription(description = "<html>Silences the player for the specified amount of time</html>",
            argnames = {"player", "duration"}, name = "Silence", parameters = {ParameterType.Player, ParameterType.Number})

    public SilenceCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    // TODO map zu uuid
    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final Integer i = (int) ((Number) ca.getParams().get(1)).doubleValue();
                AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        for (Player p : players) {
                            if (p == null) {
                                continue;
                            }
                            ClassCastCommand.silencedPlayers.put(ca.getSpellInfo(), p.getUniqueId());
                            for (Map.Entry<SpellInformationObject, UUID> entry : AncientRPGClass.executedSpells.entrySet()) {
                                if (entry.getValue().compareTo(p.getUniqueId()) == 0) {
                                    entry.getKey().canceled = true;
                                }
                            }
                        }
                        AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                            public void run() {
                                for (Player pl : players) {
                                    if (pl == null) {
                                        continue;
                                    }
                                    ClassCastCommand.silencedPlayers.remove(ca.getSpellInfo());
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