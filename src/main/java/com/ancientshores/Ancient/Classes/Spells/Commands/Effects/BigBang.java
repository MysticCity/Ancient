package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.BigBangEffect;

public class BigBang extends ICommand {
	@CommandDescription(description = "<html>Creates a big firework explosion at the given location</html>",
			argnames = {"location", "firework type", "color 1 red", "color 1 green", "color 1 blue", "color 2 red", "color 2 green", "color 2 blue", "color 3 red", "color 3 green", "color 3 blue", "fade color red", "fade color green", "fade color blue", "intensity", "radius", "explosions", "sound interval", "sound", "sound volume", "sound pitch", "period", "iterations"}, name = "BigBang", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number,ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public BigBang() {
		this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number,ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 23
				&& ca.getParams().get(22) instanceof Number
				&& ca.getParams().get(21) instanceof Number
				&& ca.getParams().get(20) instanceof Number
				&& ca.getParams().get(19) instanceof Number
				&& ca.getParams().get(18) instanceof String
				&& ca.getParams().get(17) instanceof Number
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
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof String
				&& ca.getParams().get(0) instanceof Location[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Location[] loc = (Location[]) ca.getParams().get(0);
			
			FireworkEffect.Type fireworkType = FireworkEffect.Type.valueOf((String) ca.getParams().get(1));
			
			Color color = Color.fromRGB(((Number) ca.getParams().get(2)).intValue(), ((Number) ca.getParams().get(3)).intValue(), ((Number) ca.getParams().get(4)).intValue());
			Color color2 = Color.fromRGB(((Number) ca.getParams().get(5)).intValue(), ((Number) ca.getParams().get(6)).intValue(), ((Number) ca.getParams().get(7)).intValue());
			Color color3 = Color.fromRGB(((Number) ca.getParams().get(8)).intValue(), ((Number) ca.getParams().get(9)).intValue(), ((Number) ca.getParams().get(10)).intValue());
			Color fadeColor = Color.fromRGB(((Number) ca.getParams().get(11)).intValue(), ((Number) ca.getParams().get(12)).intValue(), ((Number) ca.getParams().get(13)).intValue());
			
			int intensity = ((Number) ca.getParams().get(14)).intValue();
			float radius = ((Number) ca.getParams().get(15)).floatValue();
			int explosions = ((Number) ca.getParams().get(16)).intValue();
			
			int soundInterval = ((Number) ca.getParams().get(17)).intValue();
			Sound sound = Sound.valueOf((String) ca.getParams().get(18));
			float soundVolume = ((Number) ca.getParams().get(19)).floatValue();
			float soundPitch = ((Number) ca.getParams().get(20)).floatValue();
			
			int period = ((Number) ca.getParams().get(21)).intValue();
			int iterations = ((Number) ca.getParams().get(22)).intValue();
			
			if (loc != null && loc.length > 0) {
				for (Location l : loc) {
					if (l == null)
						continue;
					
					BigBangEffect e = new BigBangEffect(man);
					
					e.fireworkType = fireworkType;
					e.color = color;
					e.color2 = color2;
					e.color3 = color3;
					e.fadeColor = fadeColor;
					e.intensity = intensity;
					e.radius = radius;
					e.explosions = explosions;
					e.soundInterval = soundInterval;
					e.sound = sound;
					e.soundVolume = soundVolume;
					e.soundPitch = soundPitch;
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