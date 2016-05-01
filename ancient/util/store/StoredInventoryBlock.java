package com.ancientshores.Ancient.Util.store;

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
