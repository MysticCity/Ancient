package com.ancientshores.Ancient.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.PlayerData;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SpellBindCommand
{
  public static void bindCommand(String[] args, Player mPlayer)
  {
    ItemStack is = mPlayer.getItemInHand();
    BindingData bd = new BindingData(is);
    if (args.length == 3) {
      try
      {
        String[] s = args[2].split(":");
        int matid = Integer.parseInt(s[0]);
        byte data = 0;
        if (s.length == 2) {
          data = Byte.parseByte(s[1]);
        }
        bd = new BindingData(matid, data);
      }
      catch (Exception e)
      {
        mPlayer.sendMessage(Ancient.brand2 + "Expected Integer but received string");
        return;
      }
    }
    if (!mPlayer.hasPermission("Ancient.classes.bind"))
    {
      mPlayer.sendMessage(Ancient.brand2 + "You don't have permissions to bind a spell");
      return;
    }
    if (args.length >= 2) {
      bind(mPlayer, args[1], bd);
    } else {
      mPlayer.sendMessage(Ancient.brand2 + "Missing spell name");
    }
  }
  
  public static void bindSlotCommand(String[] args, Player mPlayer)
  {
    int slot = mPlayer.getInventory().getHeldItemSlot();
    if (!mPlayer.hasPermission("Ancient.classes.bind"))
    {
      mPlayer.sendMessage(Ancient.brand2 + "You don't have permissions to bind a spell");
      return;
    }
    if (args.length >= 2) {
      bindSlot(mPlayer, args[1], slot);
    } else {
      mPlayer.sendMessage(Ancient.brand2 + "Missing spell name");
    }
  }
  
  public static void bindSlot(Player p, String spell, int slot)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    if (AncientClass.canBind(spell, pd, p).booleanValue())
    {
      pd.getSlotbinds().put(Integer.valueOf(slot), spell.toLowerCase());
      p.sendMessage(Ancient.brand2 + "Successfully bound " + spell + " to the slot");
    }
    else
    {
      p.sendMessage(Ancient.brand2 + "Error in binding the spell");
    }
  }
  
  public static void bind(Player p, String spell, BindingData bd)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    if (AncientClass.canBind(spell, pd, p).booleanValue())
    {
      pd.getBindings().put(bd, spell.toLowerCase());
      p.sendMessage(Ancient.brand2 + "Successfully bound " + spell + " to " + Material.getMaterial(bd.id) + ".");
    }
    else
    {
      p.sendMessage(Ancient.brand2 + "Error in binding the spell");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Spells\Commands\SpellBindCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */