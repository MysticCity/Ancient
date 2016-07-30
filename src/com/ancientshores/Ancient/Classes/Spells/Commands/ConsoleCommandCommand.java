package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Bukkit;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ConsoleCommandCommand extends ICommand {
    @CommandDescription(description = "<html>Executes the command in the server console</html>",
            argnames = {"command"}, name = "ConsoleCommand", parameters = {ParameterType.String})
    public ConsoleCommandCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.String};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 1 && ca.getParams().get(0) instanceof String) {
            String s = (String) ca.getParams().get(0);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
        }
        return true;
    }
}