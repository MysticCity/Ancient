package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;

@ParameterDescription(amount = 2, description = "<html>returns the nearest players of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", returntype = "Player", name = "NearestPlayers")
public class NearestPlayersParameter implements IParameter {

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
            if (subparam.length == 2) {
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
        }
        if (subparam != null || ea.getSpellInfo().nearestPlayers == null || ea.getSpellInfo().nearestPlayers[0] == null) {
            UUID[] players = ea.getSpellInfo().getNearestPlayers(mPlayer, range, count);
            ea.getSpellInfo().nearestPlayers = players;
            if (players == null) {
                return;
            }
        }
        switch (pt) {
            case Player:
                ea.getParams().addLast(ea.getSpellInfo().nearestPlayers);
                break;
            case Entity:
                ea.getParams().addLast(ea.getSpellInfo().nearestPlayers);
                break;
            case Location:
                Location[] l = new Location[ea.getSpellInfo().nearestPlayers.length];
                for (int i = 0; i < ea.getSpellInfo().nearestPlayers.length; i++) {
                    if (ea.getSpellInfo().nearestPlayers[i] != null) {
                    	for (World w : Bukkit.getWorlds()) {
                    		for (Entity e : w.getEntities()) {
                    			if (e.getUniqueId().compareTo(ea.getSpellInfo().nearestPlayers[i]) != 0) {
                    				continue;
                    			}
                    			l[i] = e.getLocation();
                    		}
                    	}
                    }
                }
                break;
            case String:
                String s = "";
                for (UUID uuid : ea.getSpellInfo().nearestPlayers) {
                    s += PlayerFinder.getPlayerName(uuid) + ",";
                }
                ea.getParams().addLast(s);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "nearestplayers";
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
            if (subparam.length == 2) {
                try {
                    if (so.mSpell.variables.contains(subparam[1].toLowerCase())) {
                        count = so.parseVariable(mPlayer.getUniqueId(), subparam[1].toLowerCase());
                    } else {
                        count = Integer.parseInt(subparam[1]);
                    }
                } catch (Exception ignored) {
                }
            }
        }
        if (subparam != null || so.nearestPlayers == null || so.nearestPlayers[0] == null) {
            so.nearestPlayers = so.getNearestPlayers(mPlayer, range, count);
        }
        return so.nearestPlayers;
    }
}