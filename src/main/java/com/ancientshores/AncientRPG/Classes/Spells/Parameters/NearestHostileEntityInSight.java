package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;

@ParameterDescription(amount = 1, description = "<html>returns the nearest hostile entity in sight of the caster<br> Textfield 1: range of parameter</html>", returntype = "Entity", name = "NearestHostileEntityInSight")
public class NearestHostileEntityInSight implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        int range = 10;
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
        }
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        HashSet<Player> partyMembers = new HashSet<Player>();
        if (mParty != null) {
            partyMembers.addAll(mParty.getMembers());
        }
        Entity en = ea.getSpellInfo().getNearestEntityInSight(mPlayer, range);
        if (partyMembers.contains(en)) {
            en = null;
        }
        switch (pt) {
            case Entity:
                Entity[] e = {en};
                ea.getParams().addLast(e);
                break;
            case Location:
                if (en != null) {
                    Location[] l = {en.getLocation()};
                    ea.getParams().addLast(l);
                }
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
        }
    }

    @Override
    public String getName() {
        return "nearesthostileentityinsight";
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
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        HashSet<Player> partyMembers = new HashSet<Player>();
        if (mParty != null) {
            partyMembers.addAll(mParty.getMembers());
        }
        Entity en = so.getNearestEntityInSight(mPlayer, range);
        if (partyMembers.contains(en)) {
            en = null;
        }
        return en;
    }
}