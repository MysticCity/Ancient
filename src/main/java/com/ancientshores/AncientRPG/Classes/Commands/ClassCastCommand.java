package com.ancientshores.AncientRPG.Classes.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;

public class ClassCastCommand {
    public static final HashMap<SpellInformationObject, UUID> silencedPlayers = new HashMap<SpellInformationObject, UUID>();

    public static boolean canCast(PlayerData pd, Player mPlayer, String s) {
        if (!mPlayer.hasPermission(AncientRPGClass.cNodeSpells)) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You don't have permissions to cast a spell");
            return false;
        }
        Spell p = AncientRPGClass.getSpell(s, pd);
        if (p == null) {
            return false;
        }
        if (AncientRPGExperience.isWorldEnabled(mPlayer.getWorld())) {
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
        Spell p = AncientRPGClass.getSpell(s, pd);
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
                mPlayer.sendMessage(AncientRPG.brand2 + "You are silenced and can't cast a spell");
                return;
            }
        }
        if (!mPlayer.hasPermission(AncientRPGClass.cNodeSpells)) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You don't have permissions to cast a spell");
            return;
        }
        if (!p.active) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You can't cast passive spells");
            return;
        }
        CommandPlayer.scheduleSpell(p, mPlayer.getUniqueId());
    }
}