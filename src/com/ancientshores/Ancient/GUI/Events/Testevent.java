package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.GUI.GUIItemAction;
import com.ancientshores.Ancient.GUI.GUIItemStack;
import com.ancientshores.Ancient.GUI.GUIMenu;
import com.ancientshores.Ancient.GUI.Events.TestExtension;
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
            
            GUIMenu menu = new GUIMenu( ChatColor.GOLD + "Menu" , 9 );
            GUIItemStack helpItem = new GUIItemStack( ChatColor.GOLD + "Help" , Material.BOOK , null , 8);
            
            menu.addItem( helpItem );
            
            GUIItemAction MainMenuAction_Help = new GUIItemAction( loader.plugin , menu , helpItem , "ancient help" );
            MainMenuAction_Help.setExtension( new TestExtension() );
            
            menu.openToUser( e.getPlayer() );
            
        } 
    }
    
}
