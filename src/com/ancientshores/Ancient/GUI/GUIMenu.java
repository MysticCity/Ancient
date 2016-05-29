package com.ancientshores.Ancient.GUI;

import com.ancientshores.Ancient.Ancient;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIMenu {

    protected Inventory menu;
    protected int size;
    
    //Construction
    public GUIMenu( String menuTitle , int size )
    {
        
        this.menu = Bukkit.createInventory( null , size , menuTitle);
        this.size = size;
        setup();
        
    }
    
    //Load required code
    private void setup()
    {
       /*
        *   Everything that's required
        */
        
        Bukkit.getPluginManager().registerEvents( new GUIItemBlocker( Ancient.plugin , this ) , Ancient.plugin );
        
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
    
     //Add a new item-stack
    public void addItem( ItemStack itemToAdd, int pos )
    {
        try {

            menu.setItem( pos , itemToAdd );
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Get menu-item
    public ItemStack getItem( int i )
    {
        return menu.getItem( i );
    }
    
    //Get menu-size
    public int size()
    {
        return size;
    }
     
    //Open this menu
    public void openToUser( Player p )
    {
        try {
            
            fillEmptySlots();
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
    
     //Fill up all empty slots
     private void fillEmptySlots()
     {
       
         for ( int i = 0 ; i <= size-1 ; i++ )
         {
             if ( menu.getItem( i ) == null )
             {
                 
                 ItemStack spacer = new ItemStack( Material.STAINED_GLASS_PANE , 1 , (short) 15 );
                 ItemMeta spacerMeta = spacer.getItemMeta();
                 
                 spacerMeta.setDisplayName( Ancient.ChatBrand );
                 spacer.setItemMeta( spacerMeta );

                 menu.setItem( i , spacer );
                 
             }
         }
         
     }
     
}
