package com.ancientshores.Ancient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.Classes.CooldownTimer;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Race.AncientRace;


public class PlayerData implements Serializable, ConfigurationSerializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Speichert die Spielerdaten aller Spieler
	 */
	public static HashSet<PlayerData> playerData = new HashSet<PlayerData>();

	/**
	 * Name des Spielers und sein Archivement
	 */
	private UUID player;
	public int[] data;
	private AncientHP hpsystem;
	private String className;
	private AncientExperience xpSystem;
	public long lastFireDamage;
	public long lastCactusDamage;
	public long lastLavaDamage;
	public long lastFireDamageSpell;
	public long lastCactusDamageSpell;
	public long lastLavaDamageSpell;
	private HashSet<CooldownTimer> cooldownTimer = new HashSet<CooldownTimer>();
	private HashMap<String, Integer> classLevels;
	private HashMap<BindingData, String> bindings = new HashMap<BindingData, String>();
	private HashMap<Integer, String> slotbinds = new HashMap<Integer, String>();
	private String stance;
	private String racename;
	private final HashMap<String, Object> objects = new HashMap<String, Object>();
	private ManaSystem manasystem;

	@Override
	//Mapserialize
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("playeruuid", player.toString());
		map.put("hpsystem", hpsystem);
		map.put("className", className);
		map.put("xpsystem", xpSystem);
		// map.put("timer", cooldownTimer.toArray());
		// map.put("bindings", bindings);
		map.put("classlevels", classLevels);
		map.put("stance", stance);
		map.put("racename", racename);
		map.put("manasystem", manasystem);

		return map;
	}

	@SuppressWarnings("unchecked")
	//map UUID system
	public PlayerData(Map<String, Object> map) {
		if (map.containsKey("playeruuid")) {
			player = UUID.fromString((String) map.get("playeruuid"));
		}
		else {
			player = UUIDConverter.getUUID((String) map.get("player"));//Bukkit.getOfflinePlayer((String) map.get("player")).getUniqueId();
		}
		
		hpsystem = (AncientHP) map.get("hpsystem");
		className = (String) map.get("className");
		xpSystem = (AncientExperience) map.get("xpsystem");
		// cooldownTimer = new
		// HashSet<CooldownTimer>(Arrays.asList((CooldownTimer[])
		// map.get("timer")));
		// bindings = (HashMap<BindingData, String>) map.get("bindings");
		classLevels = (HashMap<String, Integer>) map.get("classlevels");
		stance = (String) map.get("stance");
		racename = (String) map.get("racename");
		manasystem = (ManaSystem) map.get("manasystem");
	}

	// public HashSet<Achievement> achievements;
	// public Experience level;
       /* Player UUID */
	/**
	 * creates a new object by the uuid of the Player
	 *
	 * @param playeruuid the UUID of the player for which the playerdata is created
	 */
	public PlayerData(UUID playeruuid) {
		classLevels = new HashMap<String, Integer>();
		this.player = playeruuid;
		/*
		 * if (Ancient.classExisting("de.pylamo.plugin.Achievement")) {
		 * achievements = new HashSet<Achievement>(); } data = new int[100];
		 * level = new Experience();
		 */
		hpsystem = new AncientHP(DamageConverter.getStandardHP(), playeruuid);
		
		className = AncientClass.standardclassName;
		cooldownTimer = new HashSet<CooldownTimer>();

		xpSystem = new AncientExperience(playeruuid);
		
		manasystem = new ManaSystem(playeruuid, ManaSystem.defaultMana);
		racename = AncientRace.defaultRace;
	}

	@Override
	public void finalize() {
		try {
			super.finalize();
		} catch (Throwable ignored) {

		}
		dispose();
	}
       // stop Regentimer
	public void dispose() {
		this.getManasystem().stopRegenTimer();
		this.getHpsystem().stopRegenTimer();
	}

	public void initialize() {
		this.getManasystem().setMaxMana();
		this.getManasystem().startRegenTimer();
		this.getHpsystem().setMaxHP();
		this.getHpsystem().setHpRegen();
		this.getHpsystem().startRegenTimer();
	}

	public void createMissingObjects() {
		if (className == null/* || className.equals("")*/) {
			className = AncientClass.standardclassName.toLowerCase();
			cooldownTimer = new HashSet<CooldownTimer>();
		}
		if (classLevels == null) classLevels = new HashMap<String, Integer>();
		
		if (hpsystem == null) hpsystem = new AncientHP(DamageConverter.getStandardHP(), this.player);

		AncientClass mClass = AncientClass.classList.get(className.toLowerCase());
		if (mClass != null) hpsystem.maxhp = mClass.hp;
		
		
		if (xpSystem == null) xpSystem = new AncientExperience(player);
		
		if (getManasystem() == null) manasystem = new ManaSystem(player, ManaSystem.defaultMana);
		
		if (this.cooldownTimer == null)
			this.cooldownTimer = new HashSet<CooldownTimer>();
		
		for (PlayerData pd : playerData) {
			if (pd.xpSystem != null) pd.xpSystem.addXP(0, false);
			if (pd.getBindings() == null) pd.bindings = new HashMap<BindingData, String>();
		}
		HashMap<BindingData, String> newbinds = new HashMap<BindingData, String>();
		for (Entry<BindingData, String> entr : this.getBindings().entrySet())
			newbinds.put(new BindingData(entr.getKey().id, entr.getKey().data), entr.getValue());
		
		this.bindings = newbinds;
	}

	public static void startSaveTimer() {
		Ancient.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable() {
			public void run() {
				PlayerData.writePlayerData();
			}
		}, 1200, 1200);
	}

	/** Checks if the spell with the given name is ready to get cast.
	 * 
	 * @param name The spell for which to check the cooldown
	 * @return true if ready, false otherwise. Also returns true if there is no cooldown found
	 */
	public boolean isCastReady(String name) {
		for (CooldownTimer ct : cooldownTimer)
			if (ct.cooldownname.equals(name))
				return ct.ready;
		return true;
	}

	/** Start the timer of the cooldown with the given name
	 * 
	 * @param name The cooldowns name
	 */
	public void startTimer(String name) {
		for (CooldownTimer ct : cooldownTimer) {
			if (ct.cooldownname.equals(name)) {
				ct.startTimer();
				return;
			}
		}
	}

	/** Add a new cooldown timer with the given name, which needs the given amount of time to run
	 * 
	 * @param name The cooldown's name
	 * @param time The cooldown's time
	 */
	public void addTimer(String name, int time) {
		CooldownTimer cd = new CooldownTimer(time, name);
		if (cooldownTimer.contains(cd))
			cooldownTimer.remove(cd);
		cooldownTimer.add(cd);
	}

	/** Get the time, the given cooldown needs to run
	 * 
	 * @param name The cooldown's name
	 * @return The remaining time
	 */
	public long getRemainingTime(String name) {
		for (CooldownTimer ct : cooldownTimer)
			if (ct.cooldownname.equals(name))
				return ct.time;
		return 0;
	}

	/** Check if there is a timer with the given name.
	 * 
	 * @param name The cooldown name to check
	 * @return true if it exists, false otherwise
	 */
	public boolean containsTimer(String name) {
		for (CooldownTimer ct : cooldownTimer)
			if (ct.cooldownname.equals(name)) return true;
		return false;
	}

	/**
	 * @return the uuid of the player for which this object stands for
	 */
	public UUID getPlayer() {
		return player;
	}

	/**
	 * If no playerdata exists for the player uuid given, it will create a new
	 * one
	 *
	 * @param uuid the player uuid for which it returns the (newly created) playerdata
	 * @return the (newly created) playerdata
	 */
	public static PlayerData getPlayerData(UUID uuid) {
		PlayerData pd = playerHasPlayerData(uuid);
		if (pd != null) {
			return pd;
		}
		pd = new PlayerData(uuid);
		playerData.add(pd);
		pd.initialize();
		return pd;
	}

	/**
	 * @param uuid the uuid of the player for which you want to check if he has
	 *			 already a playerdata
	 * @return the PlayerData of the given uuid
	 */
	@SuppressWarnings("unchecked")
	public static PlayerData playerHasPlayerData(UUID uuid) {
		for (PlayerData pd : playerData)
			if (pd.player != null && pd.player.compareTo(uuid) == 0)
				return pd;

		File folder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "players");
		File f = new File(folder.getPath() + File.separator + uuid + ".yml");
		if (f.exists()) {
			YamlConfiguration yc = new YamlConfiguration();
			File input = new File(folder.getPath() + File.separator + uuid + ".dat");
			try {
				yc.load(f);
				PlayerData pd = (PlayerData) yc.get("PlayerData");
				
//				FileReader fr = new FileReader(input);
//				BufferedReader br = new BufferedReader(fr);
								
				ObjectInputStream ois = null;
				try {
					ois = new ObjectInputStream(new FileInputStream(input));
					pd.cooldownTimer = (HashSet<CooldownTimer>) ois.readObject();
					pd.bindings = (HashMap<BindingData, String>) ois.readObject();
					pd.slotbinds = (HashMap<Integer, String>) ois.readObject();
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.SEVERE, "Cooldown and binding data corrupt, replacing them");
				} finally {
					if (ois != null) {
						try {
							ois.close();
						} catch (Exception ignored) {}
					}
				}
				playerData.add(pd);
				pd.createMissingObjects();
				pd.initialize();
				
				// Restart loaded timer
				for (CooldownTimer cd : pd.cooldownTimer) {
					cd.enabled = false;
					cd.startTimer();
				}
				return pd;
			} catch (Exception e) {
				e.printStackTrace();
				Bukkit.getLogger().log(Level.SEVERE, "Error in playerdata of player: " + uuid);
			}
		}
		return null;
	}

	public void save() {
		File folder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "players");
		if (!folder.exists()) {
			folder.mkdir();
		}
		File output = new File(folder.getPath() + File.separator + this.player + ".yml");
		File output2 = new File(folder.getPath() + File.separator + this.player + ".dat");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(output2));
			oos.writeObject(this.cooldownTimer);
			oos.writeObject(this.bindings);
			oos.writeObject(this.slotbinds);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception ignored) {}
			}
		}
		YamlConfiguration yc = new YamlConfiguration();
		yc.set("PlayerData", this);
		try {
			yc.save(output);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.getLogger().log(Level.SEVERE, "Error while saving playerdata of player " + player);
		}
	}

	/**
	 * Writes the playerdata in the file /Ancient/player.dat
	 */
	public static void writePlayerData() {
		for (PlayerData pd : playerData) {
			pd.save();
		}
	}

	public static boolean checkForOldAndConvertPlayerData() {
		File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "player.dat");
		if (f.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);

				ObjectInputStream ois = new ObjectInputStream(fis);
				Object input = ois.readObject();
				@SuppressWarnings("unchecked") HashSet<PlayerData> oldset = (HashSet<PlayerData>) input;
				fis.close();
				ois.close();
				playerData.addAll(oldset);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			String s = "oldplayer";
			File newFile = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + s + ".dat");
			while (newFile.exists()) {
				s += 1;
				newFile = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + s + ".dat");
			}
			f.renameTo(newFile);
			return true;
		}
		return false;
	}

	public AncientHP getHpsystem() {
		return hpsystem;
	}

	public void setHpsystem(AncientHP hpsystem) {
		this.hpsystem = hpsystem;
		save();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		save();
	}

	public AncientExperience getXpSystem() {
		return xpSystem;
	}

	public void setXpSystem(AncientExperience xpSystem) {
		this.xpSystem = xpSystem;
		save();
	}

	public HashSet<CooldownTimer> getCooldownTimer() {
		return cooldownTimer;
	}

	public void setCooldownTimer(HashSet<CooldownTimer> cooldownTimer) {
		this.cooldownTimer = cooldownTimer;
		save();
	}

	public HashMap<String, Integer> getClassLevels() {
		return classLevels;
	}

	public void setClassLevels(HashMap<String, Integer> classLevels) {
		this.classLevels = classLevels;
		save();
	}

	public HashMap<BindingData, String> getBindings() {
		return bindings;
	}

	public void setBindings(HashMap<BindingData, String> bindings) {
		this.bindings = bindings;
		save();
	}

	public String getStance() {
		return stance;
	}

	public void setStance(String stance) {
		this.stance = stance;
		save();
	}

	public String getRacename() {
		return racename;
	}

	public void setRacename(String racename) {
		this.racename = racename;
		save();
	}

	public void setPlayer(UUID player) {
		this.player = player;
		save();
	}

	public Object getKeyValue(String key) {
		return objects.get(key);
	}

	public void addKey(String key, Object o) {
		this.objects.put(key, o);
	}

	public ManaSystem getManasystem() {
		return manasystem;
	}

	public HashMap<Integer, String> getSlotbinds() {
		return slotbinds;
	}
}
