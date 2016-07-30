package com.ancientshores.Ancient.Classes.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.BindingData;

public class ClassUnbindCommand {
    @SuppressWarnings("deprecation")
	public static void unbindCommand(String[] args, Player p) {
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        Material mat = null;
        byte b = 0;
        if (args.length == 1) {
            mat = p.getItemInHand().getType();
            b = p.getItemInHand().getData().getData();
        } else if (args.length == 2) {
            try {
                int matid = Integer.parseInt(args[1]);
                mat = Material.getMaterial(matid);
                if (mat == null) {
                    p.sendMessage(Ancient.ChatBrand + "Material not found");
                    return;
                }
            } catch (Exception e) {
                p.sendMessage(Ancient.ChatBrand + "Expected Integer but received string");
            }
        } else {
            return;
        }
        unbind(pd, p, mat, b);
    }

    @SuppressWarnings("deprecation")
	public static void unbind(PlayerData pd, Player p, Material m, byte data) {
        BindingData tbd = new BindingData(m.getId(), data);
        BindingData removedata = null;
        for (BindingData bd : pd.getBindings().keySet()) {
            if (bd.data == tbd.data && bd.id == tbd.id) {
                removedata = bd;
            }
        }
        if (removedata != null) {
            pd.getBindings().remove(removedata);
        }
        p.sendMessage(Ancient.ChatBrand + "Successfully unbound " + m.name() + ".");
    }
}