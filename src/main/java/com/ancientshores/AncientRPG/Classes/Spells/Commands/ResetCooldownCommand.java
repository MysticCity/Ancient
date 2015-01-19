package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.HashSet;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.CooldownTimer;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class ResetCooldownCommand extends ICommand {
	@CommandDescription(description = "<html>Resets the cooldown with the specified name or all for all cooldowns</html>",
			argnames = {"player", "cdname"}, name = "ResetCooldown", parameters = {ParameterType.Player, ParameterType.String})
	public ResetCooldownCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 2 && ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
			final Player[] players = (Player[]) ca.getParams().get(0);
			final String cdname = (String) ca.getParams().get(1);
			for (Player p : players) {
				PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
				try {
					if (cdname.equalsIgnoreCase("all")) {
						pd.setCooldownTimer(new HashSet<CooldownTimer>());
					} else {
						HashSet<CooldownTimer> removetimer = new HashSet<CooldownTimer>();
						for (CooldownTimer cd : pd.getCooldownTimer())
							if (cd.cooldownname.equals(cdname))
								removetimer.add(cd);
						pd.getCooldownTimer().removeAll(removetimer);
					}
				} catch (Exception ignored) {
				}
			}
			return true;
		}
		return false;
	}
}