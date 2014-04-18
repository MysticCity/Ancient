package com.ancientshores.AncientRPG;

import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Config {
    final AncientRPG instance;

    public Config(AncientRPG instance) {
        this.instance = instance;
        this.directory = AncientRPG.plugin.getDataFolder().getPath();
        this.file = new File(directory + File.separator + "config.yml");
    }

    public final String directory;
    final File file;

    public void configCheck() {
    }

    public void addDefaults() {
        try {
            if (file.exists()) {
                instance.getConfig().load(file);
            }
        } catch (FileNotFoundException ignored) {
        } catch (IOException ignored) {
        } catch (InvalidConfigurationException ignored) {
        }
        instance.getConfig().set("AncientRPG.language", AncientRPG.languagecode);
        // Party Config
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.AncientRPGParty")) {
            AncientRPGParty.writeConfig(instance);
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild")) {
            AncientRPGGuild.writeConfig(instance);
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.HP.DamageConverter") && AncientRPG.classExists("com.ancientshores.AncientRPG.HP.AncientRPGHP")) {
            DamageConverter.writeConfig(instance);
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Experience.AncientRPGExperience")) {
            AncientRPGExperience.writeConfig(instance);
        }
        CreatureHp.writeConfig(instance);
        AncientRPGClass.writeConfig(instance);
        AncientRPGRace.writeRacesConfig(instance);
        ManaSystem.writeConfig(instance);
        Spell.writeConfig(instance.getConfig());
        instance.saveConfig();
    }

    public void loadkeys() {
        try {
            if (file.exists()) {
                instance.getConfig().load(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        AncientRPG.languagecode = instance.getConfig().getString("AncientRPG.language", AncientRPG.languagecode);
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Party.AncientRPGParty")) {
            AncientRPGParty.loadConfig(instance);
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Guild.AncientRPGGuild")) {
            AncientRPGGuild.loadConfig(instance);
        }

        if (AncientRPG.classExists("com.ancientshores.AncientRPG.HP.DamageConverter") && AncientRPG.classExists("com.ancientshores.AncientRPG.HP.AncientRPGHP")) {
            DamageConverter.loadConfig(instance);
        }
        if (AncientRPG.classExists("com.ancientshores.AncientRPG.Experience.AncientRPGExperience")) {
            AncientRPGExperience.loadConfig(instance);
        }
        CreatureHp.loadConfig(instance);
        AncientRPGClass.loadConfig(instance);
        AncientRPGRace.loadRacesConfig(instance);
        ManaSystem.loadConfig(instance);
        Spell.loadConfig(instance.getConfig());
        instance.loadConfig(instance.getConfig());
        if (file.exists()) {
            file.renameTo(new File(file.getPath() + ".old"));
        }
    }
}