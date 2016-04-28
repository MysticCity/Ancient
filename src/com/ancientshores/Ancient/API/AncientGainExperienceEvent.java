package com.ancientshores.Ancient.API;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AncientGainExperienceEvent
  extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  public final UUID uuid;
  public final int playeroldxp;
  public int gainedxp;
  public boolean cancelled;
  
  public AncientGainExperienceEvent(int playeroldxp, UUID uuid, int gainedxp)
  {
    this.playeroldxp = playeroldxp;
    this.gainedxp = gainedxp;
    this.uuid = uuid;
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\API\AncientGainExperienceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */