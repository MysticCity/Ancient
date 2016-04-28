package com.ancient.util.store;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class StoredBlock
{
  private final Material mat;
  private final byte data;
  private final MaterialData matData;
  
  public StoredBlock(Block b)
  {
    this.mat = b.getType();
    this.data = b.getData();
    this.matData = b.getState().getData();
  }
  
  public void restore(Location loc) {}
  
  public static StoredBlock store(Block b)
  {
    switch (b.getType())
    {
    case ANVIL: 
      return new StoredBlock(b);
    case BEACON: 
    case CHEST: 
    case HOPPER: 
    case DROPPER: 
    case DISPENSER: 
      return new StoredInventoryBlock(b);
    case BED_BLOCK: 
      break;
    case BREWING_STAND: 
      break;
    case BURNING_FURNACE: 
      break;
    case CAKE_BLOCK: 
      break;
    case CAULDRON: 
      break;
    case COMMAND: 
      break;
    case DOUBLE_PLANT: 
      break;
    case DOUBLE_STEP: 
      break;
    case ENCHANTMENT_TABLE: 
      break;
    case ENDER_CHEST: 
      break;
    case ENDER_PORTAL: 
      break;
    case ENDER_PORTAL_FRAME: 
      break;
    case FENCE_GATE: 
      break;
    case FIRE: 
      break;
    case FLOWER_POT: 
      break;
    case FURNACE: 
      break;
    case GLASS: 
      break;
    case IRON_DOOR: 
      break;
    case IRON_DOOR_BLOCK: 
      break;
    case ITEM_FRAME: 
      break;
    case JUKEBOX: 
      break;
    case LADDER: 
      break;
    case LEVER: 
      break;
    case LONG_GRASS: 
      break;
    case MOB_SPAWNER: 
      break;
    case NOTE_BLOCK: 
      break;
    case PAINTING: 
      break;
    case PISTON_BASE: 
      break;
    case PISTON_EXTENSION: 
      break;
    case PISTON_MOVING_PIECE: 
      break;
    case PISTON_STICKY_BASE: 
      break;
    case PORTAL: 
      break;
    case SIGN: 
      break;
    case SIGN_POST: 
      break;
    case SKULL: 
      break;
    case SNOW: 
      break;
    case SNOW_BLOCK: 
      break;
    case TRAPPED_CHEST: 
      break;
    case TRAP_DOOR: 
      break;
    case WALL_SIGN: 
      break;
    case WOODEN_DOOR: 
      break;
    case WOOD_DOOR: 
      break;
    }
    return null;
  }
}
