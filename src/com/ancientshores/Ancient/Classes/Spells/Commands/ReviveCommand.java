package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;

public class ReviveCommand extends ICommand {

    @CommandDescription(description = "<html>Revives a player who is still in the death screen and he is teleported back to his death location when he respawns</html>",
            argnames = {"player"}, name = "Revive", parameters = {ParameterType.Player})
    public ReviveCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player};
    }

    //TODO map zu uuid
    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 1 && ca.getParams().get(0) instanceof Player[]) {
            final Player[] players = (Player[]) ca.getParams().get(0);
            Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                public void run() {
                    for (Player p : players) {
                        if (p == null || !p.isDead()) {
                            return;
                        }
                        AncientSpellListener.revivePlayer.put(p.getUniqueId(), ca.getCaster().getLocation());
                        p.sendMessage("Press the respawn button to get revived");
                    }
                }
            });
            return true;
        }
        return false;
    }
}