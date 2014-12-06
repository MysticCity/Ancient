package com.ancientshores.AncientRPG.Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Commands.ClassCastCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassInteractiveCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassListCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassResetCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassSetCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassSetStanceCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassSpelllistCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassStanceListCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassUnbindCommand;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Spells.Commands.SpellBindCommand;
import com.ancientshores.AncientRPG.Util.LinkedStringHashMap;

public class AncientRPGClass implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final boolean enabled = true;
	public int level = 1;
	// ====
	// Permissionnodes
	// ====
	
	// config kram der in classconfig.yml ist // allgemeines
	public static final String configStandardClassName = "Class.StandardClassName";
	public static String standardclassName = "standardclass";
	public static final String configCd = "Class.change cooldown in seconds";
	public static final String configRightCast = "Class.only right click casts bound spells";
	public static final String configResetLevels = "Class.reset level on class change";
	public static final String configShowAllClasses = "Class.show all classes";
	public static boolean showAllClasses = true;
	public static boolean resetlevelonchange = false;
	public static int changeCd = 2;
	public static boolean rightClick = false;
	
	// pro klasse einzeln in classes/classenname.yml oder so
	public static final String cNodeClass = "AncientRPG.classes.set";
	public static final String cNodeSpells = "AncientRPG.classes.spells";
	public static final String cNodeBind = "AncientRPG.classes.bind";
	public static final String cNodeChatCast = "AncientRPG.classes.chatcast";
	public static LinkedStringHashMap<AncientRPGClass> classList = new LinkedStringHashMap<AncientRPGClass>();
	public static LinkedStringHashMap<Spell> globalSpells = new LinkedStringHashMap<Spell>();
	public static CommandPlayer cp = new CommandPlayer();
	public static ConcurrentHashMap<UUID, Long> playersOnCd = new ConcurrentHashMap<UUID, Long>();
	public static HashMap<SpellInformationObject, UUID> executedSpells = new HashMap<SpellInformationObject, UUID>();
	public final String name;
	public boolean hidden = false;
	public String worlds[] = new String[0];
	public final ArrayList<String> requiredraces = new ArrayList<String>();
	public String permissionNode = "";
	public final String weaponTypes = "";
	public final String armorTypes = "";
	public String description = "";
	public String preclass = "";
	public int minlevel = 0;
	public String permGroup = "";
	public final HashMap<BindingData, String> bindings = new HashMap<BindingData, String>();
	public int defaultmana = 1000;
	public int hp = 600;
	public int hpreg = 20;
	public int manareg = 20;
	public String shortcut = "";
	public final HashMap<Integer, Float> hpreglevel = new HashMap<Integer, Float>();
	public final HashMap<Integer, Float> hplevel = new HashMap<Integer, Float>();
	public final HashMap<Integer, Integer> manareglevel = new HashMap<Integer, Integer>();
	public final HashMap<Integer, Integer> manalevel = new HashMap<Integer, Integer>();
	public final HashMap<String, Spell> spellList = new HashMap<String, Spell>();
	public final LinkedHashMap<String, AncientRPGClass> stances = new LinkedHashMap<String, AncientRPGClass>();
	public final HashSet<Material> blacklistedMats = new HashSet<Material>();
	public final HashSet<Material> blacklistedArmor = new HashSet<Material>();

	public AncientRPGClass() {
		name = "standard class";
	}

	@Override
	public boolean equals(Object c) {
		return c instanceof AncientRPGClass && ((AncientRPGClass) c).name.equals(c);
	}

	@SuppressWarnings("deprecation")
	public AncientRPGClass(final File base) {
		name = base.getName();
		File[] spells = base.listFiles();
		if (spells != null) {
			for (final File f : spells) {
				if (f.getPath().endsWith(".spell")) {
					Spell p = new Spell(f);
					spellList.put(p.name.toLowerCase(), p);
				}
				if (f.isDirectory()) {
					AncientRPGClass stance = new AncientRPGClass(f);
					stances.put(stance.name.toLowerCase(), stance);
					if (stance.shortcut != null && !stance.shortcut.equals("")) stances.put(stance.shortcut, stance);		
				}
			}
		}
		final File f = new File(base.getPath() + File.separator + name + ".conf");
		final YamlConfiguration yc = new YamlConfiguration();
		if (!f.exists()) {
			yc.set("Class.Permissionnode", "");
			yc.set("Class.maxhp", this.hp);
			yc.set("Class.permissiongroup", this.permGroup);
			yc.set("Class.enabled in world", "");
			try {
				yc.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			yc.load(f);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if (yc.get("Class.Permissionnode") == null) yc.set("Class.Permissionnode", "");
		
		this.permissionNode = yc.getString("Class.Permissionnode");
		if (yc.get("Class.hidden") == null) yc.set("Class.hidden", hidden);
		
		this.hidden = yc.getBoolean("Class.hidden", hidden);
		if (yc.get("Class.blacklistedarmor") == null) yc.set("Class.blacklistedarmor", "");
		
		String[] armors = yc.getString("Class.blacklistedarmor").split(",");
		for (String s : armors) {
			try {
				int id = Integer.parseInt(s.trim());
				blacklistedArmor.add(Material.getMaterial(id));
			} catch (Exception ignored) {

			}
		}
		if (yc.get("Class.blacklisteditems") == null) yc.set("Class.blacklisteditems", "");
		
		String[] items = yc.getString("Class.blacklisteditems").split(",");
		for (String s : items) {
			try {
				int id = Integer.parseInt(s.trim());
				blacklistedMats.add(Material.getMaterial(id));
			} catch (Exception ignored) {

			}
		}
		if (yc.get("Class.maxhp") == null) yc.set("Class.maxhp", this.hp);
	
		if (yc.get("Class.shortcut") == null) yc.set("Class.shortcut", "");
		
		this.hp = yc.getInt("Class.maxhp", this.hp);
		
		if (yc.get("Class.enabled in world") == null) yc.set("Class.enabled in world", "");
		
		this.worlds = yc.getString("Class.enabled in world").split(",");
		for (int i = 0; i < worlds.length; i++) 
			worlds[i] = worlds[i].trim();
		
		if (yc.get("Class.permissiongroup") == null) yc.set("Class.permissiongroup", this.permGroup);
		
		this.permGroup = yc.getString("Class.permissiongroup", this.permGroup);
		if (yc.get("Class.minlevel") == null) yc.set("Class.minlevel", this.minlevel);
		
		this.minlevel = yc.getInt("Class.minlevel", this.minlevel);
		// bindings
		Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
			public void run() {
				HashSet<Spell> allspells = new HashSet<Spell>();
				allspells.addAll(spellList.values());
				allspells.addAll(globalSpells.values());
				for (Spell spell : allspells) {
					if (!spell.active) continue;
					
					if (yc.get("Class.Bindings." + spell.name) == null) yc.set("Class.Bindings." + spell.name, 0);
					else {
						String[] root = yc.getString("Class.Bindings." + spell.name).split(",");
						for (String l : root) {
							String[] s = l.trim().split(":");
							try {
								int id = Integer.parseInt(s[0]);
								if (id == 0) continue;
								
								byte data = 0;
								if (s.length == 2) data = Byte.parseByte(s[1]);
								
								bindings.put(new BindingData(id, data), spell.name);
								try {
									yc.save(f);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} catch (Exception ignored) {}
						}
					}
				}
				try {
					yc.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 2);
		if (yc.get("Class.preclass") == null) yc.set("Class.preclass", this.preclass);
		
		this.preclass = yc.getString("Class.preclass", this.preclass);
		if (yc.get("Class.requiredrace") == null) yc.set("Class.requiredrace", "");
		
		String races = yc.getString("Class.requiredrace").trim();
		if (races.length() > 0) {
			String[] str = races.split(",");
			for (String s : str)
				requiredraces.add(s.trim().toLowerCase());
		}
		for (int i = 1; i <= AncientRPGExperience.getLevelCount(); i++) {
			if (yc.get("Class.hp of level " + i) == null) yc.set("Class.hp of level " + i, DamageConverter.getStandardHP());
			
			hplevel.put(i, (float) yc.getDouble("Class.hp of level " + i));
			if (yc.get("Class.hpreg of level " + i) == null) yc.set("Class.hpreg of level " + i, DamageConverter.getHPRegeneration());
			
			hpreglevel.put(i, (float) yc.getDouble("Class.hpreg of level " + i));
		}
		for (int i = 1; i <= AncientRPGExperience.getLevelCount(); i++) {
			if (yc.get("Class.mana of level " + i) == null) yc.set("Class.mana of level " + i, ManaSystem.defaultMana);
			
			manalevel.put(i, yc.getInt("Class.mana of level " + i));
			if (yc.get("Class.manareg of level " + i) == null) yc.set("Class.manareg of level " + i, ManaSystem.defaultReg);
			
			manareglevel.put(i, yc.getInt("Class.manareg of level " + i));
		}
		if (yc.get("Class.default hpreg") == null) yc.set("Class.default hpreg", DamageConverter.getHPRegeneration());
		
		if (yc.get("Class.default manareg") == null) yc.set("Class.default manareg", ManaSystem.defaultMana);
		
		AncientRPG.set(yc, "Class.description", "");
		this.description = yc.getString("Class.description", "");
		hpreg = yc.getInt("Class.default hpreg");
		manareg = yc.getInt("Class.default manareg");
		shortcut = yc.getString("Class.shortcut").trim();
		if (yc.get("Class.default mana") == null) yc.set("Class.default mana", 1000);
		
		defaultmana = yc.getInt("Class.default mana");
		try {
			yc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean canEquipArmor(Player p, PlayerData pd, String t) {
		if (classList.get(pd.getClassName().toLowerCase()) == null) return true;
		
//		if (!classList.get(pd.getClassName().toLowerCase()).isWorldEnabled(p.getWorld())) {
//			return true;
//		}
		return classList.get(pd.getClassName().toLowerCase()).armorTypes.contains(t.toLowerCase());
	}

	public static boolean canUseSword(Player p, PlayerData pd, String t) {
		if (classList.get(pd.getClassName().toLowerCase()) == null) return true;
		
//		if (!classList.get(pd.getClassName().toLowerCase()).isWorldEnabled(p.getWorld())) {
//			return true;
//		}
		return classList.get(pd.getClassName().toLowerCase()).weaponTypes.contains(t.toLowerCase());
	}

	public static void writeConfig(AncientRPG instance) {
		File newconf = new File(instance.getDataFolder().getPath() + File.separator + "classconfig.yml");
		if (!newconf.exists()) {
			try {
				newconf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration yc = new YamlConfiguration();
		try {
			yc.load(newconf);
			if (yc.get(configStandardClassName) == null) {
				yc.set(configStandardClassName, standardclassName);
			}
			if (yc.get(configCd) == null) {
				yc.set(configCd, changeCd);
			}
			if (yc.get(configRightCast) == null) {
				yc.set(configRightCast, rightClick);
			}
			if (yc.get(configResetLevels) == null) {
				yc.set(configResetLevels, resetlevelonchange);
			}
			if (yc.get(configShowAllClasses) == null) {
				yc.set(configShowAllClasses, showAllClasses);
			}
			yc.save(newconf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadConfig(AncientRPG instance) {
		File newconf = new File(instance.getDataFolder().getPath() + File.separator + "classconfig.yml");
		if (newconf.exists()) {
			YamlConfiguration yc = new YamlConfiguration();
			try {
				yc.load(newconf);
				standardclassName = yc.getString(configStandardClassName, standardclassName);
				changeCd = yc.getInt(configCd, changeCd);
				rightClick = yc.getBoolean(configRightCast, rightClick);
				resetlevelonchange = yc.getBoolean(configResetLevels, resetlevelonchange);
				showAllClasses = yc.getBoolean(configShowAllClasses, showAllClasses);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			standardclassName = instance.getConfig().getString(configStandardClassName, standardclassName);
			changeCd = instance.getConfig().getInt(configCd, changeCd);
			rightClick = instance.getConfig().getBoolean(configRightCast, rightClick);
			resetlevelonchange = instance.getConfig().getBoolean(configResetLevels, resetlevelonchange);
			showAllClasses = instance.getConfig().getBoolean(configShowAllClasses, showAllClasses);
		}
	}

	public static void loadClasses() {
		classList = new LinkedStringHashMap<AncientRPGClass>();
		globalSpells = new LinkedStringHashMap<Spell>();
		File basePath = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Class");
		File[] classes = basePath.listFiles();
		if (!basePath.exists()) {
			basePath.mkdir();
		}
		if (classes == null) {
			return;
		}
		for (final File f : classes) {
			if (f.getName().endsWith(".spell") && f.isFile()) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
					@Override
					public void run() {
						try {
							Spell p = new Spell(f);
							globalSpells.put(p.name.toLowerCase(), p);
						} catch (Exception ignored) {

						}
					}
				});
			}
		}
		File globspellpath = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "globalspells" + File.separator);
		if (!globspellpath.exists()) {
			globspellpath.mkdir();
		}
		File[] files = globspellpath.listFiles();
		if (files != null) {
			for (final File f : files) {
				if (f.getName().endsWith(".spell") && f.isFile()) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
						@Override
						public void run() {
							try {
								Spell p = new Spell(f);
								globalSpells.put(p.name.toLowerCase(), p);
							} catch (Exception ignored) {

							}
						}
					});
				}
			}
		}
		for (File f : classes) {
			if (f.isDirectory()) {
				AncientRPGClass c = new AncientRPGClass(f);
				classList.put(f.getName().toLowerCase(), c);
				if (c.shortcut != null && !c.shortcut.equals("")) {
					classList.put(c.shortcut, c);
				}
			}
		}
		File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
		if (f.exists()) {
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) {
					playersOnCd.put(UUID.fromString(line.split(";")[0]), Long.getLong(line.split(";")[1]));
				}
//				playersOnCd = (ConcurrentHashMap<String, Long>) ois.readObject();
//				ois.close();
				br.close();
				fr.close();
			} catch (Exception ignored) {
			}
		}
	}

	public static void processCommand(CommandSender sender, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
			ClassSetCommand.setCommand(args, sender);
			return;
		} else if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
			ClassListCommand.showHelp(sender, args);
			return;
		} else if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
			AncientRPGClassHelp.printHelp(sender, args);
			return;
		}
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
				p.sendMessage(AncientRPG.brand2 + ChatColor.YELLOW + "Your class is: " + ChatColor.BLUE + pd.getClassName());

				AncientRPGClass mClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
				if (mClass != null && mClass.description != null && !mClass.description.equals("")) {
					p.sendMessage(mClass.description);
				}
				if (pd.getStance() != null && !pd.getStance().trim().equals("") && !pd.getStance().trim().equals("")) {
					p.sendMessage(AncientRPG.brand2 + ChatColor.YELLOW + "Your stance is " + ChatColor.BLUE + pd.getStance());
				}
				p.sendMessage(AncientRPG.brand2 + ChatColor.YELLOW + "To see a list of all Commands type: " + ChatColor.AQUA + "/class help");
			} else if (args[0].equalsIgnoreCase("bind")) {
				SpellBindCommand.bindCommand(args, p);
			} else if (args[0].equalsIgnoreCase("bindslot")) {
				SpellBindCommand.bindSlotCommand(args, p);
			} else if (args[0].equalsIgnoreCase("stancelist")) {
				ClassStanceListCommand.showStances(p, args);
			} else if (args[0].equalsIgnoreCase("spelllist")) {
				ClassSpelllistCommand.spellListCommand(args, p);
			} else if (args[0].equalsIgnoreCase("setstance")) {
				ClassSetStanceCommand.setStanceCommand(args, p);
			} else if (args[0].equalsIgnoreCase("reset")) {
				ClassResetCommand.resetCommand(p);
			} else if (args[0].equalsIgnoreCase("interactive")) {
				String line = "";
				for (int i = 1; i < args.length; i++) {
					line += args[i];
					line += " ";
				}
				line = line.trim();
				ClassInteractiveCommand.interactiveCommand(p, line);
			} else if (args[0].equalsIgnoreCase("unbind")) {
				ClassUnbindCommand.unbindCommand(args, p);
			} else {

				if (p.hasPermission(cNodeChatCast)) {
					PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
					ClassCastCommand.processCast(pd, p, args[0], ClassCastCommand.castType.COMMAND);
				}
			}
		}
	}

	public static boolean spellAvailable(String spellname, PlayerData pd) {
		try {
			if (globalSpells.containsKey(spellname.toLowerCase())) {
				return true;
			}
			if (classList.containsKey(pd.getClassName().toLowerCase())) {
				AncientRPGClass mClass = classList.get(pd.getClassName().toLowerCase());
				if (mClass != null) {
					if (mClass.spellList.containsKey(spellname.toLowerCase())) {
						return true;
					}
					if (pd.getStance() != null && !pd.getStance().equals("") && mClass.stances.containsKey(pd.getStance().toLowerCase())) {
						AncientRPGClass mStance = mClass.stances.get(pd.getStance().toLowerCase());
						if (mStance != null) {
							if (mStance.spellList.containsKey(spellname.toLowerCase())) {
								return true;
							}
						}
					}
				}
			}
			AncientRPGRace race = AncientRPGRace.getRaceByName(pd.getRacename());
			if (race != null) {
				for (Spell p : race.raceSpells) {
					if (p.name.equals(spellname)) {
						return true;
					}
				}
			}
		} catch (Exception ignored) {
		}
		return false;
	}

	public static boolean spellAvailable(Spell p, PlayerData pd) {
		try {
			if (globalSpells.containsValue(p)) {
				return true;
			}
			if (classList.containsKey(pd.getClassName().toLowerCase())) {
				AncientRPGClass mClass = classList.get(pd.getClassName().toLowerCase());
				if (mClass != null) {
					if (mClass.spellList.containsValue(p)) {
						return true;
					}
					if (pd.getStance() != null && !pd.getStance().equals("") && mClass.stances.containsValue(p)) {
						AncientRPGClass mStance = mClass.stances.get(pd.getStance().toLowerCase());
						if (mStance != null) {
							if (mStance.spellList.containsValue(p)) {
								return true;
							}
						}
					}
				}
			}
			AncientRPGRace race = AncientRPGRace.getRaceByName(pd.getRacename());
			if (race.raceSpells.contains(p)) {
				return true;
			}
		} catch (Exception ignored) {
		}
		return false;
	}

	public static Spell getSpell(String spellname, PlayerData pd) {
		Spell p = globalSpells.get(spellname.toLowerCase());
		if (p == null && pd.getClassName() != null && classList.containsKey(pd.getClassName().toLowerCase())) {
			AncientRPGClass clazz = classList.get(pd.getClassName().toLowerCase());
			if (clazz != null) {
				p = clazz.spellList.get(spellname.toLowerCase());
				if (p == null && pd.getStance() != null && clazz.stances.containsKey(pd.getStance().toLowerCase())) {
					p = clazz.stances.get(pd.getStance().toLowerCase()).spellList.get(spellname.toLowerCase());
				}
			}
		}
		if (pd.getRacename() != null) {
			AncientRPGRace race = AncientRPGRace.getRaceByName(pd.getRacename());
			if (race != null) {
				for (Spell sp : race.raceSpells) {
					if (sp.name.equalsIgnoreCase(spellname)) {
						return sp;
					}
				}
			}
		}
		return p;
	}

	public static Boolean canBind(String s, PlayerData playerData, Player player) {
		s = s.toLowerCase();
		Spell spell = getSpell(s, playerData);
		if (spell != null && !spell.active) {
			player.sendMessage("You can't bind passive spells");
			return false;
		}
		return spell != null;
	}

	public static void removePerms() {
		for (Player p : AncientRPG.plugin.getServer().getOnlinePlayers()) {
			AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
			if (p.isOnline() && mClass != null && mClass.permGroup != null && !mClass.permGroup.equals("")) {
				try {
					if (AncientRPG.permissionHandler != null && !mClass.permGroup.equals("")) {
						AncientRPG.permissionHandler.playerRemoveGroup(p, mClass.permGroup);
					}
				} catch (Exception ignored) {
				}
			}
		}
	}

	public static void addPerms() {
		for (Player p : AncientRPG.plugin.getServer().getOnlinePlayers()) {
			AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
			if (p.isOnline() && mClass != null && mClass.permGroup != null && !mClass.permGroup.equals("")) {
				try {
					if (AncientRPG.permissionHandler != null) {
						AncientRPG.permissionHandler.playerAddGroup(p, mClass.permGroup);
					}
				} catch (Exception ignored) {
				}
			}
		}
	}

	public boolean isWorldEnabled(World w) {
		if (w == null) return false;
	
		if (worlds.length == 1 && worlds[0].equals("")) return true;
	
		for (String s : worlds) {
		if (w.getName().equalsIgnoreCase(s)) return true;
	}
	
		return false;
	}
}