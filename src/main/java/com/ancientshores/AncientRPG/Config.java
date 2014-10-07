package com.ancientshores.AncientRPG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;

public class Config {
	//NAMELOSE BLOCK
	final AncientRPG plugin; // ??? kann vielleicht private
	public final String directory; // ??? kann vielleicht private
	final File file; // ??? kann vielleicht private

	public Config(AncientRPG instance) {
		this.plugin = instance; // das Plugin speichern
		this.directory = plugin.getDataFolder().getPath(); // den Pfad mit den Dateien speichern
		this.file = new File(directory + File.separator + "config.yml"); // die Config-Datei laden
	}

	/*public void configCheck() { // --- was soll hier passieren? */
   
	}
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
		
		plugin.getConfig().set("AncientRPG.language", AncientRPG.languagecode); // in der Config die verwendete Sprache auf die Angegebene setzten. --- solange bis dies auch was anderes als en sein kann
		
		// Party Config
		AncientRPGParty.writeConfig(plugin); // ???
		
		
		// Gilden Config
		AncientRPGGuild.writeConfig(plugin); // ???
		
		// HP Config
		DamageConverter.writeConfig(plugin); // ???
	   
		// XP Config
		AncientRPGExperience.writeConfig(plugin); // ???
		
		// ??? warum kommt hier nicht die Überprüfung
		CreatureHp.writeConfig(plugin); // ???
		AncientRPGClass.writeConfig(plugin); // ???
		AncientRPGRace.writeRacesConfig(plugin); // ???
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
		
		AncientRPG.languagecode = plugin.getConfig().getString("AncientRPG.language", AncientRPG.languagecode); // die Sprache setzen
		
		AncientRPGParty.loadConfig(plugin); // Party config laden ???
		
		AncientRPGGuild.loadConfig(plugin); // Guilden config laden ???
		
		DamageConverter.loadConfig(plugin); // converter config laden ???
		
		AncientRPGExperience.loadConfig(plugin); // xp config laden ???
		
		CreatureHp.loadConfig(plugin); // creaturen hp config laden ???
		AncientRPGClass.loadConfig(plugin); // klassen config laden ???
		AncientRPGRace.loadRacesConfig(plugin); // rassen config laden ???
		ManaSystem.loadConfig(plugin); // mana config laden ???
		Spell.loadConfig(plugin.getConfig()); // srüche config laden ???
		
		plugin.loadConfig(plugin.getConfig()); // plugin config laden
		
		if (file.exists()) { // wenn die datei existiert
			file.renameTo(new File(file.getPath() + ".old")); // Datei umbenennen damit sie überschrieben wird
		}
	}
}
