package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.Command;

//TODO überprüfen ob entities wirklich nicht blind sein können, wenn sie es können wieder entities akzeptieren
/** 
 * @author FroznMine
 *
 * Im Gegensatz zu BlindCommand vom alten System gibt es hier keinen Input mehr für die "Stärke" des Tranks.
 * Dieser hatte sowieso keinen Effekt...
 * Desweiteren ist nun der Zeitinput in Sekunden.
 */
public class Blind extends Command {

	public Blind(int line) {
		super(	line,
				"<html>Blinds the specified player(s) for the entered amount of time.</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.NUMBER, "duration (in seconds)", false)});
	}

	@Override
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		int time = (int) args.getValues().get(1) * 20;
		
		for (Player p : players) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 1));
		}
	}

}
