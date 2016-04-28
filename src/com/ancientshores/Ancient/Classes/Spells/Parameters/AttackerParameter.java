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
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@ParameterDescription(amount=0, description="<html>returns the attacker, can only be used in damage/attack events</html>", returntype="Entity", name="Attacker")
public class AttackerParameter
  implements IParameter
{
  public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
  {
    if ((!ea.getSpell().active) && ((ea.getSpellInfo().mEvent instanceof EntityDamageByEntityEvent)))
    {
      EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)ea.getSpellInfo().mEvent;
      Entity ent = event.getDamager();
      if ((ent instanceof Projectile)) {
        ent = (Entity)((Projectile)ent).getShooter();
      }
      switch (pt)
      {
      case Player: 
        if ((ent instanceof Player))
        {
          Player p = (Player)ent;
          ea.getParams().addLast(p.getUniqueId());
        }
        return;
      case Entity: 
        Entity[] e = new Entity[1];
        e[0] = ent;
        ea.getParams().addLast(e);
        return;
      case Location: 
        Location[] l = new Location[1];
        l[0] = ent.getLocation();
        ea.getParams().addLast(l);
        return;
      case String: 
        if ((ent instanceof Player))
        {
          Player p = (Player)ent;
          ea.getParams().addLast(p.getUniqueId());
        }
        return;
      }
    }
    else
    {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Invalid usage of attacker parameter in Command " + ea.getCommand().commandString + " in spell " + ea.getCommand().mSpell.name + " in line " + ea.getCommand().lineNumber);
    }
  }
  
  public String getName()
  {
    return "attacker";
  }
  
  public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject informationObject)
  {
    if ((informationObject.mEvent != null) && ((informationObject.mEvent instanceof EntityDamageByEntityEvent)))
    {
      EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)informationObject.mEvent;
      return new Entity[] { event.getDamager() };
    }
    return null;
  }
}
