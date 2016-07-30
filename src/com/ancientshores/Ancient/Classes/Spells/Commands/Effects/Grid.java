package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.GridEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Grid extends ICommand {
	@CommandDescription(description = "<html>Creates a grid at the given location</html>",
			argnames = {"location", "particlename", "rows", "columns", "width of cell", "height of cell", "particles spawned along horizontal borders of cell", "particles spawned along vertical borders of cell", "rotation", "period", "iterations"}, name = "Grid", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Grid() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 11
				&& ca.getParams().get(10) instanceof Number
				&& ca.getParams().get(9) instanceof Number
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
			
			int rows = ((Number) ca.getParams().get(2)).intValue();
			int columns = ((Number) ca.getParams().get(3)).intValue();
			
			float widthCell = ((Number) ca.getParams().get(4)).floatValue();
			float heightCell = ((Number) ca.getParams().get(5)).floatValue();
			
			int particlesWidth = ((Number) ca.getParams().get(6)).intValue();
			int particlesHeight = ((Number) ca.getParams().get(7)).intValue();
			
			double rotation = ((Number) ca.getParams().get(8)).doubleValue();
			
			int period = ((Number) ca.getParams().get(9)).intValue();
			int iterations = ((Number) ca.getParams().get(10)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					GridEffect e = new GridEffect(man);
					
					e.particle = particle;
					
					e.rows = rows;
					e.columns = columns;
					
					e.widthCell = widthCell;
					e.heightCell = heightCell;
					
					e.particlesWidth = particlesWidth;
					e.particlesHeight = particlesHeight;
					
					e.rotation = rotation;

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