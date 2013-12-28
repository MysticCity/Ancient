package com.ancientshores.AncientRPG.Listeners;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class AncientRPGBlockListener implements Listener {

    // You HAVE to have this!
    public static AncientRPG plugin;

    public AncientRPGBlockListener(AncientRPG instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        if (!event.isCancelled() && AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(event.getPlayer())) {
            PlayerData pd = PlayerData.getPlayerData(event.getPlayer().getName());
            switch (event.getBlock().getType()) {
                case STONE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfStone, false);
                    return;
                case COAL_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfCoal, false);
                    return;
                case IRON_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfIron, false);
                    return;
                case GOLD_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfGold, false);
                    return;
                case DIAMOND_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfDiamond, false);
                    return;
                case LAPIS_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfLapis, false);
                    return;
                case REDSTONE_ORE:
                case GLOWING_REDSTONE_ORE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfRedstone, false);
                    return;
                case GLOWSTONE:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfGlowstone, false);
                    return;
                case NETHERRACK:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfNetherrack, false);
                    return;
                case LOG:
                    pd.getXpSystem().addXP(AncientRPGExperience.XPOfWood, false);
                    return;
                default:
                    break;
            }
        }
    }
}