package com.ancient.util.chat;

import org.bukkit.ChatColor;

public class ChatParser
{
  public static String parse(String original)
  {
    String parsed = original;
    
    parsed = parsed.replaceAll("\\&0", ChatColor.BLACK + "");
    parsed = parsed.replaceAll("\\&1", ChatColor.DARK_BLUE + "");
    parsed = parsed.replaceAll("\\&2", ChatColor.DARK_GREEN + "");
    parsed = parsed.replaceAll("\\&3", ChatColor.DARK_AQUA + "");
    parsed = parsed.replaceAll("\\&4", ChatColor.DARK_RED + "");
    parsed = parsed.replaceAll("\\&5", ChatColor.DARK_PURPLE + "");
    parsed = parsed.replaceAll("\\&6", ChatColor.GOLD + "");
    parsed = parsed.replaceAll("\\&7", ChatColor.GRAY + "");
    parsed = parsed.replaceAll("\\&8", ChatColor.DARK_GRAY + "");
    parsed = parsed.replaceAll("\\&9", ChatColor.BLUE + "");
    parsed = parsed.replaceAll("\\&a", ChatColor.GREEN + "");
    parsed = parsed.replaceAll("\\&b", ChatColor.AQUA + "");
    parsed = parsed.replaceAll("\\&c", ChatColor.RED + "");
    parsed = parsed.replaceAll("\\&d", ChatColor.LIGHT_PURPLE + "");
    parsed = parsed.replaceAll("\\&e", ChatColor.YELLOW + "");
    parsed = parsed.replaceAll("\\&f", ChatColor.WHITE + "");
    
    parsed = parsed.replaceAll("\\&k", ChatColor.MAGIC + "");
    parsed = parsed.replaceAll("\\&l", ChatColor.BOLD + "");
    parsed = parsed.replaceAll("\\&m", ChatColor.STRIKETHROUGH + "");
    parsed = parsed.replaceAll("\\&n", ChatColor.UNDERLINE + "");
    parsed = parsed.replaceAll("\\&o", ChatColor.ITALIC + "");
    parsed = parsed.replaceAll("\\&r", ChatColor.RESET + "");
    
    return parsed;
  }
  
  public static String escapeAll(String text)
  {
    text = text.replaceAll("\\.", "\\\\.");
    text = text.replaceAll("\\+", "\\\\+");
    text = text.replaceAll("\\*", "\\\\*");
    text = text.replaceAll("\\^", "\\\\^");
    text = text.replaceAll("\\$", "\\\\\\$");
    
    return text;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\chat\ChatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */