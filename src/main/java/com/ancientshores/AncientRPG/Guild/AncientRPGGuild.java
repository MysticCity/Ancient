package com.ancientshores.AncientRPG.Guild;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.Commands.*;
import com.ancientshores.AncientRPG.HelpList;
import com.ancientshores.AncientRPG.Util.SerializableLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class AncientRPGGuild implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // ==============================================
    // Membervariables
    // ==============================================
    public String gName;
    public String gLeader;
    public String motd;
    public String accountName;
    public String tag;
    public boolean friendlyFire = false;
    public SerializableLocation spawnLocation;

    // ==============================================
    // HashSets/Maps
    // ==============================================
    public static final ConcurrentHashMap<Player, AncientRPGGuild> invites = new ConcurrentHashMap<Player, AncientRPGGuild>();
    public HashMap<String, AncientRPGGuildRanks> gMember;
    public static Collection<AncientRPGGuild> guilds = Collections.newSetFromMap(new ConcurrentHashMap<AncientRPGGuild, Boolean>());

    // ==============================================
    // Permissionnodes
    // ==============================================
    public static final String gNodeCreate = "AncientRPG.guild.create";
    public static final String gNodeJoin = "AncientRPG.guild.join";
    public static final String gNodeAdmin = "AncientRPG.guild.admin";

    // ==============================================
    // confignodes/variables
    // ==============================================
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
    public static boolean Iconomyenabled = true;
    public static double cost = 300;
    public static boolean spawnEnabled = false;
    boolean hasguildhouse = false;

    // GuildHouse gHouse = null;
    // protected HashSet<GuildSafe> safes = new HashSet<GuildSafe>();

    // ==============================================
    // Constructor
    // ==============================================
    public AncientRPGGuild(String name, String leadername) {
        this.gName = name;
        this.motd = " ";
        if (AncientRPG.iConomyEnabled()) {
            accountName = "[g]" + gName.replace(' ', '_');
            AncientRPG.economy.createBank(accountName, leadername);
        }
        gLeader = leadername;
        gMember = new HashMap<String, AncientRPGGuildRanks>();
        gMember.put(leadername, AncientRPGGuildRanks.LEADER);
        guilds.add(this);
        System.out.println("Created a new guild -- Name: _" + name + "_");
        writeGuild(this);
    }

    public AncientRPGGuild() {
        // TODO Auto-generated constructor stub
    }

    // ==============================================
    // nonstatic Methods
    // ==============================================

    public HashMap<String, AncientRPGGuildRanks> getgMember() {
        return gMember;
    }

    public void sendMessage(String message, Player sender) {
        for (String p : gMember.keySet()) {
            Player mPlayer = AncientRPG.plugin.getServer().getPlayer(p);
            if (mPlayer != null && mPlayer.isOnline()) {
                mPlayer.sendMessage(ChatColor.GREEN + "<Guild>" + AncientRPGGuildRanks.getChatColorByRank(gMember.get(sender.getName())) + sender.getName() + ChatColor.GREEN + ": " + message);
            }
        }
    }

    public void broadcastMessage(String message) {
        for (String p : gMember.keySet()) {
            Player mPlayer = AncientRPG.plugin.getServer().getPlayer(p);
            if (mPlayer != null && mPlayer.isOnline()) {
                if (!p.equals(gLeader)) {
                    mPlayer.sendMessage(message);
                } else {
                    mPlayer.sendMessage(message);
                }
            }
        }
    }

    public void addMember(String name, AncientRPGGuildRanks rank) {
        gMember.put(name, rank);
        AncientRPGGuild.writeGuild(this);
    }

    public void grantRights(String name, AncientRPGGuildRanks rank) {
        gMember.put(name, rank);
        AncientRPGGuild.writeGuild(this);
    }

    public void kickMember(String name) {
        AncientRPGGuildRanks gr = gMember.get(name);
        gMember.remove(name);
        this.broadcastMessage(AncientRPG.brand2 + AncientRPGGuildRanks.getChatColorByRank(gr) + name + ChatColor.GREEN + " was kicked out of the guild");
        Player p = AncientRPG.plugin.getServer().getPlayer(name);
        if (p != null && p.isOnline()) {
            p.sendMessage(AncientRPG.brand2 + "You were kicked out of your guild");
        }
    }

    public void giveNextLeader() {
        Set<String> names = gMember.keySet();
        for (String s : names) {
            if (gMember.get(s) == AncientRPGGuildRanks.CO_LEADER) {
                gLeader = s;
                this.gMember.put(s, AncientRPGGuildRanks.LEADER);
                if (AncientRPG.iConomyEnabled()) {
                    double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
                    AncientRPG.economy.deleteBank(this.accountName);
                    AncientRPG.economy.createBank(this.accountName, this.gLeader);
                    AncientRPG.economy.bankDeposit(this.accountName, balance);
                }
                this.broadcastMessage(AncientRPG.brand2 + ChatColor.DARK_RED + gLeader + " is the new Leader of the guild.");
                return;
            }
        }
        /*
         * if (AncientRPG.plugin.economy != null) {
		 * AncientRPG.economy.getAccount("g: " + this.gName).remove(); }
		 */
        this.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + " this guild has been disbanded because no one of the members can be a Leader.");
        if (AncientRPG.iConomyEnabled()) {
            double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
            AncientRPG.economy.depositPlayer(gLeader, balance);
        }
        guilds.remove(this);
        AncientRPGGuild.writeGuild(this);
    }

    public void disband(boolean admin) {
        /*
		 * if (AncientRPG.plugin.economy != null) {
		 * iConomy.getAccount(this.gName).remove(); }
		 */
        if (admin) {
            this.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "Your guild has been disbanded by an admin.");
            if (AncientRPG.iConomyEnabled()) {
                double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
                AncientRPG.economy.depositPlayer(gLeader, balance);
            }
            AncientRPGGuild.deleteGuild(this);
        } else {
            this.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "Your guild has been disbanded by the leader.");
            if (AncientRPG.iConomyEnabled()) {
                double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
                AncientRPG.economy.depositPlayer(gLeader, balance);
            }
            AncientRPGGuild.deleteGuild(this);
        }
    }

	/*
	 * public GuildSafe safeAtPos(Coordinate c) { if (safes != null &&
	 * safes.size() > 0) { for (GuildSafe gs : safes) { if
	 * (gs.isPosProtected(c)) { return gs; } } } return null; }
	 */

    // ==============================================
    // static Methods
    // ==============================================

	/*
	 * public GuildHouse getgHouse() { return gHouse; }
	 * 
	 * public HashSet<GuildSafe> getSafe() { return safes; }
	 */

    public String getgName() {
        return gName;
    }

    public static void processCommand(CommandSender sender, String[] args, AncientRPG main) {

        if (args.length == 0) {
            GuildCommandHelp.processHelp(sender, args);
            return;
        }

        // ==============================================
        // create guild
        // ==============================================
        if (args[0].equals("create") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandCreate")) {
            GuildCommandCreate.processCreate(sender, args);
        }

        // ==============================================
        // invite
        // ==============================================
        else if (args[0].equals("invite") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandInvite")) {
            GuildCommandInvite.processInvite(sender, args);
        }

        // ==============================================
        // accept
        // ==============================================
        else if (args[0].equals("accept") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandAccept")) {
            GuildCommandAccept.processAccept(sender);
        }

        // ==============================================
        // reject
        // ==============================================
        else if (args[0].equals("reject") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandReject")) {
            GuildCommandReject.processReject(sender);
        }

        // ==============================================
        // motd
        // ==============================================
        else if (args[0].equals("motd") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandMotd")) {
            GuildCommandMotd.processMotd(sender);
        }

        // ==============================================
        // setmotd
        // ==============================================
        else if (args[0].equals("setmotd") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandSetmotd")) {
            GuildCommandSetmotd.processSetmotd(sender, args);
        }

        // ==============================================
        // gsay
        // ==============================================
        else if (args[0].equals("chat") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandChat")) {
            GuildCommandChat.processChat(sender, args);
        }

        // ==============================================
        // ggrant
        // ==============================================
        else if (args[0].equals("grant") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandGrant")) {
            GuildCommandGrant.processGrant(sender, args);
        }

        // ==============================================
        // gonline
        // ==============================================
        else if (args[0].equals("online") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandOnline")) {
            GuildCommandOnline.processOnline(sender);
        }

        // ==============================================
        // glist
        // ==============================================
        else if (args[0].equals("list") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandList")) {
            GuildCommandList.processList(sender, args);
        }

        // ==============================================
        // gkick
        // ==============================================
        else if (args[0].equals("kick") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandKick")) {
            GuildCommandKick.processKick(sender, args);
        }

        // ==============================================
        // gleave
        // ==============================================
        else if (args[0].equals("leave") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandLeave")) {
            GuildCommandLeave.processLeave(sender);
        }

        // ==============================================
        // setguildspawn
        // ==============================================
        else if (args[0].equals("setguildspawn")) {
            GuildSetSpawnCommand.setGuildSpawnCommand(sender);
        }

        // ==============================================
        // gmemberlist
        // ==============================================
        else if (args[0].equals("memberlist") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandDisband")) {
            GuildCommandMemberlist.processMemberList(sender);
        }

        // ==============================================
        // gdisband
        // ==============================================
        else if (args[0].equals("disband") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandDisband")) {
            GuildCommandDisband.processDisband(sender);
        }
        // ==============================================
        // gtoggle
        // ==============================================
        else if (args[0].equals("toggle") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandToggle")) {
            GuildCommandToggle.processToggle(sender);
        } else if (args[0].equals("toggleff") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandToggleFriendlyFire")) {
            GuildCommandToggleFriendlyFire.processFF(sender);
        }

        // ==============================================
        // ginfo
        // ==============================================
        else if (args[0].equals("info") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandDisband")) {
            GuildCommandInfo.processInfo(sender);
        }

        // ==============================================
        // ga
        // ==============================================
        else if (args[0].equals("admin") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandAdmin")) {
            GuildCommandAdmin.processAdmin(sender, args);
        }

        // ==============================================
        // ghelp
        // ==============================================
        else if (args[0].equals("help") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandHelp")) {
            GuildCommandHelp.processHelp(sender, args);
        }
        // ==============================================
        // guild tag
        // ==============================================
        else if (args[0].equals("settag")) {
            GuildCommandSetTag.processSetTag(sender, args);
        } /*
		 * else if (args[0].equals("ghouse") &&
		 * AncientRPG.classExisting("de.pylamo.rpgplugin.GuildHouse")) { Guild
		 * mGuild = getPlayersGuild(mPlayer.getName()); if (mGuild != null) { if
		 * (args.length >= 1) { if (mGuild.gMember.get(mPlayer.getName()) ==
		 * GuildRanks.LEADER || mGuild.gMember.get(mPlayer.getName()) ==
		 * GuildRanks.CO_LEADER) { GuildHouse.processCommands(mPlayer, mGuild,
		 * args); } else { mPlayer.sendMessage(ChatColor.RED +
		 * "You don't have the right to use that command."); } } else {
		 * mPlayer.sendMessage(ChatColor.RED +
		 * "Correct usage: /ghouse <create/delete>"); } } else {
		 * mPlayer.sendMessage(ChatColor.RED + "You aren't in a guild."); } }
		 * else if (args[0].equals("ghouse")) { Guild mGuild =
		 * getPlayersGuild(mPlayer.getName()); if (mGuild != null) { if
		 * (args.length >= 1) { if (mGuild.gMember.get(mPlayer.getName()) ==
		 * GuildRanks.LEADER || mGuild.gMember.get(mPlayer.getName()) ==
		 * GuildRanks.CO_LEADER) { GuildHouse.processCommands(mPlayer, mGuild,
		 * args); } else { mPlayer.sendMessage(ChatColor.RED +
		 * "You don't have the right to use that command."); } } else {
		 * mPlayer.sendMessage(ChatColor.RED +
		 * "Correct usage: /ghouse <create/delete>"); } } else {
		 * mPlayer.sendMessage(ChatColor.RED + "You aren't in a guild."); } }
		 * else if (args[0].equals("gsafe") &&
		 * AncientRPG.classExisting("de.pylamo.rpgplugin.GuildSafe")) { Guild
		 * mGuild = getPlayersGuild(mPlayer.getName()); if (mGuild != null) { if
		 * (args.length >= 1) { if (mGuild.gMember.get(mPlayer.getName()) ==
		 * GuildRanks.LEADER || mGuild.gMember.get(mPlayer.getName()) ==
		 * GuildRanks.CO_LEADER) { GuildSafe.processCommands(mPlayer, mGuild,
		 * args); } else { mPlayer.sendMessage(ChatColor.RED +
		 * "You don't have the right to use that command."); } } else {
		 * mPlayer.sendMessage(ChatColor.RED +
		 * "Correct usage: /gsafe <create/delete>"); } } else {
		 * mPlayer.sendMessage(ChatColor.RED + "You aren't in a guild."); } }
		 */

        // ==============================================
        // Iconomy stuff
        // ==============================================
        else if (AncientRPGGuild.Iconomyenabled && AncientRPG.economy != null) {

            // ==============================================
            // gdeposit
            // ==============================================
            if (args[0].equals("deposit") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandDeposit")) {
                GuildCommandDeposit.processDeposit(sender, args);
            }

            // ==============================================
            // gwithdraw
            // ==============================================
            else if (args[0].equals("withdraw") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandWithdraw")) {
                GuildCommandWithdraw.processWithdraw(sender, args);
            }

            // ==============================================
            // gmoney
            // ==============================================
            else if (args[0].equals("money") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandMoney")) {
                GuildCommandMoney.processMoney(sender);
            }
        }
    }

    public static void setTag(Player p) {
        if (p == null) {
            return;
        }
        AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(p.getName());
        if (mGuild != null && mGuild.tag != null && !mGuild.tag.equals("")) {
            String tag = HelpList.replaceChatColor(mGuild.tag);
            tag = "<" + tag + ">";
            if (!p.getDisplayName().contains(tag)) {
                p.setDisplayName(tag + p.getDisplayName());
            }
        }
    }

    public static void processJoin(final PlayerJoinEvent playerJoinEvent) {
        final AncientRPGGuild mGuild = getPlayersGuild(playerJoinEvent.getPlayer().getName());
        if (mGuild != null) {
            mGuild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientRPGGuildRanks.getChatColorByRank(mGuild.getgMember().get(playerJoinEvent.getPlayer().getName()))
                    + playerJoinEvent.getPlayer().getName() + ChatColor.GREEN + " is now online.");
            AncientRPGGuild.writeGuild(mGuild);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    playerJoinEvent.getPlayer().sendMessage(ChatColor.GREEN + "<Guild> motd: " + mGuild.motd);
                }
            }, 3000);
        }
    }

    public static void processQuit(PlayerQuitEvent playerQuitEvent) {
        AncientRPGGuild mGuild = getPlayersGuild(playerQuitEvent.getPlayer().getName());
        if (mGuild != null) {
            mGuild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientRPGGuildRanks.getChatColorByRank(mGuild.getgMember().get(playerQuitEvent.getPlayer().getName()))
                    + playerQuitEvent.getPlayer().getName() + ChatColor.GREEN + " is now offline.");
            AncientRPGGuild.writeGuild(mGuild);
        }
    }

    public static void writeGuilds() {
        loadGuilds();
        File basepath = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator);
        if (!basepath.exists()) {
            basepath.mkdir();
        }
        for (AncientRPGGuild guild : guilds) {
            File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + guild.gName + ".guild");
            if (f.exists()) {
                f.renameTo(new File(f.getName() + "old"));
                f.delete();
            }
            YamlConfiguration yc = new YamlConfiguration();
            yc.set("Guild.Name", guild.gName);
            yc.set("Guild.Accountname", guild.accountName);
            yc.set("Guild.Leader", guild.gLeader);
            yc.set("Guild.FF", guild.friendlyFire);
            yc.set("Guild.Motd", guild.motd);
            yc.set("Guild.tag", guild.tag);
            if (guild.spawnLocation != null) {
                yc.set("Guild.spawnworld", guild.spawnLocation.wName);
                yc.set("Guild.spawnx", guild.spawnLocation.x);
                yc.set("Guild.spawny", guild.spawnLocation.y);
                yc.set("Guild.spawnz", guild.spawnLocation.z);
            }
            int i = 0;
            for (String member : guild.gMember.keySet()) {
                yc.set("Guild.Members." + i, member + ":" + AncientRPGGuildRanks.toString(guild.gMember.get(member)));
                i++;
            }
            try {
                yc.save(f);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		/*
		 * File outputfile = new File("plugins" + File.separator + "AncientRPG"
		 * + File.separator + "guilds.dat"); File oldfile = new File("plugins" +
		 * File.separator + "AncientRPG" + File.separator + "guilds.dat.old");
		 * if (outputfile.exists() && backup) { if (oldfile.exists()) {
		 * oldfile.delete(); } outputfile.renameTo(oldfile); } File outputfilen
		 * = new File("plugins" + File.separator + "AncientRPG" + File.separator
		 * + "guilds.dat"); try { outputfilen.createNewFile(); FileOutputStream
		 * fos = new FileOutputStream(outputfilen); ObjectOutputStream oos = new
		 * ObjectOutputStream(fos); oos.writeObject(AncientRPGGuild.guilds);
		 * oos.flush(); fos.close(); oos.close(); } catch (Exception e) {
		 * AncientRPG.plugin .getServer() .getLogger() .log(Level.SEVERE,
		 * "<PylamoRPG> Failed to save guilds, falling back to backup");
		 * AncientRPG.plugin.getServer().getLogger() .log(Level.SEVERE,
		 * "<AncientRPG> " + e.getMessage()); e.printStackTrace();
		 * outputfilen.delete(); oldfile.renameTo(outputfilen); }
		 */
    }

    public static void deleteGuild(AncientRPGGuild guild) {
        guilds.remove(guild);
        File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + guild.gName + ".guild");
        if (f.exists()) {
            File deleteFolder = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + "deleted");
            deleteFolder.mkdir();
            f.renameTo(new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator + "deleted" + File.separator + guild.gName + ".guild"));
            f.delete();
        }
    }

    public static void writeGuild(AncientRPGGuild guild) {
        File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + "Guilds" + File.separator + guild.gName + ".guild");
        if (f.exists()) {
            f.renameTo(new File(f.getName() + "old"));
            f.delete();
        }
        YamlConfiguration yc = new YamlConfiguration();
        yc.set("Guild.Name", guild.gName);
        yc.set("Guild.Accountname", guild.accountName);
        yc.set("Guild.Leader", guild.gLeader);
        yc.set("Guild.FF", guild.friendlyFire);
        yc.set("Guild.Motd", guild.motd);
        yc.set("Guild.tag", guild.tag);
        if (guild.spawnLocation != null) {
            yc.set("Guild.spawnworld", guild.spawnLocation.wName);
            yc.set("Guild.spawnx", guild.spawnLocation.x);
            yc.set("Guild.spawny", guild.spawnLocation.y);
            yc.set("Guild.spawnz", guild.spawnLocation.z);
        }
        int i = 0;
        for (String member : guild.gMember.keySet()) {
            yc.set("Guild.Members." + i, member + ":" + AncientRPGGuildRanks.toString(guild.gMember.get(member)));
            i++;
        }
        try {
            yc.save(f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
        if (guilds == null) {
            return;
        }

        for (File f : guildFiles) {
            if (!f.isDirectory() && f.getName().endsWith(".guild")) {
                YamlConfiguration yc = new YamlConfiguration();
                try {
                    AncientRPGGuild guild = new AncientRPGGuild();
                    yc.load(f);
                    guild.gName = (String) yc.get("Guild.Name");
                    guild.accountName = (String) yc.get("Guild.Accountname");
                    guild.gLeader = (String) yc.get("Guild.Leader");
                    guild.motd = (String) yc.get("Guild.Motd");
                    guild.friendlyFire = (Boolean) yc.get("Guild.FF");
                    guild.tag = yc.getString("Guild.tag", guild.tag);
                    if (yc.get("Guild.spawnx") != null) {
                        guild.spawnLocation = new SerializableLocation(new Location(Bukkit.getWorld(yc.getString("Guild.spawnworld")), yc.getDouble("Guild.spawnx"), yc.getDouble("Guild.spawny"),
                                yc.getDouble("Guild.spawnz")));
                    }
                    if (!canToggleff) {
                        guild.friendlyFire = true;
                    }
                    guild.gMember = new HashMap<String, AncientRPGGuildRanks>();
                    int i = 0;
                    String s = (String) yc.get("Guild.Members." + i);
                    while (s != null && !s.equals("")) {
                        String[] regex = s.split(":");
                        if (regex != null && regex.length == 2) {
                            guild.gMember.put(regex[0].trim(), AncientRPGGuildRanks.getGuildRankByString(regex[1].trim()));
                        } else {
                            break;
                        }
                        i++;
                        s = (String) yc.get("Guild.Members." + i);
                    }
                    guilds.add(guild);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to load guilds");
                }
            }
        }
		/*
		 * File inputfile = new File("plugins" + File.separator + "AncientRPG" +
		 * File.separator + "guilds.dat"); if (inputfile.exists()) { try {
		 * FileInputStream fis = new FileInputStream(inputfile);
		 * ObjectInputStream ois = new ObjectInputStream(fis); Object input =
		 * ois.readObject(); HashSet<AncientRPGGuild> inputhashset = null; if
		 * (input instanceof HashSet<?>) { inputhashset =
		 * (HashSet<AncientRPGGuild>) input; } if (inputhashset != null) {
		 * AncientRPGGuild.guilds = inputhashset; } else { AncientRPG.plugin
		 * .getServer() .getLogger() .log(Level.WARNING,
		 * "<AncientRPG> Unable load guilds, please try the backup file"); }
		 * fis.close(); ois.close(); } catch (Exception e) { AncientRPG.plugin
		 * .getServer() .getLogger() .log(Level.WARNING,
		 * "<AncientRPG> Unable load guilds, please try the backup file");
		 * e.printStackTrace(); }
		 * 
		 * } else { AncientRPG.plugin.getServer().getLogger()
		 * .log(Level.WARNING, "<AncientRPG> No guild file detected."); }
		 */
    }

	/*
	 * public static GuildSafe getGuildSafeAtPos(Coordinate c) { for (Guild g :
	 * guilds) { GuildSafe s = g.safeAtPos(c); if (s != null) { return s; } }
	 * return null; }
	 */

    public static void writeConfig(AncientRPG plugin) {
        File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "guildconfig.yml");
        if (!newconfig.exists()) {
            try {
                newconfig.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        YamlConfiguration yc = new YamlConfiguration();
        yc.set(gConfigEnabled, enabled);
        yc.set(gConfigSize, maxPlayers);
        yc.set(gConfigCanToggleff, canToggleff);
        yc.set(gConfigIconomyEnabled, Iconomyenabled);
        yc.set(gConfigCost, cost);
        yc.set(gConfigSpawnEnabled, spawnEnabled);
        yc.set(gConfigTagEnabled, tagenabled);
        try {
            yc.save(newconfig);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            enabled = yc.getBoolean(gConfigEnabled, enabled);
            maxPlayers = yc.getInt(gConfigSize, maxPlayers);
            canToggleff = yc.getBoolean(gConfigCanToggleff, canToggleff);
            Iconomyenabled = yc.getBoolean(gConfigIconomyEnabled, Iconomyenabled);
            cost = yc.getDouble(gConfigCost, cost);
            spawnEnabled = yc.getBoolean(gConfigSpawnEnabled, spawnEnabled);
            tagenabled = yc.getBoolean(gConfigTagEnabled, tagenabled);
        } else {
            enabled = plugin.getConfig().getBoolean(gConfigEnabled, enabled);
            maxPlayers = plugin.getConfig().getInt(gConfigSize, maxPlayers);
            canToggleff = plugin.getConfig().getBoolean(gConfigCanToggleff, canToggleff);
            Iconomyenabled = plugin.getConfig().getBoolean(gConfigIconomyEnabled, Iconomyenabled);
            cost = plugin.getConfig().getDouble(gConfigCost, cost);
            spawnEnabled = plugin.getConfig().getBoolean(gConfigSpawnEnabled, spawnEnabled);
            tagenabled = plugin.getConfig().getBoolean(gConfigTagEnabled, tagenabled);
        }
    }

    public static boolean guildExists(String guildname) {
        for (AncientRPGGuild g : guilds) {
            if (g.gName.equalsIgnoreCase(guildname)) {
                return true;
            }
        }
        return false;
    }

    public static AncientRPGGuild getGuildByName(String guildname) {
        for (AncientRPGGuild g : guilds) {
            if (g.gName.equalsIgnoreCase(guildname)) {
                return g;
            }
        }
        return null;
    }

    public static AncientRPGGuild getPlayersGuild(String playername) {
        for (AncientRPGGuild g : guilds) {
            if (g.gMember.containsKey(playername)) {
                return g;
            }
        }
        return null;
    }
	/*
	 * public static HashSet<GuildHouse> getGuildHouses(){ HashSet<GuildHouse> s
	 * = new HashSet<GuildHouse>(); for(Guild g: guilds){ if(g.hasguildhouse &&
	 * g.gHouse != null){ s.add(g.gHouse); } } return s; }
	 */
}