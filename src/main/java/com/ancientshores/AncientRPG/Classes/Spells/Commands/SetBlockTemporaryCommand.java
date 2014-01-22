package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

public class SetBlockTemporaryCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the id of the block, sets it back after the time</html>",
            argnames = {"location", "material", "duration"}, name = "SetBlockTemporary", parameters = {ParameterType.Location, ParameterType.Material, ParameterType.Number})
    private static final HashMap<Location, Integer> changedBlocks = new HashMap<Location, Integer>();
    public static int tasks;

    public SetBlockTemporaryCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Material, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Location[] && ca.getParams().get(1) instanceof Material && ca.getParams().get(2) instanceof Number) {
                final Location loc[] = (Location[]) ca.getParams().get(0);
                final Material ma = (Material) ca.getParams().get(1);
                final int time = ((Number) ca.getParams().get(2)).intValue();
                final int task = tasks;
                tasks++;
                if (loc != null && ma != null) {
                    if (time > 0) {
                        for (final Location l : loc) {
                            if (l == null) {
                                continue;
                            }
                            final Material m = l.getBlock().getType();
                            final byte data = l.getBlock().getData();
                            final MaterialData md = l.getBlock().getState().getData().clone();
                            l.getBlock().setType(ma);
                            AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (changedBlocks.get(l) == task && ma == l.getBlock().getType()) {
                                        changedBlocks.remove(l);
                                        l.getBlock().setType(m);
                                        l.getBlock().setData(data);
                                        l.getBlock().getState().setData(md);
                                    }
                                }
                            }, Math.round(time / 50));
                            changedBlocks.put(l, task);
                        }
                    }
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}