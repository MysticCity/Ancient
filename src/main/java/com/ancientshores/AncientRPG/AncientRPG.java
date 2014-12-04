package com.ancientshores.AncientRPG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.Metrics;
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
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.ancient.util.PlayerFinder;
import com.ancient.util.UUIDConverter;
import com.ancientshores.AncientRPG.API.ApiManager;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.AncientRPGClassHelp;
import com.ancientshores.AncientRPG.Classes.BindingData;
import com.ancientshores.AncientRPG.Classes.CooldownTimer;
import com.ancientshores.AncientRPG.Classes.Commands.ClassUnbindCommand;
import com.ancientshores.AncientRPG.Classes.Spells.Parameter;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Classes.Spells.Conditions.IArgument;
import com.ancientshores.AncientRPG.Commands.AncientRPGCommand;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Experience.SetXpCommand;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandChat;
import com.ancientshores.AncientRPG.Guild.Commands.GuildCommandHelp;
import com.ancientshores.AncientRPG.HP.AncientRPGHP;
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
import com.ancientshores.AncientRPG.Permissions.AncientRPGPermission;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.AncientRPG.Spells.Commands.SpellBindCommand;
import com.ancientshores.AncientRPG.Spells.Commands.SpellFreeZoneListener;
import com.ancientshores.AncientRPG.Spells.Commands.SpellsCommandExecutor;
import com.ancientshores.AncientRPG.Util.FlatFileConnector;
import com.ancientshores.AncientRPG.Util.SerializableZone;

public class AncientRPG extends JavaPlugin {

	public Config config; // Die Konfiguration ???
	public static Permission permissionHandler; // Vaults permission handler
	public static AncientRPG plugin; // ein Objekt dieser Klasse, auf die wahrscheinlich andere Klassen zugreifen ???
	public static Economy economy; // Vaults economy unterstützung
	public static final String AdminPermission = "AncientRPG.admin"; // eine Konstante zum Speichern der permission für admins
	public static CommandMap commandMap; //wird benutzt um Kommandos zu registrieren USE bei mir verwenden
	static ApiManager manager;  // ???
	public final FlatFileConnector fc = new FlatFileConnector(this); // ???
	public Thread bukkitThread; // ??? NNN
	public CommandPlayer ef; // ???
	public static String languagecode; // für die Sprache wird aus Config geladen in mconfig.loadkeys();
	// ClassListeners
	
	HashMap<SpellInformationObject, UUID> executingSpells = new HashMap<SpellInformationObject, UUID>(); // ??? Hashmap für die Zauber

	public Logger log; // zum Aufzeichnen und Ausgeben der Fehler und anderer Meldungen ???
	public static String partyCommand = "party"; // das Kommando, welches zum Ausführen des party codes genommen werden soll
	public static final String partyCommandNode = "AncientRPG.Commands.party";
	public static String guildCommand = "guild"; // das Kommando, welches zum Ausführen des gilden codes genommen werden soll
	public static final String guildCommandNode = "AncientRPG.Commands.guild";
	public static String classCommand = "class"; // das Kommando, welches zum Ausführen des klassen codes genommen werden soll
	public static final String classCommandNode = "AncientRPG.Commands.class";
	public static String levelCommand = "level"; // das Kommando, welches zum Ausführen des level codes genommen werden soll
	public static final String levelCommandNode = "AncientRPG.Commands.level";
	public static String hpCommand = "hp"; // das Kommando, welches zum Ausführen des lebenspunkte codes genommen werden soll
	public static final String hpCommandNode = "AncientRPG.Commands.hp";
	public static String raceCommand = "race"; // das Kommando, welches zum Ausführen des rassen codes genommen werden soll
	public static final String raceCommandNode = "AncientRPG.Commands.race";
	public static String spellsCommand = "spells"; // das Kommando, welches zum Ausführen des sprüche codes genommen werden soll
	public static final String spellsCommandNode = "AncientRPG.Commands.spells";
	public static String manaCommand = "mana"; // das Kommando, welches zum Ausführen des mana codes genommen werden soll
	public static final String manaCommandNode = "AncientRPG.Commands.mana";
	public static String arCommand = "ar"; // das Kommando, welches zum Ausführen des ??? codes genommen werden soll
	public static final String arCommandNode = "AncientRPG.Commands.ar";
	public static String pcCommand = "pc"; // das Kommando, welches zum Ausführen des ??? codes genommen werden soll
	public static final String pcCommandNode = "AncientRPG.Commands.pc";
	public static String gcCommand = "gc"; // das Kommando, welches zum Ausführen des ??? codes genommen werden soll
	public static final String gcCommandNode = "AncientRPG.Commands.gc";
	public static String bindCommand = "bind"; // das Kommando, welches zum Ausführen des ??? codes zum binden(wahrscheinlich sprüche auf items) genommen werden soll
	public static final String bindCommandNode = "AncientRPG.Commands.bind";
	public static String unbindCommand = "unbind"; // das Kommando, welches zum Ausführen des ??? codes zum entfernen(wahrscheinlich sprüche von items)genommen werden soll
	public static final String unbindCommandNode = "AncientRPG.Commands.unbind";
	public static String ancientrpgCommand = "ancientrpg"; // das Kommando, welches zum Ausführen des ??? allgemeinen informations codes genommen werden soll
	public static final String ancientrpgCommandNode = "AncientRPG.Commands.ancientrpg";
	public static String versionString = ""; // string der die aktuelle version des servers speichert NNN
	static Locale currentLocale; // speichert die position des servers NNN
	public static String brand = "ARPG"; // ??? NNN
	public static String brand2 = ""; // ??? NNN
	static ResourceBundle messages; // die Texte in der jeweiligen Sprache

	
	public void onEnable() { // beim aktivieren des Plugins
		// ===
		// initialize/log section
		// ==
		log = getLogger(); // setzt log auf den vom Server verwendeten Logger, damit die Meldungen auch ausgegeben werden
		this.bukkitThread = Thread.currentThread(); // speichert den Thread des Servers
		this.getDataFolder().mkdir(); // erstellt den Ordner für die Dateien des Plugins
		manager = ApiManager.getApiManager(); // ???
		plugin = this; // speichert in plugin das Objekt dieses Plugins, damit andere Klassen auf die verschiedenen Variablen zugreifen können
		Plugin vault = this.getServer().getPluginManager().getPlugin("Vault"); // speichert das Objekt des Plugins Vault in vault
		
		if (vault != null && vault instanceof Vault) { // wenn vault erfolgreich geladen wurde, d.h. auf dem Server vorhanden ist und aktiv ist und wirklich eine Instanz vom Vault plugin ist EDIT wenn dies nicht der Fall ist das plugin unloaden ??? warum war hier ein & geht nicht auch &&
			log.info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion())); // Informiere, dass Plugin aktiviert wurde MOVE zum ende von enable
		} else {
			log.warning(String.format("[%s] Vault was _NOT_ found! Disabling plugin.", getDescription().getName())); // warnen, dass Vault nicht gefunden wurde.
			getPluginLoader().disablePlugin(this); // plugin deaktivieren
			// ADD return; damit code auf keinen Fall weiter ausgeführt wird.
		}
		
		try {
			versionString = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1, Bukkit.getServer().getClass().getPackage().getName().length()); // speichert die vom server verwendete Bukkit version in versionString
			// Reflection um an die CommandMap zu kommen USE
			final Field f;
			f = Bukkit.getServer().getClass().getDeclaredField("commandMap");  // lädt die variable commandMap des Servers
			f.setAccessible(true); // macht sie zugänglich
			commandMap = (CommandMap) f.get(Bukkit.getServer()); // lädt die CommandMap dieses Servers und speichert sie in commandMap
		} catch (Exception ex) {
		
		}
	
		try {
			Metrics metrics = new Metrics(this); // erstellt ein neues objekt der mcstats metrics erfassung für dieses plugin (this)
			metrics.start(); // beginnt die Aufzeichnung der Daten
		} catch (IOException ex) { // noch keine Verwendung
			
		}
		// =============
		// enable (de)serialization of configs
		// =============
		enableSerialization();
		
		// ===
		// uuid converter section
		// ==
		UUIDConverter.runConverter(this, new File(plugin.getDataFolder().getPath() + "/players/"));
		
		// ==============
		// Player names loader section
		// ==============
		PlayerFinder.loadAllPlayers(new File(plugin.getDataFolder().getPath() + "/players/"));
		
		// ===
		// event section
		// ==
		setupPermissions(); // setzt die permissions für die einzelnen commands fest ???
		
		config = new Config(this);
//		config.configCheck(); // --- leer. dadrin passiert nichts
		
		// ==
		// load section
		// ==
		new AncientRPGPlayerListener(this); // ???
		new AncientRPGBlockListener(this); // ???
		new AncientRPGEntityListener(this); // ???
		
		config.loadkeys(); // lädt die einzelnen configurationen
		config.addDefaults(); // schreibt die standart werte der configurationen in die Dateien
		
		Parameter.registerDefaultParameters(); // registriert irgendwelche parameter?
		IArgument.addDefaults(); // registriert getter einzelner abfragen ???
		com.ancientshores.AncientRPG.Classes.Spells.Command.putDefaults(); //registriert die verschiedenen kommandos EDIT Klasse Command umbennenen zu Commands o.ä.
		
		registerCommands();
		// ??? warum wird hier nochmal registriert, weil es nicht zu den sprüchen gehört?
		
		try {
			Locale l = new Locale(languagecode); // neues Locale für das setzen der Sprache
			messages = ResourceBundle.getBundle("AncientRPGMessages", l); // die Resourcen laden, d.h. die Sprache speichern
		} catch (Exception ex) { // noch keine Verwendung

		}
		
		this.setupEconomy(); //  Economysetup ausführen, verknüpfen mit Vault
		this.saveConfig(); // speichert die standart config FIX weiß nicht, ob das funktioniert
		
		//PlayerData.loadPlayerData();
		AncientRPGGuild.loadGuilds(); // ??? Gilden laden
		new AncientRPGSpellListener(this); // ??? neuer Listener für Sprüche
		AncientRPGClass.loadClasses(); // ??? Klassen laden
		AncientRPGRace.loadRaces(); // ??? Rassen laden
		
			if (!new File(this.getDataFolder().getPath() + "/level").exists()) { // wenn der Levelordner nicht existiert wird er erstellt
				new File(this.getDataFolder().getPath() + "/level").mkdir();
			}
			
			File configFile = new File(this.getDataFolder().getPath() + "/level/level.conf"); // Datei level.conf in File objekt speichern
			if (!configFile.exists()) { // wenn diese Datei nicht existiert
				try {
					configFile.createNewFile(); // neu anlegen
					BufferedWriter bw = new BufferedWriter(new FileWriter(configFile)); // neue BufferedWriter instanz zum schreiben in die Datei
					bw.write("level \n"); // überschrift EDIT Schöner und aussagekräftiger. Vielleicht mit .yml ??? wie wird es verwendet
					for (int i = 1; i <= 10; i++) {
						bw.write("" + i + "\n"); // ??? --- mega notwendig
					}
					bw.flush(); // in die Datei kopieren
					bw.close(); // die Verbindung beenden
				} catch (Exception e) {
					AncientRPG.plugin.getLogger().log(Level.SEVERE, "AncientRPG: unable to create spell config file " + configFile.getName()); // ??? ich denke es ist eine level datei. wenn ich locales habe sollte ich die auch benutzen...
				}
			}
		
		try {
			File f = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "spellfreezones"); // ??? OMG warum frage ich hier nach seperator aber nicht 10 zeilen darüber WTF und warum nehme ich davor this und jetzt klasse.plugin | soll wahrscheinlich den ordner für spruchfreie zonen speichern. interessant...
			f.mkdir(); // Ordner erstellen
			File[] files = f.listFiles(); // Alle Dateien aus dem Ordner im Array speichern
			if (files != null) { // wenn Dateien im Ordner sind
				for (File nf : files) { // für jede Datei in dem Array
					if (nf.getName().endsWith(".sfz")) { // wenn die Datei die Endung .sfz (SpellFreeZone) hat. ??? was wird darin gespeichert, wie werden sie angelegt? --- falls einfach nur leer sind kann man sie durch config.yml o.ä. ersetzen
						try {
							String name = nf.getName().substring(0, nf.getName().lastIndexOf('.')); // lädt den Namen der Datei. ??? ist das der welt name
							FileInputStream fis = new FileInputStream(nf); // neuer FileInputStream aus dem File
							ObjectInputStream ois = new ObjectInputStream(fis); // neuer ObjectInputStream aus FileInputStream
							SerializableZone sz = (SerializableZone) ois.readObject(); // ??? was macht die Klasee
							AddSpellFreeZoneCommand.spellfreezones.put(name, sz); // ??? was wird geadded? was macht diese klasse
							ois.close(); // Stream schließen
						} catch (Exception ex) { // wenn Fehler vom Datei laden
							ex.printStackTrace();
						}
					}
				}
			}
		} catch (Exception ex) { // wenn Ordner File laden Fehler
			ex.printStackTrace();
		}
		for (PlayerData pd : PlayerData.playerData) { // ??? Funktion
			pd.createMissingObjects(); // ??? erstellt irgendwelche objekte
		}
		
		PlayerData.startSaveTimer(); // ??? startet irgendeinen Timer
		initializeHelpFiles(); // ??? initialisiert irgendwelche hilfedateien
		new SpellFreeZoneListener(this); // ??? neuer Spruch freie Zonen listener. achtet der auf die Welten oder was?
		new AncientRPGSpellListener(this); // ??? achtet das auf die auslöser der Sprüche
		AncientRPGClass.addPerms(); // ??? fügt irgendwelche Permissions zu den Klassen hinzu
		CreatureHp.startCleaner(); // ??? irgendwelche Kreaturen Leben werden gelöscht 
		
		for (Player p : Bukkit.getOnlinePlayers()) { // Herzlichen Glückwunsch. Ich glaube das funktioniert nur bei /reload... NNN ??? --- vielleicht bei join event
			PlayerData pd = PlayerData.getPlayerData(p.getUniqueId()); // speichert die PlayerData eines Spielers ??? was für daten
			pd.getHpsystem().playerUUID = p.getUniqueId(); // ??? setzt den spieler eines hpsystems auf den aktuellen
			if (DamageConverter.isEnabledInWorld(p.getWorld())) { // wenn der ??? DamageConverter aktiviert und in der entsprechenden Welt aktiviert ist
				pd.getHpsystem().setMaxHP(); // die Maximalen Leben festlegen
				pd.getHpsystem().setHpRegen(); // die Regeneration festlegen
				pd.getHpsystem().setMinecraftHP(); // die ??? Minecraft leben festlegen
			}
			else {
				p.setMaxHealth(20); // die maximale Lebensanzahl festlegen
			}
			pd.getManasystem().setMaxMana(); // die maximale Manaanzahl festlegen
			
			if (AncientRPGExperience.isEnabled()) { // wenn die ??? Experience aktiviert ist. was macht diese Klasse
				pd.getXpSystem().addXP(0, false); // ??? --- scheinbar 0 XP hinzufügen nur zum aktivieren oder was
			}	
		}
		
		Spell.initializeServerSpells(); // ??? welche Spells werden initialisiert
		// File f = new File("plugins/test.qst");
		// new AncientRPGQuest(f);
	}
	
	private void enableSerialization() { // Configurations-Klassen registrieren, damit sie beim laden bekannt sind.
		ConfigurationSerialization.registerClass(BindingData.class);
		ConfigurationSerialization.registerClass(CooldownTimer.class);
		ConfigurationSerialization.registerClass(AncientRPGExperience.class);
		ConfigurationSerialization.registerClass(AncientRPGHP.class); // registriert die Klasse für Configs
		ConfigurationSerialization.registerClass(ManaSystem.class);
		ConfigurationSerialization.registerClass(AncientRPGPermission.class);
		ConfigurationSerialization.registerClass(PlayerData.class);
	}
	
	@SuppressWarnings("unused")
	private void disableSerialization() { // Configurations-Klassen abmelden
		ConfigurationSerialization.unregisterClass(BindingData.class);
		ConfigurationSerialization.unregisterClass(CooldownTimer.class);
		ConfigurationSerialization.unregisterClass(AncientRPGExperience.class);
		ConfigurationSerialization.unregisterClass(AncientRPGHP.class); // registriert die Klasse für Configs
		ConfigurationSerialization.unregisterClass(ManaSystem.class);
		ConfigurationSerialization.unregisterClass(AncientRPGPermission.class);
		ConfigurationSerialization.unregisterClass(PlayerData.class);
	}
	
	private void registerCommands() {
		try { // registrieren der Kommandos EDIT einzelne Strings löschen, und statt dessen map, list oder array nehmen und iterieren
			Constructor<?> c = Class.forName("org.bukkit.command.PluginCommand").getDeclaredConstructors()[0];
			c.setAccessible(true);
			
			PluginCommand pc = (PluginCommand) c.newInstance(partyCommand, AncientRPG.plugin);
			pc.setExecutor(this);
			commandMap.register(partyCommand, pc);
			
			PluginCommand gc = (PluginCommand) c.newInstance(guildCommand, AncientRPG.plugin);
			gc.setExecutor(this);
			commandMap.register(guildCommand, gc);
			
			PluginCommand cc = (PluginCommand) c.newInstance(classCommand, AncientRPG.plugin);
			cc.setExecutor(this);
			commandMap.register(classCommand, cc);
			
			PluginCommand lc = (PluginCommand) c.newInstance(levelCommand, AncientRPG.plugin);
			lc.setExecutor(this);
			commandMap.register(levelCommand, lc);
			
			PluginCommand hc = (PluginCommand) c.newInstance(hpCommand, AncientRPG.plugin);
			hc.setExecutor(this);
			commandMap.register(hpCommand, hc);
			
			PluginCommand sc = (PluginCommand) c.newInstance(spellsCommand, AncientRPG.plugin);
			sc.setExecutor(new SpellsCommandExecutor());
			commandMap.register(spellsCommand, sc);
			
			PluginCommand mc = (PluginCommand) c.newInstance(manaCommand, AncientRPG.plugin);
			mc.setExecutor(this);
			commandMap.register(manaCommand, mc);
			
			PluginCommand arc = (PluginCommand) c.newInstance(arCommand, AncientRPG.plugin);
			arc.setExecutor(this);
			commandMap.register(arCommand, arc);
			
			PluginCommand rc = (PluginCommand) c.newInstance(raceCommand, AncientRPG.plugin);
			rc.setExecutor(this);
			commandMap.register(raceCommand, rc);
			
			PluginCommand gcc = (PluginCommand) c.newInstance(gcCommand, AncientRPG.plugin);
			gcc.setExecutor(this);
			commandMap.register(gcCommand, gcc);
			
			PluginCommand pcc = (PluginCommand) c.newInstance(pcCommand, AncientRPG.plugin);
			pcc.setExecutor(this);
			commandMap.register(pcCommand, pcc);
			
			PluginCommand bc = (PluginCommand) c.newInstance(bindCommand, AncientRPG.plugin);
			bc.setExecutor(this);
			commandMap.register(bindCommand, bc);
			
			PluginCommand uc = (PluginCommand) c.newInstance(unbindCommand, AncientRPG.plugin);
			uc.setExecutor(this);
			commandMap.register(unbindCommand, uc);
			
			PluginCommand ac = (PluginCommand) c.newInstance(ancientrpgCommand, AncientRPG.plugin);
			ac.setExecutor(new AncientRPGCommand());
			commandMap.register(ancientrpgCommand, ac);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

	public void onDisable() {
		this.reloadConfig();
		config.loadkeys();
		config.addDefaults();
		this.saveConfig();
		PlayerData.writePlayerData();
		log.info("AncientRPG disabled.");
		
		AncientRPGGuild.writeGuilds();
		
		if (AncientRPGClass.enabled) {
			for (UUID uuid : AncientRPGSpellListener.disarmList.keySet()) {
				Player p = Bukkit.getPlayer(uuid);
				if (p.getItemInHand() == null) {
					p.setItemInHand(AncientRPGSpellListener.disarmList.get(p.getUniqueId()));
				} else {
					p.getInventory().addItem(AncientRPGSpellListener.disarmList.get(p.getUniqueId()));
				}
			}
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
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
		File newconfig = new File(getDataFolder().getPath() + File.separator + "ancientrpgconfig.yml"); // legt ein neues File mit ziel die config an
		if (!newconfig.exists()) { // wenn sie nicht existiert
			try {
				newconfig.createNewFile(); // wird versucht die Datei anzulegen
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration yc = new YamlConfiguration(); // erstellt eine neue yamlconfiguration EDIT ist laden aus der Datei nicht sinnvoller?
		
		// setzt die verschiedenen Punkte
		// ??? funktioniert das, wenn man in der config was ändert? wird hierdurch nicht alles überschrieben besser bei if !exists
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
			yc.save(newconfig); // versucht die Config in der Datei zu speichern
		} catch (IOException e) {
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
		PartyCommandHelp.initHelp();
		GuildCommandHelp.initHelp();
		AncientRPGClassHelp.initHelp();
	}

	@SuppressWarnings("deprecation")
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
		if (commandName.equalsIgnoreCase(levelCommand) && args.length > 1 && args[0].equals("setxp")) {
			SetXpCommand.setXp(sender, args);
			return true;
		}
		if (commandName.equalsIgnoreCase(manaCommand)) {
			ManaSystem.processCommand(sender, nargs);
			return true;
		}
		if (commandName.equalsIgnoreCase(guildCommand) && args.length > 1 && args[0].equals("setplayersguild")) {
			if (sender instanceof Player && !sender.hasPermission(AncientRPGGuild.gNodeAdmin)) {
				return false;
			}
			Player p = Bukkit.getPlayer(args[1]);
			AncientRPGGuild mGuild = AncientRPGGuild.getGuildByName(args[2]);
			if (p != null && mGuild != null) {
				mGuild.addMember(p.getUniqueId(), AncientRPGGuildRanks.TRIAL);
			} else {
				sender.sendMessage("Player or guild not found");
			}
			return true;
		}
		if (commandName.equals(levelCommand) && AncientRPGExperience.isEnabled()) {
			AncientRPGExperience.processCommand(sender, nargs);
			return true;
		}
		if (commandName.equals(classCommand)) {
			AncientRPGClass.processCommand(sender, nargs);
			return true;
		}
		if (sender instanceof Player) {
			if (AncientRPGParty.enabled && commandName.equals(partyCommand)) {
				AncientRPGParty.processCommand(sender, nargs, this);
				return true;
			}
			if (AncientRPGParty.enabled && commandName.equals(pcCommand) && args != null) {
				nargs = new String[args.length + 1];
				System.arraycopy(args, 0, nargs, 1, args.length);
				nargs[0] = "";
				PartyCommandChat.processChat(sender, nargs);
				return true;
			}
			if (commandName.equals(guildCommand) && AncientRPGGuild.enabled) {
				AncientRPGGuild.processCommand(sender, nargs, this);
				return true;
			}
			if (commandName.equals(gcCommand) && AncientRPGGuild.enabled && args != null) {
				nargs = new String[args.length + 1];
				System.arraycopy(args, 0, nargs, 1, args.length);
				nargs[0] = "";
				GuildCommandChat.processChat(sender, nargs);
				return true;
			}
			if (commandName.equals(bindCommand)) {
				if (args != null) {
					if (args.length == 2) {
						SpellBindCommand.bindCommand(new String[]{"class", args[1]}, (Player) sender);
					} else if (args.length == 3) {
						SpellBindCommand.bindCommand(new String[]{"class", args[1], args[2]}, (Player) sender);
					} else {
						sender.sendMessage("Correct usage is bind [itemid]");
					}
				}
				return true;
			}
			if (commandName.equals(hpCommand)) {
				HPCommand.showHP((Player) sender);
				return true;
			}
			if (commandName.equals(unbindCommand)) {
				if (args != null) {
					if (args.length == 0) {
						ClassUnbindCommand.unbindCommand(new String[]{"unbind"}, (Player) sender);
					} else if (args.length == 1) {
						ClassUnbindCommand.unbindCommand(new String[]{"unbind", args[0]}, (Player) sender);
					} else {
						sender.sendMessage("Correct usage is unbind [itemid]");
					}
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

//	public static boolean classExists(String c) {
//		try {
//			Class.forName(c);
//			return true;
//		} catch (ClassNotFoundException e) {
//			return false;
//		}
//	}

	public static boolean classExists(String c) {
		try {
			Class.forName(c);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static String convertStringArrayToString(String[] array) {
		String returnValue = "";
		for (int i = 0; i < array.length; i++) {
			if (!returnValue.equals("")) {
				returnValue += " ";
			}
			returnValue += array[i];
		}
		return returnValue;
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

	public static String getLocalizedString(String code) {
		return messages.getString(code);
	}

	public static ApiManager getApiManager() {
		return manager;
	}

	public static boolean iConomyEnabled() {
		return AncientRPGGuild.economyenabled && AncientRPG.economy != null;	
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
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class); // eine liste aller mit vault verbundenen Economy systeme erstellen
		if (economyProvider != null) { // wenn es einen Economy provider gibt
			economy = economyProvider.getProvider(); // diesen Benutzen
		}

		return (economy != null); // --- überflüssig, wird sowieso nicht überprüft
	}
}