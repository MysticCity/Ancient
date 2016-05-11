package com.ancientshores.Ancient.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIMenu {

    protected Inventory menu;
    
    //Construction
    public GUIMenu( String menuTitle , int size )
    {
        
        this.menu = Bukkit.createInventory( null , size , menuTitle);
        setup();
        
    }
    
    //Load required code
    private void setup()
    {
       /*
        *   Everything that's required
        */
    }
    
    //Add a new item
    @Deprecated
    public void addItem( GUIItem itemToAdd )
    {
        try {
        
            ItemStack item = itemToAdd.getItem();
            ItemMeta meta = itemToAdd.getMeta();

            item.setItemMeta( meta );

            menu.setItem( itemToAdd.getPos() , item);
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Add a new item-stack
    public void addItem( GUIItemStack itemToAdd )
    {
        try {

            menu.setItem( itemToAdd.getPosition() , itemToAdd.getItemStack() );
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
     
    //Open this menu
    public void openToUser( Player p )
    {
        try {
            
            p.openInventory( menu );
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Switch to another menu
     public void switchTo( Player p , GUIMenu anotherMenu )
    {
        try {
            
            p.closeInventory();
            p.openInventory( anotherMenu.getMenu() );
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
     
    //Get the menu
     public Inventory getMenu()
     {
         return menu;
     }
     
     //Get the menu-name
     public String getMenuName()
     {
         return menu.getTitle();
     }
    
}
