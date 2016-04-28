package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ConsoleCommandCommand
  extends ICommand
{
  @CommandDescription(description="<html>Executes the command in the server console</html>", argnames={"command"}, name="ConsoleCommand", parameters={ParameterType.String})
  public ConsoleCommandCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 1) && ((ca.getParams().get(0) instanceof String)))
    {
      String s = (String)ca.getParams().get(0);
      Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
    }
    return true;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\ConsoleCommandCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */