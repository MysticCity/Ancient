/*
 * This file is part of Ancient
 *
 * Copyright (C) 2011-2013 Keyle
 * Ancient is licensed under the GNU Lesser General Public License.
 *
 * Ancient is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ancient is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ancientshores.Ancient.Classes.Spells;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Listeners.SpellListener.ISpellListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerBedEnterEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerBedLeaveEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerChatEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerDropItemEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerEggThrowEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerKickEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerPickupItemEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerPortalEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerTeleportEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerToggleSneakEventListener;
import com.ancientshores.Ancient.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.Ancient.Util.SerializableZone;

public class Spell implements Serializable {
    private static final long serialVersionUID = 1L;
    public static CommandMap commandMap = null;
    public static HashSet<String> disabledWorlds = new HashSet<String>();
    public static final String worldnode = "Spells.disabled worlds";
    public static HashMap<String, Integer> registeredTasks = new HashMap<String, Integer>();
    public static ConcurrentHashMap<String, ISpellListener> eventListeners = new ConcurrentHashMap<String, ISpellListener>();

    static {
        try {
            final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
            eventListeners.put("playerbedenterevent", new PlayerBedEnterEventListener(Ancient.plugin));
            eventListeners.put("playerbedleaveevent", new PlayerBedLeaveEventListener(Ancient.plugin));
            eventListeners.put("playerchatevent", new PlayerChatEventListener(Ancient.plugin));
            eventListeners.put("playerdropitemevent", new PlayerDropItemEventListener(Ancient.plugin));
            eventListeners.put("playerkickevent", new PlayerKickEventListener(Ancient.plugin));
            eventListeners.put("playerpickupitemevent", new PlayerPickupItemEventListener(Ancient.plugin));
            eventListeners.put("playerportalevent", new PlayerPortalEventListener(Ancient.plugin));
            eventListeners.put("playerteleportevent", new PlayerTeleportEventListener(Ancient.plugin));
            eventListeners.put("playertogglesneakevent", new PlayerToggleSneakEventListener(Ancient.plugin));
            eventListeners.put("playereggthrowevent", new PlayerEggThrowEventListener(Ancient.plugin));
        } catch (final SecurityException ignored) {
        } catch (final Exception ignored) {
        }
    }

    ICodeSection mainsection;
    public String name;
    public String caseSensitiveName;
    public boolean active;
    public boolean serverspell;
    public final HashSet<String> variables = new HashSet<String>();
    public int minlevel = 0;
    public int priority = 5;
    public String buffEvent;
    final File newConfigFile;
    String permission;
    public boolean hidden = false;
    public boolean leftclickonly = false;
    public boolean rightclickonly = false;
    public String description = "";
    public String shortdescription = "";
    public boolean buff;
    public int repeattime = 0;
    public boolean ignorespellfreezones = false;


    public Spell(File f) {
        String configName = f.getName().split("\\.(?=[^\\.]+$)")[0] + ".conf";
        String newConfigName = f.getName().split("\\.(?=[^\\.]+$)")[0] + ".cfg";
        long before = System.currentTimeMillis();
        parseFile(f);
        long after = System.currentTimeMillis();
        File configFile = new File(f.getParent() + File.separator + configName);
        newConfigFile = new File(f.getParent() + File.separator + newConfigName);
        if (!newConfigFile.exists()) {
            try {
                newConfigFile.createNewFile();
                YamlConfiguration yc = new YamlConfiguration();
                yc.set(name + ".description", "");
                Ancient.set(yc, name + ".hidden", false);
                yc.save(newConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yc1 = new YamlConfiguration();
        try {
            yc1.load(newConfigFile);
            Ancient.set(yc1, name + ".description", "");
            this.description = yc1.getString(name + ".description", "");
            Ancient.set(yc1, name + ".shortdescription", "");
            this.shortdescription = yc1.getString(name + ".shortdescription", "");
            Ancient.set(yc1, name + ".ignorespellfreezones", false);
            this.ignorespellfreezones = yc1.getBoolean(name + ".ignorespellfreezones", false);
            Ancient.set(yc1, name + ".hidden", false);
            this.hidden = yc1.getBoolean(name + ".hidden", false);
            Ancient.set(yc1, name + ".leftclickonly", false);
            this.leftclickonly = yc1.getBoolean(name + ".leftclickonly", false);
            Ancient.set(yc1, name + ".rightclickonly", false);
            this.rightclickonly = yc1.getBoolean(name + ".rightclickonly", false);
            yc1.save(newConfigFile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (configFile.exists()) {
            try {

                HashMap<String, Integer[]> map = Ancient.plugin.fc.getValues(configFile);
                YamlConfiguration yc = new YamlConfiguration();
                try {
                    yc.load(newConfigFile);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                for (Entry<String, Integer[]> entry : map.entrySet()) {
                    Integer[] vals = entry.getValue();
                    for (int i = 0; i < vals.length; i++) {
                        yc.set(name + ".variables." + entry.getKey() + ".level" + (i + 1), vals[i]);
                    }
                }
                try {
                    yc.save(newConfigFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                configFile.delete();
            } catch (Exception e) {
                Ancient.plugin.getLogger().log(Level.SEVERE, "Error converting conf file of " + name + " spell to cfg file.");
            }
        }
        Bukkit.getLogger().log(Level.INFO, "Compiled spell " + this.name + " took " + (after - before) + " milliseconds");
    }

    public void skipCommands(SpellInformationObject so, int count) {
        so.skipedCommands += count;
    }

    private void parseFile(File f) {
        try {
            int lineNumber = 1;
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line = bf.readLine();
            while (line != null && line.startsWith("//")) {
                line = bf.readLine();
                lineNumber++;
            }
            name = line;
            line = bf.readLine();
            lineNumber++;
            while (line != null && line.startsWith("//")) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line != null) {
                if (line.toLowerCase().startsWith("passive")) {
                    active = false;
                    if (line.contains(":")) {
                        String[] args = line.split(Pattern.quote(":"));
                        try {
                            priority = Integer.parseInt(args[1]);
                        } catch (Exception ignored) {

                        }
                    }
                    line = bf.readLine();
                    lineNumber++;
                    while (line.startsWith("//") && line != null) {
                        line = bf.readLine();
                        lineNumber++;
                    }
                    if (line != null) {
                        line = line.trim();
                        attachToEvent(line);
                    }
                } else if (line.toLowerCase().startsWith("active")) {
                    active = true;
                    if (line.contains(":")) {
                        String[] args = line.split(Pattern.quote(":"));
                        try {
                            priority = Integer.parseInt(args[1]);
                        } catch (Exception ignored) {

                        }
                    }
                } else if (line.toLowerCase().startsWith("buff")) {
                    active = false;
                    buff = true;
                    if (line.contains(":")) {
                        String[] args = line.split(Pattern.quote(":"));
                        try {
                            priority = Integer.parseInt(args[1]);
                        } catch (Exception ignored) {

                        }
                    }
                    line = bf.readLine();
                    lineNumber++;
                    while (line.startsWith("//") && line != null) {
                        line = bf.readLine();
                        lineNumber++;
                    }
                    line = line.trim();
                    if (line.contains(":")) {
                        String[] args = line.split(Pattern.quote(":"));
                        line = args[0];
                        try {
                            priority = Integer.parseInt(args[1]);
                        } catch (Exception ignored) {

                        }
                    }
                    this.buffEvent = line.trim().toLowerCase();
                } else {
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Failed to load Spell " + name + " in line " + lineNumber + " forgot active/passive/buff declaration?");
                    bf.close();
                    return;
                }
            }
            line = bf.readLine();
            lineNumber++;

            while (line != null && line.startsWith("//")) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.startsWith("variables")) {
                try {
                    String vars = line.split(":")[1].trim();
                    for (String s : vars.split(",")) {
                        this.variables.add(s.trim().toLowerCase());
                    }
                } catch (Exception e) {
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Failed to load Spell " + name + " in line " + lineNumber + " variable declaration in wrong line?");
                    bf.close();
                    return;
                }
            }
            while (line != null && line.startsWith("//")) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("minlevel:")) {
                String[] minlevelS = line.split(":");
                if (minlevelS.length == 2) {
                    try {
                        minlevel = Integer.parseInt(minlevelS[1].trim());
                    } catch (Exception e) {
                        Ancient.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
                        Ancient.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
                        Ancient.plugin.getLogger().log(Level.SEVERE, "Could not parse the minlevel of the spell");
                    }
                } else {
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Could not parse the minlevel of the spell");
                }
                line = bf.readLine();
                lineNumber++;
            }
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("permission:")) {
                String[] perms = line.split(":");
                if (perms.length == 2) {
                    this.permission = perms[1].trim();
                } else {
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
                    Ancient.plugin.getLogger().log(Level.SEVERE, "Could not parse the permission of the spell");
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
            }
            mainsection = new CodeSection(line, bf, lineNumber, this, null);
            bf.close();
        } catch (FileNotFoundException e) {
            Ancient.plugin.log.log(Level.SEVERE, "Wasn't able to load a spell. Reason: File not found!");
        } catch (Exception e) {
            Ancient.plugin.log.log(Level.SEVERE, "Wasn't able to load a spell. Reason: Error parsing file!");
        }
    }

    public void attachToEvent(String s) {
        if (s == null) {
            Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: No event found to attach to in spell " + this.name);
        }
        if (s.equalsIgnoreCase("damageevent")) {
            AncientSpellListener.damageEventSpells.add(this);
        } else if (s.equalsIgnoreCase("damagebyentityevent")) {
            AncientSpellListener.damageByEntityEventSpells.add(this);
        } else if (s.equalsIgnoreCase("attackevent")) {
            AncientSpellListener.attackEventSpells.add(this);
        } else if (s.equalsIgnoreCase("changeblockevent")) {
            AncientSpellListener.ChangeBlockEventSpells.add(this);
        } else if (s.equalsIgnoreCase("joinevent")) {
            AncientSpellListener.joinEventSpells.add(this);
        } else if (s.equalsIgnoreCase("interactevent")) {
            AncientSpellListener.interactEventSpells.add(this);
        } else if (s.equalsIgnoreCase("chatevent")) {
            AncientSpellListener.chatEventSpells.add(this);
        } else if (s.equalsIgnoreCase("regenevent")) {
            AncientSpellListener.regenEventSpells.add(this);
        } else if (s.equalsIgnoreCase("moveevent")) {
            AncientSpellListener.moveEventSpells.add(this);
        } else if (s.equalsIgnoreCase("levelupevent")) {
            AncientSpellListener.LevelupEventSpells.add(this);
        } else if (s.equalsIgnoreCase("killentityevent")) {
            AncientSpellListener.killEntityEventSpells.add(this);
        } else if (s.equalsIgnoreCase("playerdeathevent")) {
            AncientSpellListener.playerDeathSpells.add(this);
        } else if (s.equalsIgnoreCase("projectilehitevent")) {
            AncientSpellListener.ProjectileHitEventSpells.add(this);
        } else if (s.equalsIgnoreCase("usebedevent")) {
            AncientSpellListener.UseBedEventSpells.add(this);
        } else if (s.equalsIgnoreCase("classchangeevent")) {
            AncientSpellListener.classChangeEventSpells.add(this);
        } else if (s.startsWith("chatcommand")) {
            String[] split = s.split(Pattern.quote(":"));
            if (split.length != 2) {
                Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s);
            } else {
                if (commandMap == null) {
                    Bukkit.getLogger().log(Level.SEVERE, "Wasn't able to register Commands.");
                } else {
                    try {
                        Constructor<?> c = Class.forName("org.bukkit.command.PluginCommand").getDeclaredConstructors()[0];
                        c.setAccessible(true);
                        PluginCommand pc = (PluginCommand) c.newInstance(split[1], Ancient.plugin);
                        final Spell mSpell = this;
                        pc.setExecutor(new CommandExecutor() {

                            @Override
                            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
                                if (sender instanceof Player) {
                                    Player p = (Player) sender;
                                    for (Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
                                        if (sz.getValue().isInZone(p.getLocation()) && !p.hasPermission(AddSpellFreeZoneCommand.ignorespellfreezones)) {
                                            return true;
                                        }
                                    }
                                    if (AncientClass.spellAvailable(mSpell.name, PlayerData.getPlayerData(p.getUniqueId()))) {
                                    	mSpell.execute(p, p, null, args);
                                    }
                                }
                                return true;
                            }
                        });
                        commandMap.register(split[1], pc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (s.startsWith("repetitive")) {
            String[] split = s.split(Pattern.quote(":"));
            if (split.length != 2) {
                Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s);
            } else {
                int time;
                try {
                    time = Math.round(Integer.parseInt(split[1].trim()) / 50);
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s + " 2nd argumend needs to be an integer");
                    return;
                }
                if (registeredTasks.containsKey(this.name)) {
                    Bukkit.getScheduler().cancelTask(registeredTasks.get(this.name));
                }
                registeredTasks.put(this.name, Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (serverspell) {
                            Spell.this.executeServerSpell();
                        } else {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (AncientClass.spellAvailable(Spell.this.name, PlayerData.getPlayerData(p.getUniqueId()))) {
                                    for (Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
                                        if (sz.getValue().isInZone(p.getLocation()) && !p.hasPermission(AddSpellFreeZoneCommand.ignorespellfreezones)) {
                                            return;
                                        }
                                    }
                                    Spell.this.execute(p.getUniqueId(), p.getUniqueId(), null);
                                }
                            }
                        }
                    }
                }, time, time));
            }
        } else {
            for (Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(s)) {
                    entry.getValue().eventSpells.add(this);
                    return;
                }
            }
            Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: Event " + s + " not found in spell " + this.name);
        }
    }

    public static void initializeServerSpells() {
        File serv = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "serverspells");
        serv.mkdir();
        File[] files = serv.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".spell")) {
                    Spell s = new Spell(f);
                    s.serverspell = true;
                }
            }
        }
    }

    public int attachToEventAsBuff(Player p, Player buffcaster) {
        if (this.permission != null && !this.permission.equals("") && !buffcaster.hasPermission(this.permission)) {
            return Integer.MAX_VALUE;
        }
        if (buffEvent == null) {
            Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: No event found to attach to in spell " + this.name);
            return Integer.MAX_VALUE;
        }
        Player[] arr = {p, buffcaster};
        if (buffEvent.equalsIgnoreCase("damageevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.damageEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("damagebyentityevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.damageByEntityEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("attackevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.attackEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("changeblockevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.ChangeBlockEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("joinevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.joinEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("interactevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.interactEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("chatevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.chatEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("regenevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.regenEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("moveevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.moveEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("levelupevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.LevelupEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("playerdeathevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.playerDeathBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("killentityevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.killEntityEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("projectilehitevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.ProjectileHitEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("usebedevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.UseBedEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("classchangeevent")) {
            return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.classChangeEventBuffs, arr);
        } else {
            for (Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(buffEvent)) {
                    return entry.getValue().attachBuffToEvent(this, arr);
                }
            }
            Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: Event " + buffEvent + " not found in spell " + this.name);
            return Integer.MAX_VALUE;
        }
    }

    public void detachBuffOfEvent(Player p, Player buffcaster, int id) {
        if (buffEvent == null) {
            Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: No event found to attach to in spell " + this.name);
        }
        Player[] arr = {p, buffcaster};
        if (buffEvent.equalsIgnoreCase("damageevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.damageEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("damagebyentityevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.damageByEntityEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("attackevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.attackEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("changeblockevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.ChangeBlockEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("joinevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.joinEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("interactevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.interactEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("chatevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.chatEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("regenevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.regenEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("moveevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.moveEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("levelupevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.LevelupEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("playerdeathevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.playerDeathBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("killentityevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.killEntityEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("projectilehitevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.ProjectileHitEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("usebedevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.UseBedEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("classchangeevent")) {
            AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.classChangeEventBuffs, arr, id);
        } else {
            for (Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(buffEvent)) {
                    entry.getValue().detachBuffOfEvent(this, arr, id);
                    return;
                }
            }
            Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: Event " + buffEvent + " not found in spell " + this.name);
        }
    }

    public void executeServerSpell() {
        SpellInformationObject so = new SpellInformationObject();
        so.variables.putAll(Variable.globVars);
        so.mSpell = this;
        if (mainsection != null) {
            try {
                mainsection.executeCommand(null, so);
            } catch (Exception ignored) {

            }
        }
    }

    public void execute(Player p, Player buffcaster, Event e, String[] args) {
        if (disabledWorlds.contains(p.getWorld().getName().toLowerCase())) {
        	buffcaster.sendMessage("This spell is not enabled in this world");
            return;
        }
        if (this.permission != null && !this.permission.equals("") && !buffcaster.hasPermission(this.permission)) {
            if (this.active) {
                buffcaster.sendMessage("You don't have permission to cast this spell");
            }
            return;
        }
        if (AncientExperience.isWorldEnabled(buffcaster.getWorld())) {
            if (PlayerData.getPlayerData(buffcaster.getUniqueId()).getXpSystem().level < minlevel) {
                if (active) {
                    buffcaster.sendMessage("You need to be atleast level " + minlevel + " to cast this spell");
                }
                return;
            }
        }
        for (Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
            if (sz.getValue().isInZone(p.getLocation()) && !p.hasPermission(AddSpellFreeZoneCommand.ignorespellfreezones)) {
                if (active) {
                    p.sendMessage("You can't use a spell in this zone");
                }
                return;
            }
        }
        SpellInformationObject so = new SpellInformationObject();
        so.chatmessage = args;
        so.variables.putAll(Variable.globVars);
        if (Variable.playerVars.containsKey(buffcaster.getUniqueId())) {
            so.variables.putAll(Variable.playerVars.get(buffcaster.getUniqueId()));
        }
        if (e != null) {
            so.mEvent = e;
        }
        AncientClass.executedSpells.put(so, p.getUniqueId());
        so.buffcaster = buffcaster;
        so.mSpell = this;
        if (mainsection != null) {
        	mainsection.executeCommand(p, so);
        }
    }

    public void execute(UUID uuidPlayer, UUID uuidBuffcaster, Event e) {
    	Player p = Bukkit.getPlayer(uuidPlayer);
    	Player buffcaster = Bukkit.getPlayer(uuidBuffcaster);
    	
    	if (p == null || buffcaster == null) {
    		return;
    	}
    	
    	if (disabledWorlds.contains(p.getWorld().getName().toLowerCase())) {
    		buffcaster.sendMessage("This spell is not enabled in this world");
            return;
        }
        if (this.permission != null && !this.permission.equals("") && !buffcaster.hasPermission(this.permission)) {
            if (this.active) {
                buffcaster.sendMessage("You don't have permission to cast this spell");
            }
            return;
        }
        if (AncientExperience.isWorldEnabled(buffcaster.getWorld())) {
            if (PlayerData.getPlayerData(buffcaster.getUniqueId()).getXpSystem().level < minlevel) {
                if (active) {
                    buffcaster.sendMessage("You need to be atleast level " + minlevel + " to cast this spell");
                }
                return;
            }
        }
        for (Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
            if (sz.getValue().isInZone(p.getLocation()) && !p.hasPermission(AddSpellFreeZoneCommand.ignorespellfreezones)) {
                if (active) {
                    p.sendMessage("You can't use a spell in this zone");
                }
                return;
            }
        }
        SpellInformationObject so = new SpellInformationObject();
        so.variables.putAll(Variable.globVars);
        if (Variable.playerVars.containsKey(buffcaster.getUniqueId())) {
            so.variables.putAll(Variable.playerVars.get(buffcaster.getUniqueId()));
        }
        if (e != null) {
            so.mEvent = e;
        }
        AncientClass.executedSpells.put(so, p.getUniqueId());
        so.buffcaster = buffcaster;
        so.mSpell = this;
        if (mainsection != null) {
        	mainsection.executeCommand(p, so);
        }
    }

    public static void loadConfig(FileConfiguration yc) {
        disabledWorlds = new HashSet<String>();
        String s = yc.getString(Spell.worldnode, "");
        s = s.trim();
        String[] splits = s.split(Pattern.quote(","));
        for (String sn : splits) {
            disabledWorlds.add(sn.trim().toLowerCase());
        }
    }

    public static void writeConfig(FileConfiguration yc) {
        String s = "";
        for (String sn : disabledWorlds) {
            s += sn + ", ";
        }
        s = s.trim();
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }
        yc.set(Spell.worldnode, s.trim());
    }
}