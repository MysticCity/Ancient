package com.ancientshores.Ancient.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;

@ParameterDescription(amount = 1, description = "<html>returns the locaiton of the first target in sight (either an entity or the block you look at)<br> Textfield 1: range of parameter</html>", returntype = "Location", name = "TargetInSight")
public class TargetInSight implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        int range = 10;
        if (subparam != null) {
            try {
                if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
                    range = ea.getSpellInfo().parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        
        Entity e = ea.getSpellInfo().getNearestEntityInSight(mPlayer, range);
        
        Location l;
        if (e != null) {
            l = e.getLocation();
        } else {
            l = ea.getSpellInfo().getBlockInSight(mPlayer, range);
        }
        switch (pt) {
            case Location:
                ea.getParams().addLast(new Location[]{l});
                break;
            default:
                Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "targetinsight";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        int range = 10;
        if (subparam != null) {
            try {
                if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
                    range = so.parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
        }
        Entity e = so.getNearestEntityInSight(mPlayer, range);
        
        Location l = null;
        if (e != null) {
            l = e.getLocation();
        }
        if (l == null) {
            l = so.getBlockInSight(mPlayer, range);
        }
        return l;
    }
}