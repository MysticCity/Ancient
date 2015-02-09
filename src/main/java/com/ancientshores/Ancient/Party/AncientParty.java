package com.ancientshores.Ancient.Party;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.API.AncientPartyDisbandedEvent;
import com.ancientshores.Ancient.API.AncientPartyJoinEvent;
import com.ancientshores.Ancient.API.AncientPartyLeaveEvent;
import com.ancientshores.Ancient.Party.Commands.PartyCommandAccept;
import com.ancientshores.Ancient.Party.Commands.PartyCommandAdmin;
import com.ancientshores.Ancient.Party.Commands.PartyCommandChat;
import com.ancientshores.Ancient.Party.Commands.PartyCommandDisband;
import com.ancientshores.Ancient.Party.Commands.PartyCommandGrant;
import com.ancientshores.Ancient.Party.Commands.PartyCommandHelp;
import com.ancientshores.Ancient.Party.Commands.PartyCommandIgnore;
import com.ancientshores.Ancient.Party.Commands.PartyCommandInvite;
import com.ancientshores.Ancient.Party.Commands.PartyCommandKick;
import com.ancientshores.Ancient.Party.Commands.PartyCommandLeave;
import com.ancientshores.Ancient.Party.Commands.PartyCommandList;
import com.ancientshores.Ancient.Party.Commands.PartyCommandReject;
import com.ancientshores.Ancient.Party.Commands.PartyCommandToggle;
import com.ancientshores.Ancient.Party.Commands.PartyCommandToggleFriendlyFire;

public class AncientParty {
	protected UUID leader;
	protected boolean friendlyFire = false;
	protected Collection<UUID> members;
	public static final Collection<AncientParty> partys = Collections.newSetFromMap(new ConcurrentHashMap<AncientParty, Boolean>());
	public static final ConcurrentHashMap<UUID, AncientParty> invites = new ConcurrentHashMap<UUID, AncientParty>();
	static final ConcurrentHashMap<UUID, Timer> disconnects = new ConcurrentHashMap<UUID, Timer>();
	public static final Collection<UUID> mIgnoreList = Collections.newSetFromMap(new ConcurrentHashMap<UUID, Boolean>());
	public static final String pNodeCreate = "Ancient.party.create";
	public static final String pNodeJoin = "Ancient.party.join";
	private static String pNodeList = "Ancient.party.list";
	private static String pNodeIgnore = "Ancient.party.ignore";
	public static final String pNodeAdmin = "Ancient.party.admin";
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

	public AncientParty(UUID leader) {
		this.leader = leader;
		members = new HashSet<UUID>();
		members.add(leader);
	}

	public boolean addPlayer(UUID uuid) {
		if (uuid != null) {
			if (members.size() < maxPlayers) {
				AncientPartyJoinEvent event = new AncientPartyJoinEvent(uuid, this);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					return false;
				}
				members.add(uuid);
				return true;
			}
		}
		return false;
	}

	public void removePlayer(UUID uuid) {
		if (uuid != null) {
			AncientPartyLeaveEvent event = new AncientPartyLeaveEvent(uuid, this);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled()) {
				return;
			}
			members.remove(uuid);
		}
	}

	public boolean removeByUUID(UUID playeruuid, boolean reconnected) {
		for (UUID uuid : members) {
			if (uuid.compareTo(playeruuid) == 0) {
				if (uuid == leader && !reconnected) {
					AncientPartyLeaveEvent event = new AncientPartyLeaveEvent(uuid, this);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isCancelled()) {
						return false;
					}
					members.remove(uuid);
					this.sendMessage(Ancient.brand2 + ChatColor.GOLD + playeruuid + ChatColor.BLUE + " left your party.");
					giveNextLeader();
				} else {
					AncientPartyLeaveEvent event = new AncientPartyLeaveEvent(uuid, this);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isCancelled()) {
						return false;
					}
					members.remove(uuid);
					this.sendMessage(Ancient.brand2 + ChatColor.GOLD + PlayerFinder.getPlayerName(playeruuid) + ChatColor.BLUE + " left your party.");
					if (this.getMemberNumber() == 0) {
						partys.remove(this);
					}
				}
				return true;
			}
		}
		return false;
	}

	public void sendMessage(String[] message, Player sender) {
		for (UUID uuid : members) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null) {
				if (sender.getUniqueId().compareTo(leader) == 0) {
					p.sendMessage(ChatColor.BLUE + "<Party>" + ChatColor.GREEN + sender.getName() + ChatColor.BLUE + ": " + Ancient.convertStringArrayToString(message));
				} else {
					p.sendMessage(ChatColor.BLUE + "<Party>" + ChatColor.GOLD + sender.getName() + ChatColor.BLUE + ": " + Ancient.convertStringArrayToString(message));
				}
			}
		}
	}

	public void sendMessage(String message) {
		if (members == null) {
			return;
		}
		
		for (UUID uuid : members) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null) {
				p.sendMessage(message + "");
			}
		}
	}

	public int getMemberNumber() {
		return members.size();
	}

	public void removeAll() {
		AncientPartyDisbandedEvent event = new AncientPartyDisbandedEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			return;
		}
		for (UUID uuid : members) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null) {
				p.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Your party was disbanded.");
			}
		}
		members = null;
		partys.remove(this);
	}

	public void giveNextLeader() {
		for (UUID uuid : members) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null && (p.hasPermission(pNodeCreate))) {
				leader = uuid;
				this.sendMessage(Ancient.brand2 + ChatColor.GREEN + p.getName() + ChatColor.BLUE + " is the new leader of the party.");
				return;
			}
		}
		sendMessage(Ancient.brand2 + ChatColor.BLUE + "This Party has been disbanded because no one has the rights to be the leader of it");
		removeAll();
		partys.remove(this);
	}

	public static void processQuit(PlayerQuitEvent playerQuitEvent) {
		final AncientParty party = getPlayersParty(playerQuitEvent.getPlayer().getUniqueId());
		if (party != null) {
			final UUID uuid = playerQuitEvent.getPlayer().getUniqueId();
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					party.removeByUUID(uuid, false);
					AncientParty.disconnects.remove(uuid);
				}
			}, 60000);
			disconnects.put(uuid, new Timer());
			mIgnoreList.remove(uuid);
		}
	}

	public static void processJoin(PlayerJoinEvent playerJoinEvent) {
		UUID uuid = playerJoinEvent.getPlayer().getUniqueId();
		if (disconnects.containsKey(uuid)) {
			Timer qt = disconnects.get(uuid);
			if (qt != null) {
				qt.cancel();
			}
			AncientParty mParty = getPlayersPartyByUUID(uuid);
			if (mParty != null) {
				mParty.removeByUUID(uuid, true);
				mParty.leader = uuid;
				mParty.addPlayer(uuid);
			}
			disconnects.remove(uuid);
		}
	}

	public boolean containsUUID(UUID playeruuid) {
		for (UUID uuid : members) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null && p.getUniqueId().compareTo(playeruuid) == 0) {
				return true;
			}
		}
		return false;
	}

	public UUID getLeader() {
		return leader;
	}

	public void setLeader(UUID leader) {
		this.leader = leader;
	}

	public Collection<UUID> getMembers() {
		return Collections.unmodifiableCollection(members);
	}

	public boolean isFriendlyFireEnabled() {
		return friendlyFire;
	}

	public void setFriendlyFireEnabled(boolean friendlyFire) {
		this.friendlyFire = friendlyFire;
	}

	public static void processCommand(CommandSender sender, String[] args, Ancient main) {
//		Player mPlayer = (Player) sender;
		if (args.length == 0) {
			PartyCommandHelp.processHelp(sender, args);
		}
		if (args.length >= 1) {
			if (args[0].equals("invite")) {
				PartyCommandInvite.processInvite(sender, args, main);
				return;
			} else if (args[0].equals("chat")) {
				PartyCommandChat.processChat(sender, args);
			} else if (args[0].equals("accept")) {
				PartyCommandAccept.processAccept(sender);
			} else if (args[0].equals("toggleff")) {
				PartyCommandToggleFriendlyFire.processFF(sender);
			} else if (args[0].equals("leave")) {
				PartyCommandLeave.processLeave(sender);
			} else if (args[0].equals("list")) {
				PartyCommandList.processList(sender);
			} else if (args[0].equals("reject")) {
				PartyCommandReject.processReject(sender);
			} else if (args[0].equals("ignore")) {
				PartyCommandIgnore.processIgnore(sender, args);
			} else if (args[0].equals("kick")) {
				PartyCommandKick.processKick(sender, args, main);
			} else if (args[0].equals("disband")) {
				PartyCommandDisband.processDisband(sender);
			} else if (args[0].equals("toggle")) {
				PartyCommandToggle.processToggle(sender);
			} else if (args[0].equals("help")) {
				PartyCommandHelp.processHelp(sender, args);
			} else if (args[0].equals("grant")) {
				PartyCommandGrant.processGrant(sender, args, main);
			} else if (args[0].equals("admin")) {
				PartyCommandAdmin.processAdmin(sender, args, main);
			}
		}
	}

	public static void writeConfig(Ancient plugin) {
		File newfile = new File(plugin.getDataFolder().getPath() + File.separator + "partyconfig.yml");
		if (!newfile.exists()) {
			try {
				newfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration partyConfig = new YamlConfiguration();
		partyConfig.set(pConfigEnabled, enabled);
		partyConfig.set(pConfigCantoggleff, cantoggleff);
		partyConfig.set(pConfigSize, maxPlayers);
		partyConfig.set(pConfigSplitXpEnabled, splitxp);
		partyConfig.set(pConfigSplitXpRange, splitxprange);
		try {
			partyConfig.save(newfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadConfig(Ancient plugin) {
		File newfile = new File(plugin.getDataFolder().getPath() + File.separator + "partyconfig.yml");
		if (newfile.exists()) {
			YamlConfiguration yc = new YamlConfiguration();
			try {
				yc.load(newfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			enabled = yc.getBoolean(pConfigEnabled, enabled);
			cantoggleff = yc.getBoolean(pConfigCantoggleff, cantoggleff);
			maxPlayers = yc.getInt(pConfigSize, maxPlayers);
			splitxp = yc.getBoolean(pConfigSplitXpEnabled, splitxp);
			splitxprange = yc.getInt(pConfigSplitXpRange, splitxprange);
		} else {
			enabled = plugin.getConfig().getBoolean(pConfigEnabled, enabled);
			cantoggleff = plugin.getConfig().getBoolean(pConfigCantoggleff, cantoggleff);
			maxPlayers = plugin.getConfig().getInt(pConfigSize, maxPlayers);
			splitxp = plugin.getConfig().getBoolean(pConfigSplitXpEnabled, splitxp);
			splitxprange = plugin.getConfig().getInt(pConfigSplitXpRange, splitxprange);
		}
	}

	public static AncientParty getPlayersParty(UUID uuid) {
		for (AncientParty p : partys) {
			if (p.members.contains(uuid)) {
				return p;
			}
		}
		return null;
	}

	public static AncientParty getPlayersPartyByUUID(UUID uuid) {
		for (AncientParty p : partys) {
			if (p.containsUUID(uuid)) {
				return p;
			}
		}
		return null;
	}
}