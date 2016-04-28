package com.ancientshores.Ancient.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Config;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ReloadCommand
{
  public static void reload()
  {
    Bukkit.getServer().getLogger().log(Level.INFO, "Ancient: reloading...");
    Config mConfig = Ancient.plugin.config;
    if (mConfig == null) {
      mConfig = new Config(Ancient.plugin);
    }
    for (Map.Entry<String, Integer> entr : Spell.registeredTasks.entrySet()) {
      Bukkit.getScheduler().cancelTask(((Integer)entr.getValue()).intValue());
    }
    mConfig.loadkeys();
    mConfig.addDefaults();
    Ancient.plugin.saveConfig();
    Ancient.plugin.initializeHelpFiles();
    AncientGuild.loadGuilds();
    PlayerData.writePlayerData();
    PlayerData.playerData = new HashSet();
    Ancient.plugin.reloadConfig();
    for (Player p : Bukkit.getOnlinePlayers())
    {
      PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
      pd.getHpsystem().stopRegenTimer();
      if (DamageConverter.isEnabledInWorld(p.getWorld()))
      {
        pd.getHpsystem().setMaxHP();
        pd.getHpsystem().setHpRegen();
        pd.getHpsystem().setMinecraftHP();
      }
      pd.getManasystem().setMaxMana();
      if (AncientExperience.isEnabled()) {
        pd.getXpSystem().addXP(0, false);
      }
      pd.getHpsystem().playerUUID = p.getUniqueId();
      pd.getHpsystem().startRegenTimer();
    }
    AncientSpellListener.clearAll();
    AncientClass.classList = new LinkedStringHashMap();
    AncientRace.races = new HashSet();
    AncientClass.loadClasses();
    AncientClass.loadConfig(Ancient.plugin);
    AncientRace.loadRaces();
    AncientRace.loadRacesConfig(Ancient.plugin);
    Bukkit.getServer().getLogger().log(Level.INFO, "Ancient: reload complete");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Commands\ReloadCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */