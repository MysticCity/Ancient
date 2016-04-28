package com.ancientshores.Ancient.API;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AncientLevelupEvent
  extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  public int newlevel;
  public final UUID uuid;
  public int xp;
  public int addedxp;
  public boolean cancelled;
  
  public AncientLevelupEvent(int newlevel, UUID uuid, int xp, int addedxp)
  {
    this.newlevel = newlevel;
    this.uuid = uuid;
    this.xp = xp;
    this.addedxp = addedxp;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public void setCancelled(boolean arg0)
  {
    this.cancelled = arg0;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\API\AncientLevelupEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */