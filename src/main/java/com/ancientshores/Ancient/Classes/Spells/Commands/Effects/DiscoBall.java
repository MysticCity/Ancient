package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.DiscoBallEffect;
import de.slikey.effectlib.effect.DiscoBallEffect.Direction;
import de.slikey.effectlib.util.ParticleEffect;

public class DiscoBall extends ICommand {
	@CommandDescription(description = "<html>Creates a disco ball at the given location</html>",
			argnames = {"location", "sphere particlename", "line particlename", "particles sphere", "particles per line", "sphere radius", "max line size", "max lines", "direction", "period", "iterations"}, name = "DiscoBall", parameters = {ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Number})
	public DiscoBall() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 11
				&& ca.getParams().get(10) instanceof Number
				&& ca.getParams().get(9) instanceof Number
				&& ca.getParams().get(8) instanceof String
				&& ca.getParams().get(7) instanceof Number
				&& ca.getParams().get(6) instanceof Number
				&& ca.getParams().get(5) instanceof Number
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof String
				&& ca.getParams().get(1) instanceof String
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			ParticleEffect sphereParticle = ParticleEffect.fromName((String) ca.getParams().get(1));
			ParticleEffect lineParticle = ParticleEffect.fromName((String) ca.getParams().get(2));

			int sphereParticles = ((Number) ca.getParams().get(3)).intValue();
			int lineParticles = ((Number) ca.getParams().get(4)).intValue();
			
			float sphereRadius = ((Number) ca.getParams().get(5)).floatValue();
			int maxLineSize = ((Number) ca.getParams().get(6)).intValue();
			int maxLines = ((Number) ca.getParams().get(7)).intValue();
			
			Direction direction = Direction.valueOf((String) ca.getParams().get(8));
			
			int period = ((Number) ca.getParams().get(9)).intValue();
			int iterations = ((Number) ca.getParams().get(10)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					DiscoBallEffect e = new DiscoBallEffect(man);
					
					e.sphereParticle = sphereParticle;
					e.lineParticle = lineParticle;
					
					e.sphereParticles = sphereParticles;
					e.lineParticles = lineParticles;
					
					e.sphereRadius = sphereRadius;
					e.max = maxLineSize;
					e.maxLines = maxLines;
					
					e.direction = direction;
					
					e.period = period;
					e.iterations = iterations;
					
					e.setLocation(l);
					e.start();
				}
			}
			
			man.disposeOnTermination();
			return true;
		}
		return false;
	}
}