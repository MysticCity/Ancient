package com.ancientshores.AncientRPG.Spells.Commands;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.ancientshores.AncientRPG.AncientRPG;

public class SpellFreeZoneListener implements Listener {
    public static final String selectspellfreezoneperm = "AncientRPG.spells.selectspellfreezone";
    public static final int selectionid = 264;
    public static final ConcurrentHashMap<UUID, Location> leftlocs = new ConcurrentHashMap<UUID, Location>();
    public static final ConcurrentHashMap<UUID, Location> rightlocs = new ConcurrentHashMap<UUID, Location>();


    public SpellFreeZoneListener(Plugin p) {
        Bukkit.getPluginManager().registerEvents(this, p);
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getPlayer().hasPermission(selectspellfreezoneperm) && event.getItem() != null && event.getItem().getTypeId() == selectionid) {
                leftlocs.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
                event.getPlayer().sendMessage(AncientRPG.brand2 + "Defined first point for a spell free zone");
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().hasPermission(selectspellfreezoneperm) && event.getItem() != null && event.getItem().getTypeId() == selectionid) {
                rightlocs.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
                event.getPlayer().sendMessage(AncientRPG.brand2 + "Defined second point for a spell free zone");
            }
        }
    }
}