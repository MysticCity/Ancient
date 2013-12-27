package com.ancientshores.AncientRPG.Spells.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpellsCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg2.equals("spells")) {
            if (arg3.length >= 2 && arg3[0].equalsIgnoreCase("info")) {
                SpellsInfoCommand.spellsInfo(arg3, arg0);
                return true;
            }
            if (arg3.length >= 1 && arg3[0].equalsIgnoreCase("addspellfreezone")) {
                AddSpellFreeZoneCommand.onCommand(arg0, arg3);
                return true;
            }
            if (arg3.length >= 1 && arg3[0].equalsIgnoreCase("bind") && arg0 instanceof Player) {
                SpellBindCommand.bindCommand(arg3, ((Player) arg0));
                return true;
            }
            if (arg3.length >= 1 && arg3[0].equalsIgnoreCase("bindslot") && arg0 instanceof Player) {
                SpellBindCommand.bindSlotCommand(arg3, ((Player) arg0));
                return true;
            }
            if (arg3.length >= 1 && arg3[0].equalsIgnoreCase("removespellfreezone")) {
                RemoveSpellFreeZoneCommand.onCommand(arg0, arg3);
                return true;
            }
            if (arg3.length <= 1 && arg0 instanceof Player) {
                SpellsCommand.spellListCommand(arg3, (Player) arg0);
                return true;
            }
            return true;
        }
        return false;
    }

}
