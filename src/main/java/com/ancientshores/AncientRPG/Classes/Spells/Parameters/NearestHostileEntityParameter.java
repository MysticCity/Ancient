package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;

public class NearestHostileEntityParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        int range = 10;
        int count = 1;

        if (subparam != null) {
            try {
                if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
                    range = ea.getSpellInfo().parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
            try {
                if (ea.getSpell().variables.contains(subparam[1].toLowerCase())) {
                    count = ea.getSpellInfo().parseVariable(mPlayer.getUniqueId(), subparam[1].toLowerCase());
                } else {
                    count = Integer.parseInt(subparam[1]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.getSpellInfo().hostileEntities == null || ea.getSpellInfo().hostileEntities[0] == null) {
            UUID[] nEntities = ea.getSpellInfo().getNearestHostileEntities(mPlayer, range, count);
            ea.getSpellInfo().hostileEntities = nEntities;
            if (nEntities == null) {
                return;
            }
        }
        switch (pt) {
            case Entity:
                UUID[] uuid = ea.getSpellInfo().hostileEntities;
                ea.getParams().addLast(uuid);
                break;
            case Location:
                Location[] l = new Location[ea.getSpellInfo().hostileEntities.length];
                for (int i = 0; i < ea.getSpellInfo().hostileEntities.length; i++) {
                    if (ea.getSpellInfo().hostileEntities[i] != null) {
                    	for (World w : Bukkit.getWorlds()) {
                    		for (Entity e : w.getEntities()) {
                    			if (e.getUniqueId().compareTo(ea.getSpellInfo().hostileEntities[i]) != 0) {
                    				continue;
                    			}
                    			l[i] = e.getLocation();
                    		}
                    	}
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
        return "nearesthostilenetity";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        int range = 10;
        int count = 1;

        if (subparam != null) {
            try {
                if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
                    range = so.parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
            try {
                if (so.mSpell.variables.contains(subparam[1].toLowerCase())) {
                    count = so.parseVariable(mPlayer.getUniqueId(), subparam[1].toLowerCase());
                } else {
                    count = Integer.parseInt(subparam[1]);
                }
            } catch (Exception ignored) {
            }
        }
        if (subparam != null || so.hostileEntities == null || so.hostileEntities[0] == null) {
            so.hostileEntities = so.getNearestHostileEntities(mPlayer, range, count);
        }
        return so.hostileEntities;
    }
}