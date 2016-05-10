package com.ancientshores.Ancient.GUI;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIItem {
    
    protected ItemStack item;
    protected ItemMeta meta;
    
    //Construction
    public GUIItem ( ItemStack item , ItemMeta meta , int pos )
    {
        this.item = item; //The item
        this.meta = meta; //The items meta
    }
    
    //Get the item
    public ItemStack getItem()
    {
        return this.item;
    }
    
    //Get the meta
    public ItemMeta getMeta()
    {
        return this.meta;
    }
        
}
