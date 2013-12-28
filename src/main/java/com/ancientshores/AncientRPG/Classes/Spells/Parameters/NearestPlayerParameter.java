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

import java.util.logging.Level;

@ParameterDescription(amount = 1, description = "<html>returns the nearest player of the caster<br> Textfield: range of parameter</html>", returntype = "Player", name = "NearestPlayer")
public class NearestPlayerParameter implements IParameter {

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
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + subparam + " in command " + ea.mCommand.commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.so.nearestPlayer == null) {
            Player nPlayer = ea.so.getNearestPlayer(mPlayer, range);
            ea.so.nearestPlayer = nPlayer;
            if (nPlayer == null) {
                return;
            }
        }
        switch (pt) {
            case Player:
                Player[] p = {ea.so.nearestPlayer};
                ea.params.addLast(p);
                break;
            case Entity:
                Entity[] e = {ea.so.nearestPlayer};
                ea.params.addLast(e);
                break;
            case Location:
                Location[] l = {ea.so.nearestPlayer.getLocation()};
                ea.params.addLast(l);
                break;
            case String:
                ea.params.addLast(ea.so.nearestPlayer.getName());
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
        }
    }

    @Override
    public String getName() {
        return "nearestplayer";
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
        if (subparam != null || so.nearestPlayer == null) {
            Player nPlayer = so.getNearestPlayer(mPlayer, range);
            so.nearestPlayer = nPlayer;
        }
        return so.nearestPlayer;
    }
}