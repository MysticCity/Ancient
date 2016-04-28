package com.ancientshores.Ancient.Guild;

import java.io.Serializable;
import org.bukkit.ChatColor;

public enum AncientGuildRanks
  implements Serializable
{
  TRIAL("Trial", 0),  MEMBER("Member", 1),  OFFICER("Officer", 2),  CO_LEADER("Co Leader", 3),  LEADER("Leader", 4);
  
  private final String guildRank;
  private final int number;
  
  private AncientGuildRanks(String newguildrank, int newnumber)
  {
    this.guildRank = newguildrank;
    this.number = newnumber;
  }
  
  public String getGuildRank()
  {
    return this.guildRank;
  }
  
  public int getNumber()
  {
    return this.number;
  }
  
  public static String toString(AncientGuildRanks guildRank)
  {
    switch (guildRank)
    {
    case TRIAL: 
      return "trial";
    case MEMBER: 
      return "member";
    case OFFICER: 
      return "officer";
    case CO_LEADER: 
      return "coleader";
    case LEADER: 
      return "leader";
    }
    return "";
  }
  
  public static AncientGuildRanks getGuildRankByString(String s)
  {
    if (s.equalsIgnoreCase("trial")) {
      return TRIAL;
    }
    if (s.equalsIgnoreCase("member")) {
      return MEMBER;
    }
    if (s.equalsIgnoreCase("officer")) {
      return OFFICER;
    }
    if (s.equalsIgnoreCase("coleader")) {
      return CO_LEADER;
    }
    if (s.equalsIgnoreCase("leader")) {
      return LEADER;
    }
    return null;
  }
  
  public static boolean hasInviteRights(AncientGuildRanks guildRank)
  {
    return (guildRank == OFFICER) || (guildRank == CO_LEADER) || (guildRank == LEADER);
  }
  
  public static boolean hasMotdRights(AncientGuildRanks guildRank)
  {
    return (guildRank == CO_LEADER) || (guildRank == LEADER);
  }
  
  public static AncientGuildRanks getRankByString(String rank)
  {
    if (rank.equalsIgnoreCase("leader")) {
      return LEADER;
    }
    if (rank.equalsIgnoreCase("coleader")) {
      return CO_LEADER;
    }
    if (rank.equalsIgnoreCase("member")) {
      return MEMBER;
    }
    if (rank.equalsIgnoreCase("trial")) {
      return TRIAL;
    }
    if (rank.equalsIgnoreCase("officer")) {
      return OFFICER;
    }
    return null;
  }
  
  public static ChatColor getChatColorByRank(AncientGuildRanks gr)
  {
    switch (gr)
    {
    case TRIAL: 
      return ChatColor.AQUA;
    case MEMBER: 
      return ChatColor.YELLOW;
    case OFFICER: 
      return ChatColor.WHITE;
    case CO_LEADER: 
      return ChatColor.RED;
    case LEADER: 
      return ChatColor.DARK_RED;
    }
    return ChatColor.GREEN;
  }
}
