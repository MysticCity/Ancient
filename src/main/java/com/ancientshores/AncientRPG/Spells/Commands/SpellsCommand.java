package com.ancientshores.AncientRPG.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Commands.ClassCastCommand;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Util.PageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map.Entry;

public class SpellsCommand {
    public static void spellListCommand(String[] args, Player p) {
        int page = 1;
        if (args.length == 1) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (Exception ignored) {

            }
        }
        showSpellList(p, page);
    }

    public static void showSpellList(Player p, int page) {
        PlayerData pd = PlayerData.getPlayerData(p.getName());
        PageBuilder pb = new PageBuilder();
        if (AncientRPGClass.classList.containsKey(pd.getClassName().toLowerCase())) {
            pb.addMessage(AncientRPG.brand2 + "Class spells:");
            for (Entry<String, Spell> s : AncientRPGClass.classList.get(pd.getClassName().toLowerCase()).spellList.entrySet()) {
                String message;
                if (s.getValue().buff) {
                    continue;
                }
                if (s.getValue().hidden) {
                    continue;
                }
                if (ClassCastCommand.canCast(pd, p, s.getKey())) {
                    message = ChatColor.GREEN + s.getValue().name;
                } else {
                    if (AncientRPGExperience.isWorldEnabled(p) && AncientRPGExperience.isEnabled() && pd.getXpSystem().level < s.getValue().minlevel) {
                        message = ChatColor.RED + "[lvl " + s.getValue().minlevel + "] " + s.getValue().name;
                    } else {
                        message = ChatColor.RED + s.getValue().name;
                    }
                }
                if (s.getValue().shortdescription != null && s.getValue().shortdescription.length() > 0) {
                    message += ChatColor.WHITE + " - " + ChatColor.GOLD + s.getValue().shortdescription;
                }
                pb.addMessage(message);
            }
            pb.addMessage(AncientRPG.brand2 + "Global spells:");
            for (Entry<String, Spell> s : AncientRPGClass.globalSpells.entrySet()) {
                String message;
                if (s.getValue().buff) {
                    continue;
                }
                if (s.getValue().hidden) {
                    continue;
                }
                if (ClassCastCommand.canCast(pd, p, s.getKey())) {
                    message = ChatColor.GREEN + s.getValue().name;
                } else {
                    if (AncientRPGExperience.isWorldEnabled(p) && AncientRPGExperience.isEnabled() && pd.getXpSystem().level < s.getValue().minlevel) {
                        message = ChatColor.RED + "[lvl " + s.getValue().minlevel + "] " + s.getValue().name;
                    } else {
                        message = ChatColor.RED + s.getValue().name;
                    }
                }
                if (s.getValue().shortdescription != null && s.getValue().shortdescription.length() > 0) {
                    message += ChatColor.WHITE + " - " + ChatColor.GOLD + s.getValue().shortdescription;
                }
                pb.addMessage(message);
            }
            AncientRPGClass stance = AncientRPGClass.classList.get(pd.getClassName().toLowerCase()).stances.get(pd.getStance());
            if (stance != null) {
                pb.addMessage("-----------------------------------");
                pb.addMessage("Stance spells:");
                for (Entry<String, Spell> s : stance.spellList.entrySet()) {
                    String message;
                    if (s.getValue().buff) {
                        continue;
                    }
                    if (s.getValue().hidden) {
                        continue;
                    }
                    if (ClassCastCommand.canCast(pd, p, s.getKey())) {
                        message = ChatColor.GREEN + s.getValue().name;
                    } else {
                        if (AncientRPGExperience.isWorldEnabled(p) && AncientRPGExperience.isEnabled() && pd.getXpSystem().level < s.getValue().minlevel) {
                            message = ChatColor.RED + "[lvl " + s.getValue().minlevel + "] " + s.getValue().name;
                        } else {
                            message = ChatColor.RED + s.getValue().name;
                        }
                    }
                    if (s.getValue().shortdescription != null && s.getValue().shortdescription.length() > 0) {
                        message += ChatColor.WHITE + " - " + ChatColor.GOLD + s.getValue().shortdescription;
                    }
                    pb.addMessage(message);
                }
            }
            AncientRPGRace race = AncientRPGRace.getRaceByName(pd.getRacename());
            if (race != null) {
                pb.addMessage("-----------------------------------");
                pb.addMessage("Race spells:");
                for (Spell sp : race.raceSpells) {
                    String message;
                    if (sp.hidden) {
                        continue;
                    }
                    message = sp.name;
                    if (!sp.buff) {
                        if (sp.shortdescription != null && sp.shortdescription.length() > 0) {
                            message += ChatColor.WHITE + " - " + ChatColor.GOLD + sp.shortdescription;
                        }
                        pb.addMessage(message);
                    }
                }
            }
        } else {
            pb.addMessage(AncientRPG.brand2 + "You don't have a class");
            pb.addMessage("Global spells:");
            for (Entry<String, Spell> s : AncientRPGClass.globalSpells.entrySet()) {
                String message;
                if (s.getValue().buff) {
                    continue;
                }
                if (s.getValue().hidden) {
                    continue;
                }
                if (ClassCastCommand.canCast(pd, p, s.getKey())) {
                    message = ChatColor.GREEN + s.getValue().name;
                } else {
                    if (AncientRPGExperience.isWorldEnabled(p) && AncientRPGExperience.isEnabled() && pd.getXpSystem().level < s.getValue().minlevel) {
                        message = ChatColor.RED + "[lvl " + s.getValue().minlevel + "] " + s.getValue().name;
                    } else {
                        message = ChatColor.RED + s.getValue().name;
                    }
                }
                if (s.getValue().shortdescription != null && s.getValue().shortdescription.length() > 0) {
                    message += ChatColor.WHITE + " - " + ChatColor.GOLD + s.getValue().shortdescription;
                }
                pb.addMessage(message);
            }
        }
        pb.printPage(p, page, 8);
    }
}
