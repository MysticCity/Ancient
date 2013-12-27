package com.ancientshores.AncientRPG.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class HelpCommand {
    public static HelpList helpList;

    public static void processHelp(CommandSender sender, String[] args) {
        if (helpList == null) {
            initHelp();
        }
        if (!(sender instanceof Player)) {
            return;
        }
        Player mPlayer = (Player) sender;
        int site = 1;
        if (args.length == 2) {
            try {
                site = Integer.parseInt(args[1]);
            } catch (Exception ignored) {

            }
        }
        helpList.printToPlayer(mPlayer, site);

    }

    public static void initHelp() {
        if (helpList == null) {
            helpList = new HelpList(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "pluginhelp.txt", "pluginhelp.txt");
        }
    }
}
