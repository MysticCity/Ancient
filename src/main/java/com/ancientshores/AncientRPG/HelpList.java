package com.ancientshores.AncientRPG;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;

public class HelpList {
    final LinkedList<String> messageList = new LinkedList<String>();
    File file;

    public HelpList(String path, String ressourcePath) {
        createFile(path, ressourcePath);
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = bf.readLine();
            while (line != null && !line.equals("")) {
                messageList.addLast(line);
                line = bf.readLine();
            }
            bf.close();
        } catch (FileNotFoundException e) {
            AncientRPG.plugin.getLogger().log(Level.SEVERE, "Help file " + path + " not found");
        } catch (IOException e) {
            AncientRPG.plugin.getLogger().log(Level.SEVERE, "Unable to read help file " + path);
        }
    }

    public void createFile(String path, String ressourcePath) {
        File f = new File(path);
        if (!f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(AncientRPG.plugin.getResource(ressourcePath), "UTF-8"));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
                String s = br.readLine();
                String ges = "";
                while (s != null) {
                    ges += s + "\n";
                    s = br.readLine();
                }
                out.write(ges);
                br.close();
                out.close();
            } catch (IOException e) {
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to write help file " + f.getName());
            }
        }
        this.file = f;
    }

    public static String replaceChatColor(String message) {
        message = message.replace("{GREEN}", ChatColor.GREEN.toString());
        message = message.replace("{BLACK}", ChatColor.BLACK.toString());
        message = message.replace("{DARK_BLUE}", ChatColor.DARK_BLUE.toString());
        message = message.replace("{DARK_GREEN}", ChatColor.DARK_GREEN.toString());
        message = message.replace("{DARK_AQUA}", ChatColor.DARK_AQUA.toString());
        message = message.replace("{DARK_RED}", ChatColor.DARK_RED.toString());
        message = message.replace("{DARK_PURPLE}", ChatColor.DARK_PURPLE.toString());
        message = message.replace("{GOLD}", ChatColor.GOLD.toString());
        message = message.replace("{GRAY}", ChatColor.GRAY.toString());
        message = message.replace("{GREY}", ChatColor.GRAY.toString());
        message = message.replace("{DARK_GRAY}", ChatColor.DARK_GRAY.toString());
        message = message.replace("{DARK_GREY}", ChatColor.DARK_GRAY.toString());
        message = message.replace("{BLUE}", ChatColor.BLUE.toString());
        message = message.replace("{AQUA}", ChatColor.AQUA.toString());
        message = message.replace("{RED}", ChatColor.RED.toString());
        message = message.replace("{LIGHT_PURPLE}", ChatColor.LIGHT_PURPLE.toString());
        message = message.replace("{YELLOW}", ChatColor.YELLOW.toString());
        message = message.replace("{WHITE}", ChatColor.WHITE.toString());
        message = message.replace("{MAGIC}", ChatColor.MAGIC.toString());
        return message;
    }

    public void printToPlayer(CommandSender commandSender, int page) {
        int messagesperpage = 6;
        page -= 1;
        int pagecount = messageList.size() / messagesperpage + 1;
        if (page * messagesperpage >= messageList.size() || page <= -1) {
            commandSender.sendMessage(ChatColor.RED + "This page does not exist");
            return;
        }
        commandSender.sendMessage(ChatColor.DARK_BLUE + "Displaying page " + (page + 1) + " of " + pagecount);
        commandSender.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
        for (int i = page * messagesperpage; i < page * messagesperpage + messagesperpage; i++) {
            if (i >= messageList.size()) {
                break;
            }
            commandSender.sendMessage(replaceChatColor(messageList.get(i)));
        }
        commandSender.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
    }
}