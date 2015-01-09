package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.HashMap;
import java.util.UUID;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class CheckCooldownCommand extends ICommand {
	@CommandDescription(description = "<html>Checks if the cooldown with the specified name is ready, cancels if not</html>",
			argnames = {"cdname"}, name = "CheckCooldown", parameters = {ParameterType.String})

	private final HashMap<UUID, Long> lastcdcheck = new HashMap<UUID, Long>();

	public CheckCooldownCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.String};
	}


	// TODO was wird gemacht? wird ein name übergeben?
	@Override
	public boolean playCommand(EffectArgs ca) {
		try {
			if (ca.getParams().get(0) instanceof String) {
				String cooldownName = (String) ca.getParams().get(0);
				PlayerData pd = PlayerData.getPlayerData(ca.getCaster().getUniqueId());
				if (pd.isCastReady(cooldownName)) {
					return true;
				} else if (ca.getSpell().active) {
					if (!lastcdcheck.containsKey(ca.getCaster().getUniqueId())) {
						lastcdcheck.put(ca.getCaster().getUniqueId(), System.currentTimeMillis());
					} else if (Math.abs(System.currentTimeMillis() - lastcdcheck.get(ca.getCaster().getUniqueId())) > 1000) {
						ca.getCaster().sendMessage("This spell is not ready");
						ca.getCaster().sendMessage("Time remaining: " + ((double) pd.getRemainingTime(cooldownName) / 1000));
						lastcdcheck.put(ca.getCaster().getUniqueId(), System.currentTimeMillis());
					}
				}
			}
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
		return false;
	}
}