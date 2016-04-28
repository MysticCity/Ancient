package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.entity.Player;

public class ClassCastCommand
{
  public static final HashMap<SpellInformationObject, UUID> silencedPlayers = new HashMap();
  
  public static boolean canCast(PlayerData pd, Player mPlayer, String s)
  {
    if (!mPlayer.hasPermission("Ancient.classes.spells"))
    {
      mPlayer.sendMessage(Ancient.brand2 + "You don't have permissions to cast a spell");
      return false;
    }
    Spell p = AncientClass.getSpell(s, pd);
    if (p == null) {
      return false;
    }
    if ((AncientExperience.isWorldEnabled(mPlayer.getWorld())) && 
      (PlayerData.getPlayerData(mPlayer.getUniqueId()).getXpSystem().level < p.minlevel)) {
      return false;
    }
    return true;
  }
  
  public static enum castType
  {
    PASSIVE,  LEFT,  RIGHT,  COMMAND;
    
    private castType() {}
  }
  
  public static void processCast(PlayerData pd, Player mPlayer, String s, castType ct)
  {
    Spell p = AncientClass.getSpell(s, pd);
    if (p == null) {
      return;
    }
    if ((ct == castType.RIGHT) && (p.leftclickonly)) {
      return;
    }
    if ((ct == castType.LEFT) && (p.rightclickonly)) {
      return;
    }
    for (Map.Entry<SpellInformationObject, UUID> entry : silencedPlayers.entrySet()) {
      if (((UUID)entry.getValue()).compareTo(mPlayer.getUniqueId()) == 0)
      {
        mPlayer.sendMessage(Ancient.brand2 + "You are silenced and can't cast a spell");
        return;
      }
    }
    if (!mPlayer.hasPermission("Ancient.classes.spells"))
    {
      mPlayer.sendMessage(Ancient.brand2 + "You don't have permissions to cast a spell");
      return;
    }
    if (!p.active)
    {
      mPlayer.sendMessage(Ancient.brand2 + "You can't cast passive spells");
      return;
    }
    CommandPlayer.scheduleSpell(p, mPlayer.getUniqueId());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Commands\ClassCastCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */