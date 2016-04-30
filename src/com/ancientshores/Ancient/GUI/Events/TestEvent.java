package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.GUI.PlayerMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TestEvent implements Listener{
    
    protected GUIEvents loader;
    
    public TestEvent(GUIEvents loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void testEvents(AsyncPlayerChatEvent e)
    {
        if ( e.getMessage().contains("#") ) 
        {
            try{
            
            PlayerMenu menu = new PlayerMenu(e.getPlayer(), loader.plugin);
            menu.open();
            
            } catch (Exception ex ) {

                ex.printStackTrace();

            }
        }
    }
    
}
