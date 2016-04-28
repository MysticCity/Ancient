package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class ClassInteractiveCommand
{
  public static void interactiveCommand(Player mPlayer, String line)
  {
    if (!mPlayer.hasPermission("Ancient.classes.admin"))
    {
      mPlayer.sendMessage(Ancient.brand2 + "You don't have access to this command!");
      return;
    }
    Command interactiveCommand = new Command(line, null, 0, null);
    interactiveCommand.executeCommand(mPlayer, new SpellInformationObject());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Commands\ClassInteractiveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */