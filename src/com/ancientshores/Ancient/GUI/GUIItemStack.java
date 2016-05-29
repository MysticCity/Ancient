package com.ancientshores.Ancient.GUI;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIItemStack {
    
    private final String ItemName;
    private final int ItemPosition;
    private final Material ItemMaterial;
    private final List<String> ItemDescription;
    
    //If ItemStack already exist
    private final ItemStack ExistingStack;
    
    //Construction
    public GUIItemStack( String name , Material material , List<String> description , int position )
    {
        
        this.ItemName = name;
        this.ItemPosition = position;
        this.ItemMaterial = material;
        this.ItemDescription = description;
        this.ExistingStack = null;
        
    }
    
    //Construction with existing ItemStack
    public GUIItemStack( ItemStack item , int position )
    {
        
        this.ItemName = null;
        this.ItemPosition = position;
        this.ItemMaterial = null;
        this.ItemDescription = null;
        this.ExistingStack = item;
        
    }
    
    //Get complete Item
    public ItemStack getItemStack()
    {
        if ( ExistingStack == null )
        {
            
            ItemStack item = new ItemStack( ItemMaterial );
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName( ItemName );

            if ( ItemDescription != null )
            {
                meta.setLore( ItemDescription );
            }

            item.setItemMeta(meta);
        
            return item;
            
        } else {
            
            return ExistingStack;
            
        }
        
        
    }
    
    //Get item-name
    public String getName()
    {
        return ItemName;
    }
    
    //Get item-descripion
    public List<String> getDescription()
    {
        return ItemDescription;
    }
    
    //Get item-material
    public Material getMaterial()
    {
        return ItemMaterial;
    }
    
    //Get item-position
    public int getPosition()
    {
        return ItemPosition;
    }
    
}
