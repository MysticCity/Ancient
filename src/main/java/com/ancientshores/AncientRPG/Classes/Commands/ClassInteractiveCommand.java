package com.ancientshores.AncientRPG.Classes.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Command;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class ClassInteractiveCommand {
    public static void interactiveCommand(Player mPlayer, String line) {
        if (!mPlayer.hasPermission("AncientRPG.classes.admin")) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You don't have access to this command!");
            return;
        }
        Command interactiveCommand = new Command(line, null, 0, null);
        interactiveCommand.executeCommand(mPlayer, new SpellInformationObject());
    }
}