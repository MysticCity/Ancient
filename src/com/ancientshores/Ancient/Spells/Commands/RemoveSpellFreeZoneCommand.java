package com.ancientshores.Ancient.Spells.Commands;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Util.SerializableZone;

public class RemoveSpellFreeZoneCommand {
    public static void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission(SpellFreeZoneListener.selectspellfreezoneperm)) {
                p.sendMessage(Ancient.ChatBrand + "You don't have the permission to create spell-free zones");
                return;
            }
            if (args.length == 1) {
                HashMap<String, SerializableZone> removeZones = new HashMap<String, SerializableZone>();
                for (Entry<String, SerializableZone> sl : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
                    if (sl.getValue().isInZone(p.getLocation())) {
                        removeZones.put(sl.getKey(), sl.getValue());
                    }
                }
                for (Entry<String, SerializableZone> sl : removeZones.entrySet()) {
                    AddSpellFreeZoneCommand.spellfreezones.remove(sl.getKey());
                    File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
                    f.mkdir();
                    File nf = new File(f.getPath() + File.separator + sl.getKey() + ".sfz");
                    nf.delete();
                    p.sendMessage(Ancient.ChatBrand + "Successfully removed spellfreezone " + sl.getKey());
                }
            } else if (args.length == 2) {
                HashMap<String, SerializableZone> removeZones = new HashMap<String, SerializableZone>();
                for (Entry<String, SerializableZone> sl : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
                    if (sl.getKey().equalsIgnoreCase(args[1])) {
                        removeZones.put(sl.getKey(), sl.getValue());
                    }
                }
                for (Entry<String, SerializableZone> sl : removeZones.entrySet()) {
                    AddSpellFreeZoneCommand.spellfreezones.remove(sl.getKey());
                    File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
                    f.mkdir();
                    File nf = new File(f.getPath() + File.separator + sl.getKey() + ".sfz");
                    nf.delete();
                    p.sendMessage(Ancient.ChatBrand + "Successfully removed spellfreezone " + sl.getKey());
                }
            }
        }
    }
}