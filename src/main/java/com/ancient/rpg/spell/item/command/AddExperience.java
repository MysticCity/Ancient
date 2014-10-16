package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.exceptions.AncientRPGExperienceNotEnabledException;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;

public class AddExperience extends CommandParameterizable {
	
	public AddExperience(int line) {
		super(	line,
				"<html>Adds the specific amount of experience to the player<br>1. The player(s) who receive(s) the xp<br>2. The amount of experience the player(s) receive(s)",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "players", true), new Parameter(ParameterType.NUMBER, "XP amount", false)});
	}

	@Override
	public Object[] execute() throws AncientRPGExperienceNotEnabledException {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) parameterValues[0];
		int amount = Integer.parseInt((String) parameterValues[1]);
		
		for (Player p : players) {
			if (!AncientRPGExperience.isWorldEnabled(p.getWorld())) throw new AncientRPGExperienceNotEnabledException();
			
			PlayerData.getPlayerData(p.getUniqueId()).getXpSystem().addXP(amount, false);
		}
		return new Object[]{line}; // IMPORTANT every spellitem returns the next line to execute in the spell
	}
}
