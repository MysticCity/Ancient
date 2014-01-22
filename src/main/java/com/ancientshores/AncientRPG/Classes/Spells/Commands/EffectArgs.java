package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.Command;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class EffectArgs {
    private final LinkedList<Object> params = new LinkedList<Object>();
    private final Player caster;
    private final Spell spell;
    private final SpellInformationObject spellInfo;
    private final Command mCommand;

    public EffectArgs(Player caster, Spell spell, SpellInformationObject spellInfo, Command mCommand) {
        this.caster = caster;
        this.spell = spell;
        this.spellInfo = spellInfo;
        this.mCommand = mCommand;
    }

    public LinkedList<Object> getParams() {
        return params;
    }

    public Player getCaster() {
        return caster;
    }

    public Spell getSpell() {
        return spell;
    }

    public SpellInformationObject getSpellInfo() {
        return spellInfo;
    }

    public Command getCommand() {
        return mCommand;
    }
}