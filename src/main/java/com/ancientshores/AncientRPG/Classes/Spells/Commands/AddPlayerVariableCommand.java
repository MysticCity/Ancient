package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AddPlayerVariableCommand extends ICommand {
    @CommandDescription(description = "<html>Creates a player variable which is visible to all of the players spells, can be accessed using normal variables.</html>",
            argnames = {"player", "varname"}, name = "AddPlayerVariable", parameters = {ParameterType.Player, ParameterType.String})
    public AddPlayerVariableCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
                Player[] ps = (Player[]) ca.getParams().get(0);
                String name = (String) ca.getParams().get(1);
                for (Player p : ps) {
                    Variable v = new Variable(name);
                    if (!Variable.playerVars.containsKey(p.getName())) {
                        Variable.playerVars.put(p.getName(), new HashMap<String, Variable>());
                    }
                    Variable.playerVars.get(p.getName()).put(name, v);
                    ca.getSpellInfo().variables.put(v.name.toLowerCase(), v);
                }
                return true;
            }
        }
        return false;
    }
}