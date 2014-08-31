package com.ancientshores.AncientRPG.Race.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SetRaceCommand {
    public static void setRaceCommand(Player sender, String[] command) {
        if (command.length >= 2) {
            Player p = sender;
            if (AncientRPGRace.playersOnCd.containsKey(sender.getName())) {
                long t = System.currentTimeMillis();
                long div = t - AncientRPGRace.playersOnCd.get(sender.getName());
                if (div < (AncientRPGRace.changeCd * 1000)) {
                    sender.sendMessage(AncientRPG.brand2 + "The race change cooldown hasn't expired yet");
                    long timeleft = AncientRPGRace.playersOnCd.get(sender.getName()) + (AncientRPGRace.changeCd * 1000) - System.currentTimeMillis();
                    int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
                    sender.sendMessage("You have to wait another " + minutes + " minutes.");
                    return;
                }
            }
            if (command.length == 3) {
                if (!AncientRPG.hasPermissions(p, AncientRPGRace.adminRacePermission)) {
                    sender.sendMessage(AncientRPG.brand2 + "You don't have the permission to change another persons race");
                    return;
                }
                p = Bukkit.getServer().getPlayer(command[2]);
                if (p == null) {
                    sender.sendMessage(AncientRPG.brand2 + "Player not found");
                    return;
                }
            }
            AncientRPGRace race = AncientRPGRace.getRaceByName(command[1]);
            if (race == null) {
                sender.sendMessage(AncientRPG.brand2 + "This race does not exist!");
                return;
            }
            if (race.permission != null && !race.permission.equals("") && !AncientRPG.hasPermissions(sender, race.permission)) {
                sender.sendMessage(AncientRPG.brand2 + "You don't have the permission to set your race to this race");
                return;
            }
            if (!AncientRPG.hasPermissions(sender, AncientRPGRace.setRacePermission)) {
                sender.sendMessage(AncientRPG.brand2 + "You don't have the permission to use this command");
                return;
            }
            setRace(p, race);
            p.sendMessage(AncientRPG.brand2 + "Your race is now " + race.name);
            if (p != sender) {
                sender.sendMessage(AncientRPG.brand2 + "Successfully changed the race of " + command[2]);
            }
            AncientRPGRace.playersOnCd.put(sender.getName(), System.currentTimeMillis());
            File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Races" + File.separator + "changecds.dat");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(AncientRPGRace.playersOnCd);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage(AncientRPG.brand2 + "Incorrect usage of setrace");
        }
    }

    public static void setRace(Player p, AncientRPGRace race) {
        PlayerData.getPlayerData(p.getUniqueId()).setRacename(race.name.toLowerCase());
    }
}