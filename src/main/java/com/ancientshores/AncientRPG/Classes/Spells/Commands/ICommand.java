package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public abstract class ICommand {
    public ParameterType[] paramTypes; //ToDo read from annotation

    public abstract boolean playCommand(EffectArgs ca);
}