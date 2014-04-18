package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class GuildCommandHelp {
    public static HelpList helpList;

    public static void processHelp(CommandSender sender, String[] args) {
        if (helpList == null) {
            initHelp();
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
            File PartyFolder = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Guilds".replace('/', File.separatorChar));
            if (!PartyFolder.exists()) {
                PartyFolder.mkdir();
            }
            helpList = new HelpList(AncientRPG.plugin.getDataFolder().getPath() + File.separator +
                    "Guilds/guildhelp.txt".replace('/', File.separatorChar), "guildhelp.txt");
        }
    }
}