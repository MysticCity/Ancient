package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.Command;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class EffectArgs {
    public final LinkedList<Object> params = new LinkedList<Object>();
    public Player caster;
    public Spell p;
    public SpellInformationObject so;
    public Command mCommand;

    public EffectArgs() {
    }
}