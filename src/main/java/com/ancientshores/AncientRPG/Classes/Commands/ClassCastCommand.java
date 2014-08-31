package com.ancientshores.AncientRPG.Classes.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ClassCastCommand {
    public static final HashMap<SpellInformationObject, Player> silencedPlayers = new HashMap<SpellInformationObject, Player>();

    public static boolean canCast(PlayerData pd, Player mPlayer, String s) {
        if (!AncientRPG.hasPermissions(mPlayer, AncientRPGClass.cNodeSpells)) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You don't have permissions to cast a spell");
            return false;
        }
        Spell p = AncientRPGClass.getSpell(s, pd);
        if (p == null) {
            return false;
        }
        if (AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(mPlayer)) {
            if (PlayerData.getPlayerData(mPlayer.getUniqueId()).getXpSystem().level < p.minlevel) {
                return false;
            }
        }
        return true;
    }

    public static enum castType {
        Passive, Left, Right, Command
    }

    public static void processCast(PlayerData pd, Player mPlayer, String s, castType ct) {
        Spell p = AncientRPGClass.getSpell(s, pd);
        if (p == null) {
            return;
        }
        if (ct == castType.Right && p.leftclickonly) {
            return;
        }
        if (ct == castType.Left && p.rightclickonly) {
            return;
        }
        for (Map.Entry<SpellInformationObject, Player> entry : silencedPlayers.entrySet()) {
            if (entry.getValue().equals(mPlayer)) {
                mPlayer.sendMessage(AncientRPG.brand2 + "You are silenced and can't cast a spell");
                return;
            }
        }
        if (!AncientRPG.hasPermissions(mPlayer, AncientRPGClass.cNodeSpells)) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You don't have permissions to cast a spell");
            return;
        }
        if (!p.active) {
            mPlayer.sendMessage(AncientRPG.brand2 + "You can't cast passive spells");
            return;
        }
        CommandPlayer.scheduleSpell(p, mPlayer);
    }
}