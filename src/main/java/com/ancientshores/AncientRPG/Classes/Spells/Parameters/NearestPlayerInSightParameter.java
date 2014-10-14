package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;

@ParameterDescription(amount = 1, description = "<html>returns the nearest players in sight of the caster<br> Textfield: range of parameter</html>", returntype = "Player", name = "NearestPlayerInSight")
public class NearestPlayerInSightParameter implements IParameter {

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
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        if (subparam != null || ea.getSpellInfo().nearestPlayerInSight == null) {
            UUID playerInSight = ea.getSpellInfo().getNearestPlayerInSight(mPlayer, range);
            ea.getSpellInfo().nearestPlayerInSight = playerInSight;
            if (playerInSight == null) {
                return;
            }
        }
        switch (pt) {
            case Player:
                UUID[] p = {ea.getSpellInfo().nearestPlayerInSight};
                ea.getParams().addLast(p);
                break;
            case Entity:
                UUID[] e = {ea.getSpellInfo().nearestPlayerInSight};
                ea.getParams().addLast(e);
                break;
            case Location:
                Location[] l = {Bukkit.getPlayer(ea.getSpellInfo().nearestPlayerInSight).getLocation()};
                ea.getParams().addLast(l);
                break;
            case String:
                ea.getParams().addLast(PlayerFinder.getPlayerName(ea.getSpellInfo().nearestPlayerInSight));
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "nearestplayerinsight";
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
        if (subparam != null || so.nearestPlayerInSight == null) {
            so.nearestPlayerInSight = so.getNearestPlayerInSight(mPlayer, range);
        }
        return so.nearestPlayerInSight;
    }
}