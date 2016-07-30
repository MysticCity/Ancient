package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.HillEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Hill extends ICommand {
	@CommandDescription(description = "<html>Creates a hill at the given location</html>",
			argnames = {"location", "particlename", "height", "particles per row", "y rotation", "period", "iterations"}, name = "Hill", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Hill() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 7
				&& ca.getParams().get(6) instanceof Number
				&& ca.getParams().get(5) instanceof Number
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof String
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			ParticleEffect particle = ParticleEffect.fromName((String) ca.getParams().get(1));
			
			float height = ((Number) ca.getParams().get(2)).floatValue();
			
			int particlesPerRow = ((Number) ca.getParams().get(3)).intValue();
			
			double yRotation = ((Number) ca.getParams().get(4)).doubleValue();
			
			int period = ((Number) ca.getParams().get(5)).intValue();
			int iterations = ((Number) ca.getParams().get(6)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					HillEffect e = new HillEffect(man);
					
					e.particle = particle;
					e.height = height;
					
					e.particles = particlesPerRow;
					
					e.yRotation = yRotation;
					
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