package com.ancientshores.Ancient.GUI;

import com.ancientshores.Ancient.Ancient;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerMenu {
    
    protected Player p;
    protected Inventory menu;
    protected Ancient plugin;
    
    //Construct
    public PlayerMenu(Player p, Ancient plugin)
    {
        
        this.p = p;
        this.plugin = plugin;
        this.menu = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.GOLD + "Menu");
               
    }
    
    //Load the menu items/icons
    private void loadMenuItems()
    {
              
        /*
         * Help Item
         */
        ItemStack test = new ItemStack(Material.BOOK);
        ItemMeta meta = test.getItemMeta();
        
        List<String> desc = null;
        
        //desc.add("§2Everything you need to know :)");
        
        meta.setDisplayName("§eHelp");
        //meta.setLore(desc);
        
        test.setItemMeta(meta);
        
        menu.setItem(4, test);
                
    }
    
    //Switch to another inventory
    public void switchTo(Inventory inv)
    {
        
        p.closeInventory(); //Close this
        p.openInventory(inv); //Open another
        
    }
    
    //Open the inventory inGame
    public void open()
    {
       //p.openInventory(p.getInventory());
       loadMenuItems(); 
       p.openInventory(menu);
        
    }
    
    //Return menu-name
    public String getName()
    {
        return menu.getName();
    }
    
    //Return this
    public Inventory getMenu()
    {
        return menu;
    }
    
}
