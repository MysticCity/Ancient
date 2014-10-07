package com.ancientshores.AncientRPG.Race.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;

public class SetRaceCommand {
    @SuppressWarnings("deprecation")
	public static void setRaceCommand(Player sender, String[] command) {
        if (command.length >= 2) {
            Player p = sender;
            if (AncientRPGRace.playersOnCd.containsKey(p.getUniqueId())) {
                long t = System.currentTimeMillis();
                long div = t - AncientRPGRace.playersOnCd.get(p.getUniqueId());
                if (div < (AncientRPGRace.changeCd * 1000)) {
                    sender.sendMessage(AncientRPG.brand2 + "The race change cooldown hasn't expired yet");
                    long timeleft = AncientRPGRace.playersOnCd.get(p.getUniqueId()) + (AncientRPGRace.changeCd * 1000) - System.currentTimeMillis();
                    int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
                    sender.sendMessage("You have to wait another " + minutes + " minutes.");
                    return;
                }
            }
            if (command.length == 3) {
                if (!p.hasPermission(AncientRPGRace.adminRacePermission)) {
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
            if (race.permission != null && !race.permission.equals("") && !sender.hasPermission(race.permission)) {
                sender.sendMessage(AncientRPG.brand2 + "You don't have the permission to set your race to this race");
                return;
            }
            if (!sender.hasPermission(AncientRPGRace.setRacePermission)) {
                sender.sendMessage(AncientRPG.brand2 + "You don't have the permission to use this command");
                return;
            }
            setRace(p, race);
            p.sendMessage(AncientRPG.brand2 + "Your race is now " + race.name);
            if (p != sender) {
                sender.sendMessage(AncientRPG.brand2 + "Successfully changed the race of " + command[2]);
            }
            AncientRPGRace.playersOnCd.put(sender.getUniqueId(), System.currentTimeMillis());
            File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Races" + File.separator + "changecds.dat");
            try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for (UUID uuid :AncientRPGClass.playersOnCd.keySet()) {
					bw.write(uuid.toString() + ";" + AncientRPGClass.playersOnCd.get(uuid)); //.writeObject(AncientRPGClass.playersOnCd);
					bw.newLine();
				}
				bw.close();
				fw.close();
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