package com.ancientshores.Ancient.Spells.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;

public class SpellBindCommand {
    public static void bindCommand(String[] args, Player mPlayer) {
        ItemStack is = mPlayer.getItemInHand();
        BindingData bd = new BindingData(is);
        if (args.length == 3) {
            try {
                String[] s = args[2].split(":");
                int matid = Integer.parseInt(s[0]);
                byte data = 0;
                if (s.length == 2) {
                    data = Byte.parseByte(s[1]);
                }
                bd = new BindingData(matid, data);
            } catch (Exception e) {
                mPlayer.sendMessage(Ancient.ChatBrand + "Expected Integer but received string");
                return;
            }
        }
        if (!mPlayer.hasPermission(AncientClass.cNodeBind)) {
            mPlayer.sendMessage(Ancient.ChatBrand + "You don't have permissions to bind a spell");
            return;
        }
        if (args.length >= 2) {
            bind(mPlayer, args[1], bd);
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + "Missing spell name");
        }
    }

    public static void bindSlotCommand(String[] args, Player mPlayer) {
        int slot = mPlayer.getInventory().getHeldItemSlot();
        if (!mPlayer.hasPermission(AncientClass.cNodeBind)) {
            mPlayer.sendMessage(Ancient.ChatBrand + "You don't have permissions to bind a spell");
            return;
        }
        if (args.length >= 2) {
            bindSlot(mPlayer, args[1], slot);
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + "Missing spell name");
        }
    }

    public static void bindSlot(Player p, String spell, int slot) {
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        if (AncientClass.canBind(spell, pd, p)) {
            pd.getSlotbinds().put(slot, spell.toLowerCase());
            p.sendMessage(Ancient.ChatBrand + "Successfully bound " + spell + " to the slot");
        } else {
            p.sendMessage(Ancient.ChatBrand + "Error in binding the spell");
        }
    }

    @SuppressWarnings("deprecation")
	public static void bind(Player p, String spell, BindingData bd) {
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        if (AncientClass.canBind(spell, pd, p)) {
            pd.getBindings().put(bd, spell.toLowerCase());
            p.sendMessage(Ancient.ChatBrand + "Successfully bound " + spell + " to " + Material.getMaterial(bd.id) + ".");
        } else {
            p.sendMessage(Ancient.ChatBrand + "Error in binding the spell");
        }
    }
}