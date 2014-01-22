package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Level;

@ParameterDescription(amount = 2, description = "<html>returns the nearest entities of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", returntype = "Entity", name = "NearestEntities")
public class NearestEntitiesParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        int range = 10;
        int count = 1;

        if (subparam != null) {
            try {
                if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
                    range = ea.getSpellInfo().parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
            try {
                if (ea.getSpell().variables.contains(subparam[1].toLowerCase())) {
                    count = ea.getSpellInfo().parseVariable(mPlayer, subparam[1].toLowerCase());
                } else {
                    count = Integer.parseInt(subparam[1]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.getSpellInfo().nearestEntities == null || ea.getSpellInfo().nearestEntities[0] == null) {
            Entity[] nEntities = ea.getSpellInfo().getNearestEntities(mPlayer, range, count);
            ea.getSpellInfo().nearestEntities = nEntities;
            if (nEntities == null) {
                return;
            }
        }
        switch (pt) {
            case Entity:
                Entity[] e = ea.getSpellInfo().nearestEntities;
                ea.getParams().addLast(e);
                break;
            case Location:
                Location[] l = new Location[ea.getSpellInfo().nearestEntities.length];
                for (int i = 0; i < ea.getSpellInfo().nearestEntities.length; i++) {
                    if (ea.getSpellInfo().nearestEntities[i] != null) {
                        l[i] = ea.getSpellInfo().nearestEntities[i].getLocation();
                    }
                }
                ea.getParams().addLast(l);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "nearestentities";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        int range = 10;
        int count = 1;

        if (subparam != null) {
            try {
                if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
                    range = so.parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
            try {
                if (so.mSpell.variables.contains(subparam[1].toLowerCase())) {
                    count = so.parseVariable(mPlayer, subparam[1].toLowerCase());
                } else {
                    count = Integer.parseInt(subparam[1]);
                }
            } catch (Exception ignored) {
            }
        }
        if (subparam != null || so.nearestEntities == null || so.nearestEntities[0] == null) {
            Entity[] nEntities = so.getNearestEntities(mPlayer, range, count);
            so.nearestEntities = nEntities;
            if (nEntities == null) {
                return null;
            }
        }
        return so.nearestEntities;
    }
}