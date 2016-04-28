package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ImageEffect;
import de.slikey.effectlib.effect.ImageEffect.Plane;
import de.slikey.effectlib.util.ParticleEffect;

public class Image extends ICommand {
	@CommandDescription(description = "<html>Creates an image at the given location</html>",
			argnames = {"location", "particlename", "file name", "invert", "show every x pixel", "show every y pixel", "size", "enable rotation", "rotation axis", "angular velocity x", "angular velocity y", "angular velocity z", "period", "iterations"}, name = "Image", parameters = {ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Image() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 14
				&& ca.getParams().get(13) instanceof Number
				&& ca.getParams().get(12) instanceof Number
				&& ca.getParams().get(11) instanceof Number
				&& ca.getParams().get(10) instanceof Number
				&& ca.getParams().get(9) instanceof Number
				&& ca.getParams().get(8) instanceof String
				&& ca.getParams().get(7) instanceof Boolean
				&& ca.getParams().get(6) instanceof Number
				&& ca.getParams().get(5) instanceof Number
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Boolean
				&& ca.getParams().get(2) instanceof String
				&& ca.getParams().get(1) instanceof String
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			ParticleEffect particle = ParticleEffect.fromName((String) ca.getParams().get(1));
			
			File file = new File(Ancient.plugin.getDataFolder().getPath() + "/images/" + (String) ca.getParams().get(2));
			
			boolean invert = (Boolean) ca.getParams().get(3);
			
			int stepX = ((Number) ca.getParams().get(4)).intValue();
			int stepY = ((Number) ca.getParams().get(5)).intValue();
			float size = ((Number) ca.getParams().get(6)).floatValue();
			
			boolean enableRotation = (Boolean) ca.getParams().get(7);
			String rotationPlane = (String) ca.getParams().get(8);
			
			double angularVelocityX = ((Number) ca.getParams().get(9)).doubleValue();
			double angularVelocityY = ((Number) ca.getParams().get(10)).doubleValue();
			double angularVelocityZ = ((Number) ca.getParams().get(11)).doubleValue();
			
			int period = ((Number) ca.getParams().get(12)).intValue();
			int iterations = ((Number) ca.getParams().get(13)).intValue();
			
			try {
				if (loc != null && loc.length > 0) {
					for (Location l : loc) {
						if (l == null)
							continue;
						
						ImageEffect e = new ImageEffect(man);
						
						e.loadFile(file);
						
						e.particle = particle;
						
						e.invert = invert;
						
						e.stepX = stepX;
						e.stepY = stepY;
						
						e.size = size;
						
						e.enableRotation = enableRotation;
						e.plane = Plane.valueOf(rotationPlane);
						
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
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
}