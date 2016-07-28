package com.ancientshores.Ancient.GUI.Menus;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIExtension;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MainMenu_Extension_Help extends GUIExtension {

    @Override
    public void runExtension() {
        
        Player p = player;
        
        p.sendMessage( Ancient.ChatBrand + ChatColor.GOLD + "Need more help ?" );
        p.sendMessage( Ancient.ChatBrand + ChatColor.GOLD + "Use " + ChatColor.BLUE + "/ancient help <1:2:3>" );
        
    }
    
}
