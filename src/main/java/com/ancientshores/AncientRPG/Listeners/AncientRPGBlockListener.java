package com.ancientshores.AncientRPG.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;

public class AncientRPGBlockListener implements Listener {

    // You HAVE to have this!
    public static AncientRPG plugin;

    public AncientRPGBlockListener(AncientRPG instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        if (!event.isCancelled() && AncientRPGExperience.isWorldEnabled(event.getPlayer().getWorld())) {
            PlayerData pd = PlayerData.getPlayerData(event.getPlayer().getUniqueId());
            switch (event.getBlock().getType()) {
                case STONE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfStone(), false);
                    return;
                case COAL_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfCoal(), false);
                    return;
                case IRON_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfIron(), false);
                    return;
                case GOLD_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfGold(), false);
                    return;
                case DIAMOND_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfDiamond(), false);
                    return;
                case LAPIS_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfLapis(), false);
                    return;
                case REDSTONE_ORE: case GLOWING_REDSTONE_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfRedstone(), false);
                    return;
                case GLOWSTONE:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfGlowstone(), false);
                    return;
                case NETHERRACK:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfNetherrack(), false);
                    return;
                case LOG:
                    pd.getXpSystem().addXP(AncientRPGExperience.getXPOfWood(), false);
                    return;
                default:
                    break;
            }
        }
    }
}