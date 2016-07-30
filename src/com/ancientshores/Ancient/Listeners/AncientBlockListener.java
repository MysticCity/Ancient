package com.ancientshores.Ancient.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Experience.AncientExperience;

public class AncientBlockListener implements Listener {

    // You HAVE to have this!
    public static Ancient plugin;

    public AncientBlockListener(Ancient instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        if (!event.isCancelled() && AncientExperience.isWorldEnabled(event.getPlayer().getWorld())) {
            PlayerData pd = PlayerData.getPlayerData(event.getPlayer().getUniqueId());
            switch (event.getBlock().getType()) {
                case STONE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfStone(), false);
                    return;
                case COAL_ORE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfCoal(), false);
                    return;
                case IRON_ORE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfIron(), false);
                    return;
                case GOLD_ORE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfGold(), false);
                    return;
                case DIAMOND_ORE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfDiamond(), false);
                    return;
                case LAPIS_ORE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfLapis(), false);
                    return;
                case REDSTONE_ORE: case GLOWING_REDSTONE_ORE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfRedstone(), false);
                    return;
                case GLOWSTONE:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfGlowstone(), false);
                    return;
                case NETHERRACK:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfNetherrack(), false);
                    return;
                case LOG:
                    pd.getXpSystem().addXP(AncientExperience.getXPOfWood(), false);
                    return;
                default:
                    break;
            }
        }
    }
}