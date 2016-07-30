package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.IconEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Icon extends ICommand {
	@CommandDescription(description = "<html>Creates an icon at the given location</html>",
			argnames = {"location", "particlename", "y offset", "period", "iterations"}, name = "Icon", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Icon() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 5
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof String
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			ParticleEffect particle = ParticleEffect.fromName((String) ca.getParams().get(1));
			
			int yOffset = ((Number) ca.getParams().get(2)).intValue();
			
			int period = ((Number) ca.getParams().get(3)).intValue();
			int iterations = ((Number) ca.getParams().get(4)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					IconEffect e = new IconEffect(man);
					
					e.particle = particle;
					
					e.yOffset = yOffset;
					
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