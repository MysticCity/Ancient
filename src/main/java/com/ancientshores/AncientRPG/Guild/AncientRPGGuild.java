package com.ancientshores.AncientRPG.Guild;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ancient.util.PlayerFinder;
import com.ancient.util.UUIDConverter;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandAccept;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandAdmin;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandChat;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandCreate;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandDeposit;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandDisband;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandGrant;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandHelp;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandInfo;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandInvite;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandKick;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandLeave;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandList;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandMemberlist;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandMoney;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandMotd;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandOnline;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandReject;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandSetTag;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandSetmMotd;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandToggle;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandToggleFriendlyFire;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandWithdraw;
import com.ancientshores.AncientRPG.Guild.Commands.GuildSetSpawnCommand;
import com.ancientshores.AncientRPG.Util.SerializableLocation;

public class AncientRPGGuild implements Serializable {
	private static final long serialVersionUID = 1L;

	// ====
	// Membervariables
	// ====
	public String guildName;
	public UUID gLeader;
	public String motd;
	public String accountName;
	public String tag;
	public boolean friendlyFire = false;
	public SerializableLocation spawnLocation;

	// ====
	// HashSets/Maps
	// ====
	public static final ConcurrentHashMap<UUID, AncientRPGGuild> invites = new ConcurrentHashMap<UUID, AncientRPGGuild>();
	public HashMap<UUID, AncientRPGGuildRanks> gMember;
	public static Collection<AncientRPGGuild> guilds = Collections.newSetFromMap(new ConcurrentHashMap<AncientRPGGuild, Boolean>());

	// ====
	// Permissionnodes
	// ====
	public static final String gNodeCreate = "AncientRPG.guild.create";
	public static final String gNodeJoin = "AncientRPG.guild.join";
	public static final String gNodeAdmin = "AncientRPG.guild.admin";

	// ====
	// confignodes/variables
	// ====
	protected static final String gConfigIconomyEnabled = "guild.Iconomyenabled";
	protected static final String gConfigSize = "guild.size";
	protected static final String gConfigEnabled = "guild.guild enabled";
	protected static final String gConfigCost = "guild.cost";
	protected static final String gConfigCanToggleff = "guild.Can toggle ff";
	protected static final String gConfigSpawnEnabled = "guild.guildspawn enabled";
	protected static final String gConfigTagEnabled = "guild.guildtag enabled";
	public static int maxPlayers = 15;
	public static boolean enabled = true;
	public static boolean tagenabled = true;
	public static boolean canToggleff = true;
	public static boolean economyenabled = true;
	public static double cost = 300;
	public static boolean spawnEnabled = false;

	// ====
	// Constructor
	// ====
	public AncientRPGGuild(String name, UUID leaderuuid) {
		this.guildName = name;
		this.motd = " ";
		if (AncientRPG.iConomyEnabled()) {
			accountName = "[g]" + guildName.replace(' ', '_');
			AncientRPG.economy.createBank(accountName, PlayerFinder.getPlayerName(leaderuuid));
		}
		gLeader = leaderuuid;
		gMember = new HashMap<UUID, AncientRPGGuildRanks>();
		gMember.put(leaderuuid, AncientRPGGuildRanks.LEADER);
		guilds.add(this);
		System.out.println("Created a new guild -- Name: _" + name + "_");
		writeGuild(this);
	}

	public AncientRPGGuild() {
	}

	// ====
	// nonstatic Methods
	// ====

	public HashMap<UUID, AncientRPGGuildRanks> getGuildMembers() {
		return gMember;
	}

	public void sendMessage(String message, Player sender) {
		for (UUID uuid : gMember.keySet()) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null) {
				p.sendMessage(ChatColor.GREEN + "<Guild>" + AncientRPGGuildRanks.getChatColorByRank(gMember.get(sender.getUniqueId())) + sender.getName() + ChatColor.GREEN + ": " + message);
			}
		}
	}

	public void broadcastMessage(String message) {
		for (UUID uuid : gMember.keySet()) {
			Player p = Bukkit.getPlayer(uuid);
			if (p != null) p.sendMessage(message);
		}
	}

	public void addMember(UUID uuid, AncientRPGGuildRanks rank) {
		gMember.put(uuid, rank);
		AncientRPGGuild.writeGuild(this);
	}

	public void grantRights(UUID uuid, AncientRPGGuildRanks rank) {
		gMember.put(uuid, rank);
		AncientRPGGuild.writeGuild(this);
	}

	public void kickMember(UUID uuid) {
		AncientRPGGuildRanks gr = gMember.get(uuid);
		gMember.remove(uuid);
		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		this.broadcastMessage(AncientRPG.brand2 + AncientRPGGuildRanks.getChatColorByRank(gr) + p.getName() + ChatColor.GREEN + " was kicked out of the guild");
		if (p.isOnline()) {
			p.getPlayer().sendMessage(AncientRPG.brand2 + "You were kicked out of your guild");
		}
	}

	public void giveNextLeader() {
		Set<UUID> uuids = gMember.keySet();
		for (UUID uuid : uuids) {
			if (gMember.get(uuid) == AncientRPGGuildRanks.CO_LEADER) {
				gLeader = uuid;
				gMember.put(uuid, AncientRPGGuildRanks.LEADER);
				if (AncientRPG.iConomyEnabled()) {
					double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
					AncientRPG.economy.deleteBank(this.accountName);
					AncientRPG.economy.createBank(this.accountName, Bukkit.getOfflinePlayer(this.gLeader).getName());
					AncientRPG.economy.bankDeposit(this.accountName, balance);
				}
				this.broadcastMessage(AncientRPG.brand2 + ChatColor.DARK_RED + Bukkit.getPlayer(gLeader) + " is the new Leader of the guild.");

				AncientRPGGuild.writeGuild(this);
				return;
			}
		}
		this.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + " this guild has been disbanded because no one of the members can be a Leader.");
		if (AncientRPG.iConomyEnabled()) { // ??? -- wofür? mega unnötig
			double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
			AncientRPG.economy.depositPlayer(PlayerFinder.getPlayerName(gLeader), balance);
		}
		guilds.remove(this);
		AncientRPGGuild.writeGuild(this);
	}

	public void disband(boolean admin) {
		if (admin) {
			for (UUID uuid : gMember.keySet()) {
				Player p = Bukkit.getPlayer(uuid);
				if (p != null) {
					p.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Your guild has been disbanded by an admin.");
				}
			}
		} else {
			for (UUID uuid : gMember.keySet()) {
				Player p = Bukkit.getPlayer(uuid);
				if (p != null) {
					p.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Your guild has been disbanded by the leader.");
				}
			}
		}
		
		if (AncientRPG.iConomyEnabled()) {
			double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
			AncientRPG.economy.depositPlayer(PlayerFinder.getPlayerName(gLeader), balance);
		}
		
		AncientRPGGuild.deleteGuild(this);
	}

	/*
	 * public GuildSafe safeAtPos(Coordinate c) { if (safes != null &&
	 * safes.size() > 0) { for (GuildSafe gs : safes) { if
	 * (gs.isPosProtected(c)) { return gs; } } } return null; }
	 */

	// ====
	// static Methods
	// ====

	public String getGuildName() {
		return guildName;
	}

	public static void processCommand(CommandSender sender, String[] args, AncientRPG main) {
		if (args.length == 0) {
			GuildCommandHelp.processHelp(sender, args);
			return;
		}

		// ====
		// create guild
		// ====
		if (args[0].equals("create")) {
			GuildCommandCreate.processCreate(sender, args);
		}

		// ====
		// invite
		// ====
		else if (args[0].equals("invite")) {
			GuildCommandInvite.processInvite(sender, args);
		}

		// ====
		// accept
		// ====
		else if (args[0].equals("accept")) {
			GuildCommandAccept.processAccept(sender);
		}

		// ====
		// reject
		// ====
		else if (args[0].equals("reject")) {
			GuildCommandReject.processReject(sender);
		}

		// ====
		// motd
		// ====
		else if (args[0].equals("motd")) {
			GuildCommandMotd.processMotd(sender);
		}

		// ====
		// setmotd
		// ====
		else if (args[0].equals("setmotd")) {
			GuildCommandSetmMotd.processSetmotd(sender, args);
		}

		// ====
		// gsay
		// ====
		else if (args[0].equals("chat")) {
			GuildCommandChat.processChat(sender, args);
		}

		// ====
		// ggrant
		// ====
		else if (args[0].equals("grant")) {
			GuildCommandGrant.processGrant(sender, args);
		}

		// ====
		// gonline
		// ====
		else if (args[0].equals("online")) {
			GuildCommandOnline.processOnline(sender);
		}

		// ====
		// glist
		// ====
		else if (args[0].equals("list")) {
			GuildCommandList.processList(sender, args);
		}

		// ====
		// gkick
		// ====
		else if (args[0].equals("kick")) {
			GuildCommandKick.processKick(sender, args);
		}

		// ====
		// gleave
		// ====
		else if (args[0].equals("leave")) {
			GuildCommandLeave.processLeave(sender);
		}

		// ====
		// setguildspawn
		// ====
		else if (args[0].equals("setguildspawn")) {
			GuildSetSpawnCommand.setGuildSpawnCommand(sender);
		}

		// ====
		// gmemberlist
		// ====
		else if (args[0].equals("memberlist")) {
			GuildCommandMemberlist.processMemberList(sender);
		}

		// ====
		// gdisband
		// ====
		else if (args[0].equals("disband")) {
			GuildCommandDisband.processDisband(sender);
		}
		// ====
		// gtoggle
		// ====
		else if (args[0].equals("toggle")) {
			GuildCommandToggle.processToggle(sender);
		}
		
		// ====
		// toggle friendly fire
		// ====
		else if (args[0].equals("toggleff")) {
			GuildCommandToggleFriendlyFire.processFF(sender);
		}

		// ====
		// ginfo
		// ====
		else if (args[0].equals("info")) {
			GuildCommandInfo.processInfo(sender);
		}

		// ====
		// ga
		// ====
		else if (args[0].equals("admin")) {
			GuildCommandAdmin.processAdmin(sender, args);
		}

		// ====
		// ghelp
		// ====
		else if (args[0].equals("help")) {
			GuildCommandHelp.processHelp(sender, args);
		}
		// ====
		// guild tag
		// ====
		else if (args[0].equals("settag")) {
			GuildCommandSetTag.processSetTag(sender, args);
		}

		// ====
		// Economy stuff
		// ====
		else if (AncientRPGGuild.economyenabled && AncientRPG.economy != null) {

			// ====
			// gdeposit
			// ====
			if (args[0].equals("deposit")) {
				GuildCommandDeposit.processDeposit(sender, args);
			}

			// ====
			// gwithdraw
			// ====
			else if (args[0].equals("withdraw")) {
				GuildCommandWithdraw.processWithdraw(sender, args);
			}

			// ====
			// gmoney
			// ====
			else if (args[0].equals("money")) {
				GuildCommandMoney.processMoney(sender);
			}
		}
	}

	public static void setTag(Player p) {
		if (p == null) return;
		
		AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(p.getUniqueId());
		if (mGuild != null && mGuild.tag != null && !mGuild.tag.equals("")) {
			String tag = "<" + HelpList.replaceChatColor(mGuild.tag) + ">";
			if (!p.getDisplayName().contains(tag)) p.setDisplayName(tag + p.getDisplayName());
		}
	}

	public static void processJoin(final PlayerJoinEvent event) {
		final Player p = event.getPlayer();
		final AncientRPGGuild guild = getPlayersGuild(p.getUniqueId());
		if (guild != null) {
			guild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientRPGGuildRanks.getChatColorByRank(guild.getGuildMembers().get(p.getUniqueId())) + p.getName() + ChatColor.GREEN + " is now online.");
			AncientRPGGuild.writeGuild(guild);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					event.getPlayer().sendMessage(ChatColor.GREEN + "<Guild> MOTD: " + guild.motd);
				}
			}, 3000);
		}
	}

	// UNUSED
	public static void processQuit(PlayerQuitEvent event) {
		AncientRPGGuild mGuild = getPlayersGuild(event.getPlayer().getUniqueId());
		if (mGuild != null) {
			mGuild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientRPGGuildRanks.getChatColorByRank(mGuild.getGuildMembers().get(event.getPlayer().getUniqueId())) + event.getPlayer().getName() + ChatColor.GREEN + " is now offline.");
			writeGuild(mGuild);
		}
	}

	public static void writeGuilds() {
		File basepath = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator);
		if (!basepath.exists()) {
			basepath.mkdir();
		}
		
		for (AncientRPGGuild guild : guilds) {
			File f = new File(basepath.getPath() + guild.guildName + ".guild");
			if (f.exists()) {
				f.renameTo(new File(f.getName() + "old"));
				f.delete();
			}
			YamlConfiguration guildConfig = new YamlConfiguration();
			guildConfig.set("Guild.Name", guild.guildName);
			guildConfig.set("Guild.Accountname", guild.accountName);
			guildConfig.set("Guild.Leader", guild.gLeader.toString());
			guildConfig.set("Guild.FF", guild.friendlyFire);
			guildConfig.set("Guild.Motd", guild.motd);
			guildConfig.set("Guild.tag", guild.tag);
			
			if (guild.spawnLocation != null) {
				guildConfig.set("Guild.spawnworld", guild.spawnLocation.wName);
				guildConfig.set("Guild.spawnx", guild.spawnLocation.x);
				guildConfig.set("Guild.spawny", guild.spawnLocation.y);
				guildConfig.set("Guild.spawnz", guild.spawnLocation.z);
			}
			
			int i = 0;
			for (UUID uuid : guild.gMember.keySet()) {
				guildConfig.set("Guild.Members." + i, uuid.toString() + ":" + AncientRPGGuildRanks.toString(guild.gMember.get(uuid)));
				i++;
			}
			
			try {
				guildConfig.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteGuild(AncientRPGGuild guild) {
		guilds.remove(guild);
		File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + guild.guildName + ".guild");
		if (f.exists()) {
			File deleteFolder = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + "deleted");
			if (!deleteFolder.exists()) {
				deleteFolder.mkdir();
			}
			f.renameTo(new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator + "deleted" + File.separator + guild.guildName + ".guild"));
			f.delete();
		}
	}

	public static void writeGuild(AncientRPGGuild guild) {
		File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + "Guilds" + File.separator + guild.guildName + ".guild");
		if (f.exists()) {
			f.renameTo(new File(f.getName() + "old"));
			f.delete();
		}
		YamlConfiguration guildConfig = new YamlConfiguration();
		guildConfig.set("Guild.Name", guild.guildName);
		guildConfig.set("Guild.Accountname", guild.accountName);
		guildConfig.set("Guild.Leader", guild.gLeader.toString());
		guildConfig.set("Guild.FF", guild.friendlyFire);
		guildConfig.set("Guild.Motd", guild.motd);
		guildConfig.set("Guild.tag", guild.tag);
		if (guild.spawnLocation != null) {
			guildConfig.set("Guild.spawnworld", guild.spawnLocation.wName);
			guildConfig.set("Guild.spawnx", guild.spawnLocation.x);
			guildConfig.set("Guild.spawny", guild.spawnLocation.y);
			guildConfig.set("Guild.spawnz", guild.spawnLocation.z);
		}
		int i = 0;
		for (UUID uuid : guild.gMember.keySet()) {
			guildConfig.set("Guild.Members." + i, uuid.toString() + ":" + AncientRPGGuildRanks.toString(guild.gMember.get(uuid)));
			i++;
		}
		try {
			guildConfig.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadGuilds() {
		File basepath = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator);
		if (!basepath.exists()) {
			basepath.mkdir();
		}
		guilds = new HashSet<AncientRPGGuild>();

		File[] guildFiles = basepath.listFiles();
		if (guildFiles != null) {
			for (File f : guildFiles) {
				if (!f.isDirectory() && f.getName().endsWith(".guild")) {
					YamlConfiguration guildConfig = new YamlConfiguration();
						AncientRPGGuild guild = new AncientRPGGuild();
						try {
							guildConfig.load(f);
						} catch (Exception e) {
							e.printStackTrace();
							AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to load guild " + f.getName().replaceAll("\\.guild", ""));
							continue;
						}
						guild.guildName = guildConfig.getString("Guild.Name");
						guild.accountName = guildConfig.getString("Guild.Accountname");
						
						try {
							guild.gLeader = UUID.fromString(guildConfig.getString("Guild.Leader"));
						} catch (IllegalArgumentException e) {
							guild.gLeader = UUIDConverter.getUUID(guildConfig.getString("Guild.Leader"));
						}
						
						guild.motd = guildConfig.getString("Guild.Motd");
						guild.friendlyFire = guildConfig.getBoolean("Guild.FF");
						guild.tag = guildConfig.getString("Guild.tag", guild.tag);
						if (guildConfig.get("Guild.spawnworld") != null) {
							guild.spawnLocation = new SerializableLocation(new Location(Bukkit.getWorld(guildConfig.getString("Guild.spawnworld")), guildConfig.getDouble("Guild.spawnx"), guildConfig.getDouble("Guild.spawny"), guildConfig.getDouble("Guild.spawnz")));
						}
						if (!canToggleff) {
							guild.friendlyFire = true;
						}
						guild.gMember = new HashMap<UUID, AncientRPGGuildRanks>();
						int i = 0;
						String s = guildConfig.getString("Guild.Members." + i);
						while (s != null && !s.equals("")) {
							String[] regex = s.split(":");
							if (regex.length == 2) {
								UUID uuid = null;
								try {
									uuid = UUID.fromString(regex[0].trim());
								} catch (IllegalArgumentException e) {
									uuid = UUIDConverter.getUUID(regex[0].trim());
								}
								guild.gMember.put(uuid, AncientRPGGuildRanks.getGuildRankByString(regex[1].trim()));
							}
							else break;
							i++;
							s = guildConfig.getString("Guild.Members." + i);
						}
						guilds.add(guild);
				}
			}
		}
	}

	public static void writeConfig(AncientRPG plugin) {
		File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "guildconfig.yml");
		if (!newconfig.exists()) {
			try {
				newconfig.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration guildConfig = new YamlConfiguration();
		guildConfig.set(gConfigEnabled, enabled);
		guildConfig.set(gConfigSize, maxPlayers);
		guildConfig.set(gConfigCanToggleff, canToggleff);
		guildConfig.set(gConfigIconomyEnabled, economyenabled);
		guildConfig.set(gConfigCost, cost);
		guildConfig.set(gConfigSpawnEnabled, spawnEnabled);
		guildConfig.set(gConfigTagEnabled, tagenabled);
		try {
			guildConfig.save(newconfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadConfig(AncientRPG plugin) {
		File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "guildconfig.yml");
		if (newconfig.exists()) {
			YamlConfiguration yc = new YamlConfiguration();
			try {
				yc.load(newconfig);
			} catch (Exception e) {
				e.printStackTrace();
			}
			enabled = yc.getBoolean(gConfigEnabled, enabled);
			maxPlayers = yc.getInt(gConfigSize, maxPlayers);
			canToggleff = yc.getBoolean(gConfigCanToggleff, canToggleff);
			economyenabled = yc.getBoolean(gConfigIconomyEnabled, economyenabled);
			cost = yc.getDouble(gConfigCost, cost);
			spawnEnabled = yc.getBoolean(gConfigSpawnEnabled, spawnEnabled);
			tagenabled = yc.getBoolean(gConfigTagEnabled, tagenabled);
		} else {
			enabled = plugin.getConfig().getBoolean(gConfigEnabled, enabled);
			maxPlayers = plugin.getConfig().getInt(gConfigSize, maxPlayers);
			canToggleff = plugin.getConfig().getBoolean(gConfigCanToggleff, canToggleff);
			economyenabled = plugin.getConfig().getBoolean(gConfigIconomyEnabled, economyenabled);
			cost = plugin.getConfig().getDouble(gConfigCost, cost);
			spawnEnabled = plugin.getConfig().getBoolean(gConfigSpawnEnabled, spawnEnabled);
			tagenabled = plugin.getConfig().getBoolean(gConfigTagEnabled, tagenabled);
		}
	}

	public static boolean guildExists(String guildname) {
		for (AncientRPGGuild g : guilds)
			if (g.guildName.equalsIgnoreCase(guildname)) return true;
		return false;
	}

	public static AncientRPGGuild getGuildByName(String guildname) {
		for (AncientRPGGuild g : guilds) 
			if (g.guildName.equalsIgnoreCase(guildname)) return g;
		return null;
	}

	public static AncientRPGGuild getPlayersGuild(UUID uuid) {
		for (AncientRPGGuild g : guilds)
			if (g.gMember.containsKey(uuid)) return g;
		return null;
	}
}