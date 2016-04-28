package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.PlayerData;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ClassUnbindCommand
{
  public static void unbindCommand(String[] args, Player p)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    Material mat = null;
    byte b = 0;
    if (args.length == 1)
    {
      mat = p.getItemInHand().getType();
      b = p.getItemInHand().getData().getData();
    }
    else
    {
      if (args.length == 2) {
        try
        {
          int matid = Integer.parseInt(args[1]);
          mat = Material.getMaterial(matid);
          if (mat == null)
          {
            p.sendMessage(Ancient.brand2 + "Material not found");
            return;
          }
        }
        catch (Exception e)
        {
          p.sendMessage(Ancient.brand2 + "Expected Integer but received string");
        }
      }
      return;
    }
    unbind(pd, p, mat, b);
  }
  
  public static void unbind(PlayerData pd, Player p, Material m, byte data)
  {
    BindingData tbd = new BindingData(m.getId(), data);
    BindingData removedata = null;
    for (BindingData bd : pd.getBindings().keySet()) {
      if ((bd.data == tbd.data) && (bd.id == tbd.id)) {
        removedata = bd;
      }
    }
    if (removedata != null) {
      pd.getBindings().remove(removedata);
    }
    p.sendMessage(Ancient.brand2 + "Successfully unbound " + m.name() + ".");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Commands\ClassUnbindCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */