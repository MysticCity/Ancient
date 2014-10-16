package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;

//TODO überprüfen ob entities wirklich nicht blind sein können, wenn sie es können wieder entities akzeptieren
/** 
 * @author FroznMine
 *
 * Im Gegensatz zu BlindCommand vom alten System gibt es hier keinen Input mehr für die "Stärke" des Tranks.
 * Dieser hatte sowieso keinen Effekt...
 * Desweiteren ist nun der Zeitinput in Sekunden.
 */
public class Blind extends CommandParameterizable {

	public Blind(int line) {
		super(	line,
				"<html>Blinds the specified player(s) for the entered amount of time.</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.NUMBER, "duration (in seconds)", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) parameterValues[0];
		int time = Integer.parseInt((String) parameterValues[1]) * 20;
		
		for (Player p : players) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 1));
		}
		return new Object[]{line};
	}

}
