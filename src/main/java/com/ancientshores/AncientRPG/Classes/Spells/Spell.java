/*
 * This file is part of AncientRPG
 *
 * Copyright (C) 2011-2013 Keyle
 * AncientRPG is licensed under the GNU Lesser General Public License.
 *
 * AncientRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AncientRPG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ancientshores.AncientRPG.Classes.Spells;

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

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.ISpellListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerBedEnterEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerBedLeaveEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerChatEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerDropItemEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerEggThrowEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerKickEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerPickupItemEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerPortalEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerTeleportEventListener;
import com.ancientshores.AncientRPG.Listeners.SpellListener.PlayerToggleSneakEventListener;
import com.ancientshores.AncientRPG.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.AncientRPG.Util.SerializableZone;

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
            eventListeners.put("playerbedenterevent", new PlayerBedEnterEventListener(AncientRPG.plugin));
            eventListeners.put("playerbedleaveevent", new PlayerBedLeaveEventListener(AncientRPG.plugin));
            eventListeners.put("playerchatevent", new PlayerChatEventListener(AncientRPG.plugin));
            eventListeners.put("playerdropitemevent", new PlayerDropItemEventListener(AncientRPG.plugin));
            eventListeners.put("playerkickevent", new PlayerKickEventListener(AncientRPG.plugin));
            eventListeners.put("playerpickupitemevent", new PlayerPickupItemEventListener(AncientRPG.plugin));
            eventListeners.put("playerportalevent", new PlayerPortalEventListener(AncientRPG.plugin));
            eventListeners.put("playerteleportevent", new PlayerTeleportEventListener(AncientRPG.plugin));
            eventListeners.put("playertogglesneakevent", new PlayerToggleSneakEventListener(AncientRPG.plugin));
            eventListeners.put("playereggthrowevent", new PlayerEggThrowEventListener(AncientRPG.plugin));
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
                AncientRPG.set(yc, name + ".hidden", false);
                yc.save(newConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yc1 = new YamlConfiguration();
        try {
            yc1.load(newConfigFile);
            AncientRPG.set(yc1, name + ".description", "");
            this.description = yc1.getString(name + ".description", "");
            AncientRPG.set(yc1, name + ".shortdescription", "");
            this.shortdescription = yc1.getString(name + ".shortdescription", "");
            AncientRPG.set(yc1, name + ".ignorespellfreezones", false);
            this.ignorespellfreezones = yc1.getBoolean(name + ".ignorespellfreezones", false);
            AncientRPG.set(yc1, name + ".hidden", false);
            this.hidden = yc1.getBoolean(name + ".hidden", false);
            AncientRPG.set(yc1, name + ".leftclickonly", false);
            this.leftclickonly = yc1.getBoolean(name + ".leftclickonly", false);
            AncientRPG.set(yc1, name + ".rightclickonly", false);
            this.rightclickonly = yc1.getBoolean(name + ".rightclickonly", false);
            yc1.save(newConfigFile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (configFile.exists()) {
            try {

                HashMap<String, Integer[]> map = AncientRPG.plugin.fc.getValues(configFile);
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
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error converting conf file of " + name + " spell to cfg file.");
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
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to load Spell " + name + " in line " + lineNumber + " forgot active/passive/buff declaration?");
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
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to load Spell " + name + " in line " + lineNumber + " variable declaration in wrong line?");
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
                        AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
                        AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
                        AncientRPG.plugin.getLogger().log(Level.SEVERE, "Could not parse the minlevel of the spell");
                    }
                } else {
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Could not parse the minlevel of the spell");
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
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
                    AncientRPG.plugin.getLogger().log(Level.SEVERE, "Could not parse the permission of the spell");
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
            AncientRPG.plugin.log.log(Level.SEVERE, "Wasn't able to load a spell. Reason: File not found!");
        } catch (Exception e) {
            AncientRPG.plugin.log.log(Level.SEVERE, "Wasn't able to load a spell. Reason: Error parsing file!");
        }
    }

    public void attachToEvent(String s) {
        if (s == null) {
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: No event found to attach to in spell " + this.name);
        }
        if (s.equalsIgnoreCase("damageevent")) {
            AncientRPGSpellListener.damageEventSpells.add(this);
        } else if (s.equalsIgnoreCase("damagebyentityevent")) {
            AncientRPGSpellListener.damageByEntityEventSpells.add(this);
        } else if (s.equalsIgnoreCase("attackevent")) {
            AncientRPGSpellListener.attackEventSpells.add(this);
        } else if (s.equalsIgnoreCase("changeblockevent")) {
            AncientRPGSpellListener.ChangeBlockEventSpells.add(this);
        } else if (s.equalsIgnoreCase("joinevent")) {
            AncientRPGSpellListener.joinEventSpells.add(this);
        } else if (s.equalsIgnoreCase("interactevent")) {
            AncientRPGSpellListener.interactEventSpells.add(this);
        } else if (s.equalsIgnoreCase("chatevent")) {
            AncientRPGSpellListener.chatEventSpells.add(this);
        } else if (s.equalsIgnoreCase("regenevent")) {
            AncientRPGSpellListener.regenEventSpells.add(this);
        } else if (s.equalsIgnoreCase("moveevent")) {
            AncientRPGSpellListener.moveEventSpells.add(this);
        } else if (s.equalsIgnoreCase("levelupevent")) {
            AncientRPGSpellListener.LevelupEventSpells.add(this);
        } else if (s.equalsIgnoreCase("killentityevent")) {
            AncientRPGSpellListener.killEntityEventSpells.add(this);
        } else if (s.equalsIgnoreCase("playerdeathevent")) {
            AncientRPGSpellListener.playerDeathSpells.add(this);
        } else if (s.equalsIgnoreCase("projectilehitevent")) {
            AncientRPGSpellListener.ProjectileHitEventSpells.add(this);
        } else if (s.equalsIgnoreCase("usebedevent")) {
            AncientRPGSpellListener.UseBedEventSpells.add(this);
        } else if (s.equalsIgnoreCase("classchangeevent")) {
            AncientRPGSpellListener.classChangeEventSpells.add(this);
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
                        PluginCommand pc = (PluginCommand) c.newInstance(split[1], AncientRPG.plugin);
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
                                    if (AncientRPGClass.spellAvailable(mSpell.name, PlayerData.getPlayerData(p.getUniqueId()))) {
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
                    Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s + " Needs to 2nd argumend needs to be an integer");
                    return;
                }
                if (registeredTasks.containsKey(this.name)) {
                    Bukkit.getScheduler().cancelTask(registeredTasks.get(this.name));
                }
                registeredTasks.put(this.name, Bukkit.getScheduler().scheduleSyncRepeatingTask(AncientRPG.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (serverspell) {
                            Spell.this.executeServerSpell();
                        } else {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (AncientRPGClass.spellAvailable(Spell.this.name, PlayerData.getPlayerData(p.getUniqueId()))) {
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
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: Event " + s + " not found in spell " + this.name);
        }
    }

    public static void initializeServerSpells() {
        File serv = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "serverspells");
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
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: No event found to attach to in spell " + this.name);
            return Integer.MAX_VALUE;
        }
        Player[] arr = {p, buffcaster};
        if (buffEvent.equalsIgnoreCase("damageevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.damageEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("damagebyentityevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.damageByEntityEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("attackevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.attackEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("changeblockevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.ChangeBlockEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("joinevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.joinEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("interactevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.interactEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("chatevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.chatEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("regenevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.regenEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("moveevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.moveEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("levelupevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.LevelupEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("playerdeathevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.playerDeathBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("killentityevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.killEntityEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("projectilehitevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.ProjectileHitEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("usebedevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.UseBedEventBuffs, arr);
        } else if (buffEvent.equalsIgnoreCase("classchangeevent")) {
            return AncientRPGSpellListener.attachBuffToEvent(this, AncientRPGSpellListener.classChangeEventBuffs, arr);
        } else {
            for (Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(buffEvent)) {
                    return entry.getValue().attachBuffToEvent(this, arr);
                }
            }
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: Event " + buffEvent + " not found in spell " + this.name);
            return Integer.MAX_VALUE;
        }
    }

    public void detachBuffOfEvent(Player p, Player buffcaster, int id) {
        if (buffEvent == null) {
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: No event found to attach to in spell " + this.name);
        }
        Player[] arr = {p, buffcaster};
        if (buffEvent.equalsIgnoreCase("damageevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.damageEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("damagebyentityevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.damageByEntityEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("attackevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.attackEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("changeblockevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.ChangeBlockEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("joinevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.joinEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("interactevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.interactEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("chatevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.chatEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("regenevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.regenEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("moveevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.moveEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("levelupevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.LevelupEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("playerdeathevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.playerDeathBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("killentityevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.killEntityEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("projectilehitevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.ProjectileHitEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("usebedevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.UseBedEventBuffs, arr, id);
        } else if (buffEvent.equalsIgnoreCase("classchangeevent")) {
            AncientRPGSpellListener.detachBuffToEvent(this, AncientRPGSpellListener.classChangeEventBuffs, arr, id);
        } else {
            for (Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(buffEvent)) {
                    entry.getValue().detachBuffOfEvent(this, arr, id);
                    return;
                }
            }
            AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: Event " + buffEvent + " not found in spell " + this.name);
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
        if (AncientRPGExperience.isWorldEnabled(buffcaster.getWorld())) {
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
        AncientRPGClass.executedSpells.put(so, p.getUniqueId());
        so.buffcaster = buffcaster.getUniqueId();
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
        if (AncientRPGExperience.isWorldEnabled(buffcaster.getWorld())) {
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
        AncientRPGClass.executedSpells.put(so, p.getUniqueId());
        so.buffcaster = buffcaster.getUniqueId();
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