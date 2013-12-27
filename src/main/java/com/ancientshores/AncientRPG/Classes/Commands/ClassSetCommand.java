package com.ancientshores.AncientRPG.Classes.Commands;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ClassSetCommand
{
	public static void setCommand(String[] args, CommandSender p)
	{
		if (args.length == 1)
		{
			p.sendMessage(AncientRPG.brand2 + "Not enough arguments");
			return;
		}
		if (AncientRPGClass.playersOnCd.containsKey(p.getName()))
		{
			long t = System.currentTimeMillis();
			long div = t - AncientRPGClass.playersOnCd.get(p.getName());
			if (div < (AncientRPGClass.changeCd * 1000))
			{
				p.sendMessage(AncientRPG.brand2 + "The class change cooldown hasn't expired yet");
				long timeleft = AncientRPGClass.playersOnCd.get(p.getName()) + (AncientRPGClass.changeCd * 1000) - System.currentTimeMillis();
				int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
				p.sendMessage("You have to wait another " + minutes + " minutes.");
				return;
			}
		}
		Player player = null;
		if (p instanceof Player)
		{
			player = (Player) p;
		}
		PlayerData pd = PlayerData.getPlayerData(p.getName());
		if (args.length == 3 && senderHasAdminPermissions(p))
		{
			Player pl = AncientRPG.plugin.getServer().getPlayer(args[1]);
			if (pl != null)
			{
				pd = PlayerData.getPlayerData(p.getName());
				player = pl;
				args[1] = args[2];
			} else
			{
				p.sendMessage(AncientRPG.brand2 + "Player not found");
				return;
			}
		}
		if(player == null)
			return;
		AncientRPGClass oldclass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		AncientRPGClass c = AncientRPGClass.classList.get(args[1].toLowerCase());
		if (c != null)
		{
			if (player == p && c == null || (c.preclass != null && !c.preclass.equals("") && (pd.getClassName() == null || !c.preclass.toLowerCase().equals(pd.getClassName().toLowerCase()))))
			{
				p.sendMessage(AncientRPG.brand2 + "You need to be a " + c.preclass + " to join this class");
				return;
			}
			setClass(c, oldclass, player, p);
		} else
		{
			p.sendMessage(AncientRPG.brand2 + "This class does not exist (typo?)");
		}
	}

	public static void setCommandConsole(String[] args)
	{
		PlayerData pd;
		Player pl = AncientRPG.plugin.getServer().getPlayer(args[1]);
		if (pl != null)
		{
			pd = PlayerData.getPlayerData(pl.getName());
		} else
		{
			Bukkit.getLogger().log(Level.WARNING, "Player not found");
			return;
		}
		AncientRPGClass oldclass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		AncientRPGClass c = AncientRPGClass.classList.get(args[2].toLowerCase());
		if (c != null)
		{
			setClass(c, oldclass, pl, null);
		} else
		{
			Bukkit.getLogger().log(Level.WARNING, "This class does not exist (typo?)");
		}
	}

	public static boolean canSetClass(AncientRPGClass newClass, final Player p)
	{
		PlayerData pd = PlayerData.getPlayerData(p.getName());
		if (AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(p))
		{
			if (pd.getXpSystem().level < newClass.minlevel)
			{
				p.sendMessage(AncientRPG.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
				return false;
			}
		}
		if (!newClass.isWorldEnabled(p))
		{
			return false;
		}
		if (newClass.preclass != null && !newClass.preclass.equals("") && !newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase()))
		{
			return false;
		}
		AncientRPGRace race = AncientRPGRace.getRaceByName(PlayerData.getPlayerData(p.getName()).getRacename());
		if (newClass.requiredraces.size() >= 0 && (race != null && !newClass.requiredraces.contains(race.name.toLowerCase())))
		{
			return false;
		}
		if (!(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase("")) && !AncientRPG.hasPermissions(p, newClass.permissionNode))
		{
			return false;
		}
		return AncientRPG.hasPermissions(p, AncientRPGClass.cNodeClass);
	}

	public static boolean senderHasAdminPermissions(CommandSender cs)
	{
		return ((cs instanceof ConsoleCommandSender || cs instanceof RemoteConsoleCommandSender) || (cs instanceof Player && AncientRPG.hasPermissions((Player) cs, "AncientRPG.classes.admin")));
	}

	public static boolean senderHasPermissions(CommandSender cs, String perm)
	{
		return ((cs instanceof ConsoleCommandSender || cs instanceof RemoteConsoleCommandSender) || (cs instanceof Player && AncientRPG.hasPermissions((Player) cs, perm)));
	}

	public static void setClass(AncientRPGClass newClass, AncientRPGClass oldClass, final Player p, CommandSender sender)
	{
		if(p == null)
			return;
		PlayerData pd = PlayerData.getPlayerData(p.getName());
		if (newClass != null)
		{
			if (sender == p && AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(p))
			{
				if (pd.getXpSystem().level < newClass.minlevel)
				{
					p.sendMessage(AncientRPG.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
					return;
				}
			}
			if (!senderHasPermissions(sender, AncientRPGClass.cNodeClass))
			{
				sender.sendMessage(AncientRPG.brand2 + "You don't have the required permissions to become this class");
				return;
			}
			if (!newClass.isWorldEnabled(p))
			{
				sender.sendMessage(AncientRPG.brand2 + "This class cannot be used in this world");
				return;
			}
			AncientRPGRace race = AncientRPGRace.getRaceByName(PlayerData.getPlayerData(p.getName()).getRacename());
			if (newClass.requiredraces.size() > 0 && (race == null || !newClass.requiredraces.contains(race.name.toLowerCase())))
			{
				p.sendMessage(AncientRPG.brand2 + "Your race can't use this class");
				return;
			}
			if (sender == null || !(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase("")) && !senderHasPermissions(sender, newClass.permissionNode))
			{
				p.sendMessage(AncientRPG.brand2 + "You don't have permissions to use this class");
				return;
			}
			AncientRPGClassChangeEvent classevent = new AncientRPGClassChangeEvent(p, oldClass, newClass);
			Bukkit.getPluginManager().callEvent(classevent);
			if (classevent.isCancelled())
				return;
			try
			{
				if (oldClass != null && oldClass.permGroup != null && !oldClass.permGroup.equals(""))
				{
					if (AncientRPG.permissionHandler != null)
						AncientRPG.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
				}
				if (AncientRPGExperience.isEnabled())
					pd.getClassLevels().put(oldClass.name.toLowerCase(), pd.getXpSystem().xp);
			} catch (Exception ignored)
			{

			}
			pd.setClassName(newClass.name);
			if (AncientRPGExperience.isEnabled())
			{
				int xp = 0;
				if (pd.getClassLevels().get(newClass.name.toLowerCase()) != null && !AncientRPGClass.resetlevelonchange)
					xp = pd.getClassLevels().get(newClass.name.toLowerCase());
				pd.getXpSystem().xp = xp;
				pd.getXpSystem().addXP(0, false);
				pd.getXpSystem().recalculateLevel();
			}
			pd.getHpsystem().setHpRegen();
			pd.getHpsystem().setMinecraftHP();
			pd.getHpsystem().setMaxHp();
			// pd.hpsystem.maxhp = ct.getMaxHp();
			pd.setBindings(new HashMap<BindingData, String>());
			p.sendMessage(AncientRPG.brand2 + "Your class is now " + newClass.name);
			pd.setStance("");
			try
			{
				if (newClass.permGroup != null && !newClass.permGroup.equals("") && AncientRPG.permissionHandler != null)
					AncientRPG.permissionHandler.playerAddGroup(p, newClass.permGroup);
			} catch (Exception ignored)
			{

			}
			for (Map.Entry<BindingData, String> bind : newClass.bindings.entrySet())
			{
				pd.getBindings().put(bind.getKey(), bind.getValue());
			}
			AncientRPGClass.playersOnCd.put(p.getName(), System.currentTimeMillis());
			File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
			FileOutputStream fos;
			try
			{
				fos = new FileOutputStream(f);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(AncientRPGClass.playersOnCd);
				oos.close();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pd.getManasystem().setMaxMana();
	}
}
