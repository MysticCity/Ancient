package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

public class TameCommand
  extends ICommand
{
  @CommandDescription(description="<html>Tames the target if it is tameable</html>", argnames={"entity"}, name="Tame", parameters={ParameterType.Entity})
  public TameCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 1) && ((ca.getParams().get(0) instanceof Entity[])))
    {
      Entity[] ents = (Entity[])ca.getParams().get(0);
      for (Entity e : ents) {
        if ((e instanceof Tameable)) {
          ((Tameable)e).setOwner(ca.getCaster());
        }
      }
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\TameCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */