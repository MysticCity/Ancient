package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.GUI.GUIItem;
import com.ancientshores.Ancient.GUI.GUIMenu;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIEvents {
    
    protected Ancient plugin;
    protected GUIMenu menu;
    
    public GUIEvents(Ancient plugin)
    {
        
        this.plugin = plugin;
        
        try{
            
            loadGUI();
            registerGuiEvents();
            
        } catch (Exception ex) {
          
            ex.printStackTrace();
            
        }
        
    }
    
    //Register all GUI-Events
    private void registerGuiEvents()
    {
        try {
       
            /*
             *  Load up everything has to do with the GUI
             */
            
            plugin.getServer().getPluginManager().registerEvents( new Testevent( this ) , plugin);
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Load gui
    private void loadGUI()
    {
        try {
            
            menu = new GUIMenu( ChatColor.GOLD + "Menu" , 9 );
            
            ItemStack item = new ItemStack( Material.BOOK );
            ItemMeta meta = item.getItemMeta();
            
            meta.setDisplayName( ChatColor.GOLD + "Help" );
            
            //ArrayList<String> lore = null;
            //lore.add( ChatColor.BLUE + "Click for help !" );
            
            item.setItemMeta( meta );
            
            menu.addItem( new GUIItem( item , meta , 0) );
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
