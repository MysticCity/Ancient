package com.ancientshores.Ancient.GUI;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Deprecated
public class GUIItem {
    
    protected ItemStack item;
    protected ItemMeta meta;
    protected int pos;
    
    //Construction
    public GUIItem ( ItemStack item , ItemMeta meta , int pos )
    {
        this.item = item; //The item
        this.meta = meta; //The items meta
        this.pos = pos; //The item-position
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
    
    //Get position
    public int getPos()
    {
        return this.pos;
    }
        
}
