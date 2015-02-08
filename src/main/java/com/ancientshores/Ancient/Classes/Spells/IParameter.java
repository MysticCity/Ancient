package com.ancientshores.Ancient.Classes.Spells;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;

public interface IParameter {
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt);

    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so);

    public String getName();
}