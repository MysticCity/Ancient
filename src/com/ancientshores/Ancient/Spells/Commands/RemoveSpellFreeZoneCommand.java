package com.ancientshores.Ancient.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Util.SerializableZone;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveSpellFreeZoneCommand
{
  public static void onCommand(CommandSender sender, String[] args)
  {
    Player p;
    if ((sender instanceof Player))
    {
      p = (Player)sender;
      if (!p.hasPermission("Ancient.spells.selectspellfreezone"))
      {
        p.sendMessage(Ancient.brand2 + "You don't have the permission to create spell-free zones");
        return;
      }
      if (args.length == 1)
      {
        HashMap<String, SerializableZone> removeZones = new HashMap();
        for (Map.Entry<String, SerializableZone> sl : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
          if (((SerializableZone)sl.getValue()).isInZone(p.getLocation())) {
            removeZones.put(sl.getKey(), sl.getValue());
          }
        }
        for (Map.Entry<String, SerializableZone> sl : removeZones.entrySet())
        {
          AddSpellFreeZoneCommand.spellfreezones.remove(sl.getKey());
          File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
          f.mkdir();
          File nf = new File(f.getPath() + File.separator + (String)sl.getKey() + ".sfz");
          nf.delete();
          p.sendMessage(Ancient.brand2 + "Successfully removed spellfreezone " + (String)sl.getKey());
        }
      }
      else if (args.length == 2)
      {
        HashMap<String, SerializableZone> removeZones = new HashMap();
        for (Map.Entry<String, SerializableZone> sl : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
          if (((String)sl.getKey()).equalsIgnoreCase(args[1])) {
            removeZones.put(sl.getKey(), sl.getValue());
          }
        }
        for (Map.Entry<String, SerializableZone> sl : removeZones.entrySet())
        {
          AddSpellFreeZoneCommand.spellfreezones.remove(sl.getKey());
          File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
          f.mkdir();
          File nf = new File(f.getPath() + File.separator + (String)sl.getKey() + ".sfz");
          nf.delete();
          p.sendMessage(Ancient.brand2 + "Successfully removed spellfreezone " + (String)sl.getKey());
        }
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Spells\Commands\RemoveSpellFreeZoneCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */