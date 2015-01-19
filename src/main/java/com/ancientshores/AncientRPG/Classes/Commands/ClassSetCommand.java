package com.ancientshores.AncientRPG.Classes.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.BindingData;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class ClassSetCommand {
	@SuppressWarnings("deprecation")
	public static void setCommand(Object[] args, CommandSender sender) {
		if (args.length == 1) {
			sender.sendMessage(AncientRPG.brand2 + "Not enough arguments");
			return;
		}
		
		if (!(sender instanceof Player)) return;
		
		Player player = (Player) sender;
		
		if (AncientRPGClass.playersOnCd.containsKey(player.getUniqueId())) {
			long t = System.currentTimeMillis();
			long div = t - AncientRPGClass.playersOnCd.get(player.getUniqueId());
			if (div < (AncientRPGClass.changeCd * 1000)) {
				sender.sendMessage(AncientRPG.brand2 + "The class change cooldown hasn't expired yet");
				long timeleft = AncientRPGClass.playersOnCd.get(player.getUniqueId()) + (AncientRPGClass.changeCd * 1000) - System.currentTimeMillis();
				int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
				sender.sendMessage("You have to wait another " + minutes + " minutes.");
				return;
			}
		}
		
		
		PlayerData pd = PlayerData.getPlayerData(player.getUniqueId());
		
		AncientRPGClass oldclass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		AncientRPGClass c = AncientRPGClass.classList.get(((String) args[1]).toLowerCase());
		
		// if 3 arguments given, the second has to be the name of the player that gets changed
		if (args.length == 3 && senderHasAdminPermissions(sender)) {
			Player pl = AncientRPG.plugin.getServer().getPlayer((String) args[1]);
			if (pl != null) {
				pd = PlayerData.getPlayerData(player.getUniqueId());
				player = pl;
				c = AncientRPGClass.classList.get(((String) args[2]).toLowerCase());
			} else {
				sender.sendMessage(AncientRPG.brand2 + "Player not found");
				return;
			}
		}
		
		if (c != null) {
			if ((c.preclass != null && !c.preclass.equals("") && (pd.getClassName() == null || !c.preclass.toLowerCase().equals(pd.getClassName().toLowerCase())))) {
				sender.sendMessage(AncientRPG.brand2 + "You need to be a " + c.preclass + " to join this class");
				return;
			}
			setClass(c, oldclass, player, sender);
		} else sender.sendMessage(AncientRPG.brand2 + "This class does not exist (typo?)");
	}

	public static void setCommandConsole(Object[] args) {
		PlayerData pd;
		Player player = AncientRPG.plugin.getServer().getPlayer(UUID.fromString((String) args[1]));
		if (player != null) pd = PlayerData.getPlayerData(player.getUniqueId());
		else {
			Bukkit.getLogger().log(Level.WARNING, "Player not found");
			return;
		}
		AncientRPGClass oldclass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		AncientRPGClass c = AncientRPGClass.classList.get(((String) args[2]).toLowerCase());
		if (c != null) setClass(c, oldclass, player, null);
		else Bukkit.getLogger().log(Level.WARNING, "This class does not exist (typo?)");
		
	}

	public static boolean canSetClass(AncientRPGClass newClass, final Player p) {
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		
		if (!newClass.isWorldEnabled(p.getWorld())) {
			return false;
		}
		
		if (AncientRPGExperience.isWorldEnabled(p.getWorld())) {
			if (pd.getXpSystem().level < newClass.minlevel) {
				p.sendMessage(AncientRPG.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
				return false;
			}
		}
		
		if (newClass.preclass != null && !newClass.preclass.equals("") && !newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase())) return false;
		
		AncientRPGRace race = AncientRPGRace.getRaceByName(PlayerData.getPlayerData(p.getUniqueId()).getRacename());
		
		if (newClass.requiredraces.size() >= 0 && (race != null && !newClass.requiredraces.contains(race.name.toLowerCase()))) return false;
		
		if (!(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase("")) && !AncientRPG.hasPermissions(p, newClass.permissionNode)) return false;
		
		return AncientRPG.hasPermissions(p, AncientRPGClass.cNodeClass);
	}

	public static boolean senderHasAdminPermissions(CommandSender cs) {
		return ((cs instanceof ConsoleCommandSender || cs instanceof RemoteConsoleCommandSender) || (cs instanceof Player && AncientRPG.hasPermissions((Player) cs, "AncientRPG.classes.admin")));
	}

	public static boolean senderHasPermissions(CommandSender cs, String perm) {
		return ((cs instanceof ConsoleCommandSender || cs instanceof RemoteConsoleCommandSender) || (cs instanceof Player && AncientRPG.hasPermissions((Player) cs, perm)));
	}

	public static void setClass(AncientRPGClass newClass, AncientRPGClass oldClass, final Player p, CommandSender sender) {
		if (p == null) return;
		
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (newClass != null) {
			if (sender == p && AncientRPGExperience.isWorldEnabled(p.getWorld()))
				if (pd.getXpSystem().level < newClass.minlevel) {
					p.sendMessage(AncientRPG.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
					return;
				}

			if (!sender.hasPermission(AncientRPGClass.cNodeClass)) {
				sender.sendMessage(AncientRPG.brand2 + "You don't have the required permissions to become this class");
				return;
			}
			if (!newClass.isWorldEnabled(p.getWorld())) {
				sender.sendMessage(AncientRPG.brand2 + "This class cannot be used in this world");
				return;
			}
			AncientRPGRace race = AncientRPGRace.getRaceByName(PlayerData.getPlayerData(p.getUniqueId()).getRacename());
			if (newClass.requiredraces.size() > 0 && (race == null || !newClass.requiredraces.contains(race.name.toLowerCase()))) {
				p.sendMessage(AncientRPG.brand2 + "Your race can't use this class");
				return;
			}

			if (sender == null || !(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase("")) && !sender.hasPermission(newClass.permissionNode)) {
				p.sendMessage(AncientRPG.brand2 + "You don't have permissions to use this class");
				return;
			}
			AncientRPGClassChangeEvent classevent = new AncientRPGClassChangeEvent(p.getUniqueId(), oldClass, newClass);
			Bukkit.getPluginManager().callEvent(classevent);
			if (classevent.isCancelled()) return;
			
			try {
				if (oldClass != null && oldClass.permGroup != null && !oldClass.permGroup.equals("")) 
					if (AncientRPG.permissionHandler != null) AncientRPG.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
				
				if (AncientRPGExperience.isEnabled()) 
					if (oldClass != null) pd.getClassLevels().put(oldClass.name.toLowerCase(), pd.getXpSystem().xp);
				
			} catch (Exception ignored) {}
			
			pd.setClassName(newClass.name);
			
			if (AncientRPGExperience.isEnabled()) {
				int xp = 0;
				if (pd.getClassLevels().get(newClass.name.toLowerCase()) != null && !AncientRPGClass.resetlevelonchange)
					xp = pd.getClassLevels().get(newClass.name.toLowerCase());
				
				pd.getXpSystem().xp = xp;
				pd.getXpSystem().addXP(0, false);
				pd.getXpSystem().recalculateLevel();
			}
			pd.getHpsystem().setHpRegen();
			pd.getHpsystem().setMinecraftHP();
			pd.getHpsystem().setMaxHP();
			// pd.hpsystem.maxhp = ct.getMaxHp();
			pd.setBindings(new HashMap<BindingData, String>());
			p.sendMessage(AncientRPG.brand2 + "Your class is now " + newClass.name);
			pd.setStance("");
			try {
				if (newClass.permGroup != null && !newClass.permGroup.equals("") && AncientRPG.permissionHandler != null)
					AncientRPG.permissionHandler.playerAddGroup(p, newClass.permGroup);
			} catch (Exception ignored) {}
			for (Map.Entry<BindingData, String> bind : newClass.bindings.entrySet()) 
				pd.getBindings().put(bind.getKey(), bind.getValue());

			AncientRPGClass.playersOnCd.put(p.getUniqueId(), System.currentTimeMillis());
			File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
			try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for (UUID uuid : AncientRPGClass.playersOnCd.keySet()) {
					bw.write(uuid.toString() + ";" + AncientRPGClass.playersOnCd.get(uuid)); //.writeObject(AncientRPGClass.playersOnCd);
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