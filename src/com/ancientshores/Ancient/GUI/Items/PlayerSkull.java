package com.ancientshores.Ancient.GUI.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemStack;

public class PlayerSkull {

    private final Player player;
    private final String displayName;
    
    private ItemStack skull;
    private SkullMeta meta;
    
   //Construction 
   public PlayerSkull( Player p , String displayName )
   {
       
       this.player = p;
       this.displayName = displayName;
       
       setup();
       
   }
   
   //Setup all the required stuff
   private void setup()
   {
       
        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        skull.setDurability((short)3);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwner( player.getName() );
        sm.setDisplayName( displayName );
        skull.setItemMeta( sm );
        
        this.skull = skull;
       
   }
   
   //Get the skull
   public ItemStack getSkull()
   {
       
       return this.skull;
       
   }
            
}
