package com.ancientshores.AncientRPG.Spells.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;

public class SpellsInfoCommand {
    public static void spellsInfo(String[] args, CommandSender cs) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(AncientRPG.brand2 + "Can't use this command from/as console");
        }
        Player p = (Player) cs;
        if (args.length < 2) {
            p.sendMessage(AncientRPG.brand2 + "The right usage is /spells info <spellname>");
            return;
        }
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        Spell mSpell = AncientRPGClass.getSpell(args[1], pd);
        if (mSpell == null) {
            p.sendMessage(AncientRPG.brand2 + "This spell doesn't exist or you don't have access to this spell");
            return;
        }
        p.sendMessage(AncientRPG.brand2 + "Spell " + mSpell.name);
        if (mSpell.minlevel != 0) {
            p.sendMessage(AncientRPG.brand2 + "The minimum level to use this spell is " + mSpell.minlevel);
        }
        if (mSpell.description != null && !mSpell.description.equals("")) {
            p.sendMessage(mSpell.description);
        }
    }
}