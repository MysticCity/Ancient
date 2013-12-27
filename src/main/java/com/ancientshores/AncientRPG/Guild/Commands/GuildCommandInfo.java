package com.ancientshores.AncientRPG.Guild.Commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;

public class GuildCommandInfo
{
	public static void processInfo(CommandSender sender)
	{
		Player mPlayer = (Player) sender;
		AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(mPlayer
				.getName());
		if (guild != null)
		{
            mPlayer.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
            mPlayer.sendMessage(ChatColor.GREEN
					+ "Guild info:");
			Set<String> guildplayers = guild.gMember.keySet();
			int online = 0;
			for (String playersg : guildplayers)
			{
				Player p = AncientRPG.plugin.getServer().getPlayer(playersg);
				if(p != null && p.isOnline())
					online++;
			}
			mPlayer.sendMessage("Members in your guild: " + guild.gMember.size());
			mPlayer.sendMessage("Currently online: " + online);
			if(AncientRPGGuild.Iconomyenabled && AncientRPG.economy != null)
				mPlayer.sendMessage("Money of your guild: " + AncientRPG.economy.getBalance(guild.accountName));
			mPlayer.sendMessage("To see a list of all players online type /guild online");
			mPlayer.sendMessage("To see a list of all members type /guild memberlist");
            mPlayer.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
        } else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
		}
	}
}
