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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Mana\ManaCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */