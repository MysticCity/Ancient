package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Bukkit;

public class ConsoleCommandCommand extends ICommand {
    @CommandDescription(description = "<html>Executes the command in the server console</html>",
            argnames = {"command"}, name = "ConsoleCommand", parameters = {ParameterType.String})
    public ConsoleCommandCommand() {
        ParameterType[] buffer = {ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 1 && ca.params.get(0) instanceof String) {
            String s = (String) ca.params.get(0);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
        }
        return true;
    }
}