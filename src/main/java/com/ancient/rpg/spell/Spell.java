package com.ancient.rpg.spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Spell implements SpellSection {
	private HashMap<Integer, SpellItem> items;
	private List<String> disabledWorlds;
	private SpellType spellType;
	private int priority;
	private String description;
	private boolean ignoreSpellFreeZones;
	private boolean hidden;
	private ClickType clickType;
	private String shortDescription;
	private int repeatTime;
	
	public Spell(File f) {
		loadConfig(f);
		loadSpell(f);
	}
	
	private void loadSpell(File f) {
		File spell = new File(f.getAbsolutePath() + "/spell.spll");
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(spell));
		} catch (FileNotFoundException e) {
			// TODO cancel spell laden
			e.printStackTrace();
		}
		String s;	
		
//			Altes System
//			
//			Name
//			passive:5
//			playerteleportevent
//			if, 2  ==  3
//			AddItem, (GetPlayerByName, "FroznMine"), 89.0, 5.0
//			endif
//------------------------------------------------------------------			
//			Neues System
//			
//			if (2 == 3) {
//			  AddItem: (GetPlayerByName: "FroznMine"), 89.0, 5.0
//			}
			try {
				while ((s = br.readLine()) != null) {
					items.put(items.size(), SpellParser.parseLine(s, this));	
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
//
//				while ((s = br.readLine()) != null) {
//				s = s.trim();
//				
//				String[] splittedColon = s.split(":");
//				
//				if (splittedColon.length > 1) {
//					String commandName = SpellItems.getFullName(splittedColon[0].trim());
//					Class.forName(commandName).getConstructor(int.class).newInstance(line);
//				} else {
//					// check if else while for usw
//				}
//				SpellItems.getFullName()
//				SpellItem sp = new AddExperience(line);
//				items.put(items.size(), sp);
//				
//				line++;
//			}
				
	}

	//TODO überall überprüfen ob nicht null usw
	private void loadConfig(File f) {
		File file = new File(f.getAbsolutePath() + "/spell.cfg");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		this.items = new HashMap<Integer, SpellItem>();
		this.disabledWorlds = new ArrayList<String>();
		
		for (String s : config.getString("disabled Worlds").split(",")) {
			this.disabledWorlds.add(s.trim());
		}
		
		this.spellType = SpellType.getByName(config.getString("spell type").trim());
		
		if (this.spellType == null) {}//TODO exception abbrechen. ungültiger spell
		
		this.priority = config.getInt("priority");
		
		if (this.priority < 1) this.priority = 1;
		if (this.priority > 10) this.priority = 10;
		
		this.description = config.getString("description").trim();
		
		this.ignoreSpellFreeZones = config.getBoolean("ignore spellfree zones");
		
		this.hidden = config.getBoolean("hidden");
		
		this.clickType = ClickType.getByName(config.getString("click type").trim());
	}

	public void executeSpell() {
		int line = 0;
		while (line < items.size()) {
			Object[] returns;
			try {
				returns = ((ExecutableSpellItem) items.get(line)).execute();
			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
			
			line = (int) returns[0];
			
		}
	}
}
