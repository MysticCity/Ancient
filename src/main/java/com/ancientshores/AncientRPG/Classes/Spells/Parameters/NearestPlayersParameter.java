package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

@ParameterDescription(amount = 2, description = "<html>returns the nearest players of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", returntype = "Player", name = "NearestPlayers")
public class NearestPlayersParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        int range = 10;
        int count = 1;
        if (subparam != null) {
            try {
                if (ea.p.variables.contains(subparam[0].toLowerCase())) {
                    range = ea.so.parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + subparam + " in command " + ea.mCommand.commandString + " falling back to default");
            }
            if (subparam.length == 2) {
                try {
                    if (ea.p.variables.contains(subparam[1].toLowerCase())) {
                        count = ea.so.parseVariable(mPlayer, subparam[1].toLowerCase());
                    } else {
                        count = Integer.parseInt(subparam[1]);
                    }
                } catch (Exception e) {
                    AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + subparam + " in command " + ea.mCommand.commandString + " falling back to default");
                }
            }
        }
        if (subparam != null || ea.so.nearestPlayers == null || ea.so.nearestPlayers[0] == null) {
            Player[] nEntity = ea.so.getNearestPlayers(mPlayer, range, count);
            ea.so.nearestPlayers = nEntity;
            if (nEntity == null) {
                return;
            }
        }
        switch (pt) {
            case Player:
                ea.params.addLast(ea.so.nearestPlayers);
                break;
            case Entity:
                ea.params.addLast(ea.so.nearestPlayers);
                break;
            case Location:
                Location[] l = new Location[ea.so.nearestPlayers.length];
                for (int i = 0; i < ea.so.nearestPlayers.length; i++) {
                    if (ea.so.nearestPlayers[i] != null) {
                        l[i] = ea.so.nearestPlayers[i].getLocation();
                    }
                }
                break;
            case String:
                String s = "";
                for (Player p : ea.so.nearestPlayers) {
                    s += p.getName() + ",";
                }
                ea.params.addLast(s);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
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
                    range = so.parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
            if (subparam.length == 2) {
                try {
                    if (so.mSpell.variables.contains(subparam[1].toLowerCase())) {
                        count = so.parseVariable(mPlayer, subparam[1].toLowerCase());
                    } else {
                        count = Integer.parseInt(subparam[1]);
                    }
                } catch (Exception ignored) {
                }
            }
        }
        if (subparam != null || so.nearestPlayers == null || so.nearestPlayers[0] == null) {
            Player[] nEntity = so.getNearestPlayers(mPlayer, range, count);
            so.nearestPlayers = nEntity;
        }
        return so.nearestPlayers;
    }

}
