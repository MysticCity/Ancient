package com.ancientshores.Ancient.GUI.Menus;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIExtension;
import com.ancientshores.external.LocStats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MainMenu_Extension_Stats extends GUIExtension {

    private LocStats loc;

    @Override
    public void runExtension() {
        
        Player p = player;
       
        if ( new LocStats( Ancient.plugin ).isInstalled() )
        {
            
            menu.switchTo( p , new StatMenu( p , plugin ).getMenu() );
        
        } else {
            
            p.sendMessage( Ancient.ChatBrand + ChatColor.RED + "LOCStats is required for this function. Sorry :(" );
            
        }
        
    }
    
}
