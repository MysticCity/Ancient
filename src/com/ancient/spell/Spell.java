package com.ancient.spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Spell
  implements SpellSection
{
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
  
  public Spell(File f)
  {
    loadConfig(f);
    loadSpell(f);
  }
  
  private void loadSpell(File f)
  {
    File spell = new File(f.getAbsolutePath() + "/spell.spll");
    BufferedReader br = null;
    try
    {
      br = new BufferedReader(new FileReader(spell));
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    try
    {
      String s;
      while ((s = br.readLine()) != null) {
        this.items.put(Integer.valueOf(this.items.size()), SpellParser.parseLine(s, this));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void loadConfig(File f)
  {
    File file = new File(f.getAbsolutePath() + "/spell.cfg");
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    
    this.items = new HashMap();
    this.disabledWorlds = new ArrayList();
    for (String s : config.getString("disabled Worlds").split(",")) {
      this.disabledWorlds.add(s.trim());
    }
    this.spellType = SpellType.getByName(config.getString("spell type").trim());
    if (this.spellType == null) {}
    this.priority = config.getInt("priority");
    if (this.priority < 1) {
      this.priority = 1;
    }
    if (this.priority > 10) {
      this.priority = 10;
    }
    this.description = config.getString("description").trim();
    
    this.ignoreSpellFreeZones = config.getBoolean("ignore spellfree zones");
    
    this.hidden = config.getBoolean("hidden");
    
    this.clickType = ClickType.getByName(config.getString("click type").trim());
  }
  
  public void executeSpell()
  {
    int line = 0;
    while (line < this.items.size())
    {
      Object[] returns;
      try
      {
        returns = ((ExecutableSpellItem)this.items.get(Integer.valueOf(line))).execute();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
        return;
      }
      line = Integer.valueOf((String)returns[0]).intValue();
    }
  }
}
