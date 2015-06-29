package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.EarthEffect;

public class Earth extends ICommand {
	@CommandDescription(description = "<html>Creates a earth at the given location</html>",
			argnames = {"location", "precision", "particles", "radius", "mountain high", "period", "iterations"}, name = "Earth", parameters = {ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Earth() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 7
				&& ca.getParams().get(6) instanceof Number
				&& ca.getParams().get(5) instanceof Number
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof Number
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			int precision = ((Number) ca.getParams().get(1)).intValue();
			int particles = ((Number) ca.getParams().get(2)).intValue();
			float radius = ((Number) ca.getParams().get(3)).floatValue();
			float mountainHigh = ((Number) ca.getParams().get(4)).floatValue();
			
			int period = ((Number) ca.getParams().get(5)).intValue();
			int iterations = ((Number) ca.getParams().get(6)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					EarthEffect e = new EarthEffect(man);
					
					e.precision = precision;
					e.particles = particles;
					e.radius = radius;
					e.mountainHeight = mountainHigh;
					
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