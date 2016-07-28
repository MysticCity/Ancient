
package com.ancientshores.Ancient.GUI;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIItemBlocker implements Listener {
    
    protected Ancient loader;
    protected GUIMenu menu;
    
    public GUIItemBlocker( Ancient loaderClass , GUIMenu menu )
    {
        
        this.loader = loaderClass;
        this.menu = menu;
        
    }
    
    @EventHandler( priority = EventPriority.LOW )
    public void blockItemInput( InventoryClickEvent e )
    {
        
        if ( e.getInventory().getName().equals( menu.getMenuName() ) )
        {
            
            try {
            
                //Cancel action
                if ( e.isCancelled() == false ) //Cancelled via GUIItemAction ?
                {
                    e.setCancelled( true ); //Cancel
                }
                
            } catch ( Exception ex ) {
                
                //Nothing to cancel
                
            }   
            
        }
        
    }
    
}
