package com.ancientshores.AncientRPG;

import com.ancientshores.AncientRPG.API.ApiManager;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.AncientRPGClassHelp;
import com.ancientshores.AncientRPG.Classes.Commands.ClassUnbindCommand;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Classes.Spells.Conditions.IArgument;
import com.ancientshores.AncientRPG.Classes.Spells.Parameter;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Commands.HelpCommand;
import com.ancientshores.AncientRPG.Commands.ReloadCommand;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Experience.SetXpCommand;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandChat;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandHelp;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.HP.HPCommand;
import com.ancientshores.AncientRPG.Listeners.AncientRPGBlockListener;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandChat;
import com.ancientshores.AncientRPG.Party.Commands.PartyCommandHelp;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.AncientRPG.Spells.Commands.SpellBindCommand;
import com.ancientshores.AncientRPG.Spells.Commands.SpellFreeZoneListener;
import com.ancientshores.AncientRPG.Spells.Commands.SpellsCommandExecutor;
import com.ancientshores.AncientRPG.Util.FlatFileConnector;
import com.ancientshores.AncientRPG.Util.SerializableZone;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AncientRPG extends JavaPlugin {
    /*
     * // ClassListeners private final AncientRPGPlayerListener playerListener =
	 * new AncientRPGPlayerListener(this); private final AncientRPGBlockListener
	 * blockListener = new AncientRPGBlockListener(this); private final
	 * AncientRPGEntityListener entityListener = new
	 * AncientRPGEntityListener(this);
	 */

    public Config mConfig;
    public static Permission permissionHandler;
    public static AncientRPG plugin;
    public static Economy economy = null;
    public static final String AdminPermission = "AncientRPG.admin";
    public static CommandMap commandMap = null;
    static ApiManager manager;
    public final FlatFileConnector fc = new FlatFileConnector(this);
    public Thread bukkitThread;
    public CommandPlayer ef;
    public static String languagecode = "en";
    // private Vault vault = null;
    // ClassListeners

    HashMap<SpellInformationObject, Player> executingSpells = new HashMap<SpellInformationObject, Player>();

    public final Logger log = Logger.getLogger("Minecraft");// Define your logger
    public static String partyCommand = "party";
    public static final String partyCommandNode = "AncientRPG.Commands.party";
    public static String guildCommand = "guild";
    public static final String guildCommandNode = "AncientRPG.Commands.guild";
    public static String classCommand = "class";
    public static final String classCommandNode = "AncientRPG.Commands.class";
    public static String levelCommand = "level";
    public static final String levelCommandNode = "AncientRPG.Commands.level";
    public static String hpCommand = "hp";
    public static final String hpCommandNode = "AncientRPG.Commands.hp";
    public static String raceCommand = "race";
    public static final String raceCommandNode = "AncientRPG.Commands.race";
    public static String spellsCommand = "spells";
    public static final String spellsCommandNode = "AncientRPG.Commands.spells";
    public static String manaCommand = "mana";
    public static final String manaCommandNode = "AncientRPG.Commands.mana";
    public static String arCommand = "ar";
    public static final String arCommandNode = "AncientRPG.Commands.ar";
    public static String pcCommand = "pc";
    public static final String pcCommandNode = "AncientRPG.Commands.pc";
    public static String gcCommand = "gc";
    public static final String gcCommandNode = "AncientRPG.Commands.gc";
    public static String bindCommand = "bind";
    public static final String bindCommandNode = "AncientRPG.Commands.bind";
    public static String unbindCommand = "unbind";
    public static final String unbindCommandNode = "AncientRPG.Commands.unbind";
    public static String ancientrpgCommand = "ancientrpg";
    public static final String ancientrpgCommandNode = "AncientRPG.Commands.ancientrpg";
    public static String versionString = "";
    static Locale currentLocale;
    public static String brand = "ARPG";
    public static String brand2 = "";

    public void onEnable() {
        // ==================================================================
        // initialize/log section
        // =================================================================
        this.bukkitThread = Thread.currentThread();
        this.getDataFolder().mkdir();
        AncientRPG.manager = ApiManager.getApiManager();
        plugin = this;
        Plugin vault = this.getServer().getPluginManager().getPlugin("Vault");
        if (vault != null & vault instanceof Vault) {
            log.info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));
        } else {
            log.warning(String.format("[%s] Vault was _NOT_ found! Disabling plugin.", getDescription().getName()));
            getPluginLoader().disablePlugin(this);
        }
        try {
            versionString = Bukkit.getServer().getClass().getPackage().getName()
                    .substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1, Bukkit.getServer().getClass().getPackage().getName().length());
            final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (final Exception ignored) {
        }
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ignored) {
        }
        // ==================================================================
        // event section
        // =================================================================
        setupPermissions();
        if (mConfig == null) {
            mConfig = new Config(this);
        }
        mConfig.configCheck();
        // ============================================
        // load section
        // ============================================
        new AncientRPGPlayerListener(this);
        new AncientRPGBlockListener(this);
        new AncientRPGEntityListener(this);
        mConfig.loadkeys();
        mConfig.addDefaults();
        Parameter.registerDefaultParameters();
        IArgument.addDefaults();
        com.ancientshores.AncientRPG.Classes.Spells.Command.putDefaults();
        try {
            Constructor<?> c = Class.forName("org.bukkit.command.PluginCommand").getDeclaredConstructors()[0];
            c.setAccessible(true);
            PluginCommand pc = (PluginCommand) c.newInstance(partyCommand, AncientRPG.plugin);
            pc.setExecutor(this);
            commandMap.register(partyCommand, pc);
            PluginCommand pc1 = (PluginCommand) c.newInstance(guildCommand, AncientRPG.plugin);
            pc1.setExecutor(this);
            commandMap.register(guildCommand, pc1);
            PluginCommand pc2 = (PluginCommand) c.newInstance(classCommand, AncientRPG.plugin);
            pc2.setExecutor(this);
            commandMap.register(classCommand, pc2);
            PluginCommand pc3 = (PluginCommand) c.newInstance(levelCommand, AncientRPG.plugin);
            pc3.setExecutor(this);
            commandMap.register(levelCommand, pc3);
            PluginCommand pc4 = (PluginCommand) c.newInstance(hpCommand, AncientRPG.plugin);
            pc4.setExecutor(this);
            commandMap.register(hpCommand, pc4);
            PluginCommand pc5 = (PluginCommand) c.newInstance(spellsCommand, AncientRPG.plugin);
            pc5.setExecutor(new SpellsCommandExecutor());
            commandMap.register(spellsCommand, pc5);
            PluginCommand pc6 = (PluginCommand) c.newInstance(manaCommand, AncientRPG.plugin);
            pc6.setExecutor(this);
            commandMap.register(manaCommand, pc6);
            PluginCommand pc7 = (PluginCommand) c.newInstance(arCommand, AncientRPG.plugin);
            pc7.setExecutor(this);
            commandMap.register(arCommand, pc7);
            PluginCommand pc8 = (PluginCommand) c.newInstance(raceCommand, AncientRPG.plugin);
            pc8.setExecutor(this);
            commandMap.register(raceCommand, pc8);
            PluginCommand pc9 = (PluginCommand) c.newInstance(gcCommand, AncientRPG.plugin);
            pc9.setExecutor(this);
            commandMap.register(gcCommand, pc9);
            PluginCommand pc10 = (PluginCommand) c.newInstance(pcCommand, AncientRPG.plugin);
            pc10.setExecutor(this);
            commandMap.register(pcCommand, pc10);
            PluginCommand pc11 = (PluginCommand) c.newInstance(bindCommand, AncientRPG.plugin);
            pc11.setExecutor(this);
            commandMap.register(bindCommand, pc11);
            PluginCommand pc12 = (PluginCommand) c.newInstance(unbindCommand, AncientRPG.plugin);
            pc12.setExecutor(this);
            commandMap.register(unbindCommand, pc12);
            PluginCommand pc13 = (PluginCommand) c.newInstance(ancientrpgCommand, AncientRPG.plugin);
            pc13.setExecutor(this);
            commandMap.register(ancientrpgCommand, pc13);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Locale l = new Locale(languagecode);
            messages = ResourceBundle.getBundle("AncientRPGMessages", l);
        } catch (Exception ignored) {

        }
        this.setupEconomy();
        this.saveConfig();
        //PlayerData.loadPlayerData();
        AncientRPGGuild.loadGuilds();
        new AncientRPGSpellListener(this);
        AncientRPGClass.loadClasses();
        AncientRPGRace.loadRaces();
        if (classExists("com.ancientshores.AncientRPG.Experiece.AncientRPGExperience")) {
            if (!new File(this.getDataFolder().getPath() + "/level").exists()) {
                new File(this.getDataFolder().getPath() + "/level").mkdir();
            }
            File configFile = new File(this.getDataFolder().getPath() + "/level/level.conf");
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
                    bw.write("level \n");
                    for (int i = 1; i <= 10; i++) {
                        bw.write("" + i + "\n");
                    }
                    bw.flush();
                    bw.close();
                } catch (Exception e) {
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "AncientRPG: unable to create spell config file " + configFile.getName());
                }
            }
        }
        try {
            File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
            f.mkdir();
            for (File nf : f.listFiles()) {
                if (nf.getName().endsWith(".sfz")) {
                    try {
                        String name = nf.getName().substring(0, nf.getName().lastIndexOf('.'));
                        FileInputStream fis = new FileInputStream(nf);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        SerializableZone sz = (SerializableZone) ois.readObject();
                        AddSpellFreeZoneCommand.spellfreezones.put(name, sz);
                        ois.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (PlayerData pd : PlayerData.playerData) {
            pd.createMissingObjects();
        }
        PlayerData.startSaveTimer();
        initializeHelpFiles();
        new SpellFreeZoneListener(this);
        new AncientRPGSpellListener(this);
        AncientRPGClass.addPerms();
        CreatureHp.startCleaner();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData pd = PlayerData.getPlayerData(p.getName());
            pd.getHpsystem().player = p;
            if (DamageConverter.isEnabled() && DamageConverter.isEnabled(p.getWorld())) {
                pd.getHpsystem().setMaxHp();
                pd.getHpsystem().setHpRegen();
                pd.getHpsystem().setMinecraftHP();
            }
            pd.getManasystem().setMaxMana();
            if (AncientRPGExperience.isEnabled()) {
                pd.getXpSystem().addXP(0, false);
            }
            if (!DamageConverter.isEnabled() || !DamageConverter.isEnabled(p.getWorld())) {
                p.setMaxHealth(20);
            }
        }
        Spell.initializeServerSpells();
        // File f = new File("plugins/test.qst");
        // new AncientRPGQuest(f);

    }

    public void onDisable() {
        this.reloadConfig();
        mConfig.loadkeys();
        mConfig.addDefaults();
        this.saveConfig();
        PlayerData.writePlayerData();
        log.info("AncientRPG disabled.");
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild") && AncientRPGGuild.enabled) {
            AncientRPGGuild.writeGuilds();
        }
        if (AncientRPGClass.enabled) {
            for (Player p : AncientRPGSpellListener.disarmList.keySet()) {
                if (p.getItemInHand() == null) {
                    p.setItemInHand(AncientRPGSpellListener.disarmList.get(p));
                } else {
                    p.getInventory().addItem(AncientRPGSpellListener.disarmList.get(p));
                }
            }
        }
        for (Player p : this.getServer().getOnlinePlayers()) {
            AncientRPGPlayerListener.setVisibleToAll(p);
            AncientRPGPlayerListener.setAllVisible(p);
        }
        for (Entry<String, Integer> entr : Spell.registeredTasks.entrySet()) {
            Bukkit.getScheduler().cancelTask(entr.getValue());
        }
        Bukkit.getScheduler().cancelTasks(this);
        Spell.registeredTasks.clear();
        Bukkit.getScheduler().cancelTasks(this);
        AncientRPGClass.removePerms();
        PlayerData.playerData = null;
        AncientRPG.plugin = null;
        AncientRPGClass.executedSpells = null;
        AncientRPGClass.playersOnCd = null;
        AncientRPGClass.globalSpells = null;
        AncientRPGClass.classList = null;
        AncientRPGRace.races = null;
        AncientRPGRace.playersOnCd = null;
        IArgument.registeredArguments = null;
        for (Entry<String, Integer> entry : Spell.registeredTasks.entrySet()) {
            Bukkit.getScheduler().cancelTask(entry.getValue());
        }
        Spell.registeredTasks = null;
        Spell.eventListeners = null;
        Parameter.registeredParameters = null;
        com.ancientshores.AncientRPG.Classes.Spells.Command.registeredCommands = null;
    }

    public int scheduleThreadSafeTask(Plugin p, Runnable r) {
        if (Thread.currentThread().equals(bukkitThread)) {
            return Bukkit.getScheduler().scheduleSyncDelayedTask(p, r);
        } else {
            r.run();
        }
        return 0;
    }

    public int scheduleThreadSafeTask(Plugin p, Runnable r, int delay) {
        if (Thread.currentThread().equals(bukkitThread) && delay == 0) {
            r.run();
            return 0;
        }
        return Bukkit.getScheduler().scheduleSyncDelayedTask(p, r, delay);
    }

    public void saveConfig() {
        File newconfig = new File(getDataFolder().getPath() + File.separator + "ancientrpgconfig.yml");
        if (!newconfig.exists()) {
            try {
                newconfig.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        YamlConfiguration yc = new YamlConfiguration();
        //BRAND
        yc.set("Brand", brand);
        yc.set(partyCommandNode, partyCommand);
        yc.set(guildCommandNode, guildCommand);
        yc.set(classCommandNode, classCommand);
        yc.set(levelCommandNode, levelCommand);
        yc.set(hpCommandNode, hpCommand);
        yc.set(raceCommandNode, raceCommand);
        yc.set(spellsCommandNode, spellsCommand);
        yc.set(manaCommandNode, manaCommand);
        yc.set(arCommandNode, arCommand);
        yc.set(pcCommandNode, pcCommand);
        yc.set(gcCommandNode, gcCommand);
        yc.set(bindCommandNode, bindCommand);
        yc.set(unbindCommandNode, unbindCommand);
        yc.set(ancientrpgCommandNode, ancientrpgCommand);
        try {
            yc.save(newconfig);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadConfig(FileConfiguration yc) {
        File newconfig = new File(getDataFolder().getPath() + File.separator + "ancientrpgconfig.yml");
        if (newconfig.exists()) {
            YamlConfiguration yc1 = new YamlConfiguration();
            try {
                yc1.load(newconfig);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // BRAND STUFF
            brand = yc1.getString("Brand", brand);
            brand2 = ChatColor.GOLD + "[" + brand + "] " + ChatColor.YELLOW;
            partyCommand = yc1.getString(partyCommandNode, partyCommand);
            guildCommand = yc1.getString(guildCommandNode, guildCommand);
            classCommand = yc1.getString(classCommandNode, classCommand);
            levelCommand = yc1.getString(levelCommandNode, levelCommand);
            hpCommand = yc1.getString(hpCommandNode, hpCommand);
            raceCommand = yc1.getString(raceCommandNode, raceCommand);
            spellsCommand = yc1.getString(spellsCommandNode, spellsCommand);
            manaCommand = yc1.getString(manaCommandNode, manaCommand);
            arCommand = yc1.getString(arCommandNode, arCommand);
            pcCommand = yc1.getString(pcCommandNode, pcCommand);
            gcCommand = yc1.getString(gcCommandNode, gcCommand);
            bindCommand = yc1.getString(bindCommandNode, bindCommand);
            unbindCommand = yc1.getString(unbindCommandNode, unbindCommand);
            ancientrpgCommand = yc1.getString(ancientrpgCommandNode, ancientrpgCommand);
        } else {
            // BRAND STUFF
            brand = yc.getString("Brand", brand);
            brand2 = ChatColor.GOLD + "[" + brand + "] " + ChatColor.YELLOW;
            partyCommand = yc.getString(partyCommandNode, partyCommand);
            guildCommand = yc.getString(guildCommandNode, guildCommand);
            classCommand = yc.getString(classCommandNode, classCommand);
            levelCommand = yc.getString(levelCommandNode, levelCommand);
            hpCommand = yc.getString(hpCommandNode, hpCommand);
            raceCommand = yc.getString(raceCommandNode, raceCommand);
            spellsCommand = yc.getString(spellsCommandNode, spellsCommand);
            manaCommand = yc.getString(manaCommandNode, manaCommand);
            arCommand = yc.getString(arCommandNode, arCommand);
            pcCommand = yc.getString(pcCommandNode, pcCommand);
            gcCommand = yc.getString(gcCommandNode, gcCommand);
            bindCommand = yc.getString(bindCommandNode, bindCommand);
            unbindCommand = yc.getString(unbindCommandNode, unbindCommand);
            ancientrpgCommand = yc.getString(ancientrpgCommandNode, ancientrpgCommand);
        }
    }

    public void initializeHelpFiles() {
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.Commands.PartyCommandHelp")) {
            PartyCommandHelp.initHelp();
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.Commands.GuildCommandHelp")) {
            GuildCommandHelp.initHelp();
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Classes.AncientRPGClassHelp")) {
            AncientRPGClassHelp.initHelp();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String[] nargs = args;
        String commandName = command.getName().toLowerCase();
        if (commandName.equalsIgnoreCase(arCommand)) {
            if (args.length < 2) {
                return true;
            }
            String[] buffer = new String[nargs.length - 1];
            System.arraycopy(nargs, 1, buffer, 0, nargs.length - 1);
            commandName = nargs[0];
            nargs = buffer;
        }
        if (commandName.equalsIgnoreCase("ancientrpg")) {
            if (args.length != 0 && args[0].equalsIgnoreCase("reload") && (!(sender instanceof Player) || (sender instanceof Player && hasPermissions((Player) sender, AdminPermission)))) {
                ReloadCommand.reload();
                sender.sendMessage("AncientRPG reload complete!");
            }
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                HelpCommand.processHelp(sender, nargs);
            }
            return true;
        }
        if (commandName.equalsIgnoreCase(levelCommand) && args.length > 1 && args[0].equals("setxp")) {
            SetXpCommand.setXp(sender, args);
            return true;
        }
        if (commandName.equalsIgnoreCase(manaCommand)) {
            ManaSystem.processCommand(sender, nargs);
            return true;
        }
        if (commandName.equalsIgnoreCase(guildCommand) && args.length > 1 && args[0].equals("setplayersguild")) {
            if (sender instanceof Player && !AncientRPG.hasPermissions((Player) sender, AncientRPGGuild.gNodeAdmin)) {
                return false;
            }
            Player p = Bukkit.getPlayer(args[1]);
            AncientRPGGuild mGuild = AncientRPGGuild.getGuildByName(args[2]);
            if (p != null && mGuild != null) {
                mGuild.addMember(p.getName(), AncientRPGGuildRanks.TRIAL);
            } else {
                sender.sendMessage("Player or guild not found");
            }
            return true;
        }
        if (commandName.equals(levelCommand) && AncientRPGExperience.enabled) {
            AncientRPGExperience.processCommand(sender, nargs);
            return true;
        }
        if (commandName.equals(classCommand) && AncientRPGClass.enabled) {
            AncientRPGClass.processCommand(sender, nargs);
            return true;
        }
        if (sender instanceof Player) {
            if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.AncientRPGParty") && AncientRPGParty.enabled && commandName.equals(partyCommand)) {
                AncientRPGParty.processCommand(sender, nargs, this);
                return true;
            }
            if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.AncientRPGParty") && AncientRPGParty.enabled && commandName.equals(pcCommand) && args != null) {
                nargs = new String[args.length + 1];
                System.arraycopy(args, 0, nargs, 1, args.length);
                nargs[0] = "";
                PartyCommandChat.processChat(sender, nargs);
                return true;
            }
            if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild") && commandName.equals(guildCommand) && AncientRPGGuild.enabled) {
                AncientRPGGuild.processCommand(sender, nargs, this);
                return true;
            }
            if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild") && commandName.equals(gcCommand) && AncientRPGGuild.enabled && args != null) {
                nargs = new String[args.length + 1];
                System.arraycopy(args, 0, nargs, 1, args.length);
                nargs[0] = "";
                GuildCommandChat.processChat(sender, nargs);
                return true;
            }

            if (commandName.equals(bindCommand) && AncientRPGClass.enabled) {
                if (args.length == 2) {
                    SpellBindCommand.bindCommand(new String[]{"class", args[1]}, (Player) sender);
                } else if (args.length == 3) {
                    SpellBindCommand.bindCommand(new String[]{"class", args[1], args[2]}, (Player) sender);
                } else {
                    sender.sendMessage("Correct usage is bind [itemid]");
                }
                return true;
            }
            if (commandName.equals(hpCommand)) {
                HPCommand.showHP((Player) sender);
                return true;
            }
            if (commandName.equals(unbindCommand) && AncientRPGClass.enabled) {
                if (args.length == 0) {
                    ClassUnbindCommand.unbindCommand(new String[]{"unbind"}, (Player) sender);
                } else if (args.length == 1) {
                    ClassUnbindCommand.unbindCommand(new String[]{"unbind", args[0]}, (Player) sender);
                } else {
                    sender.sendMessage("Correct usage is unbind [itemid]");
                }
                return true;
            }
            if (commandName.equals(raceCommand) && AncientRPGRace.enabled) {
                AncientRPGRace.processCommand(sender, args);
                return true;
            }
            /*
			 * if
			 * (AncientRPG.classExisting("com.ancientshores.AncientRPG.Achievement"
			 * )I'll include this later && Achievement.enabled) {
			 * Achievement.processCommands(sender, command, commandLabel, args,
			 * commandName, this); }
			 */
        } else {
            System.out.println("AncientRPG Commands can only be used in-game");
        }
        return false;
    }

    public static boolean classExists(String c) {
        try {
            Class.forName(c);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static String convertStringArrayToString(String[] array) {
        String rueckstring = "";
        for (int i = 0; i < array.length; i++) {
            rueckstring += array[i];
            if (i < array.length - 1) {
                rueckstring += " ";
            }
        }
        return rueckstring;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permissionHandler = permissionProvider.getProvider();
        }
        return (permissionHandler != null);
    }

    public static boolean hasPermissions(Player a, String b) {
        if (b == null || b.equals("")) {
            return true;
        }
        if ((AncientRPG.permissionHandler != null && AncientRPG.permissionHandler.has(a, b))) {
            return true;
        }
        return a.hasPermission(b) || a.isOp();
    }

    static ResourceBundle messages;

    public static String getLocalizedString(String code) {
        return messages.getString(code);
    }

    public static ApiManager getApiManager() {
        return manager;
    }

    public static boolean iConomyEnabled() {
        return AncientRPGGuild.Iconomyenabled && AncientRPG.economy != null;
    }

    @SuppressWarnings("unused")
    private Boolean setupTowny() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public static void set(YamlConfiguration yc, String path, Object obj) {
        if (yc.get(path) == null) {
            yc.set(path, obj);
        }
    }

    private Boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}