package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIItemAction;
import com.ancientshores.Ancient.GUI.GUIItemStack;
import com.ancientshores.Ancient.GUI.GUIMenu;
import com.ancientshores.Ancient.GUI.Menus.MainMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Testevent implements Listener {
    
    public GUIEvents loader;
   
    public Testevent( GUIEvents loaderClass )
    {
        
        this.loader = loaderClass;

    }
    
    @EventHandler
    public void openOnChat( AsyncPlayerChatEvent e )
    {
        if ( e.getMessage().startsWith( "#" ) )
        {
            
            MainMenu menu = new MainMenu( e.getPlayer() , loader.plugin );
            
            menu.open();
            
            e.setCancelled(true);
            
        } 
    }
    
}
