package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class EffectArgs
{
  private final LinkedList<Object> params = new LinkedList();
  private final Player caster;
  private final Spell spell;
  private final SpellInformationObject spellInfo;
  private final Command mCommand;
  
  public EffectArgs(Player caster, Spell spell, SpellInformationObject spellInfo, Command mCommand)
  {
    this.caster = caster;
    this.spell = spell;
    this.spellInfo = spellInfo;
    this.mCommand = mCommand;
  }
  
  public LinkedList<Object> getParams()
  {
    return this.params;
  }
  
  public Player getCaster()
  {
    return this.caster;
  }
  
  public Spell getSpell()
  {
    return this.spell;
  }
  
  public SpellInformationObject getSpellInfo()
  {
    return this.spellInfo;
  }
  
  public Command getCommand()
  {
    return this.mCommand;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\EffectArgs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */