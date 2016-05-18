package com.ancientshores.Ancient.GUI.Menus;

import com.ancientshores.Ancient.GUI.GUIExtension;
import com.ancientshores.external.LocStats;
import org.bukkit.entity.Player;

public class MainMenu_Extension_Stats extends GUIExtension {

    @Override
    public void runExtension() {
       
        Player p = player;
        
        try{
            
            LocStats loc = new LocStats( plugin ); //Local LocStats-plugin
            
            if (loc.isInstalled()) //LocalStats is installed
            {
                
                menu.switchTo( p , new StatMenu( p , plugin ).getMenu() ); //Switch to stat-menu
                
            } else {
                
                p.sendMessage( plugin.ChatBrand + plugin.lang.getText( "GUI.StatMenu.LocStatsError" ).replaceAll( "&" , "§" ) ); //Stat-menu error-message
                
            }
            
        } catch (Exception ex) {
                 
            //Error-Message from lang-file
            
        }
        
    }
    
}
