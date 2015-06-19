package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.FlameEffect;

public class Flame extends ICommand {
	@CommandDescription(description = "<html>Creates flames at the given location</html>",
			argnames = {"location", "period", "iterations"}, name = "Flame", parameters = {ParameterType.Location, ParameterType.Number, ParameterType.Number})
	public Flame() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 3
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof Number
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			int period = ((Number) ca.getParams().get(1)).intValue();
			
			int iterations = ((Number) ca.getParams().get(2)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					FlameEffect e = new FlameEffect(man);
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