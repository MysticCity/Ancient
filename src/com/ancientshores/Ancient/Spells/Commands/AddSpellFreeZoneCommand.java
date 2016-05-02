package com.ancientshores.Ancient.Spells.Commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Util.SerializableZone;

public class AddSpellFreeZoneCommand {
    public static final String ignorespellfreezones = "Ancient.spells.ignorespellfreezones";
    public static final ConcurrentHashMap<String, SerializableZone> spellfreezones = new ConcurrentHashMap<String, SerializableZone>();

    public static void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission(SpellFreeZoneListener.selectspellfreezoneperm)) {
                p.sendMessage(Ancient.ChatBrand + "You don't have the permission to create spell-free zones");
                return;
            }
            if (SpellFreeZoneListener.leftlocs.containsKey(p.getUniqueId()) && SpellFreeZoneListener.rightlocs.containsKey(p.getUniqueId()) && args.length == 2) {
                SerializableZone sz = new SerializableZone(SpellFreeZoneListener.leftlocs.get(p.getUniqueId()), SpellFreeZoneListener.rightlocs.get(p.getUniqueId()));
                spellfreezones.put(args[1], sz);
                sender.sendMessage(Ancient.ChatBrand + "Successfully created spell-free zone");
                File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
                f.mkdir();
                File nf = new File(f.getPath() + File.separator + args[1] + ".sfz");
                try {
                    FileOutputStream fos = new FileOutputStream(nf);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(sz);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(Ancient.ChatBrand + "You have to define two corners first, and specify a name");
            }
        }
    }

    public static boolean isLocationInSpellfreeZone(Location l) {
        for (Map.Entry<String, SerializableZone> sz : spellfreezones.entrySet()) {
            if (sz.getValue().isInZone(l)) {
                return true;
            }
        }
        return false;
    }
}