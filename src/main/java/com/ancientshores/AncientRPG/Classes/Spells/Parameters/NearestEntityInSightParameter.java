package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;

public class NearestEntityInSightParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player p, String[] subparam, ParameterType pt) {
        int range = 10;
        if (subparam != null) {
            try {
                if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
                    range = ea.getSpellInfo().parseVariable(p, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.getSpellInfo().nearestEntityInSight == null) {
            Entity nEntity = ea.getSpellInfo().getNearestEntityInSight(p, range);
            ea.getSpellInfo().nearestEntityInSight = nEntity;
            if (nEntity == null) {
                return;
            }
        }
        switch (pt) {
            case Entity:
                Entity[] e = {ea.getSpellInfo().nearestEntityInSight};
                ea.getParams().addLast(e);
                break;
            case Location:
                Location[] l = {ea.getSpellInfo().nearestEntityInSight.getLocation()};
                ea.getParams().addLast(l);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "nearestentityinsight";
    }

    @Override
    public Object parseParameter(Player p, String[] subparam, SpellInformationObject so) {

        int range = 10;
        if (subparam != null) {
            try {
                if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
                    range = so.parseVariable(p, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
        }
        if (subparam != null || so.nearestEntityInSight == null) {
            Entity nEntity = so.getNearestEntityInSight(p, range);
            so.nearestEntityInSight = nEntity;
            if (nEntity == null) {
                return null;
            }
        }
        return so.nearestEntityInSight;
    }
}