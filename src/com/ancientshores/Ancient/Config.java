package com.ancientshores.Ancient;

import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.Race.AncientRace;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
  final Ancient plugin;
  public final String directory;
  final File file;
  
  public Config(Ancient instance)
  {
    this.plugin = instance;
    this.directory = this.plugin.getDataFolder().getPath();
    this.file = new File(this.directory + File.separator + "config.yml");
  }
  
  public void addDefaults()
  {
    try
    {
      if (this.file.exists()) {
        this.plugin.getConfig().load(this.file);
      }
    }
    catch (FileNotFoundException ex) {}catch (IOException ex) {}catch (InvalidConfigurationException ex) {}
    this.plugin.getConfig().set("Ancient.language", Ancient.languagecode);
    
    AncientParty.writeConfig(this.plugin);
    
    AncientGuild.writeConfig(this.plugin);
    
    DamageConverter.writeConfig();
    
    AncientExperience.writeConfig(this.plugin);
    
    CreatureHp.writeConfig();
    AncientClass.writeConfig(this.plugin);
    AncientRace.writeRacesConfig(this.plugin);
    ManaSystem.writeConfig(this.plugin);
    Spell.writeConfig(this.plugin.getConfig());
    this.plugin.saveConfig();
  }
  
  public void loadkeys()
  {
    try
    {
      if (this.file.exists()) {
        this.plugin.getConfig().load(this.file);
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (InvalidConfigurationException e)
    {
      e.printStackTrace();
    }
    Ancient.languagecode = this.plugin.getConfig().getString("Ancient.language", Ancient.languagecode);
    
    AncientParty.loadConfig(this.plugin);
    
    AncientGuild.loadConfig(this.plugin);
    
    DamageConverter.loadConfig();
    
    AncientExperience.loadConfig(this.plugin);
    
    CreatureHp.loadConfig();
    AncientClass.loadConfig(this.plugin);
    AncientRace.loadRacesConfig(this.plugin);
    ManaSystem.loadConfig(this.plugin);
    Spell.loadConfig(this.plugin.getConfig());
    
    this.plugin.loadConfig(this.plugin.getConfig());
    if (this.file.exists()) {
      this.file.renameTo(new File(this.file.getPath() + ".old"));
    }
  }
}
