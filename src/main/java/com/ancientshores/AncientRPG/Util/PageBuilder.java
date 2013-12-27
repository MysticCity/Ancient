package com.ancientshores.AncientRPG.Util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class PageBuilder {
    final ArrayList<String> messages = new ArrayList<String>();

    public PageBuilder() {

    }

    public void addMessage(String s) {
        messages.add(s);
    }

    public void printPage(CommandSender p, int page, int messagesperpage) {
        page -= 1;
        int pagecount = messages.size() / messagesperpage + 1;
        if (page * messagesperpage >= messages.size() || page <= -1) {
            p.sendMessage(ChatColor.RED + "This page does not exist");
            return;
        }
        p.sendMessage(ChatColor.DARK_BLUE + "Displaying page " + (page + 1) + " of " + pagecount);
        p.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
        for (int i = page * messagesperpage; i < page * messagesperpage + messagesperpage; i++) {
            if (i >= messages.size()) {
                break;
            }
            p.sendMessage(messages.get(i));
        }
        p.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
    }
}
