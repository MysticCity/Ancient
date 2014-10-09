package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.exceptions.AncientRPGExperienceNotEnabledException;
import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.Command;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;

public class AddExperience extends Command {
	
	public AddExperience(int line) {
		super(	line,
				"<html>Adds the specific amount of experience to the player<br>1. The player(s) who receive(s) the xp<br>2. The amount of experience the player(s) receive(s)",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "players", true), new Parameter(ParameterType.NUMBER, "XP amount", false)});
	}

	@Override
	public void execute(Arguments args) throws AncientRPGExperienceNotEnabledException {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		int amount = Integer.parseInt((String) args.getValues().get(1));
		
		for (Player p : players) {
			if (!AncientRPGExperience.isWorldEnabled(p.getWorld())) throw new AncientRPGExperienceNotEnabledException();
			
			PlayerData.getPlayerData(p.getUniqueId()).getXpSystem().addXP(amount, false);
		}
	}


}
