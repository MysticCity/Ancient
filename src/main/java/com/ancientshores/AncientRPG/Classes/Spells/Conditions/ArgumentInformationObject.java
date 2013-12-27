package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class ArgumentInformationObject {
    public ArgumentParameter[] parameters;
    public IArgument argument;

    public Object getArgument(SpellInformationObject so, Player mPlayer) {
        if (parameters != null && parameters.length > 0 && argument.requiredTypes != null && argument.requiredTypes.length > 0) {
            Object[] arguments = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                arguments[i] = parameters[i].parseParameter(argument.requiredTypes[i], so, mPlayer, so.mSpell);
            }
            if (arguments.length == 0) {
                return argument.getArgument(new Object[]{mPlayer}, so);
            } else {
                return argument.getArgument(arguments, so);
            }
        } else {
            return argument.getArgument(new Object[]{new Player[]{mPlayer}}, so);
        }
    }
}
