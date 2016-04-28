package com.ancientshores.Ancient.API;

import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameter;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.entity.Player;

public class ApiManager
{
  private static ApiManager instance;
  
  public static ApiManager getApiManager()
  {
    if (instance == null) {
      instance = new ApiManager();
    }
    return instance;
  }
  
  public PlayerData getPlayerData(UUID uuid)
  {
    return PlayerData.getPlayerData(uuid);
  }
  
  public AncientClass getPlayerClass(PlayerData pd)
  {
    AncientClass playerClass = (AncientClass)AncientClass.classList.get(pd.getClassName());
    return playerClass;
  }
  
  @Deprecated
  public AncientParty getPlayerParty(Player p)
  {
    return AncientParty.getPlayersParty(p.getUniqueId());
  }
  
  public AncientParty getPlayerParty(UUID uuid)
  {
    return AncientParty.getPlayersParty(uuid);
  }
  
  public AncientGuild getPlayerGuild(UUID uuid)
  {
    AncientGuild guild = AncientGuild.getPlayersGuild(uuid);
    return guild;
  }
  
  public void registerSpellParameter(IParameter param)
  {
    Parameter.registeredParameters.add(param);
  }
  
  public void registerSpellCommand(ICommand param, String name)
  {
    Command.registeredCommands.put(name.toLowerCase(), param);
  }
  
  public AncientExperience getXpSystem(PlayerData pd)
  {
    return pd.getXpSystem();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\API\ApiManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */