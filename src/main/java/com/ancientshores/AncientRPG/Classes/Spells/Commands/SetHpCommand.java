package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HP.DamageConverter;

public class SetHpCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the health of the target to the specified amount</html>",
            argnames = {"entity", "amount"}, name = "SetHp", parameters = {ParameterType.Entity, ParameterType.Number})

    public SetHpCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
                for (Entity e : (Entity[]) ca.getParams().get(0)) {
                    if (e == null) {
                        continue;
                    }
                    if (e instanceof Player && DamageConverter.isEnabled() && DamageConverter.isWorldEnabled((Player) e)) {
                        PlayerData.getPlayerData(((Player) e).getUniqueId()).getHpsystem().health = (int) ((Number) ca.getParams().get(1)).doubleValue();
                        PlayerData.getPlayerData(((Player) e).getUniqueId()).getHpsystem().setMinecraftHP();
                    } else {
                        ((LivingEntity) e).setHealth((Integer) ca.getParams().get(1));
                    }
                }
                return true;
            }
        }
        return false;
    }
}