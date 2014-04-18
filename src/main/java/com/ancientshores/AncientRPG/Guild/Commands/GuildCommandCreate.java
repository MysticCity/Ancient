package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuildCommandCreate {
    public static boolean isValidGuildname(String Name) {
        Pattern pattern = Pattern.compile("^[A-Za-z]{3,15}$");
        Matcher matcher = pattern.matcher(Name);

        return matcher.matches();
    }

    public static void processCreate(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (AncientRPG.hasPermissions(mPlayer, AncientRPGGuild.gNodeCreate)) {
            if (AncientRPGGuild.getPlayersGuild(mPlayer.getName()) == null) {
                args[0] = "";
                String name = AncientRPG.convertStringArrayToString(Arrays.copyOfRange(args, 1, args.length));
                if (!GuildCommandCreate.isValidGuildname(name)) {
                    sender.sendMessage(AncientRPG.brand2 + ChatColor.RED + "The guildname has invalid characters (only A-Z) or is too long/short");
                    return;
                }
                if (!AncientRPGGuild.guildExists(name)) {
                    if (AncientRPG.iConomyEnabled()) {
                        if (AncientRPG.economy.has(mPlayer.getName(), AncientRPGGuild.cost)) {
                            AncientRPG.economy.withdrawPlayer(mPlayer.getName(), AncientRPGGuild.cost);
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You don't have enough money to create a guild");
                            return;
                        }
                    }
                    AncientRPGGuild.guilds.add(new AncientRPGGuild(name, mPlayer.getName()));
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Succesfully created a new Guild");
                } else {
                    sender.sendMessage(AncientRPG.brand2 + ChatColor.RED + "A guild with that name already exists.");
                }
            } else {
                sender.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are already in a guild.");
            }
        } else {
            sender.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You don't have the permissions to create a guild");
        }
    }
}