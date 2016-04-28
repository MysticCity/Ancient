package com.ancientshores.Ancient.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class SpellFreeZoneListener
  implements Listener
{
  public static final String selectspellfreezoneperm = "Ancient.spells.selectspellfreezone";
  public static final int selectionid = 264;
  public static final ConcurrentHashMap<UUID, Location> leftlocs = new ConcurrentHashMap();
  public static final ConcurrentHashMap<UUID, Location> rightlocs = new ConcurrentHashMap();
  
  public SpellFreeZoneListener(Plugin p)
  {
    Bukkit.getPluginManager().registerEvents(this, p);
  }
  
  @EventHandler
  public void playerInteract(PlayerInteractEvent event)
  {
    if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && 
      (event.getPlayer().hasPermission("Ancient.spells.selectspellfreezone")) && (event.getItem() != null) && (event.getItem().getTypeId() == 264))
    {
      leftlocs.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
      event.getPlayer().sendMessage(Ancient.brand2 + "Defined first point for a spell free zone");
    }
    if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && 
      (event.getPlayer().hasPermission("Ancient.spells.selectspellfreezone")) && (event.getItem() != null) && (event.getItem().getTypeId() == 264))
    {
      rightlocs.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
      event.getPlayer().sendMessage(Ancient.brand2 + "Defined second point for a spell free zone");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Spells\Commands\SpellFreeZoneListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */