package com.ancientshores.Ancient.Spells.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;

public class SpellsInfoCommand {
    public static void spellsInfo(String[] args, CommandSender cs) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(Ancient.ChatBrand + "Can't use this command from/as console");
        }
        Player p = (Player) cs;
        if (args.length < 2) {
            p.sendMessage(Ancient.ChatBrand + "The right usage is /spells info <spellname>");
            return;
        }
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        Spell mSpell = AncientClass.getSpell(args[1], pd);
        if (mSpell == null) {
            p.sendMessage(Ancient.ChatBrand + "This spell doesn't exist or you don't have access to this spell");
            return;
        }
        p.sendMessage(Ancient.ChatBrand + "Spell " + mSpell.name);
        if (mSpell.minlevel != 0) {
            p.sendMessage(Ancient.ChatBrand + "The minimum level to use this spell is " + mSpell.minlevel);
        }
        if (mSpell.description != null && !mSpell.description.equals("")) {
            p.sendMessage(mSpell.description);
        }
    }
}