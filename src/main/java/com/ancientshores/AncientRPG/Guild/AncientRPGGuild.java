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
    private static final long serialVersionUID = 1L;

    // ==============================================
    // Membervariables
    // ==============================================
    public String guildName;
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

    // ==============================================
    // Constructor
    // ==============================================
    public AncientRPGGuild(String name, String leadername) {
        this.guildName = name;
        this.motd = " ";
        if (AncientRPG.iConomyEnabled()) {
            accountName = "[g]" + guildName.replace(' ', '_');
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
    }

    // ==============================================
    // nonstatic Methods
    // ==============================================

    public HashMap<String, AncientRPGGuildRanks> getGuildMembers() {
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
        this.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + " this guild has been disbanded because no one of the members can be a Leader.");
        if (AncientRPG.iConomyEnabled()) {
            double balance = AncientRPG.economy.bankBalance(this.accountName).amount;
            AncientRPG.economy.depositPlayer(gLeader, balance);
        }
        guilds.remove(this);
        AncientRPGGuild.writeGuild(this);
    }

    public void disband(boolean admin) {
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

    public String getGuildName() {
        return guildName;
    }

    @SuppressWarnings("unused")
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
        else if (args[0].equals("setmotd") && AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandSetmMotd")) {
            GuildCommandSetmMotd.processSetmotd(sender, args);
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
        }

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
            String tag = "<" + HelpList.replaceChatColor(mGuild.tag) + ">";
            if (!p.getDisplayName().contains(tag)) {
                p.setDisplayName(tag + p.getDisplayName());
            }
        }
    }

    public static void processJoin(final PlayerJoinEvent playerJoinEvent) {
        final AncientRPGGuild guild = getPlayersGuild(playerJoinEvent.getPlayer().getName());
        if (guild != null) {
            guild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientRPGGuildRanks.getChatColorByRank(guild.getGuildMembers().get(playerJoinEvent.getPlayer().getName()))
                    + playerJoinEvent.getPlayer().getName() + ChatColor.GREEN + " is now online.");
            AncientRPGGuild.writeGuild(guild);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    playerJoinEvent.getPlayer().sendMessage(ChatColor.GREEN + "<Guild> motd: " + guild.motd);
                }
            }, 3000);
        }
    }

    public static void processQuit(PlayerQuitEvent playerQuitEvent) {
        AncientRPGGuild mGuild = getPlayersGuild(playerQuitEvent.getPlayer().getName());
        if (mGuild != null) {
            mGuild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientRPGGuildRanks.getChatColorByRank(mGuild.getGuildMembers().get(playerQuitEvent.getPlayer().getName()))
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
            File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + guild.guildName + ".guild");
            if (f.exists()) {
                f.renameTo(new File(f.getName() + "old"));
                f.delete();
            }
            YamlConfiguration guildConfig = new YamlConfiguration();
            guildConfig.set("Guild.Name", guild.guildName);
            guildConfig.set("Guild.Accountname", guild.accountName);
            guildConfig.set("Guild.Leader", guild.gLeader);
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
            for (String member : guild.gMember.keySet()) {
                guildConfig.set("Guild.Members." + i, member + ":" + AncientRPGGuildRanks.toString(guild.gMember.get(member)));
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
            deleteFolder.mkdir();
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
        guildConfig.set("Guild.Leader", guild.gLeader);
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
        for (String member : guild.gMember.keySet()) {
            guildConfig.set("Guild.Members." + i, member + ":" + AncientRPGGuildRanks.toString(guild.gMember.get(member)));
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
                    try {
                        AncientRPGGuild guild = new AncientRPGGuild();
                        guildConfig.load(f);
                        guild.guildName = (String) guildConfig.get("Guild.Name");
                        guild.accountName = (String) guildConfig.get("Guild.Accountname");
                        guild.gLeader = (String) guildConfig.get("Guild.Leader");
                        guild.motd = (String) guildConfig.get("Guild.Motd");
                        guild.friendlyFire = (Boolean) guildConfig.get("Guild.FF");
                        guild.tag = guildConfig.getString("Guild.tag", guild.tag);
                        if (guildConfig.get("Guild.spawnx") != null) {
                            guild.spawnLocation = new SerializableLocation(new Location(Bukkit.getWorld(guildConfig.getString("Guild.spawnworld")), guildConfig.getDouble("Guild.spawnx"), guildConfig.getDouble("Guild.spawny"),
                                    guildConfig.getDouble("Guild.spawnz")));
                        }
                        if (!canToggleff) {
                            guild.friendlyFire = true;
                        }
                        guild.gMember = new HashMap<String, AncientRPGGuildRanks>();
                        int i = 0;
                        String s = (String) guildConfig.get("Guild.Members." + i);
                        while (s != null && !s.equals("")) {
                            String[] regex = s.split(":");
                            if (regex.length == 2) {
                                guild.gMember.put(regex[0].trim(), AncientRPGGuildRanks.getGuildRankByString(regex[1].trim()));
                            } else {
                                break;
                            }
                            i++;
                            s = (String) guildConfig.get("Guild.Members." + i);
                        }
                        guilds.add(guild);
                    } catch (Exception e) {
                        e.printStackTrace();
                        AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to load guilds");
                    }
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
        guildConfig.set(gConfigIconomyEnabled, Iconomyenabled);
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
            if (g.guildName.equalsIgnoreCase(guildname)) {
                return true;
            }
        }
        return false;
    }

    public static AncientRPGGuild getGuildByName(String guildname) {
        for (AncientRPGGuild g : guilds) {
            if (g.guildName.equalsIgnoreCase(guildname)) {
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
}