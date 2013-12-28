package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import org.bukkit.entity.Player;

public interface IParameter {
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt);

    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so);

    public String getName();
}