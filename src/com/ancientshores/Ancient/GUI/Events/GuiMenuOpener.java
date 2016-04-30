package com.ancientshores.Ancient.GUI.Events;

import com.ancientshores.Ancient.GUI.PlayerMenu;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiMenuOpener implements Listener{
    
    protected GUIEvents loader;
    
    //Construction
    public GuiMenuOpener(GUIEvents loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    //Give menu-item to player
    @EventHandler
    public void giveMenuItem(PlayerJoinEvent e)
    {
        try{
            
            Player p = e.getPlayer();
            
            ItemStack openItem = new ItemStack(Material.COMPASS); //Item
            ItemMeta openMeta = openItem.getItemMeta(); //Item-meta
            ArrayList<String> lore = new ArrayList<String>(); //Item lore/description
            
            lore.add(ChatColor.DARK_PURPLE + "Left click to open"); //Add description
            openMeta.setLore(lore); //Set description
            
            openMeta.setDisplayName(ChatColor.BLUE + "Menu"); //Set item-name
            
            openItem.setItemMeta(openMeta); //Set meta to item
            
            if ( !p.getInventory().contains(openItem) ) //Is in inventory ?
            {
                
                p.getInventory().setItem(8, openItem); //Give menu-item
                
            }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }
    }
    
    //Denie dropping
    @EventHandler
    public void denieDropping(PlayerDropItemEvent e)
    {
        
        Player p = e.getPlayer();
        
        try{
            
            ItemStack openItem = new ItemStack(Material.COMPASS); //Item
            ItemMeta openMeta = openItem.getItemMeta(); //Item-meta
            ArrayList<String> lore = new ArrayList<String>(); //Item lore/description
            
            lore.add(ChatColor.DARK_PURPLE + "Left click to open"); //Add description
            openMeta.setLore(lore); //Set description
            
            openMeta.setDisplayName(ChatColor.BLUE + "Menu"); //Set item-name
            
            openItem.setItemMeta(openMeta); //Set meta to item
            
            if ( e.getItemDrop().getItemStack().equals(openItem) ) //Check drop
            {
                
                e.setCancelled(true);
                
            }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }
        
    }
    
    //Remove user if inventory opend
    @EventHandler
    public void inventoryClosed(InventoryOpenEvent e)
    {


    }
    
    //Open menu
    @EventHandler
    public void openMenu(PlayerInteractEvent e)
    {
        if (e.getAction() == Action.LEFT_CLICK_AIR | e.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            
            try{
                
                Player p = e.getPlayer();
        
                       
                ItemStack openItem = new ItemStack(Material.COMPASS); //Item
                ItemMeta openMeta = openItem.getItemMeta(); //Item-meta
                ArrayList<String> lore = new ArrayList<String>(); //Item lore/description

                lore.add(ChatColor.DARK_PURPLE + "Left click to open"); //Add description
                openMeta.setLore(lore); //Set description

                openMeta.setDisplayName(ChatColor.BLUE + "Menu"); //Set item-name

                openItem.setItemMeta(openMeta); //Set meta to item

                if ( e.getItem().equals(openItem) )
                {

                    PlayerMenu menu = new PlayerMenu(p, loader.plugin);
                    menu.open();

                }
                
            } catch (Exception ex) {
                
                //Nothing
                
            }
            
        }
    }
            
}
