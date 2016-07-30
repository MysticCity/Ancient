package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Cylinder extends ICommand {
	@CommandDescription(description = "<html>Creates a cylinder at the given location</html>",
			argnames = {"location", "particlename", "radius", "height", "x rotation", "y rotation", "z rotation", "particles", "solid", "enable rotation", "angular velocity x", "angular velocity y", "angular velocity z", "period", "iterations"}, name = "Cylinder", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Cylinder() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 15
				&& ca.getParams().get(14) instanceof Number
				&& ca.getParams().get(13) instanceof Number
				&& ca.getParams().get(12) instanceof Number
				&& ca.getParams().get(11) instanceof Number
				&& ca.getParams().get(10) instanceof Number
				&& ca.getParams().get(9) instanceof Boolean
				&& ca.getParams().get(8) instanceof Boolean
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
			float height = ((Number) ca.getParams().get(3)).floatValue();
			
			double xRotation = ((Number) ca.getParams().get(4)).doubleValue();
			double yRotation = ((Number) ca.getParams().get(5)).doubleValue();
			double zRotation = ((Number) ca.getParams().get(6)).doubleValue();
			
			int particles = ((Number) ca.getParams().get(7)).intValue();

			boolean solid = (Boolean) ca.getParams().get(8);
			
			boolean enableRotation = (Boolean) ca.getParams().get(9);
			double angularVelocityX = ((Number) ca.getParams().get(10)).doubleValue();
			double angularVelocityY = ((Number) ca.getParams().get(11)).doubleValue();
			double angularVelocityZ = ((Number) ca.getParams().get(12)).doubleValue();
			
			int period = ((Number) ca.getParams().get(13)).intValue();
			int iterations = ((Number) ca.getParams().get(14)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					CylinderEffect e = new CylinderEffect(man);
					
					e.particle = particle;
					
					e.radius = radius;
					e.height = height;
					
					e.rotationX = xRotation;
					e.rotationY = yRotation;
					e.rotationZ = zRotation;
					
					e.particles = particles;
					
					e.solid = solid;
					
					e.enableRotation = enableRotation;
					e.angularVelocityX = angularVelocityX;
					e.angularVelocityY = angularVelocityY;
					e.angularVelocityZ = angularVelocityZ;
					
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