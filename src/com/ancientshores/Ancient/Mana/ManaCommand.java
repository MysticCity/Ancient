package com.ancientshores.Ancient.Mana;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManaCommand
{
  public static void processManaCommand(CommandSender sender)
  {
    if ((sender instanceof Player))
    {
      Player p = (Player)sender;
      PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
      p.sendMessage(Ancient.brand2 + "Your mana regeneration per " + pd.getManasystem().manareginterval + " seconds is " + pd.getManasystem().manareg);
      p.sendMessage(Ancient.brand2 + "You currently have " + pd.getManasystem().curmana + " mana, your max mana is " + pd.getManasystem().maxmana);
    }
  }
}
