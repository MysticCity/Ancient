package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import org.bukkit.entity.Entity;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.TraceEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class Trace extends ICommand {
	@CommandDescription(description = "<html>Creates a particle trace following the entity</html>",
			argnames = {"entity", "particlename", "only refresh particles every x. iteration", "maximum locations to store", "period", "iterations"}, name = "Trace", parameters = {ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public Trace() {
		this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 6
				&& ca.getParams().get(5) instanceof Number
				&& ca.getParams().get(4) instanceof Number
				&& ca.getParams().get(3) instanceof Number
				&& ca.getParams().get(2) instanceof Number
				&& ca.getParams().get(1) instanceof String
				&& ca.getParams().get(0) instanceof Entity[]) {
			
			EffectManager man = new EffectManager(Ancient.effectLib);
			
			Entity[] entities = (Entity[]) ca.getParams().get(0);
			
			ParticleEffect particle = ParticleEffect.fromName((String) ca.getParams().get(1));
			
			int refreshInterval = ((Number) ca.getParams().get(2)).intValue();
			
			int maxWayPoints = ((Number) ca.getParams().get(3)).intValue();
			
			int period = ((Number) ca.getParams().get(4)).intValue();
			int iterations = ((Number) ca.getParams().get(5)).intValue();
			
			if (entities != null && entities.length > 0) {
				for (Entity ent : entities) {
					if (ent == null)
						continue;
                                        
					TraceEffect e = new TraceEffect(man);
					
					e.particle = particle;
					
					e.refresh = refreshInterval;
					
					e.maxWayPoints = maxWayPoints;
					
					e.period = period;
					e.iterations = iterations;
                                        
                                        e.setEntity(ent); //Set entity
					
					e.start();
				}
			}
			
			man.disposeOnTermination();
			return true;
		}
		return false;
	}
}