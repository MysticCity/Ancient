package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Wolf;

import java.util.logging.Level;

public class SummonCommand extends ICommand {
    @CommandDescription(description = "<html>Summons the amount of creatures at the specified location for the specified amount of time</html>",
            argnames = {"location", "creaturename", "duration", "amount"}, name = "Summon", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number})
    public SummonCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Location[] && ca.params.get(1) instanceof String && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number) {
                final Location[] loca = (Location[]) ca.params.get(0);
                final String cName = (String) ca.params.get(1);
                final int time = (int) ((Number) ca.params.get(2)).doubleValue();
                final int amount = (int) ((Number) ca.params.get(3)).doubleValue();
                if (loca != null && cName != null) {
                    final EntityType e = getCreatureTypeOfString(cName);
                    if (e != null) {
                        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                            public void run() {
                                for (Location loc : loca) {
                                    if (loc == null) {
                                        continue;
                                    }
                                    for (int i = 0; i < amount; i++) {
                                        final Entity mob = ca.caster.getWorld().spawnEntity(loc, e);

                                        if (mob instanceof Wolf) {
                                            ((Wolf) mob).setOwner(ca.caster);
                                        } else if (mob instanceof Ocelot) {
                                            ((Ocelot) mob).setOwner(ca.caster);
                                        }
                                        if (time > 0) {
                                            AncientRPGPlayerListener.summonedCreatures.put(mob, ca.caster);
                                            AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                                                public void run() {
                                                    mob.remove();
                                                    AncientRPGPlayerListener.summonedCreatures.remove(mob);
                                                }
                                            }, Math.round(time / 50));
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

    public static EntityType getCreatureTypeOfString(String s) {
        if (s.equalsIgnoreCase("chicken")) {
            return EntityType.CHICKEN;
        } else if (s.equalsIgnoreCase("cow")) {
            return EntityType.COW;
        } else if (s.equalsIgnoreCase("mooshroom")) {
            return EntityType.MUSHROOM_COW;
        } else if (s.equalsIgnoreCase("ocelot")) {
            return EntityType.OCELOT;
        } else if (s.equalsIgnoreCase("pig")) {
            return EntityType.PIG;
        } else if (s.equalsIgnoreCase("sheep")) {
            return EntityType.SHEEP;
        } else if (s.equalsIgnoreCase("squid")) {
            return EntityType.SQUID;
        } else if (s.equalsIgnoreCase("villager")) {
            return EntityType.VILLAGER;
        } else if (s.equalsIgnoreCase("enderman")) {
            return EntityType.ENDERMAN;
        } else if (s.equalsIgnoreCase("wolf")) {
            return EntityType.WOLF;
        } else if (s.equalsIgnoreCase("zombie_pigman")) {
            return EntityType.PIG_ZOMBIE;
        } else if (s.equalsIgnoreCase("blaze")) {
            return EntityType.BLAZE;
        } else if (s.equalsIgnoreCase("creeper")) {
            return EntityType.CREEPER;
        } else if (s.equalsIgnoreCase("ghast")) {
            return EntityType.GHAST;
        } else if (s.equalsIgnoreCase("magma_cube")) {
            return EntityType.MAGMA_CUBE;
        } else if (s.equalsIgnoreCase("silverfish")) {
            return EntityType.SILVERFISH;
        } else if (s.equalsIgnoreCase("skeleton")) {
            return EntityType.SKELETON;
        } else if (s.equalsIgnoreCase("slime")) {
            return EntityType.SLIME;
        } else if (s.equalsIgnoreCase("spider")) {
            return EntityType.SPIDER;
        } else if (s.equalsIgnoreCase("cave_spider")) {
            return EntityType.CAVE_SPIDER;
        } else if (s.equalsIgnoreCase("zombie")) {
            return EntityType.ZOMBIE;
        } else if (s.equalsIgnoreCase("snow_golem")) {
            return EntityType.SNOWMAN;
        } else if (s.equalsIgnoreCase("iron_golem")) {
            return EntityType.IRON_GOLEM;
        } else if (s.equalsIgnoreCase("ender_dragon")) {
            return EntityType.ENDER_DRAGON;
        } else if (s.equalsIgnoreCase("witch")) {
            return EntityType.WITCH;
        } else if (s.equalsIgnoreCase("bat")) {
            return EntityType.BAT;
        } else if (s.equalsIgnoreCase("wither")) {
            return EntityType.WITHER;
        } else {
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: creature " + s + " not found");
            return null;
        }
    }

}
