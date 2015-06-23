package com.ancientshores.Ancient.Display;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Util.Bar.BarAPI;

public class Display {
	public static Bar xpBar = Bar.NONE;
	public static Bar manaBar = Bar.NONE;
	private static long changeTime = 0;
	
	// switch which should be shown when both are on same bar
	private static boolean xpCurrentlyShown;
	
	private final static String configNodeXPBar = "xp bar";
	private final static String configNodeManaBar = "mana bar";
	private final static String configNodeChangeTime = "switch time when both are on same bar";
	
	public static void loadConfig(Ancient plugin) {
		File f = new File(plugin.getDataFolder().getPath() + File.separator + "displayconfig.yml");
		if (f.exists()) {
			YamlConfiguration yc = new YamlConfiguration();
			try {
				yc.load(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
			xpBar = Bar.getByName(yc.getString(configNodeXPBar));
			manaBar = Bar.getByName(yc.getString(configNodeManaBar));
			changeTime = yc.getLong(configNodeChangeTime);
			
			// start switch timer
			if (changeTime > 0) {
				BukkitRunnable runnable = new BukkitRunnable() {
					
					@Override
					public void run() {
						switchBar();
					}
				};
				
				runnable.runTaskTimer(Ancient.plugin, 0, changeTime);
			}
		} else {
			writeConfig(plugin);
			loadConfig(plugin);
		}
	}
	
	public static void writeConfig(Ancient plugin) {
		File newfile = new File(plugin.getDataFolder().getPath() + File.separator + "displayconfig.yml");
		if (!newfile.exists()) {
			try {
				newfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration config = new YamlConfiguration();
		
		config.set(configNodeXPBar, xpBar.toString());
		config.set(configNodeManaBar, manaBar.toString());
		config.set(configNodeChangeTime, changeTime);
		
		try {
			config.save(newfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateMana(PlayerData pd) {
		Player p = Bukkit.getPlayer(pd.getPlayer());
		
		if (p == null) return;
		
		int mana = pd.getManasystem().curmana;
		int maxMana = pd.getManasystem().maxmana;
		
		switch (manaBar) {
		case BOSS:
			try {
				if (manaBar == xpBar) {
					// same bar
					if (!xpCurrentlyShown) {
						// mana shown now
						BarAPI.getInstance().setStatus(p, "Mana: " + mana, (float) mana / maxMana * 100, true);
					}
					break;
				}
				BarAPI.getInstance().setStatus(p, "Mana: " + mana, (float) mana / maxMana * 100, true);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case XP:
			if (manaBar == xpBar) {
				// same bar
				if (!xpCurrentlyShown) {
					// mana shown now
					p.setLevel(mana);
					p.setExp((float) mana / maxMana);
				}
				break;
			}
			p.setLevel(mana);
			p.setExp((float) mana / maxMana);
			break;
		case NONE:
			break;
		}
	}
	
	public static void updateXP(PlayerData pd) {
		Player p = Bukkit.getPlayer(pd.getPlayer());
		
		if (p == null) return;
		
		AncientExperience expSys = PlayerData.getPlayerData(p.getUniqueId()).getXpSystem();
		
		int xp = expSys.xp;
		
		int xpReqForCurrLvl = expSys.getExperienceOfLevel(expSys.level);
		int xpReqForNextLvl = expSys.getExperienceOfLevel(expSys.level + 1);
		
		int xpOfCurrLvl = xp - xpReqForCurrLvl;
		int deltaxpOfLevel = xpReqForNextLvl - xpReqForCurrLvl;
		
		float done = (float) xpOfCurrLvl / deltaxpOfLevel;
		
		switch (xpBar) {
		case BOSS:
			try {
				if (xpBar == manaBar) {
					// same bar
					if (xpCurrentlyShown) {
						// xp shown now
						BarAPI.getInstance().setStatus(p, "Level: " + expSys.level, (done >= 0 && done <= 100) ? done * 100 : 100, true);
					}
					break;
				}
				BarAPI.getInstance().setStatus(p, "Level: " + expSys.level, (done >= 0 && done <= 100) ? done * 100 : 100, true);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case XP:
			if (xpBar == manaBar) {
				// same bar
				if (xpCurrentlyShown) {
					// xp shown now
					p.setLevel(expSys.level);
					p.setExp((done >= 0 && done <= 1) ? done : 1);
				}
				break;
			}
			p.setLevel(expSys.level);
			p.setExp((done >= 0 && done <= 1) ? done : 1);
			break;
		case NONE:
			break;
		}
	}
	
	
	public static void switchBar() {
		xpCurrentlyShown ^= true;
		
		if (xpCurrentlyShown) {
			// XP Bar
			switch (xpBar) {
			case BOSS:
				for (Player p : Bukkit.getOnlinePlayers()) {
					AncientExperience expSys = PlayerData.getPlayerData(p.getUniqueId()).getXpSystem();
					
					int xp = expSys.xp;
					int xpReqForCurrLvl = expSys.getExperienceOfLevel(expSys.level);
					int xpReqForNextLvl = expSys.getExperienceOfLevel(expSys.level + 1);
					
					int xpOfCurrLvl = xp - xpReqForCurrLvl;
					int deltaxpOfLevel = xpReqForNextLvl - xpReqForCurrLvl;
					
					float done = (float) xpOfCurrLvl / deltaxpOfLevel * 100;
					
					try {
						BarAPI.getInstance().setStatus(p, "Level: " + expSys.level, (done >= 0 && done <= 100) ? done * 100 : 100, true);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				break;
			case XP:
				for (Player p : Bukkit.getOnlinePlayers()) {
					AncientExperience expSys = PlayerData.getPlayerData(p.getUniqueId()).getXpSystem();
					
					int xp = expSys.xp;
					int xpReqForCurrLvl = expSys.getExperienceOfLevel(expSys.level);
					int xpReqForNextLvl = expSys.getExperienceOfLevel(expSys.level + 1);
					
					int xpOfCurrLvl = xp - xpReqForCurrLvl;
					int deltaxpOfLevel = xpReqForNextLvl - xpReqForCurrLvl;
					
					float done = (float) xpOfCurrLvl / deltaxpOfLevel;
					
					p.setLevel(expSys.level);
					p.setExp(100 >= done && done >= 0 ? done : 100);
				}
				break;
			case NONE:
				break;
			}
		}
		else {
			// mana bar
			switch (manaBar) {
			case BOSS:
				for (Player p : Bukkit.getOnlinePlayers()) {
					ManaSystem manaSys = PlayerData.getPlayerData(p.getUniqueId()).getManasystem();
					
					int mana = manaSys.curmana;
					int maxMana = manaSys.maxmana;
					
					float done = (float) mana / maxMana * 100;
					
					try {
						BarAPI.getInstance().setStatus(p, "Mana: " + mana, done, true);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				break;
			case XP:
				for (Player p : Bukkit.getOnlinePlayers()) {
					ManaSystem manaSys = PlayerData.getPlayerData(p.getUniqueId()).getManasystem();
					
					int mana = manaSys.curmana;
					int maxMana = manaSys.maxmana;
					
					float done = (float) mana / maxMana * 100;
					
					p.setLevel(mana);
					p.setExp(100 >= done && done >= 0 ? done : 100);
				}
				break;
			case NONE:
				break;
			}
		}
	}
}