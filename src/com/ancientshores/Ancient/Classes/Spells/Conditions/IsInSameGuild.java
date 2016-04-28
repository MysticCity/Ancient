package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Guild.AncientGuild;
import org.bukkit.entity.Player;

public class IsInSameGuild
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the 2 players are in the same guild, false otherwise", parameterdescription={"player1", "player2"}, returntype=ParameterType.Boolean, rparams={ParameterType.Player, ParameterType.Player})
  public IsInSameGuild()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.Player };
    this.name = "isinsameguild";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((!(obj[0] instanceof Player[])) || (!(obj[1] instanceof Player[]))) {
      return null;
    }
    if ((((Player[])obj[0]).length > 0) && (((Player[])obj[1]).length > 0) && (((Player[])(Player[])obj[0])[0] != null) && (((Player[])(Player[])obj[1])[0] != null))
    {
      Player p1 = ((Player[])(Player[])obj[0])[0];
      Player p2 = ((Player[])(Player[])obj[1])[0];
      AncientGuild guild1 = AncientGuild.getPlayersGuild(p1.getUniqueId());
      AncientGuild guild2 = AncientGuild.getPlayersGuild(p2.getUniqueId());
      if ((guild1 != null) && (guild2 != null) && (guild1 == guild2)) {
        return Boolean.valueOf(true);
      }
    }
    return Boolean.valueOf(false);
  }
}
