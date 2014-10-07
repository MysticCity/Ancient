package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import com.ancientshores.AncientRPG.Util.SerializableLocation;

public class GuildSetSpawnCommand {
    public static void setGuildSpawnCommand(CommandSender sender) {
    	Player mPlayer = (Player) sender;
        AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (mGuild != null) {
            AncientRPGGuildRanks rank = mGuild.gMember.get(mPlayer.getUniqueId());
            if (rank == AncientRPGGuildRanks.LEADER || rank == AncientRPGGuildRanks.CO_LEADER) {
                if (AncientRPGGuild.spawnEnabled) {
                    mGuild.spawnLocation = new SerializableLocation(mPlayer.getLocation());
                    sender.sendMessage(AncientRPG.brand2 + "Successfully set the guildspawn to your current location");
                    AncientRPGGuild.writeGuild(mGuild);
                } else {
                    sender.sendMessage(AncientRPG.brand2 + "The spawn feature is not enabled on this server");
                }
            } else {
                sender.sendMessage(AncientRPG.brand2 + "You have to be atleast a coleader to use this command");
            }
        } else {
            sender.sendMessage(AncientRPG.brand2 + "You have to be in a guild to use this command");
        }
    }
}