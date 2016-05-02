package com.ancientshores.Ancient.Guild.Commands;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;

public class GuildCommandCreate {
    public static boolean isValidGuildname(String Name) {
        Pattern pattern = Pattern.compile("^[A-Za-z]{3,15}$");
        Matcher matcher = pattern.matcher(Name);

        return matcher.matches();
    }

    public static void processCreate(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (mPlayer.hasPermission(AncientGuild.gNodeCreate)) {
            if (AncientGuild.getPlayersGuild(mPlayer.getUniqueId()) == null) {
                args[0] = "";
                String name = Ancient.convertStringArrayToString(Arrays.copyOfRange(args, 1, args.length));
                if (!GuildCommandCreate.isValidGuildname(name)) {
                    sender.sendMessage(Ancient.ChatBrand + ChatColor.RED + "The guildname has invalid characters (only A-Z) or is too long/short");
                    return;
                }
                if (!AncientGuild.guildExists(name)) {
                    if (Ancient.iConomyEnabled()) {
                        if (Ancient.economy.has(mPlayer.getName(), AncientGuild.cost)) {
                            Ancient.economy.withdrawPlayer(mPlayer.getName(), AncientGuild.cost);
                        } else {
                            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "You don't have enough money to create a guild");
                            return;
                        }
                    }
                    AncientGuild.guilds.add(new AncientGuild(name, mPlayer.getUniqueId()));
                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.GREEN + "Succesfully created a new Guild");
                } else {
                    sender.sendMessage(Ancient.ChatBrand + ChatColor.RED + "A guild with that name already exists.");
                }
            } else {
                sender.sendMessage(Ancient.ChatBrand + ChatColor.RED + "You are already in a guild.");
            }
        } else {
            sender.sendMessage(Ancient.ChatBrand + ChatColor.RED + "You don't have the permissions to create a guild");
        }
    }
}