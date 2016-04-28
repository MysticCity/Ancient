package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class SetMaxHitpointsCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the maximumhealth of the target to the specified amount</html>",
            argnames = {"player", "amount"}, name = "SetMaxHp", parameters = {ParameterType.Player, ParameterType.Number})
    public SetMaxHitpointsCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                final Player[] target = (Player[]) ca.getParams().get(0);
                final int hp = (int) ((Number) ca.getParams().get(1)).doubleValue();
                for (Player p : target) {
                    if (p == null) {
                        continue;
                    }
                    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
                    double ratio = pd.getHpsystem().health / pd.getHpsystem().maxhp;
                    pd.getHpsystem().maxhp = hp;
                    pd.getHpsystem().health = Math.round(pd.getHpsystem().maxhp * ratio);
                    pd.getHpsystem().setMinecraftHP();
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}