package com.ancient.util.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryOrganizer
{
  public static Inventory[] sortInventory(ItemStack[] items, ItemStack[] menuItems, Material nextPageMaterial, int border, int contentMenuSpace, int maxRowsPerPage, String name)
  {
    if (border >= 5) {
      return null;
    }
    if (maxRowsPerPage <= border * 2 + contentMenuSpace) {
      return null;
    }
    Inventory[] invs = new Inventory[0];
    
    int menuItemsPerRow = (int)Math.ceil(menuItems.length / (9 - 2 * border - 2));
    int maxPerPage;
    int maxPerPage;
    if (menuItems.length == 0) {
      maxPerPage = (9 - 2 * border) * (maxRowsPerPage - 2 * border);
    } else {
      maxPerPage = (9 - 2 * border) * (maxRowsPerPage - 2 * border - contentMenuSpace - menuItemsPerRow);
    }
    int pages = (int)Math.ceil(items.length / maxPerPage);
    for (int page = 1; page <= pages; page++)
    {
      Inventory inv = Bukkit.createInventory(null, maxRowsPerPage * 9, name + " - Page " + page);
      int posX = 0;
      int posY = 0;
      for (int i = 0; i < maxPerPage; i++)
      {
        inv.setItem(posX + border + 9 * (posY + border), items[(i + (page - 1) * maxPerPage)]);
        posX++;
        if (posX + border > 8 - border)
        {
          posX = 0;
          posY++;
        }
      }
      if (page > 1)
      {
        ItemStack previousPage = new ItemStack(nextPageMaterial);
        previousPage.setAmount(1);
        previousPage.getItemMeta().setDisplayName("Previous page: " + page);
        inv.setItem(border + 9 * (posY + border), previousPage);
      }
      if (page != pages)
      {
        ItemStack nextPage = new ItemStack(nextPageMaterial);
        nextPage.setAmount(1);
        nextPage.getItemMeta().setDisplayName("Next page: " + page);
        inv.setItem(8 - border + 9 * (posY + border + contentMenuSpace), nextPage);
      }
      posX = 1;
      posY += contentMenuSpace;
      
      int menuRows = (int)Math.ceil(menuItems.length / menuItemsPerRow);
      for (int row = 0; row < menuRows; row++)
      {
        for (int i = 0; i < maxRowsPerPage; i++)
        {
          inv.setItem(posX + border + 9 * (posY + border), menuItems[(i + row * menuItemsPerRow)]);
          posX++;
        }
        posY++;
      }
      Inventory[] invsNew = new Inventory[invs.length + 1];
      for (int i = 0; i < invs.length; i++) {
        invsNew[i] = invs[i];
      }
      invsNew[invs.length] = inv;
      
      invs = invsNew;
    }
    return invs;
  }
}
