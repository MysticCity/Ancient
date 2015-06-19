package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.entity.Entity;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.BleedEffect;

public class Bleed extends ICommand {
	@CommandDescription(description = "<html>Plays an bleed effect to/at the entity</html>",
			argnames = {"entity", "hurt effect visible for player", "color", "period", "iterations"}, name = "Bleed", parameters = {ParameterType.Location, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Bleed() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 5
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof Boolean
				&& ca.getParams().get(0) instanceof Entity[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Entity[] entities = (Entity[]) ca.getParams().get(0);
			
			boolean hurt = (Boolean) ca.getParams().get(1);
			int color = ((Number) ca.getParams().get(2)).intValue();
			int period = ((Number) ca.getParams().get(3)).intValue();
			int iterations = ((Number) ca.getParams().get(4)).intValue();
			
			if (entities != null && entities.length > 0) {
				for (Entity ent : entities) {
					if (ent == null)
						continue;
					
					BleedEffect e = new BleedEffect(man);
					
					e.hurt = hurt;
					e.color = color;
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