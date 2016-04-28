package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public abstract class ICommand {
    public ParameterType[] paramTypes; //ToDo read from annotation

    public abstract boolean playCommand(EffectArgs ca);
}