package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.entity.Entity;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.JumpEffect;

public class Jump extends ICommand {
	@CommandDescription(description = "<html>Let the given entity jump</html>",
			argnames = {"entity", "power"}, name = "Jump", parameters = {ParameterType.Entity, ParameterType.Number})
	public Jump() {
		this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 2
				&& ca.getParams().get(1) instanceof Number
				&& ca.getParams().get(0) instanceof Entity[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Entity[] entities = (Entity[]) ca.getParams().get(0);
			
			float power = ((Number) ca.getParams().get(1)).floatValue();
			
			if (entities != null && entities.length > 0) {
				for (Entity ent : entities) {
					if (ent == null)
						continue;
					
					JumpEffect e = new JumpEffect(man);
					
					e.power = power;
					
					e.setEntity(ent);
					e.start();
				}
			}
			
			man.disposeOnTermination();
			return true;
		}
		return false;
	}
}