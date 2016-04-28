package com.ancientshores.Ancient.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;

@ParameterDescription(amount = 1, description = "<html>returns the nearest entity of the caster<br> Textfield: range of parameter</html>", returntype = "Entity", name = "NearestEntity")
public class NearestEntityParameter implements IParameter {

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
        if (subparam != null || ea.getSpellInfo().nearestEntity == null) {
            Entity nEntity = ea.getSpellInfo().getNearestEntity(p, range);
            ea.getSpellInfo().nearestEntity = nEntity;
            if (nEntity == null) {
                return;
            }
        }
        switch (pt) {
            case Entity: {
                Entity[] entities = {ea.getSpellInfo().nearestEntity};
                ea.getParams().addLast(entities);
                break;
            }
            case Location:
            	for (World w : Bukkit.getWorlds()) {
            		for (Entity e : w.getEntities()) {
            			if (e.getUniqueId().compareTo(ea.getSpellInfo().nearestEntity.getUniqueId()) != 0) {
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
        return "nearestentity";
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
        if (subparam != null || so.nearestEntity == null) {
            Entity nEntity = so.getNearestEntity(p, range);
            so.nearestEntity = nEntity;
            if (nEntity == null) {
                return null;
            }
        }
        return so.nearestEntity;
    }
}