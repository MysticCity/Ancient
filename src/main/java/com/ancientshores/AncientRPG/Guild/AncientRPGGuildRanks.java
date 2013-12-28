package com.ancientshores.AncientRPG.Guild;

import org.bukkit.ChatColor;

import java.io.Serializable;

public enum AncientRPGGuildRanks implements Serializable {
    TRIAL("Trial", 0),
    MEMBER("Member", 1),
    OFFICER("Officer", 2),
    CO_LEADER("Co Leader", 3),
    LEADER("Leader", 4);

    private final String guildrank;
    private final int number;

    private AncientRPGGuildRanks(String newguildrank, int newnumber) {
        this.guildrank = newguildrank;
        this.number = newnumber;
    }

    public String getguildrank() {
        return guildrank;
    }

    public int getnumber() {
        return number;
    }

    public static String toString(AncientRPGGuildRanks gr) {
        switch (gr) {
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

    public static AncientRPGGuildRanks getGuildRankByString(String s) {
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

    public static boolean hasInviteRights(AncientRPGGuildRanks gr) {
        return gr == OFFICER || gr == CO_LEADER || gr == LEADER;
    }

    public static boolean hasMotdRights(AncientRPGGuildRanks gr) {
        return gr == CO_LEADER || gr == LEADER;
    }

    public static AncientRPGGuildRanks getRankByString(String rank) {
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

    public static ChatColor getChatColorByRank(AncientRPGGuildRanks gr) {
        switch (gr) {
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