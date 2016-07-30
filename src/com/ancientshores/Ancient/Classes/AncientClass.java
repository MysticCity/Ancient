package com.ancientshores.Ancient.Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
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

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Commands.ClassCastCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassInteractiveCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassListCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassResetCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassSetCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassSetStanceCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassSpelllistCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassStanceListCommand;
import com.ancientshores.Ancient.Classes.Commands.ClassUnbindCommand;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Spells.Commands.SpellBindCommand;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;

public class AncientClass implements Serializable {
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
	public static final String cNodeClass = "Ancient.classes.set";
	public static final String cNodeSpells = "Ancient.classes.spells";
	public static final String cNodeBind = "Ancient.classes.bind";
	public static final String cNodeChatCast = "Ancient.classes.chatcast";
	public static LinkedStringHashMap<AncientClass> classList = new LinkedStringHashMap<AncientClass>();
	public static LinkedStringHashMap<Spell> globalSpells = new LinkedStringHashMap<Spell>();
	public static CommandPlayer cp = new CommandPlayer();
	public static ConcurrentHashMap<UUID, Long> playersOnCd = new ConcurrentHashMap<UUID, Long>();
	public static HashMap<SpellInformationObject, UUID> executedSpells = new HashMap<SpellInformationObject, UUID>();
	public final String name;
	public boolean hidden = false;
	public List<String> worlds = new ArrayList<String>();
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
	public final LinkedHashMap<String, AncientClass> stances = new LinkedHashMap<String, AncientClass>();
	public final HashSet<Material> blacklistedMats = new HashSet<Material>();
	public final HashSet<Material> blacklistedArmor = new HashSet<Material>();

	public AncientClass() {
		name = "standard class";
	}

	@Override
	public boolean equals(Object c) {
		return c instanceof AncientClass && ((AncientClass) c).name.equals(c);
	}

	@SuppressWarnings("deprecation")
	public AncientClass(final File base) {
		name = base.getName();
		File[] spells = base.listFiles();
		if (spells != null) {
			for (final File f : spells) {
				if (f.getPath().endsWith(".spell")) {
					Spell p = new Spell(f);
					spellList.put(p.name.toLowerCase(), p);
				}
				if (f.isDirectory()) {
					AncientClass stance = new AncientClass(f);
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
		
		this.worlds = yc.getStringList("Class.enabled in world");
		
		if (yc.get("Class.permissiongroup") == null) yc.set("Class.permissiongroup", this.permGroup);
		
		this.permGroup = yc.getString("Class.permissiongroup", this.permGroup);
		if (yc.get("Class.minlevel") == null) yc.set("Class.minlevel", this.minlevel);
		
		this.minlevel = yc.getInt("Class.minlevel", this.minlevel);
		// bindings
		Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable() {
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
		for (int i = 1; i <= AncientExperience.getLevelCount(); i++) {
			if (yc.get("Class.hp of level " + i) == null) yc.set("Class.hp of level " + i, DamageConverter.getStandardHP());
			
			hplevel.put(i, (float) yc.getDouble("Class.hp of level " + i));
			if (yc.get("Class.hpreg of level " + i) == null) yc.set("Class.hpreg of level " + i, DamageConverter.getHPRegeneration());
			
			hpreglevel.put(i, (float) yc.getDouble("Class.hpreg of level " + i));
		}
		for (int i = 1; i <= AncientExperience.getLevelCount(); i++) {
			if (yc.get("Class.mana of level " + i) == null) yc.set("Class.mana of level " + i, ManaSystem.defaultMana);
			
			manalevel.put(i, yc.getInt("Class.mana of level " + i));
			if (yc.get("Class.manareg of level " + i) == null) yc.set("Class.manareg of level " + i, ManaSystem.defaultReg);
			
			manareglevel.put(i, yc.getInt("Class.manareg of level " + i));
		}
		if (yc.get("Class.default hpreg") == null) yc.set("Class.default hpreg", DamageConverter.getHPRegeneration());
		
		if (yc.get("Class.default manareg") == null) yc.set("Class.default manareg", ManaSystem.defaultReg);
		
		Ancient.set(yc, "Class.description", "");
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
		
		if (!classList.get(pd.getClassName().toLowerCase()).isWorldEnabled(p.getWorld())) {
			return true;
		}
		return classList.get(pd.getClassName().toLowerCase()).armorTypes.contains(t.toLowerCase());
	}

	public static boolean canUseSword(Player p, PlayerData pd, String t) {
		if (classList.get(pd.getClassName().toLowerCase()) == null) return true;
		
		if (!classList.get(pd.getClassName().toLowerCase()).isWorldEnabled(p.getWorld())) {
			return true;
		}
		return classList.get(pd.getClassName().toLowerCase()).weaponTypes.contains(t.toLowerCase());
	}

	public static void writeConfig(Ancient instance) {
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

	public static void loadConfig(Ancient instance) {
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
		classList = new LinkedStringHashMap<AncientClass>();
		globalSpells = new LinkedStringHashMap<Spell>();
		File basePath = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Class");
		File[] classes = basePath.listFiles();
		if (!basePath.exists()) {
			basePath.mkdir();
		}
		if (classes == null) {
			return;
		}
		for (final File f : classes) {
			if (f.getName().endsWith(".spell") && f.isFile()) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable() {
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
		File globspellpath = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "globalspells" + File.separator);
		if (!globspellpath.exists()) {
			globspellpath.mkdir();
		}
		File[] files = globspellpath.listFiles();
		if (files != null) {
			for (final File f : files) {
				if (f.getName().endsWith(".spell") && f.isFile()) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable() {
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
				AncientClass c = new AncientClass(f);
				classList.put(f.getName().toLowerCase(), c);
				if (c.shortcut != null && !c.shortcut.equals("")) {
					classList.put(c.shortcut, c);
				}
			}
		}
		File f = new File(Ancient.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
		if (f.exists()) {
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null)
					playersOnCd.put(UUID.fromString(line.split(";")[0]), Long.getLong(line.split(";")[1]));
				
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
			AncientClassHelp.printHelp(sender, args);
			return;
		}
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
				p.sendMessage(Ancient.ChatBrand + ChatColor.YELLOW + "Your class is: " + ChatColor.BLUE + pd.getClassName());

				AncientClass mClass = AncientClass.classList.get(pd.getClassName().toLowerCase());
				if (mClass != null && mClass.description != null && !mClass.description.equals("")) {
					p.sendMessage(mClass.description);
				}
				if (pd.getStance() != null && !pd.getStance().trim().equals("") && !pd.getStance().trim().equals("")) {
					p.sendMessage(Ancient.ChatBrand + ChatColor.YELLOW + "Your stance is " + ChatColor.BLUE + pd.getStance());
				}
				p.sendMessage(Ancient.ChatBrand + ChatColor.YELLOW + "To see a list of all Commands type: " + ChatColor.AQUA + "/class help");
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
			if (globalSpells.containsKey(spellname.toLowerCase()))
				return true;
			
			if (classList.containsKey(pd.getClassName().toLowerCase())) {
				AncientClass mClass = classList.get(pd.getClassName().toLowerCase());
				if (mClass != null) {
					if (mClass.spellList.containsKey(spellname.toLowerCase()))
						return true;
					if (pd.getStance() != null && !pd.getStance().equals("") && mClass.stances.containsKey(pd.getStance().toLowerCase())) {
						AncientClass mStance = mClass.stances.get(pd.getStance().toLowerCase());
						if (mStance != null)
							if (mStance.spellList.containsKey(spellname.toLowerCase()))
								return true;
					}
				}
			}
			AncientRace race = AncientRace.getRaceByName(pd.getRacename());
			if (race != null)
				for (Spell p : race.raceSpells)
					if (p.name.equals(spellname)) return true;
		} catch (Exception ignored) {}
		return false;
	}

	public static boolean spellAvailable(Spell p, PlayerData pd) {
		return spellAvailable(p.name, pd);
	}

	public static Spell getSpell(String spellname, PlayerData pd) {
		Spell p = globalSpells.get(spellname.toLowerCase());
		if (p == null && pd.getClassName() != null && classList.containsKey(pd.getClassName().toLowerCase())) {
			AncientClass clazz = classList.get(pd.getClassName().toLowerCase());
			if (clazz != null) {
				p = clazz.spellList.get(spellname.toLowerCase());
				if (p == null && pd.getStance() != null && clazz.stances.containsKey(pd.getStance().toLowerCase())) {
					p = clazz.stances.get(pd.getStance().toLowerCase()).spellList.get(spellname.toLowerCase());
				}
			}
		}
		if (pd.getRacename() != null) {
			AncientRace race = AncientRace.getRaceByName(pd.getRacename());
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
		for (Player p : Ancient.plugin.getServer().getOnlinePlayers()) {
			AncientClass mClass = AncientClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
			if (p.isOnline() && mClass != null && mClass.permGroup != null && !mClass.permGroup.equals("")) {
				try {
					if (Ancient.permissionHandler != null && !mClass.permGroup.equals("")) {
						Ancient.permissionHandler.playerRemoveGroup(p, mClass.permGroup);
					}
				} catch (Exception ignored) {
				}
			}
		}
	}

	public static void addPerms() {
		for (Player p : Ancient.plugin.getServer().getOnlinePlayers()) {
			AncientClass mClass = AncientClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
			if (p.isOnline() && mClass != null && mClass.permGroup != null && !mClass.permGroup.equals("")) {
				try {
					if (Ancient.permissionHandler != null) {
						Ancient.permissionHandler.playerAddGroup(p, mClass.permGroup);
					}
				} catch (Exception ignored) {
				}
			}
		}
	}

	public boolean isWorldEnabled(World w) {
		if (w == null) return false;
		
		if (worlds == null || worlds.size() == 0) return true; // if enabled and no worlds in list its everywhere enabled
		if (worlds.size() == 1 && worlds.get(0).equalsIgnoreCase("")) return true; // if list is not empty but only has a blank line
		
		for (String s : worlds) 
			if (w.getName().equalsIgnoreCase(s))
				return true;
		
		return false;
	}
}