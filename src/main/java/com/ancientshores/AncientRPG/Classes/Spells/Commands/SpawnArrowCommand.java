package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class SpawnArrowCommand extends ICommand {
    @CommandDescription(description = "<html>Spawns an arrow at the location with the velocity</html>",
            argnames = {"location", "forward", "sideward", "upward"}, name = "SpawnArrow", parameters = {ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number})
    public SpawnArrowCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 4 && ca.getParams().get(0) instanceof Location[] && ca.getParams().get(1) instanceof Number && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Number) {
            Location[] locs = (Location[]) ca.getParams().get(0);
            double forward = ((Number) ca.getParams().get(1)).doubleValue();
            double sideward = ((Number) ca.getParams().get(2)).doubleValue();
            double upward = ((Number) ca.getParams().get(3)).doubleValue();
            double x;
            double y = upward;
            double z;
            for (Location l : locs) {
                Vector v = l.getDirection();
                if (Math.abs(v.getX()) > Math.abs(v.getZ())) {
                    x = forward * v.getX();
                    z = sideward * v.getZ();
                } else {
                    x = sideward * v.getX();
                    z = forward * v.getZ();
                }
                Vector vs = new Vector(x, y, z);
                float speed = (float) vs.length();
                l.getWorld().spawnArrow(l, vs, speed, 12);
            }
        }
        return false;
    }
}