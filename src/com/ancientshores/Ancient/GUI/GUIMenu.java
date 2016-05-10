package com.ancientshores.Ancient.GUI;

import java.util.Map;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIMenu {
    
    protected Map< GUIItem , Integer > items;
    protected Inventory menu;
    
    //Construction
    public GUIMenu( Map< GUIItem , Integer > items )
    {
        
        this.items = items;
        setup();
        
    }
    
    private void setup()
    {
        for ( GUIItem item : items.keySet() )
        {
            
            ItemStack menuItem = item.getItem();
            menuItem.setItemMeta( item.getMeta() );
            
            //menu.addItem( items. , menuItem );
            
        }
    }
    
}
