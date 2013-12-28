package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class NearestHostilePlayerParameter implements IParameter {

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
        if (subparam != null || ea.so.hostilePlayers == null || ea.so.hostilePlayers[0] == null) {
            Player[] nEntity = ea.so.getNearestHostilePlayers(mPlayer, range, 3);
            ea.so.hostilePlayers = nEntity;
            if (nEntity == null) {
                return;
            }
        }
        switch (pt) {
            case Entity:
                ea.params.addLast(ea.so.hostilePlayers);
                break;
            case Location:
                Location[] l = new Location[ea.so.hostilePlayers.length];
                for (int i = 0; i < ea.so.hostilePlayers.length; i++) {
                    if (ea.so.hostilePlayers[i] != null) {
                        l[i] = ea.so.hostilePlayers[i].getLocation();
                    }
                }
                ea.params.addLast(l);
                break;
            case String:
                String s = "";
                for (Player p : ea.so.hostilePlayers) {
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
        return "nearesthostileplayer";
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
        if (subparam != null || so.hostilePlayers == null || so.hostilePlayers[0] == null) {
            Player[] nEntity = so.getNearestHostilePlayers(mPlayer, range, 3);
            so.hostilePlayers = nEntity;
        }
        return so.hostilePlayers;
    }
}