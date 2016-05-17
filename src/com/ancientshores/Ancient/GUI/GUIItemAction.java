package com.ancientshores.Ancient.GUI;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIItemAction implements Listener {
    
    private GUIMenu menu;
    private GUIItemStack item;
    private String command;
    private GUIExtension extension;
    
    //Construction
    public GUIItemAction ( Ancient plugin , GUIMenu menu , GUIItemStack item , String command )
    {
        
        this.menu = menu;
        this.item = item;
        this.command = command;
        
        plugin.getServer().getPluginManager().registerEvents( this , plugin );
        
    }
    
    //Add optional code
    public void setExtension( GUIExtension extension )
    {
        this.extension = extension;
    }
    
    //Add automated action to item
    @EventHandler
    public void guiItemAction( InventoryClickEvent e )
    {
        if ( e.getInventory().getTitle().equals( menu.getMenuName() ) )
        {
            if ( e.getCurrentItem().equals( item.getItemStack() ) )
            {
                if ( e.getWhoClicked() instanceof Player )
                {
                    
                    Player p = (Player) e.getWhoClicked();
                    
                    if ( command != null )
                    {
                        p.performCommand( command );
                    }
                    
                    p.closeInventory();
                    
                    if ( extension != null )
                    {
                        extension.player = p;
                        extension.menu = menu;
                        extension.runExtension();
                    }
                    
                    e.setCancelled(true);
                    
                }
            }
        }
    }
    
}
