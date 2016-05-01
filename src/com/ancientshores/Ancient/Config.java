package com.ancientshores.Ancient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.Race.AncientRace;

public class Config {
	//NAMELOSE BLOCK
	final Ancient plugin; // ??? kann vielleicht private
	public final String directory; // ??? kann vielleicht private
	final File file; // ??? kann vielleicht private

	public Config(Ancient instance) {
		this.plugin = instance; // das Plugin speichern
		this.directory = plugin.getDataFolder().getPath(); // den Pfad mit den Dateien speichern
		this.file = new File(directory + File.separator + "config.yml"); // die Config-Datei laden
	}

	/*public void configCheck() { // --- was soll hier passieren? 
   
	}*/
         //Laden der Konfiguration
	public void addDefaults() {
		try {
			if (file.exists()) {
				plugin.getConfig().load(file); // wenn die datei existiert die plugin-config durch diese datei ersetzen
			}
		} catch (FileNotFoundException ex) { // nichts bei Fehlern
		} catch (IOException ex) {
		} catch (InvalidConfigurationException ex) {
		}
		
		plugin.getConfig().set("Ancient.language", Ancient.languagecode); // in der Config die verwendete Sprache auf die Angegebene setzten. --- solange bis dies auch was anderes als en sein kann
		
		// Party Config
		AncientParty.writeConfig(plugin); // ???
		
		
		// Gilden Config
		AncientGuild.writeConfig(plugin); // ???
		
		// HP Config
		DamageConverter.writeConfig(); // ???
	   
		// XP Config
		AncientExperience.writeConfig(plugin); // ???
		
		// ??? warum kommt hier nicht die Überprüfung
		CreatureHp.writeConfig(); // ???
		AncientClass.writeConfig(plugin); // ???
		AncientRace.writeRacesConfig(plugin); // ???
		ManaSystem.writeConfig(plugin); // ???
		Spell.writeConfig(plugin.getConfig()); // ??? wird hier die gleiche config wie die des plugins verwendet?
		plugin.saveConfig(); // die configuration wird gespeichert
	}

	public void loadkeys() {
		try {
			if (file.exists()) { // wenn die Datei existiert
				plugin.getConfig().load(file); // Configuration aus der angegebenen Datei laden
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
                //      WANT TO REENABLE ?  DONT FORGET THE ANCIENT CLASS !
		//Ancient.languagecode = plugin.getConfig().getString("Ancient.language", Ancient.languagecode); // die Sprache setzen
		
		AncientParty.loadConfig(plugin); // Party config laden ???
		
		AncientGuild.loadConfig(plugin); // Guilden config laden ???
		
		DamageConverter.loadConfig(); // load damage converter config; handles damage made by monsters/weapons
		
		AncientExperience.loadConfig(plugin); // xp config laden ???
		
		CreatureHp.loadConfig(); // creaturen hp config laden ???
		AncientClass.loadConfig(plugin); // klassen config laden ???
		AncientRace.loadRacesConfig(plugin); // rassen config laden ???
		ManaSystem.loadConfig(plugin); // mana config laden ???
		Spell.loadConfig(plugin.getConfig()); // srüche config laden ???
		
		plugin.loadConfig(plugin.getConfig()); // plugin config laden
		
		if (file.exists()) { // wenn die datei existiert
			file.renameTo(new File(file.getPath() + ".old")); // Datei umbenennen damit sie überschrieben wird
		}
	}
}
