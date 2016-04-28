package com.ancientshores.Ancient.Classes.Spells.Parameters;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@ParameterDescription(amount=0, description="<html>returns the attacked entity, can only be used in attack/damagebyentity events</html>", returntype="Entity", name="AttackedEntity")
public class AttackedEntityParameter
  implements IParameter
{
  public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
  {
    if ((!ea.getSpell().active) && ((ea.getSpellInfo().mEvent instanceof EntityDamageEvent)))
    {
      EntityDamageEvent event = (EntityDamageEvent)ea.getSpellInfo().mEvent;
      switch (pt)
      {
      case Player: 
        if ((event.getEntity() instanceof Player))
        {
          Player p = (Player)event.getEntity();
          ea.getParams().addLast(p.getUniqueId());
        }
        return;
      case Entity: 
        Entity[] e = new Entity[1];
        e[0] = event.getEntity();
        ea.getParams().addLast(e);
        return;
      case Location: 
        Location[] l = new Location[1];
        l[0] = event.getEntity().getLocation();
        ea.getParams().addLast(l);
        return;
      case String: 
        if ((event.getEntity() instanceof Player))
        {
          Player p = (Player)event.getEntity();
          ea.getParams().addLast(p.getUniqueId());
        }
        return;
      }
    }
    else
    {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Invalid usage of attackedentity parameter in Command " + ea.getCommand().commandString + " in spell " + ea.getCommand().mSpell.name + " in line " + ea.getCommand().lineNumber);
    }
  }
  
  public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so)
  {
    if ((so.mEvent instanceof EntityDamageByEntityEvent))
    {
      EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)so.mEvent;
      Entity[] e = new Entity[1];
      e[0] = event.getEntity();
      return e;
    }
    return null;
  }
  
  public String getName()
  {
    return "attackedentity";
  }
}
