package com.ancientshores.Ancient.Race.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Race.AncientRace;

public class SetRaceCommand {
    @SuppressWarnings("deprecation")
	public static void setRaceCommand(Player sender, String[] command) {
        if (command.length >= 2) {
            Player p = sender;
            if (AncientRace.playersOnCd.containsKey(p.getUniqueId())) {
                long t = System.currentTimeMillis();
                long div = t - AncientRace.playersOnCd.get(p.getUniqueId());
                if (div < (AncientRace.changeCd * 1000)) {
                    sender.sendMessage(Ancient.brand2 + "The race change cooldown hasn't expired yet");
                    long timeleft = AncientRace.playersOnCd.get(p.getUniqueId()) + (AncientRace.changeCd * 1000) - System.currentTimeMillis();
                    int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
                    sender.sendMessage("You have to wait another " + minutes + " minutes.");
                    return;
                }
            }
            if (command.length == 3) {
                if (!p.hasPermission(AncientRace.adminRacePermission)) {
                    sender.sendMessage(Ancient.brand2 + "You don't have the permission to change another persons race");
                    return;
                }
                p = Bukkit.getServer().getPlayer(command[2]);
                if (p == null) {
                    sender.sendMessage(Ancient.brand2 + "Player not found");
                    return;
                }
            }
            AncientRace race = AncientRace.getRaceByName(command[1]);
            if (race == null) {
                sender.sendMessage(Ancient.brand2 + "This race does not exist!");
                return;
            }
            if (race.permission != null && !race.permission.equals("") && !sender.hasPermission(race.permission)) {
                sender.sendMessage(Ancient.brand2 + "You don't have the permission to set your race to this race");
                return;
            }
            if (!sender.hasPermission(AncientRace.setRacePermission)) {
                sender.sendMessage(Ancient.brand2 + "You don't have the permission to use this command");
                return;
            }
            setRace(p, race);
            p.sendMessage(Ancient.brand2 + "Your race is now " + race.name);
            if (p != sender) {
                sender.sendMessage(Ancient.brand2 + "Successfully changed the race of " + command[2]);
            }
            AncientRace.playersOnCd.put(sender.getUniqueId(), System.currentTimeMillis());
            File f = new File(Ancient.plugin.getDataFolder() + File.separator + "Races" + File.separator + "changecds.dat");
            try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for (UUID uuid :AncientClass.playersOnCd.keySet()) {
					bw.write(uuid.toString() + ";" + AncientClass.playersOnCd.get(uuid)); //.writeObject(AncientClass.playersOnCd);
					bw.newLine();
				}
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else {
            sender.sendMessage(Ancient.brand2 + "Incorrect usage of setrace");
        }
    }

    public static void setRace(Player p, AncientRace race) {
        PlayerData.getPlayerData(p.getUniqueId()).setRacename(race.name.toLowerCase());
    }
}