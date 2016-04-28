package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

public class RemoveBuffCommand
  extends ICommand
{
  @CommandDescription(description="<html>removes the specified buff of the player so it can't be triggered anymore</html>", argnames={"player", "buffname"}, name="RemoveBuff", parameters={ParameterType.Player, ParameterType.String})
  public RemoveBuffCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof String)))
    {
      Player[] players = (Player[])ca.getParams().get(0);
      String spellname = (String)ca.getParams().get(1);
      ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> affectedSpells = AncientSpellListener.getAllBuffs();
      boolean all = false;
      if (spellname.equalsIgnoreCase("all")) {
        all = true;
      }
      Player p;
      Iterator i$;
      Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> entry;
      for (p : players) {
        if (p != null) {
          for (i$ = affectedSpells.entrySet().iterator(); i$.hasNext();)
          {
            entry = (Map.Entry)i$.next();
            ConcurrentHashMap<UUID[], Integer> maps = new ConcurrentHashMap();
            for (Map.Entry<UUID[], Integer> entry1 : ((ConcurrentHashMap)entry.getValue()).entrySet()) {
              if ((((UUID[])entry1.getKey())[0].compareTo(p.getUniqueId()) == 0) && ((all) || (((Spell)entry.getKey()).name.equalsIgnoreCase(spellname)))) {
                AncientSpellListener.detachBuffOfEvent(((Spell)entry.getKey()).buffEvent, (Spell)entry.getKey(), p);
              }
            }
            for (Map.Entry<UUID[], Integer> entry1 : maps.entrySet()) {
              ((ConcurrentHashMap)entry.getValue()).remove(entry1.getKey());
            }
          }
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\RemoveBuffCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */