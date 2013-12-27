package com.ancientshores.AncientRPG.Race;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Race.Commands.*;
import com.ancientshores.AncientRPG.Util.SerializableLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

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
    public static ConcurrentHashMap<String, Long> playersOnCd = new ConcurrentHashMap<String, Long>();
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
        for (File spell : f.listFiles()) {
            if (spell.getPath().endsWith(".spell")) {
                raceSpells.add(new Spell(spell));
            }
        }
    }

    public static void processCommand(CommandSender cs, String[] command) {
        if (command.length >= 1) {
            if (cs instanceof Player) {
                Player sender = (Player) cs;
                if (command[0].equalsIgnoreCase("set")) {
                    SetRaceCommand.setRaceCommand(sender, command);
                }
                if (command[0].equalsIgnoreCase("help")) {
                    RaceHelpCommand.processHelp(sender, command);
                } else if (command[0].equalsIgnoreCase("list")) {
                    RaceListCommand.showRaces(sender, command);
                } else if (command[0].equalsIgnoreCase("setspawn") && command.length == 2) {
                    SetRaceSpawnCommand.setRaceSpawn(sender, command[1]);
                } else if (command[0].equalsIgnoreCase("spawn")) {
                    RaceSpawnCommand.raceSpawnCommand(sender);
                }
            }
        } else {
            if (cs instanceof Player) {
                AncientRPGRace race = AncientRPGRace.getRaceByName(PlayerData.getPlayerData(cs.getName()).getRacename());
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
                    spawnLoc = new SerializableLocation(new Location(Bukkit.getWorld(yc.getString("Race.spawnworld")), yc.getDouble("Race.spawnx"), yc.getDouble("Race.spawny"),
                            yc.getDouble("Race.spawnz")));
                }
                description = yc.getString("Race.description", description);
            } catch (Exception e) {
                // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
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
                // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadRaces() {
        File raceFolder = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Races");
        if (!raceFolder.exists()) {
            raceFolder.mkdirs();
        }
        for (File f : raceFolder.listFiles()) {
            if (f.isDirectory()) {
                races.add(new AncientRPGRace(f));
            }
        }
        File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Races" + File.separator + "changecds.dat");
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                playersOnCd = (ConcurrentHashMap<String, Long>) ois.readObject();
                ois.close();
            } catch (Exception ignored) {

            }
        }
    }
}
