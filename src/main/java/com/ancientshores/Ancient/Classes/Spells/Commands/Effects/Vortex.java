package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Vortex extends ICommand {
	@CommandDescription(description = "<html>Creates a vortex at the given location</html>",
			argnames = {"location", "particlename", "grow per iteration", "radials per iteration", "helix circles per iterations", "helices", "period", "iterations"}, name = "Vortex", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Vortex() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 9
				&& ca.getParams().get(8) instanceof Number
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
			
			float radius = ((Number) ca.getParams().get(2)).floatValue();
			
			float growPerIteration = ((Number) ca.getParams().get(3)).floatValue();
			
			double radialsPerIteration = ((Number) ca.getParams().get(4)).doubleValue();
			
			int helixCirclesPerIteration = ((Number) ca.getParams().get(5)).intValue();
			int helices = ((Number) ca.getParams().get(6)).intValue();
			
			int period = ((Number) ca.getParams().get(7)).intValue();
			int iterations = ((Number) ca.getParams().get(8)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					VortexEffect e = new VortexEffect(man);
					
					e.particle = particle;
					
					e.radius = radius;
					
					e.grow = growPerIteration;
					
					e.radials = radialsPerIteration;
					
					e.circles = helixCirclesPerIteration;
					
					e.helixes = helices;
					
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