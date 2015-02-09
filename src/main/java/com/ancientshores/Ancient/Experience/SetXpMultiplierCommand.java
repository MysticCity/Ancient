package com.ancientshores.Ancient.Experience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;

public class SetXpMultiplierCommand {
    private static int taskid = 0;

    public static void setXpMultiplier(CommandSender cs, String[] args) {
        if (cs instanceof Player && !cs.hasPermission(AncientExperience.nodeXPAdmin)) {
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "You don't have the permission to use this command");
            return;
        }
        if (args.length < 3) {
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "wrong number of parameters");
            return;
        }
        float multiplier = Float.parseFloat(args[1]);
        AncientExperience.multiplier = multiplier;
        int t = Integer.parseInt(args[2]);
        int time = Math.round(t * 60 * 20);
        if (taskid != 0) {
            Bukkit.getScheduler().cancelTask(taskid);
        }
        taskid = Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable() {
            @Override
            public void run() {
                AncientExperience.multiplier = 1;
                Bukkit.getServer().broadcastMessage("The XP multiplier was reset.");
                taskid = 0;
            }
        }, time);
        cs.sendMessage("Successfully set the xp multiplier to " + multiplier + " for the next " + t + " minutes");
        Bukkit.getServer().broadcastMessage("The XP multiplier was set to " + multiplier + " for the next " + t + " minutes.");
    }
}