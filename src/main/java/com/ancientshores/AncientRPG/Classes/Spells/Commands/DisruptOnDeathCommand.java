package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import org.bukkit.entity.Player;

public class DisruptOnDeathCommand extends ICommand {
    @CommandDescription(description = "<html> Disrupts the spell if player dies in the specified time</html>",
            argnames = {"player", "duration"}, name = "DisruptOnDeath", parameters = {ParameterType.Player, ParameterType.Number})
    public DisruptOnDeathCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
                AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        for (final Player p : players) {
                            AncientRPGSpellListener.addDisruptCommand(p, ca.getSpellInfo(), AncientRPGSpellListener.disruptOnDeath);
                            AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                                public void run() {
                                    AncientRPGSpellListener.removeDisruptCommand(p, ca.getSpellInfo(), AncientRPGSpellListener.disruptOnDeath);
                                }
                            }, Math.round(time / 50));
                        }
                    }
                });
            }
        }
        return true;
    }
}