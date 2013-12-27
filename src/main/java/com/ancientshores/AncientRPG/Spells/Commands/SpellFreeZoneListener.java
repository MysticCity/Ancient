package com.ancientshores.AncientRPG.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class SpellFreeZoneListener implements Listener {
    public static final String selectspellfreezoneperm = "AncientRPG.spells.selectspellfreezone";
    public static final int selectionid = 264;
    public static final ConcurrentHashMap<String, Location> leftlocs = new ConcurrentHashMap<String, Location>();
    public static final ConcurrentHashMap<String, Location> rightlocs = new ConcurrentHashMap<String, Location>();


    public SpellFreeZoneListener(Plugin p) {
        Bukkit.getPluginManager().registerEvents(this, p);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (AncientRPG.hasPermissions(event.getPlayer(), selectspellfreezoneperm) && event.getItem() != null && event.getItem().getTypeId() == selectionid) {
                leftlocs.put(event.getPlayer().getName(), event.getClickedBlock().getLocation());
                event.getPlayer().sendMessage(AncientRPG.brand2 + "Defined first point for a spell free zone");
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (AncientRPG.hasPermissions(event.getPlayer(), selectspellfreezoneperm) && event.getItem() != null && event.getItem().getTypeId() == selectionid) {
                rightlocs.put(event.getPlayer().getName(), event.getClickedBlock().getLocation());
                event.getPlayer().sendMessage(AncientRPG.brand2 + "Defined second point for a spell free zone");
            }
        }
    }
}
