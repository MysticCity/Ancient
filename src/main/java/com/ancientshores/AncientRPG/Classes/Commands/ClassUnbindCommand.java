package com.ancientshores.AncientRPG.Classes.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.BindingData;

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
                    p.sendMessage(AncientRPG.brand2 + "Material not found");
                    return;
                }
            } catch (Exception e) {
                p.sendMessage(AncientRPG.brand2 + "Expected Integer but received string");
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
        p.sendMessage(AncientRPG.brand2 + "Successfully unbound " + m.name() + ".");
    }
}