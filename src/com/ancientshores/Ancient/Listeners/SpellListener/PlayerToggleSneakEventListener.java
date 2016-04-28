package com.ancientshores.Ancient.Listeners.SpellListener;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.PlayerData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerToggleSneakEventListener
  extends ISpellListener
{
  public PlayerToggleSneakEventListener(Ancient instance)
  {
    super(instance);
    this.eventName = "playertogglesneakevent";
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onEvent(final PlayerToggleSneakEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (AncientSpellListener.ignoredEvents.contains(event)) {
      return;
    }
    AncientSpellListener.ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    Player mPlayer = event.getPlayer();
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : this.eventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = this.eventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Listeners\SpellListener\PlayerToggleSneakEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */