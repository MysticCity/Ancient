package com.ancientshores.Ancient.Commands;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Config;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;

public class ReloadCommand {
    public static void reload() {
        Bukkit.getServer().getLogger().log(Level.INFO, "Ancient: reloading...");
        Config mConfig = Ancient.plugin.config;
        if (mConfig == null) {
            mConfig = new Config(Ancient.plugin);
        }
        for (Entry<String, Integer> entr : Spell.registeredTasks.entrySet()) {
            Bukkit.getScheduler().cancelTask(entr.getValue());
        }
//        mConfig.configCheck();
        mConfig.loadkeys();
        mConfig.addDefaults();
        Ancient.plugin.saveConfig();
        Ancient.plugin.initializeHelpFiles();
        AncientGuild.loadGuilds();
        PlayerData.writePlayerData();
        PlayerData.playerData = new HashSet<PlayerData>();
        Ancient.plugin.reloadConfig();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            pd.getHpsystem().stopRegenTimer();
            if (DamageConverter.isEnabledInWorld(p.getWorld())) {
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
        AncientClass.classList = new LinkedStringHashMap<AncientClass>();
        AncientRace.races = new HashSet<AncientRace>();
        AncientClass.loadClasses();
        AncientClass.loadConfig(Ancient.plugin);
        AncientRace.loadRaces();
        AncientRace.loadRacesConfig(Ancient.plugin);
        Bukkit.getServer().getLogger().log(Level.INFO, "Ancient: reload complete");
    }
}