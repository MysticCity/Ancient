package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.Spell;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.bukkit.event.Event;

public class CommandPlayer
  extends Thread
{
  public static final HashSet<UUID> alreadyDead = new HashSet();
  static Lock lockVar = new ReentrantLock();
  
  public static void scheduleSpell(Spell p, UUID uuidPlayer, Event e, UUID uuidBuffPlayer)
  {
    p.execute(uuidPlayer, uuidBuffPlayer, e);
  }
  
  public static void scheduleSpell(Spell p, UUID uuidPlayer)
  {
    p.execute(uuidPlayer, uuidPlayer, null);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\CommandPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */