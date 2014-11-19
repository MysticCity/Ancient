package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Location;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class LightningEffectCommand extends ICommand {
	@CommandDescription(description = "<html>Lightning effect will strike at the location</html>",
			argnames = {"location"}, name = "LightningEffect", parameters = {ParameterType.Location})
	public LightningEffectCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Location};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		try {
			if (ca.getParams().get(0) != null && ca.getParams().get(0) instanceof Location[] && ((Location[]) ca.getParams().get(0)).length > 0 && ca.getParams().get(0) != null) {
				Location[] loc = (Location[]) ca.getParams().getFirst();
				for (Location l : loc) {
					if (l == null) continue;
					ca.getCaster().getWorld().strikeLightningEffect(l);
				}
				return true;
			} else if (ca.getSpell().active) ca.getCaster().sendMessage("No target in range");
		
		} catch (IndexOutOfBoundsException ignored) {}
		return false;
	}
}