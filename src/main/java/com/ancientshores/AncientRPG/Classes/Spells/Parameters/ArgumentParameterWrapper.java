package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Conditions.IArgument;

public class ArgumentParameterWrapper implements IParameter {
    final ArgumentInformationObject arg;

    public ArgumentParameterWrapper(ArgumentInformationObject arg) {
        this.arg = arg;
    }

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        IArgument.AutoCast(arg.getArgument(ea.getSpellInfo(), mPlayer), pt, ea);
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        return arg.getArgument(so, mPlayer);
    }

    @Override
    public String getName() {
        return null;
    }
}