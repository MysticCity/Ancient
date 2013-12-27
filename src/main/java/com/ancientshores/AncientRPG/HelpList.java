package com.ancientshores.AncientRPG;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpList
{
	final LinkedList<String> messageList = new LinkedList<String>();
	File f;

	public HelpList(String path, String ressourcePath)
	{
		createFile(path, ressourcePath);
		try
		{
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			String line = bf.readLine();
			while (line != null && !line.equals(""))
			{
				messageList.addLast(line);
				line = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e)
		{
			AncientRPG.plugin.getLogger().log(Level.SEVERE, "Help file " + path + " not found");
		} catch (IOException e)
		{
			AncientRPG.plugin.getLogger().log(Level.SEVERE, "Unable to read help file " + path);
		}
	}

	public void createFile(String path, String ressourcePath)
	{
		File f = new File(path);
		if (!f.exists())
		{
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(AncientRPG.plugin.getResource(ressourcePath), "UTF-8"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
				String s = br.readLine();
				String ges = "";
				while (s != null)
				{
					ges += s + "\n";
					s = br.readLine();
				}
				out.write(ges);
				br.close();
				out.close();
			} catch (FileNotFoundException e)
			{
				AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to write help file " + f.getName());
			} catch (IOException e)
			{
				AncientRPG.plugin.getLogger().log(Level.SEVERE, "Failed to write help file " + f.getName());
			}
		}
		this.f = f;
	}

	public static String replaceChatColor(String s)
	{
		s = s.replace("{GREEN}", ChatColor.GREEN.toString());
		s = s.replace("{BLACK}", ChatColor.BLACK.toString());
		s = s.replace("{DARK_BLUE}", ChatColor.DARK_BLUE.toString());
		s = s.replace("{DARK_GREEN}", ChatColor.DARK_GREEN.toString());
		s = s.replace("{DARK_AQUA}", ChatColor.DARK_AQUA.toString());
		s = s.replace("{DARK_RED}", ChatColor.DARK_RED.toString());
		s = s.replace("{DARK_PURPLE}", ChatColor.DARK_PURPLE.toString());
		s = s.replace("{GOLD}", ChatColor.GOLD.toString());
		s = s.replace("{GRAY}", ChatColor.GRAY.toString());
		s = s.replace("{GREY}", ChatColor.GRAY.toString());
		s = s.replace("{DARK_GRAY}", ChatColor.DARK_GRAY.toString());
		s = s.replace("{DARK_GREY}", ChatColor.DARK_GRAY.toString());
		s = s.replace("{BLUE}", ChatColor.BLUE.toString());
		s = s.replace("{AQUA}", ChatColor.AQUA.toString());
		s = s.replace("{RED}", ChatColor.RED.toString());
		s = s.replace("{LIGHT_PURPLE}", ChatColor.LIGHT_PURPLE.toString());
		s = s.replace("{YELLOW}", ChatColor.YELLOW.toString());
		s = s.replace("{WHITE}", ChatColor.WHITE.toString());
		s = s.replace("{MAGIC}", ChatColor.MAGIC.toString());
		return s;
	}

	public void printToPlayer(CommandSender mPlayer, int page)
	{
		int messagesperpage = 6;
		page -= 1;
		int pagecount = messageList.size() / messagesperpage + 1;
		if (page * messagesperpage >= messageList.size() || page <= -1)
		{
			mPlayer.sendMessage(ChatColor.RED + "This page does not exist");
			return;
		}
		mPlayer.sendMessage(ChatColor.DARK_BLUE + "Displaying page " + (page + 1) + " of " + pagecount);
		mPlayer.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
		for (int i = page * messagesperpage; i < page * messagesperpage + messagesperpage; i++)
		{
			if (i >= messageList.size())
				break;
			mPlayer.sendMessage(replaceChatColor(messageList.get(i)));
		}
		mPlayer.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
		/*
		 * if (site * 8 > messageList.size() && site < 1)
		 * mPlayer.sendMessage("This site does not exist"); else { int start =
		 * (site - 1) * 8; mPlayer.sendMessage("Help:");
		 * mPlayer.sendMessage(ChatColor.GRAY +
		 * "--------------------------------------------"); for (int i = start;
		 * i < start + 8; i++) { if(this.messageList.size() > i && i >= 0) {
		 * String line = messageList.get(i); line = replaceChatColor(line);
		 * mPlayer.sendMessage(line); } } mPlayer.sendMessage(ChatColor.GRAY +
		 * "--------------------------------------------");
		 * mPlayer.sendMessage(ChatColor.BLUE + "displayed site: " +
		 * ChatColor.RED + site); }
		 */
	}
}
