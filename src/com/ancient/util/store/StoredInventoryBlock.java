package com.ancient.util.store;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class StoredInventoryBlock
  extends StoredBlock
{
  private final Inventory inv;
  
  public StoredInventoryBlock(Block b)
  {
    super(b);
    this.inv = ((InventoryHolder)b.getState()).getInventory();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\store\StoredInventoryBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */