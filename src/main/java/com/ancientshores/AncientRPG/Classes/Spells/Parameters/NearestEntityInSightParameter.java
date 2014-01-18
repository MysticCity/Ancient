package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Level;

public class NearestEntityInSightParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        int range = 10;
        if (subparam != null) {
            try {
                if (ea.p.variables.contains(subparam[0].toLowerCase())) {
                    range = ea.so.parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.mCommand.commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.so.nearestEntityInSight == null) {
            Entity nEntity = ea.so.getNearestEntityInSight(mPlayer, range);
            ea.so.nearestEntityInSight = nEntity;
            if (nEntity == null) {
                return;
            }
        }
        switch (pt) {
            case Entity:
                Entity[] e = {ea.so.nearestEntityInSight};
                ea.params.addLast(e);
                break;
            case Location:
                Location[] l = {ea.so.nearestEntityInSight.getLocation()};
                ea.params.addLast(l);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
        }
    }

    @Override
    public String getName() {
        return "nearestentityinsight";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {

        int range = 10;
        if (subparam != null) {
            try {
                if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
                    range = so.parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
        }
        if (subparam != null || so.nearestEntityInSight == null) {
            Entity nEntity = so.getNearestEntityInSight(mPlayer, range);
            so.nearestEntityInSight = nEntity;
            if (nEntity == null) {
                return null;
            }
        }
        return so.nearestEntityInSight;
    }
}