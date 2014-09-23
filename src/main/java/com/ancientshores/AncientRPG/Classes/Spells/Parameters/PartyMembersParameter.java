package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;

@ParameterDescription(amount = 2, description = "<html>returns members of the party<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", returntype = "Player", name = "PartyMembers")
public class PartyMembersParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs effectArgs, Player mPlayer, String[] subparam, ParameterType parameterType) {
        int range = 10;

        if (subparam != null) {
            try {
                if (effectArgs.getSpell().variables.contains(subparam[0].toLowerCase())) {
                    range = effectArgs.getSpellInfo().parseVariable(mPlayer, subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception e) {
                AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + effectArgs.getCommand().commandString + " falling back to default");
            }
        }
        if (subparam != null || effectArgs.getSpellInfo().partyMembers == null) {
            Player[] nEntity = effectArgs.getSpellInfo().getPartyMembers(mPlayer, range);
            effectArgs.getSpellInfo().partyMembers = nEntity;
            if (nEntity == null) {
                return;
            }
        }

        switch (parameterType) {
            case Player:
                effectArgs.getParams().addLast(effectArgs.getSpellInfo().partyMembers);
                break;
            case Entity:
                effectArgs.getParams().addLast(effectArgs.getSpellInfo().partyMembers);
                break;
            case Location:
                Location[] l = new Location[effectArgs.getSpellInfo().partyMembers.length];
                for (int i = 0; i < effectArgs.getSpellInfo().partyMembers.length; i++) {
                    if (effectArgs.getSpellInfo().partyMembers[i] != null) {
                        l[i] = effectArgs.getSpellInfo().partyMembers[i].getLocation();
                        effectArgs.getParams().addLast(l);
                    }
                }
                break;
            case String:
                String s = "";
                for (Player p : effectArgs.getSpellInfo().partyMembers) {
                    s += p.getName() + ",";
                }
                effectArgs.getParams().addLast(s);
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + effectArgs.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "partymembers";
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
        if (subparam != null || so.partyMembers == null) {
            so.partyMembers = so.getPartyMembers(mPlayer, range);
        }
        return so.partyMembers;
    }
}