package com.ancientshores.Ancient.Classes.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.Experience.AncientExperience;

public class ClassSetStanceCommand {
	public static void setStanceCommand(Object[] args, Player p) {
		if (args.length == 1) {
			p.sendMessage(Ancient.ChatBrand + "Not enough arguments");
			return;
		}

		if (AncientClass.playersOnCd.containsKey(p.getUniqueId())) {
			long t = System.currentTimeMillis();
			long div = t - AncientClass.playersOnCd.get(p.getUniqueId());
			if (div < (AncientClass.changeCd * 1000)) {
				p.sendMessage(Ancient.ChatBrand + "The class change cooldown hasn't expired yet");
				long timeleft = AncientClass.playersOnCd.get(p.getUniqueId()) + (AncientClass.changeCd * 1000) - System.currentTimeMillis();
				int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
				if (minutes == 1) {
					p.sendMessage("You have to wait another minute.");
				}
				else {
					p.sendMessage("You have to wait another " + minutes + " minutes.");
				}

				return;
			}
		}
		
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		Player csender = p;

		if (args.length == 3 && csender.hasPermission("Ancient.classes.admin")) {
			Player pl = Ancient.plugin.getServer().getPlayer(UUID.fromString((String) args[1]));
			if (pl != null) {
				pd = PlayerData.getPlayerData(p.getUniqueId());
				p = pl;
			} else {
				p.sendMessage(Ancient.ChatBrand + "Player not found");
				return;
			}
		}
		AncientClass rootclass = AncientClass.classList.get(pd.getClassName().toLowerCase());
		if (rootclass == null) {
			p.sendMessage(Ancient.ChatBrand + "You need a class to set a stance!");
			return;
		}
		AncientClass oldstance = rootclass.stances.get(pd.getStance());
		AncientClass stance = rootclass.stances.get(((String) args[2]).toLowerCase());
		if (stance != null) {
			setStance(oldstance, stance, rootclass, p, csender);
		} else {
			p.sendMessage(Ancient.ChatBrand + "This stance does not exist (typo?)");
		}
	}

	public static boolean canSetStanceClass(AncientClass newClass, final Player p) {
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (AncientExperience.isWorldEnabled(p.getWorld())) {
			if (pd.getXpSystem().level < newClass.minlevel) {
				p.sendMessage(Ancient.ChatBrand + "You need to be level " + newClass.minlevel + " to join this class");
				return false;
			}
		}
		if (!newClass.isWorldEnabled(p.getWorld())) {
			return false;
		}
		if (newClass.preclass != null && !newClass.preclass.equals("") && !newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase())) return false;
		
		return !(!(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase("")) && !p.hasPermission(newClass.permissionNode));
	}

	public static void setStance(AncientClass oldstance, AncientClass newStance, AncientClass rootclass, final Player p, Player sender) {
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (newStance != null) {
			if (sender != p && AncientExperience.isWorldEnabled(p.getWorld())) {
				if (pd.getXpSystem().level < newStance.minlevel) {
					p.sendMessage(Ancient.ChatBrand + "You need to be level " + newStance.minlevel + " to join this stance");
					return;
				}
			}
			if (!newStance.isWorldEnabled(p.getWorld())) {
				p.sendMessage(Ancient.ChatBrand + "This stance cannot be used in this world");
				return;
			}

			if (sender == p && !(newStance.permissionNode == null || newStance.permissionNode.equalsIgnoreCase("")) && !sender.hasPermission(newStance.permissionNode)) {
				p.sendMessage(Ancient.ChatBrand + "You don't have permissions to set join stance");
				return;
			}
			if (sender == p && newStance.preclass != null && !newStance.preclass.equals("") && !oldstance.name.equalsIgnoreCase(newStance.preclass)) {
				p.sendMessage(Ancient.ChatBrand + "You need to be the stance " + newStance.preclass);
				return;
			}
			try {
				if (oldstance != null && oldstance.permGroup != null && !oldstance.permGroup.equals("")) {
					if (Ancient.permissionHandler != null) {
						Ancient.permissionHandler.playerRemoveGroup(p, oldstance.permGroup);
					}
					if (AncientExperience.isEnabled()) {
						pd.getClassLevels().put(oldstance.name.toLowerCase(), pd.getXpSystem().xp);
					}
				}
			} catch (Exception ignored) {

			}
			pd.setStance(newStance.name);
			if (AncientExperience.isEnabled()) {
				pd.getHpsystem().setHpRegen();
				pd.getHpsystem().setMinecraftHP();
			}
			// pd.hpsystem.maxhp = ct.getMaxHp();
			pd.setBindings(new HashMap<BindingData, String>());
			pd.getHpsystem().maxhp = newStance.hp;
			p.sendMessage(Ancient.ChatBrand + "Your stance is now " + newStance.name);
			try {
				if (newStance.permGroup != null && !newStance.permGroup.equals("") && Ancient.permissionHandler != null) {
					Ancient.permissionHandler.playerAddGroup(p, newStance.permGroup);
				}
			} catch (Exception ignored) {

			}
			for (Map.Entry<BindingData, String> bind : rootclass.bindings.entrySet()) {
				pd.getBindings().put(bind.getKey(), bind.getValue());
			}
			for (Map.Entry<BindingData, String> bind : newStance.bindings.entrySet()) {
				pd.getBindings().put(bind.getKey(), bind.getValue());
			}

			AncientClass.playersOnCd.put(p.getUniqueId(), System.currentTimeMillis());
			File f = new File(Ancient.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
			try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for (UUID uuid :AncientClass.playersOnCd.keySet()) {
					bw.write(uuid + ";" + AncientClass.playersOnCd.get(uuid)); //.writeObject(AncientClass.playersOnCd);
					bw.newLine();
				}
				bw.close();
				fw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pd.getManasystem().setMaxMana();
	}
}