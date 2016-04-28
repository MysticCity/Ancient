package com.ancientshores.Ancient.Listeners;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Quests.AncientQuest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class AncientQuestListener
  implements Listener
{
  final HashMap<UUID, HashSet<AncientQuest>> enabledQuests = new HashMap();
  final Ancient plugin;
  
  public AncientQuestListener(Ancient instance)
  {
    this.plugin = instance;
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }
  
  public void registerQuest(AncientQuest quest, Player p)
  {
    if (!this.enabledQuests.containsKey(p.getUniqueId())) {
      this.enabledQuests.put(p.getUniqueId(), new HashSet());
    }
    ((HashSet)this.enabledQuests.get(quest)).add(quest);
  }
  
  public void unregisterQuest(AncientQuest quest, Player p)
  {
    if (this.enabledQuests.containsKey(p.getUniqueId())) {
      ((HashSet)this.enabledQuests.get(p.getUniqueId())).remove(quest);
    }
  }
  
  @EventHandler
  public void entityDamageEvent(final EntityDamageEvent e)
  {
    Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        if ((e.getEntity().isDead()) && 
          ((e instanceof EntityDamageByEntityEvent)))
        {
          EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
          if ((event.getDamager() instanceof Player))
          {
            Player p = (Player)event.getDamager();
            HashSet<AncientQuest> quests = (HashSet)AncientQuestListener.this.enabledQuests.get(p.getUniqueId());
            for (AncientQuest quest : quests) {
              quest.checkObjectives();
            }
          }
        }
      }
    }, 1L);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Listeners\AncientQuestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */