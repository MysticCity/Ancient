package com.ancientshores.Ancient.GUI.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerMenu_Click implements Listener{
 
    protected GUIEvents loader;
    
    public PlayerMenu_Click(GUIEvents loaderClass)
    {
        
        this.loader = loaderClass;
        
    }
    
    @EventHandler
    public void item_clicked(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
                
        if ( e.getInventory().getName().equals(ChatColor.GOLD + "Menu") ) { //If item is Players-Menu
            try{
             
                if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Help")) { //If help is clicked
                    p.performCommand("ancient help");
                    p.sendMessage(ChatColor.GOLD + "Need more help ?");
                    p.sendMessage(ChatColor.GOLD + "Use " + ChatColor.BLUE + "/ancient help <1:2:3>");
                    e.setCancelled(true);
                }else{
                    
                    //Nothing
                    
                }
                
            }catch (Exception ex){
                
                //If click is out of border
                
            }
        }
    }
    
}
