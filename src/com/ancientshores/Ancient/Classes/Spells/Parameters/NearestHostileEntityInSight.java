package com.ancientshores.Ancient.Classes.Spells.Parameters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
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
import com.ancientshores.Ancient.Party.AncientParty;

@ParameterDescription(amount = 1, description = "<html>returns the nearest hostile entity in sight of the caster<br> Textfield 1: range of parameter</html>", returntype = "Entity", name = "NearestHostileEntityInSight")
public class NearestHostileEntityInSight implements IParameter {

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
                Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
            }
        }
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        HashSet<UUID> partyMembers = new HashSet<UUID>();
        if (mParty != null) {
            partyMembers.addAll(mParty.getMembers());
        }
        Entity playerParty = ea.getSpellInfo().getNearestEntityInSight(mPlayer, range);
        if (partyMembers.contains(playerParty)) {
            playerParty = null;
        }
        switch (pt) {
            case Entity:
                Entity[] e = {playerParty};
                ea.getParams().addLast(e);
                break;
            case Location:
                if (playerParty != null) {
                    Location[] l = {playerParty.getLocation()};
                    ea.getParams().addLast(l);
                }
                break;
            default:
                Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
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
                    range = so.parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
                } else {
                    range = Integer.parseInt(subparam[0]);
                }
            } catch (Exception ignored) {
            }
        }
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        HashSet<UUID> partyMembers = new HashSet<UUID>();
        if (mParty != null) {
            partyMembers.addAll(mParty.getMembers());
        }
        Entity en = null;
        for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e.getUniqueId().compareTo(so.getNearestEntityInSight(mPlayer, range).getUniqueId()) == 0) {
					en = e;
				}
			}
		}
        
        if (partyMembers.contains(en.getUniqueId())) {
            en = null;
        }
        return en;
    }
}