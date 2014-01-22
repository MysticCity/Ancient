package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.SmallFireball;

public class ThrowSmallFireballCommand extends ICommand {
    @CommandDescription(description = "The caster throws a asmall fireball",
            argnames = {}, name = "ThrowSmallFireball", parameters = {})
    public ThrowSmallFireballCommand() {
        this.paramTypes = new ParameterType[]{};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
            public void run() {
                Location loc = ca.getCaster().getLocation().add(ca.getCaster().getLocation().getDirection().normalize().multiply(2));
                final SmallFireball f = ca.getCaster().getWorld().spawn(loc.add(new Location(ca.getCaster().getWorld(), 0, 1, 0)), SmallFireball.class);
                f.setShooter(ca.getCaster());
                f.setVelocity(ca.getCaster().getLocation().getDirection().normalize().multiply(3));
                Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        f.remove();
                    }
                }, 250);
            }
        });
        return true;
    }
}