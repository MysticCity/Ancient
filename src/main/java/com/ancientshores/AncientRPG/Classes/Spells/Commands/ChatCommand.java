	package com.ancientshores.AncientRPG.Classes.Spells.Commands;

	import com.ancientshores.AncientRPG.AncientRPG;
	import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
	import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
	import com.ancientshores.AncientRPG.HelpList;
	import org.bukkit.entity.Player;

public class ChatCommand extends ICommand
{
	@CommandDescription(description = "<html>The player says the specified message in the chat</html>",
			argnames = {"player", "message"}, name = "Chat", parameters = {ParameterType.Player, ParameterType.String})
	public ChatCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.String };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String)
			{
				final Player[] target = (Player[]) ca.params.get(0);
				final String message = (String) ca.params.get(1);
				if (target != null && target.length > 0 && target[0] instanceof Player && message != null)
				{
					AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
					{

						@Override
						public void run()
						{
							for (final Player p : target)
							{
								if (p == null)
									continue;
								p.chat(HelpList.replaceChatColor(message));
							}
						}
					});
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e)
		{
			return false;
		}
		return false;
	}
}
