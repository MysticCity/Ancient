package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Guild.AncientGuild;
import org.bukkit.entity.Player;

public class GetGuildName
  extends IArgument
{
  @ArgumentDescription(description="Returns the guild name of the player", parameterdescription={"player"}, returntype=ParameterType.String, rparams={ParameterType.Player})
  public GetGuildName()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getguildname";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return Integer.valueOf(0);
    }
    Player p = ((Player[])(Player[])obj[0])[0];
    AncientGuild guild = AncientGuild.getPlayersGuild(p.getUniqueId());
    if (guild != null) {
      return guild.getGuildName();
    }
    return "";
  }
}
