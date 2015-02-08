package com.ancientshores.Ancient.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;

public class NearestEntityInSightParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player p, String[] subparam, ParameterType pt) {
        int range = 10;
        if (subparam != null) {
            try {
                if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
                    range = ea.getSpellInfo().parseVariable(p.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.getSpellInfo().nearestEntityInSight == null) {
            UUID nEntity = ea.getSpellInfo().getNearestEntityInSight(p, range);
            ea.getSpellInfo().nearestEntityInSight = nEntity;
        }
        switch (pt) {
            case Entity:
                UUID[] uuid = {ea.getSpellInfo().nearestEntityInSight};
                ea.getParams().addLast(uuid);
                break;
            case Location:
            	for (World w : Bukkit.getWorlds()) {
            		for (Entity e : w.getEntities()) {
            			if (e.getUniqueId().compareTo(ea.getSpellInfo().nearestEntityInSight) != 0) {
            				continue;
            			}
            			Location[] l = {e.getLocation()};
            			ea.getParams().addLast(l);
                	}
            	}	
            	break;
            default:
                Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
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
                    range = so.parseVariable(p.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
        }
        if (subparam != null || so.nearestEntityInSight == null) {
            UUID nEntity = so.getNearestEntityInSight(p, range);
            so.nearestEntityInSight = nEntity;
        }
        return so.nearestEntityInSight;
    }
}