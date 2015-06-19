package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.WaveEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Wave extends ICommand {
	@CommandDescription(description = "<html>Creates a wave at the given location</html>",
			argnames = {"location", "wave particlename", "cloud particlename", "speed x", "speed y", "speed z", "particles on the front forming the tube", "particles forming the back", "width of wave in rows", "length of front", "length of back", "depth of parabola tube", "height of parabola arc at the end", "height", "width", "period", "iterations"}, name = "Wave", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Wave() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 17
				&& ca.getParams().get(16) instanceof Number
				&& ca.getParams().get(15) instanceof Number
				&& ca.getParams().get(14) instanceof Number
				&& ca.getParams().get(13) instanceof Number
				&& ca.getParams().get(12) instanceof Number
				&& ca.getParams().get(11) instanceof Number
				&& ca.getParams().get(10) instanceof Number
				&& ca.getParams().get(9) instanceof Number
				&& ca.getParams().get(8) instanceof Number
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
			
			ParticleEffect particle = ParticleEffect.fromName((String) ca.getParams().get(1));
			ParticleEffect cloudParticle = ParticleEffect.fromName((String) ca.getParams().get(2));
			
			float speedX = ((Number) ca.getParams().get(3)).floatValue();
			float speedY = ((Number) ca.getParams().get(4)).floatValue();
			float speedZ = ((Number) ca.getParams().get(5)).floatValue();
			
			int particlesFront = ((Number) ca.getParams().get(6)).intValue();
			int particlesBack = ((Number) ca.getParams().get(7)).intValue();
			
			int rowsWidth = ((Number) ca.getParams().get(8)).intValue();
			
			float lengthFront = ((Number) ca.getParams().get(9)).floatValue();
			float lengthBack = ((Number) ca.getParams().get(10)).floatValue();
			
			float depthFront = ((Number) ca.getParams().get(11)).floatValue();
			
			float heightBack = ((Number) ca.getParams().get(12)).floatValue();
			
			float height = ((Number) ca.getParams().get(13)).floatValue();
			
			float width = ((Number) ca.getParams().get(14)).floatValue();
			
			int period = ((Number) ca.getParams().get(15)).intValue();
			int iterations = ((Number) ca.getParams().get(16)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					WaveEffect e = new WaveEffect(man);
					
					e.particle = particle;
					e.cloudParticle = cloudParticle;
					
					e.velocity = new Vector(speedX, speedY, speedZ);
					
					e.particlesFront = particlesFront;
					e.particlesBack = particlesBack;
					
					e.rows = rowsWidth;
					
					e.lengthFront = lengthFront;
					e.lengthBack = lengthBack;
					
					e.depthFront = depthFront;
					
					e.heightBack = heightBack;
					
					e.height = height;
					
					e.width = width;
					
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