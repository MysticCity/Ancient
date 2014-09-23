package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;

public class InvisibleCommand extends ICommand {
	@CommandDescription(description = "<html>Makes the player invisible for the specified amount of time</html>",
			argnames = {"player", "duration"}, name = "Invisible", parameters = {ParameterType.Player, ParameterType.Number})
	public static final HashSet<UUID> invisiblePlayers = new HashSet<UUID>();

	public InvisibleCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		try {
			if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
				final Player[] target = (Player[]) ca.getParams().get(0);
				final int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
				AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {

					@Override
					public void run() {
						int t = Math.round(time / 50);
						if (t == 0) {
							t = Integer.MAX_VALUE;
						}
						for (final Player invisP : target) {
							if (invisP == null) {
								continue;
							}
							for (Player p : AncientRPG.plugin.getServer().getOnlinePlayers()) {
								p.hidePlayer(invisP);
							}
							invisiblePlayers.add(invisP.getUniqueId());
							final int id = AncientRPGPlayerListener.addInvis(invisP);
							AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
								@Override
								public void run() {
									if (!AncientRPGPlayerListener.removeInvis(invisP, id)) {
										return;
									}
									for (Player p : AncientRPG.plugin.getServer().getOnlinePlayers()) {
										p.showPlayer(invisP);
										invisiblePlayers.remove(invisP.getUniqueId());
									}
								}
							}, t);
						}
					}
				});
				return true;
			}
		} catch (IndexOutOfBoundsException ignored) {
		}
		return false;
	}
}