package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.StarEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Star extends ICommand {
	@CommandDescription(description = "<html>Creates a star at the given location</html>",
			argnames = {"location", "particlename", "particles per spike", "spike height", "half amount of spikes", "inner radius", "period", "iterations"}, name = "Star", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Star() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 8
				&& ca.getParams().get(7) instanceof Number
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
			
			int particlesPerSpike = ((Number) ca.getParams().get(2)).intValue();
			
			float spikeHeight = ((Number) ca.getParams().get(3)).floatValue();
			
			int halfSpikesAmount = ((Number) ca.getParams().get(4)).intValue();
			
			float innerRadius = ((Number) ca.getParams().get(5)).floatValue();
			
			int period = ((Number) ca.getParams().get(6)).intValue();
			int iterations = ((Number) ca.getParams().get(7)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					StarEffect e = new StarEffect(man);
					
					e.particle = particle;
					
					e.particles = particlesPerSpike;
					
					e.spikeHeight = spikeHeight;
					
					e.spikesHalf = halfSpikesAmount;
					
					e.innerRadius = innerRadius;
					
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