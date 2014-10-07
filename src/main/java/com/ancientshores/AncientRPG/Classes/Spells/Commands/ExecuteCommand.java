package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;

public class ExecuteCommand extends ICommand {

    @CommandDescription(description = "<html>Executes the spell</html>",
            argnames = {"player", "spellname"}, name = "Execute", parameters = {ParameterType.Player, ParameterType.String})
    public ExecuteCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
                final Player[] pl = (Player[]) ca.getParams().get(0);
                final String spellName = (String) ca.getParams().get(1);
                AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        for (Player p : pl) {
                            Spell s = AncientRPGClass.getSpell(spellName, PlayerData.getPlayerData(p.getUniqueId()));
                            CommandPlayer.scheduleSpell(s, p.getUniqueId());
                        }
                    }
                });
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}