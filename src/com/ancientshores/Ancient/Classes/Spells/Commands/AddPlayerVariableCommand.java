package com.ancientshores.Ancient.Classes.Spells.Commands;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Variable;

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
                Player[] players = (Player[]) ca.getParams().get(0);
                String name = (String) ca.getParams().get(1);
                for (Player p : players) {
                    Variable v = new Variable(name);
                    if (!Variable.playerVars.containsKey(p.getUniqueId())) {
                        Variable.playerVars.put(p.getUniqueId(), new HashMap<String, Variable>());
                    }
                    Variable.playerVars.get(p.getUniqueId()).put(name, v);
                    ca.getSpellInfo().variables.put(v.name.toLowerCase(), v);
                }
                return true;
            }
        }
        return false;
    }
}