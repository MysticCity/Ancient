package com.ancientshores.Ancient.Classes.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Experience.AncientExperience;

public class ClassCastCommand {
    public static final HashMap<SpellInformationObject, UUID> silencedPlayers = new HashMap<SpellInformationObject, UUID>();

    public static boolean canCast(PlayerData pd, Player mPlayer, String s) {
        if (!mPlayer.hasPermission(AncientClass.cNodeSpells)) {
            mPlayer.sendMessage(Ancient.ChatBrand + "You don't have permissions to cast a spell");
            return false;
        }
        Spell p = AncientClass.getSpell(s, pd);
        if (p == null) {
            return false;
        }
        if (AncientExperience.isWorldEnabled(mPlayer.getWorld())) {
            if (PlayerData.getPlayerData(mPlayer.getUniqueId()).getXpSystem().level < p.minlevel) {
                return false;
            }
        }
        return true;
    }

    public static enum castType {
        PASSIVE, LEFT, RIGHT, COMMAND
    }

    public static void processCast(PlayerData pd, Player mPlayer, String s, castType ct) {
        Spell p = AncientClass.getSpell(s, pd);
        if (p == null) {
            return;
        }
        if (ct == castType.RIGHT && p.leftclickonly) {
            return;
        }
        if (ct == castType.LEFT && p.rightclickonly) {
            return;
        }
        for (Map.Entry<SpellInformationObject, UUID> entry : silencedPlayers.entrySet()) {
            if (entry.getValue().compareTo(mPlayer.getUniqueId()) == 0) {
                mPlayer.sendMessage(Ancient.ChatBrand + "You are silenced and can't cast a spell");
                return;
            }
        }
        if (!mPlayer.hasPermission(AncientClass.cNodeSpells)) {
            mPlayer.sendMessage(Ancient.ChatBrand + "You don't have permissions to cast a spell");
            return;
        }
        if (!p.active) {
            mPlayer.sendMessage(Ancient.ChatBrand + "You can't cast passive spells");
            return;
        }
        CommandPlayer.scheduleSpell(p, mPlayer.getUniqueId());
    }
}