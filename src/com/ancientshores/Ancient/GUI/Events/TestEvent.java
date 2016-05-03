package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.Interactive.InteractiveMessage;
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
            
                InteractiveMessage m = new InteractiveMessage("&9Testmessage", "help");
                m.sendToPlayer(e.getPlayer());
            
            } catch (Exception ex ) {

                ex.printStackTrace();

            }
        }
    }
    
}
