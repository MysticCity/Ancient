package com.ancientshores.AncientRPG.Commands;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Config;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Util.LinkedStringHashMap;

public class ReloadCommand {
    public static void reload() {
        Bukkit.getServer().getLogger().log(Level.INFO, "AncientRPG: reloading...");
        Config mConfig = AncientRPG.plugin.config;
        if (mConfig == null) {
            mConfig = new Config(AncientRPG.plugin);
        }
        for (Entry<String, Integer> entr : Spell.registeredTasks.entrySet()) {
            Bukkit.getScheduler().cancelTask(entr.getValue());
        }
        mConfig.configCheck();
        mConfig.loadkeys();
        mConfig.addDefaults();
        AncientRPG.plugin.saveConfig();
        AncientRPG.plugin.initializeHelpFiles();
        for (PlayerData pd : PlayerData.playerData) {
            pd.save();
            pd.dispose();
        }
        AncientRPGGuild.loadGuilds();
        PlayerData.writePlayerData();
        PlayerData.playerData = new HashSet<PlayerData>();
        AncientRPG.plugin.reloadConfig();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            pd.getHpsystem().stopRegenTimer();
            if (DamageConverter.isEnabled() && DamageConverter.isEnabled(p.getWorld())) {
                pd.getHpsystem().setMaxHp();
                pd.getHpsystem().setHpRegen();
                pd.getHpsystem().setMinecraftHP();
            }
            pd.getManasystem().setMaxMana();
            if (AncientRPGExperience.isEnabled()) {
                pd.getXpSystem().addXP(0, false);
            }
            pd.getHpsystem().playerUUID = p.getUniqueId();
            pd.getHpsystem().startRegenTimer();
        }
        AncientRPGSpellListener.clearAll();
        AncientRPGClass.classList = new LinkedStringHashMap<AncientRPGClass>();
        AncientRPGRace.races = new HashSet<AncientRPGRace>();
        AncientRPGClass.loadClasses();
        AncientRPGClass.loadConfig(AncientRPG.plugin);
        AncientRPGRace.loadRaces();
        AncientRPGRace.loadRacesConfig(AncientRPG.plugin);
        Bukkit.getServer().getLogger().log(Level.INFO, "AncientRPG: reload complete");
    }
}