package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnCreatureCommand extends ICommand {
    @CommandDescription(description = "<html>Spawns creatures at the location with the given health and of the specified type</html>",
            argnames = {"location", "creaturename", "health", "amount"}, name = "SpawnCreature", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number})
    public SpawnCreatureCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Location[] && ca.params.get(1) instanceof String && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number) {
                final Location[] loca = (Location[]) ca.params.get(0);
                final String cName = (String) ca.params.get(1);
                final int health = (int) ((Number) ca.params.get(2)).doubleValue();
                final int amount = (int) ((Number) ca.params.get(3)).doubleValue();
                if (loca != null && cName != null) {
                    final EntityType e = SummonCommand.getCreatureTypeOfString(cName);
                    if (e != null) {
                        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                            public void run() {
                                for (Location loc : loca) {
                                    if (loc == null) {
                                        continue;
                                    }
                                    for (int i = 0; i < amount; i++) {
                                        final Entity mob = loc.getWorld().spawnEntity(loc, e);
                                        if (mob instanceof Creature && CreatureHp.isEnabled(mob.getWorld())) {
                                            ((Creature) mob).setMaxHealth(health);
                                            ((Creature) mob).setHealth(health);
                                        }
                                    }
                                }
                            }
                        });
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }

}
