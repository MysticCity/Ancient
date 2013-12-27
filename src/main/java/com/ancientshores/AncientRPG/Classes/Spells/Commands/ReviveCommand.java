package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import org.bukkit.entity.Player;

public class ReviveCommand extends ICommand
{

	@CommandDescription(description = "<html>Revives a player who is still in the death screen and he is teleported back to his death location when he respawns</html>",
			argnames = {"player"}, name = "Revive", parameters = {ParameterType.Player})
	public ReviveCommand()
	{
		ParameterType[] buffer = { ParameterType.Player };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		if (ca.params.size() == 1 && ca.params.get(0) instanceof Player[])
		{
			final Player[] players = (Player[]) ca.params.get(0);
			AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
			{
				public void run()
				{
					for (Player p : players)
					{
						if(p == null || !p.isDead())
							return;
						AncientRPGSpellListener.revivePlayer.put(p, ca.caster.getLocation());
						p.sendMessage("Press the respawn button to get revived");
					}
				}
			});
			return true;
		}
		return false;
	}

}
