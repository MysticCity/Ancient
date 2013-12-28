package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public abstract class ICommand {
    public ParameterType[] paramTypes;

    public abstract boolean playCommand(EffectArgs ca);
}