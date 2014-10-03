package com.ancientshores.AncientRPG.Classes.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.BindingData;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;

public class ClassSetStanceCommand {
	public static void setStanceCommand(Object[] args, Player p) {
		if (args.length == 1) {
			p.sendMessage(AncientRPG.brand2 + "Not enough arguments");
			return;
		}

		if (AncientRPGClass.playersOnCd.containsKey(p.getUniqueId())) {
			long t = System.currentTimeMillis();
			long div = t - AncientRPGClass.playersOnCd.get(p.getUniqueId());
			if (div < (AncientRPGClass.changeCd * 1000)) {
				p.sendMessage(AncientRPG.brand2 + "The class change cooldown hasn't expired yet");
				long timeleft = AncientRPGClass.playersOnCd.get(p.getUniqueId()) + (AncientRPGClass.changeCd * 1000) - System.currentTimeMillis();
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

		if (args.length == 3 && csender.hasPermission("AncientRPG.classes.admin")) {
			Player pl = AncientRPG.plugin.getServer().getPlayer(UUID.fromString((String) args[1]));
			if (pl != null) {
				pd = PlayerData.getPlayerData(p.getUniqueId());
				p = pl;
			} else {
				p.sendMessage(AncientRPG.brand2 + "Player not found");
				return;
			}
		}
		AncientRPGClass rootclass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		if (rootclass == null) {
			p.sendMessage(AncientRPG.brand2 + "You need a class to set a stance!");
			return;
		}
		AncientRPGClass oldstance = rootclass.stances.get(pd.getStance());
		AncientRPGClass stance = rootclass.stances.get(((String) args[2]).toLowerCase());
		if (stance != null) {
			setStance(oldstance, stance, rootclass, p, csender);
		} else {
			p.sendMessage(AncientRPG.brand2 + "This stance does not exist (typo?)");
		}
	}

	public static boolean canSetStanceClass(AncientRPGClass newClass, final Player p) {
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (AncientRPGExperience.isWorldEnabled(p.getWorld())) {
			if (pd.getXpSystem().level < newClass.minlevel) {
				p.sendMessage(AncientRPG.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
				return false;
			}
		}
		if (!newClass.isWorldEnabled(p.getWorld())) {
			return false;
		}
		if (newClass.preclass != null && !newClass.preclass.equals("") && !newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase())) {
			return false;
		}

		return !(!(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase("")) && !p.hasPermission(newClass.permissionNode));
	}

	public static void setStance(AncientRPGClass oldstance, AncientRPGClass newStance, AncientRPGClass rootclass, final Player p, Player sender) {
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (newStance != null) {
			if (sender != p && AncientRPGExperience.isWorldEnabled(p.getWorld())) {
				if (pd.getXpSystem().level < newStance.minlevel) {
					p.sendMessage(AncientRPG.brand2 + "You need to be level " + newStance.minlevel + " to join this stance");
					return;
				}
			}
			if (!newStance.isWorldEnabled(p.getWorld())) {
				p.sendMessage(AncientRPG.brand2 + "This stance cannot be used in this world");
				return;
			}

			if (sender == p && !(newStance.permissionNode == null || newStance.permissionNode.equalsIgnoreCase("")) && !sender.hasPermission(newStance.permissionNode)) {
				p.sendMessage(AncientRPG.brand2 + "You don't have permissions to set join stance");
				return;
			}
			if (sender == p && newStance.preclass != null && !newStance.preclass.equals("") && !oldstance.name.equalsIgnoreCase(newStance.preclass)) {
				p.sendMessage(AncientRPG.brand2 + "You need to be the stance " + newStance.preclass);
				return;
			}
			try {
				if (oldstance != null && oldstance.permGroup != null && !oldstance.permGroup.equals("")) {
					if (AncientRPG.permissionHandler != null) {
						AncientRPG.permissionHandler.playerRemoveGroup(p, oldstance.permGroup);
					}
					if (AncientRPGExperience.isEnabled()) {
						pd.getClassLevels().put(oldstance.name.toLowerCase(), pd.getXpSystem().xp);
					}
				}
			} catch (Exception ignored) {

			}
			pd.setStance(newStance.name);
			if (AncientRPGExperience.isEnabled()) {
				pd.getHpsystem().setHpRegen();
				pd.getHpsystem().setMinecraftHP();
			}
			// pd.hpsystem.maxhp = ct.getMaxHp();
			pd.setBindings(new HashMap<BindingData, String>());
			pd.getHpsystem().maxhp = newStance.hp;
			p.sendMessage(AncientRPG.brand2 + "Your stance is now " + newStance.name);
			try {
				if (newStance.permGroup != null && !newStance.permGroup.equals("") && AncientRPG.permissionHandler != null) {
					AncientRPG.permissionHandler.playerAddGroup(p, newStance.permGroup);
				}
			} catch (Exception ignored) {

			}
			for (Map.Entry<BindingData, String> bind : rootclass.bindings.entrySet()) {
				pd.getBindings().put(bind.getKey(), bind.getValue());
			}
			for (Map.Entry<BindingData, String> bind : newStance.bindings.entrySet()) {
				pd.getBindings().put(bind.getKey(), bind.getValue());
			}

			AncientRPGClass.playersOnCd.put(p.getUniqueId(), System.currentTimeMillis());
			File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
			try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for (UUID uuid :AncientRPGClass.playersOnCd.keySet()) {
					bw.write(uuid + ";" + AncientRPGClass.playersOnCd.get(uuid)); //.writeObject(AncientRPGClass.playersOnCd);
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