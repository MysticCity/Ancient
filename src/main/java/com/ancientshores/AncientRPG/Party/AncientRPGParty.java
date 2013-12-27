package com.ancientshores.AncientRPG.Party;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.API.AncientRPGPartyDisbandedEvent;
import com.ancientshores.AncientRPG.API.AncientRPGPartyJoinEvent;
import com.ancientshores.AncientRPG.API.AncientRPGPartyLeaveEvent;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandAccept;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandAdmin;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandChat;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandDisband;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandGrant;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandHelp;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandIgnore;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandInvite;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandKick;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandLeave;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandList;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandReject;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandToggle;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandToggleFriendlyFire;

public class AncientRPGParty
{
	public Player mLeader;
	public boolean friendlyFire = false;
	public Collection<Player> Member;
	public static final Collection<AncientRPGParty> partys = Collections.newSetFromMap(new ConcurrentHashMap<AncientRPGParty, Boolean>());
	public static final ConcurrentHashMap<Player, AncientRPGParty> invites = new ConcurrentHashMap<Player, AncientRPGParty>();
	static final ConcurrentHashMap<String, QuitTimer> disconnects = new ConcurrentHashMap<String, QuitTimer>();
	public static final Collection<Player> mIgnoreList = Collections.newSetFromMap(new ConcurrentHashMap<Player, Boolean>());
	public static final String pNodeCreate = "AncientRPG.party.create";
	public static final String pNodeJoin = "AncientRPG.party.join";
	// private static String pNodeList = "pylamo.party.list";
	// private static String pNodeIgnore = "pylamo.party.ignore";
	public static final String pNodeAdmin = "AncientRPG.partyadmin";
	public static final String pConfigSize = "party.max party size";
	public static final String pConfigCantoggleff = "party.can toggle ff";
	public static final String pConfigEnabled = "party.enabled";
	public static final String pConfigSplitXpEnabled = "party.split xp";
	public static final String pConfigSplitXpRange = "party.split xp range";
	public static int maxPlayers = 5;
	public static boolean enabled = true;
	public static boolean cantoggleff = true;
	public static boolean splitxp = true;
	public static int splitxprange = 10;

	public AncientRPGParty(Player Leader)
	{
		mLeader = Leader;
		Member = Collections.newSetFromMap(new ConcurrentHashMap<Player, Boolean>());
		Member.add(mLeader);
	}

	public boolean addPlayer(Player player)
	{

		if (player != null)
		{
			if (Member.size() <= maxPlayers)
			{
				AncientRPGPartyJoinEvent event = new AncientRPGPartyJoinEvent(player, this);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled())
					return false;
				Member.add(player);
				return true;
			}
		}
		return false;
	}

	public void removePlayer(Player mMember)
	{
		if (mMember != null)
		{
			AncientRPGPartyLeaveEvent event = new AncientRPGPartyLeaveEvent(mMember, this);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled())
				return;
			Member.remove(mMember);
		}
	}

	public boolean removeByName(String name, boolean reconnected)
	{
		for (Player p : Member)
		{
			if (p != null && p.getName().equals(name))
			{
				if (p == mLeader && !reconnected)
				{
					AncientRPGPartyLeaveEvent event = new AncientRPGPartyLeaveEvent(p, this);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isCancelled())
						return false;
					Member.remove(p);
					giveNextLeader();
					this.sendMessage(AncientRPG.brand2 + ChatColor.GOLD + name + ChatColor.BLUE + " left your party.");
				} else
				{
					AncientRPGPartyLeaveEvent event = new AncientRPGPartyLeaveEvent(p, this);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isCancelled())
						return false;
					Member.remove(p);
					this.sendMessage(AncientRPG.brand2 + ChatColor.GOLD + name + ChatColor.BLUE + " left your party.");
					if (this.getMemberNumber() == 0)
					{
						partys.remove(this);
					}
				}
				return true;
			}
		}
		return false;
	}

	public void sendMessage(String[] message, Player sender)
	{
		for (Player p : Member)
		{
			if (p != null)
			{
				if (sender == mLeader)
				{
					p.sendMessage(ChatColor.BLUE + "<Party>" + ChatColor.GREEN + sender.getName() + ChatColor.BLUE + ": " + AncientRPG.convertStringArrayToString(message));
				} else
				{
					p.sendMessage(ChatColor.BLUE + "<Party>" + ChatColor.GOLD + sender.getName() + ChatColor.BLUE + ": " + AncientRPG.convertStringArrayToString(message));
				}
			}
		}
	}

	public void sendMessage(String message)
	{
		if(Member == null)
			return;
		for (Player p : Member)
		{
			if (p != null)
			{
				p.sendMessage(message + "");
			}
		}
	}

	public int getMemberNumber()
	{
		return Member.size();
	}

	public void removeAll()
	{
		AncientRPGPartyDisbandedEvent event = new AncientRPGPartyDisbandedEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		for (Player p : Member)
		{
			if (p != null)
			{
				p.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Your party was disbanded.");
				partys.remove(this);
			}
		}
		Member = null;
	}

	public void giveNextLeader()
	{
		for (Player p : Member)
		{
			if (p != null && (AncientRPG.hasPermissions(p, pNodeCreate)))
			{
				mLeader = p;
				this.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + mLeader.getName() + ChatColor.BLUE + " is the new leader of the party.");
				return;
			}
		}
		sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "This Party has been disbanded because no one has the rights to be the leader of it");
		removeAll();
		partys.remove(this);
	}

	public static void processQuit(PlayerQuitEvent playerQuitEvent)
	{
		AncientRPGParty mParty = getPlayersParty(playerQuitEvent.getPlayer());
		if (getPlayersParty(playerQuitEvent.getPlayer()) != null)
		{
			String playername = playerQuitEvent.getPlayer().getName();
			disconnects.put(playername, new QuitTimer(playername, mParty));
			mIgnoreList.remove(playerQuitEvent.getPlayer());
		}
	}

	public static void processJoin(PlayerJoinEvent playerJoinEvent)
	{
		Player mPlayer = playerJoinEvent.getPlayer();
		String playername = mPlayer.getName();
		if (disconnects.containsKey(playername))
		{
			QuitTimer qt = disconnects.get(playername);
			if (qt != null)
			{
				qt.cancel();
			}
			AncientRPGParty mParty = getPlayersPartyByName(playername);
			if (mParty != null)
			{
				mParty.removeByName(playername, true);
				mParty.mLeader = mPlayer;
				mParty.addPlayer(mPlayer);
			}
			disconnects.remove(playername);
		}
	}

	public boolean containsName(String name)
	{
		for (Player p : Member)
		{
			if (p != null && p.getName().equals(name))
			{
				return true;
			}
		}
		return false;
	}

	public static void processCommand(CommandSender sender, String[] args, AncientRPG main)
	{
		// Player mPlayer = (Player) sender;
		if (args.length == 0)
		{
			PartyCommandHelp.processHelp(sender, args);
		}
		if (args.length >= 1)
		{
			if (args[0].equals("invite") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandInvite"))
			{
				PartyCommandInvite.processInvite(sender, args, main);
				return;
			} else if (args[0].equals("chat") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandChat"))
			{
				PartyCommandChat.processChat(sender, args);
			} else if (args[0].equals("accept") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandAccept"))
			{
				PartyCommandAccept.processAccept(sender);
			} else if (args[0].equals("toggleff") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandToggleFriendlyFire"))
			{
				PartyCommandToggleFriendlyFire.processFF(sender);
			} else if (args[0].equals("leave") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandLeave"))
			{
				PartyCommandLeave.processLeave(sender);
			} else if (args[0].equals("list") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandList"))
			{
				PartyCommandList.processList(sender);
			} else if (args[0].equals("reject") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandReject"))
			{
				PartyCommandReject.processReject(sender);
			} else if (args[0].equals("ignore") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandIgnore"))
			{
				PartyCommandIgnore.processIgnore(sender, args);
			} else if (args[0].equals("kick") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandKick"))
			{
				PartyCommandKick.processKick(sender, args, main);
			} else if (args[0].equals("disband") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandDisband"))
			{
				PartyCommandDisband.processDisband(sender);
			} else if (args[0].equals("toggle") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandToggle"))
			{
				PartyCommandToggle.processToggle(sender);
			} else if (args[0].equals("help") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandHelp"))
			{
				PartyCommandHelp.processHelp(sender, args);
			} else if (args[0].equals("grant") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandGrant"))
			{
				PartyCommandGrant.processGrant(sender, args, main);
			}
			if (args[0].equals("admin") && AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandAdmin"))
			{
				PartyCommandAdmin.processAdmin(sender, args, main);
			}
		}
	}

	public static void writeConfig(AncientRPG plugin)
	{
		File newfile = new File(plugin.getDataFolder().getPath() + File.separator + "partyconfig.yml");
		if (!newfile.exists())
		{
			try
			{
				newfile.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		YamlConfiguration yc = new YamlConfiguration();
		yc.set(pConfigEnabled, enabled);
		yc.set(pConfigCantoggleff, cantoggleff);
		yc.set(pConfigSize, maxPlayers);
		yc.set(pConfigSplitXpEnabled, splitxp);
		yc.set(pConfigSplitXpRange, splitxprange);
		try
		{
			yc.save(newfile);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadConfig(AncientRPG plugin)
	{
		File newfile = new File(plugin.getDataFolder().getPath() + File.separator + "partyconfig.yml");
		if (newfile.exists())
		{
			YamlConfiguration yc = new YamlConfiguration();
			try
			{
				yc.load(newfile);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			enabled = yc.getBoolean(pConfigEnabled, enabled);
			cantoggleff = yc.getBoolean(pConfigCantoggleff, cantoggleff);
			maxPlayers = yc.getInt(pConfigSize, maxPlayers);
			splitxp = yc.getBoolean(pConfigSplitXpEnabled, splitxp);
			splitxprange = yc.getInt(pConfigSplitXpRange, splitxprange);
		}
		else
		{
			enabled = plugin.getConfig().getBoolean(pConfigEnabled, enabled);
			cantoggleff = plugin.getConfig().getBoolean(pConfigCantoggleff, cantoggleff);
			maxPlayers = plugin.getConfig().getInt(pConfigSize, maxPlayers);
			splitxp = plugin.getConfig().getBoolean(pConfigSplitXpEnabled, splitxp);
			splitxprange = plugin.getConfig().getInt(pConfigSplitXpRange, splitxprange);
		}
	}

	public static AncientRPGParty getPlayersParty(Player mplayer)
	{
		for (AncientRPGParty p : partys)
		{
			if (p.Member.contains(mplayer))
			{
				return p;
			}
		}
		return null;
	}

	public static AncientRPGParty getPlayersPartyByName(String name)
	{
		for (AncientRPGParty p : partys)
		{
			if (p.containsName(name))
			{
				return p;
			}
		}
		return null;
	}

}