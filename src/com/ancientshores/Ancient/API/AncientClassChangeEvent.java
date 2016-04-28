package com.ancientshores.Ancient.API;

import com.ancientshores.Ancient.Classes.AncientClass;
import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AncientClassChangeEvent
  extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  boolean cancelled = false;
  public AncientClass oldclass;
  public AncientClass newclass;
  public UUID uuid;
  
  public AncientClassChangeEvent(UUID uuid, AncientClass old, AncientClass newc)
  {
    this.uuid = uuid;
    this.oldclass = old;
    this.newclass = newc;
  }
  
  public boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public void setCancelled(boolean arg0)
  {
    this.cancelled = arg0;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\API\AncientClassChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */