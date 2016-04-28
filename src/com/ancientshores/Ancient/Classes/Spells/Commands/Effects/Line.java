package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Line extends ICommand {
	@CommandDescription(description = "<html>Creates a line from the given location one to the other</html>",
			argnames = {"location1", "location2", "particlename", "is a zig-zag-line", "amount of zig zags", "particles (per arc)", "period", "iterations"}, name = "Line", parameters = {ParameterType.Location, ParameterType.Location, ParameterType.String, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Line() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Location, ParameterType.String, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 8
				&& ca.getParams().get(7) instanceof Number
				&& ca.getParams().get(6) instanceof Number
				&& ca.getParams().get(5) instanceof Number
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Boolean
				&& ca.getParams().get(2) instanceof String
				&& ca.getParams().get(1) instanceof Location[]
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc1 = (Location[]) ca.getParams().get(0);
			Location[] loc2 = (Location[]) ca.getParams().get(1);
			
			ParticleEffect particle = ParticleEffect.fromName((String) ca.getParams().get(2));
			
			boolean isZigZag = (Boolean) ca.getParams().get(3);
			
			int zigZags = ((Number) ca.getParams().get(4)).intValue();
			int particles = ((Number) ca.getParams().get(5)).intValue();
			
			int period = ((Number) ca.getParams().get(6)).intValue();
			int iterations = ((Number) ca.getParams().get(7)).intValue();
			
			if (loc1 != null && loc2 != null && loc1.length != 0 && loc2.length != 0) {
						
				LineEffect e = new LineEffect(man);
				
				e.particle = particle;
				
				e.isZigZag = isZigZag;
				
				e.zigZags = zigZags;
				e.particles = particles;
				
				e.period = period;
				e.iterations = iterations;
				
				e.setLocation(loc1[0]);
				e.setTarget(loc2[0]);
				e.start();
			
			}
			
			man.disposeOnTermination();
			return true;
		}
		return false;
	}
}