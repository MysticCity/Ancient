package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;

public class DisruptOnMoveCommand extends ICommand {
    @CommandDescription(description = "<html> Disrupts the spell if player moves in the specified time</html>",
            argnames = {"player", "duration"}, name = "DisruptOnMove", parameters = {ParameterType.Player, ParameterType.Number})
    public DisruptOnMoveCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
                Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                    public void run() {
                        for (final Player p : players) {
                            AncientSpellListener.addDisruptCommand(p, ca.getSpellInfo(), AncientSpellListener.disruptOnMove);
                            Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                                public void run() {
                                    AncientSpellListener.removeDisruptCommand(p, ca.getSpellInfo(), AncientSpellListener.disruptOnMove);
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