package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.entity.Entity;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.TurnEffect;

public class Turn extends ICommand {
	@CommandDescription(description = "<html>Turns the given entity</html>",
			argnames = {"entity", "angle", "period", "iterations"}, name = "Turn", parameters = {ParameterType.Entity, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Turn() {
		this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 4
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof Number
				&& ca.getParams().get(0) instanceof Entity[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Entity[] entities = (Entity[]) ca.getParams().get(0);
			
			float angle = ((Number) ca.getParams().get(1)).floatValue();
			
			int period = ((Number) ca.getParams().get(2)).intValue();
			int iterations = ((Number) ca.getParams().get(3)).intValue();
			
			if (entities != null && entities.length > 0) {
				for (Entity ent : entities) {
					if (ent == null)
						continue;
					
					TurnEffect e = new TurnEffect(man);
					
					e.step = angle;
					
					e.period = period;
					e.iterations = iterations;
					
					e.setEntity(ent);
					e.start();
				}
			}
			
			man.disposeOnTermination();
			return true;
		}
		return false;
	}
}