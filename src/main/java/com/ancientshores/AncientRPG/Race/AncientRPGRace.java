package com.ancientshores.AncientRPG.Race;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Race.Commands.RaceHelpCommand;
import com.ancientshores.AncientRPG.Race.Commands.RaceListCommand;
import com.ancientshores.AncientRPG.Race.Commands.RaceSpawnCommand;
import com.ancientshores.AncientRPG.Race.Commands.SetRaceCommand;
import com.ancientshores.AncientRPG.Race.Commands.SetRaceSpawnCommand;
import com.ancientshores.AncientRPG.Util.SerializableLocation;

public class AncientRPGRace {
    public final String name;
    public static boolean enabled = true;
    public static final String enabledConfigNode = "AncientRPG.Race.enabled";
    public static final String setRacePermission = "AncientRPG.Race.set";
    public static final String listRacePermission = "AncientRPG.Race.list";
    public static final String adminRacePermission = "AncientRPG.Race.admin";
    public static final String teleportToSpawnPermission = "AncientRPG.Race.spawn";
    public static final String configCd = "Race.change cooldown in seconds";
    public static int changeCd = 2;
    public static final String configDefaultRace = "Race.default race";
    public static String defaultRace = "";
    public static HashSet<AncientRPGRace> races = new HashSet<AncientRPGRace>();
    public static ConcurrentHashMap<UUID, Long> playersOnCd = new ConcurrentHashMap<UUID, Long>();
    public String permission = "";
    public String description = "";
    public final String permissionNode = "Race.permission";
    public SerializableLocation spawnLoc = null;
    public final HashSet<Spell> raceSpells = new HashSet<Spell>();
    public final File root;

    public AncientRPGRace(File folder) {
        this.name = folder.getName();
        this.root = folder;
        loadSpells(folder);
        loadConfig();
        writeConfig();
    }

    public void loadSpells(File f) {
        File[] files = f.listFiles();
        if (files != null) {
            for (File spell : files) {
                if (spell.getPath().endsWith(".spell")) {
                    raceSpells.add(new Spell(spell));
                }
            }
        }
    }

    public static void processCommand(CommandSender cs, String[] command) {
        if (command.length >= 1) {
            if (cs instanceof Player) {
                Player p = (Player) cs;
                if (command[0].equalsIgnoreCase("set")) {
                    SetRaceCommand.setRaceCommand(p, command);
                }
                if (command[0].equalsIgnoreCase("help")) {
                    RaceHelpCommand.processHelp(p, command);
                } else if (command[0].equalsIgnoreCase("list")) {
                    RaceListCommand.showRaces(p, command);
                } else if (command[0].equalsIgnoreCase("setspawn") && command.length == 2) {
                    SetRaceSpawnCommand.setRaceSpawn(p, command[1]);
                } else if (command[0].equalsIgnoreCase("spawn")) {
                    RaceSpawnCommand.raceSpawnCommand(p);
                }
            }
        } else {
            if (cs instanceof Player) {
                Player p = (Player) cs;
            	AncientRPGRace race = AncientRPGRace.getRaceByName(PlayerData.getPlayerData(p.getUniqueId()).getRacename());
                if (race != null) {
                    cs.sendMessage("Your race is " + race.name);
                } else {
                    cs.sendMessage("You aren't part of a race");
                }
            }
        }
    }

    public void loadConfig() {
        File config = new File(root.getPath() + File.separator + name + ".conf");
        if (config.exists()) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(config);
                permission = yc.getString(permissionNode, permission);
                if (yc.get("Race.spawnx") != null) {
                    spawnLoc = new SerializableLocation(new Location(Bukkit.getWorld(yc.getString("Race.spawnworld")), yc.getDouble("Race.spawnx"), yc.getDouble("Race.spawny"), yc.getDouble("Race.spawnz")));
                }
                description = yc.getString("Race.description", description);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeConfig() {
        File config = new File(root.getPath() + File.separator + name + ".conf");
        YamlConfiguration yc = new YamlConfiguration();
        try {
            if (yc.get(permissionNode) == null) {
                yc.set(permissionNode, permission);
            }
            if (spawnLoc != null) {
                yc.set("Race.spawnworld", spawnLoc.wName);
                yc.set("Race.spawnx", spawnLoc.x);
                yc.set("Race.spawny", spawnLoc.y);
                yc.set("Race.spawnz", spawnLoc.z);
            }
            AncientRPG.set(yc, "Race.description", description);
            yc.save(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AncientRPGRace getRaceByName(String name) {
        for (AncientRPGRace race : races) {
            if (race.name.equalsIgnoreCase(name)) {
                return race;
            }
        }
        return null;
    }

    public static void loadRacesConfig(AncientRPG instance) {
        File newconfig = new File(instance.getDataFolder().getPath() + File.separator + "raceconfig.yml");
        if (newconfig.exists()) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(newconfig);
                enabled = yc.getBoolean(enabledConfigNode, enabled);
                changeCd = yc.getInt(configCd, changeCd);
                defaultRace = yc.getString(configDefaultRace, defaultRace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            enabled = instance.getConfig().getBoolean(enabledConfigNode, enabled);
            changeCd = instance.getConfig().getInt(configCd, changeCd);
            defaultRace = instance.getConfig().getString(configDefaultRace, defaultRace);
        }
    }

    public static void writeRacesConfig(AncientRPG instance) {
        File newconfig = new File(instance.getDataFolder().getPath() + File.separator + "raceconfig.yml");
        if (!newconfig.exists()) {
            try {
                newconfig.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yc = new YamlConfiguration();
        yc.set(enabledConfigNode, enabled);
        yc.set(configCd, changeCd);
        yc.set(configDefaultRace, defaultRace);
        try {
            yc.save(newconfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRaces() {
        File raceFolder = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Races");
        if (!raceFolder.exists()) {
            raceFolder.mkdirs();
        }
        File[] files = raceFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    races.add(new AncientRPGRace(f));
                }
            }
        }
        File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Races" + File.separator + "changecds.dat");
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                	playersOnCd.put(UUID.fromString(line.split(";")[0]), Long.getLong(line.split(";")[1]));
                }
//                playersOnCd = (ConcurrentHashMap<String, Long>) ois.readObject();
//                ois.close();
                br.close();
                fr.close();
            } catch (Exception ignored) {
            }
        }
    }
}