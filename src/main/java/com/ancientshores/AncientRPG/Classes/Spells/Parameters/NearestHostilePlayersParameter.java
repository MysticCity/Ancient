package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Level;

@ParameterDescription(amount = 2, description = "<html>returns the nearest hostile players of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", returntype = "Player", name = "NearestHostilePlayers")
public class NearestHostilePlayersParameter implements IParameter {
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
            if (subparam.length == 2) {
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
        }
        if (subparam != null || ea.getSpellInfo().hostilePlayers == null || ea.getSpellInfo().hostilePlayers[0] == null) {
            Player[] nEntity = ea.getSpellInfo().getNearestHostilePlayers(mPlayer, range, count);
            ea.getSpellInfo().hostilePlayers = nEntity;
            if (nEntity == null) {
                return;
            }
        }
        switch (pt) {
            case Entity:
                ea.getParams().addLast(ea.getSpellInfo().hostilePlayers);
                break;
            case Location:
                Location[] l = new Location[ea.getSpellInfo().hostilePlayers.length];
                for (int i = 0; i < ea.getSpellInfo().hostilePlayers.length; i++) {
                    if (ea.getSpellInfo().hostilePlayers[i] != null) {
                        l[i] = ea.getSpellInfo().hostilePlayers[i].getLocation();
                    }
                }
                ea.getParams().addLast(l);
                break;
            case String:
                String s = "";
                for (Player p : ea.getSpellInfo().hostilePlayers) {
                    s += p.getName() + ",";
                }
                ea.getParams().addLast(s);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "nearesthostileplayers";
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
        if (subparam != null || so.hostilePlayers == null || so.hostilePlayers[0] == null) {
            so.hostilePlayers = so.getNearestHostilePlayers(mPlayer, range, count);
        }
        return so.hostilePlayers;
    }
}