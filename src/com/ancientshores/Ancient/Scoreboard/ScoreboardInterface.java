package com.ancientshores.Ancient.Scoreboard;

public class ScoreboardInterface {
    /*
    public static boolean scoreboardenabled = false;
    public static void showScoreboard(Player p)
	{
		ScoreboardAPI api = ScoreboardAPI.getInstance();
		String scoreboardname = "Ancient"+p.getName();
		if(scoreboardname.length() > 16)
			scoreboardname = scoreboardname.substring(0, 16);
		Scoreboard scoreboard = api.createScoreboard(scoreboardname, 3);
		scoreboard.setType(Scoreboard.Type.SIDEBAR);
		scoreboard.setScoreboardName("Ancient");
		if (AncientExperience.isEnabled() && AncientExperience.isWorldEnabled(p))
		{
			int level = PlayerData.getPlayerData(p.getName()).getXpSystem().level;
			scoreboard.setItem("Level", level);
		}
		if (DamageConverter.isEnabled(p.getWorld()))
		{
			int hp = PlayerData.getPlayerData(p.getName()).getHpsystem().hp;
			scoreboard.setItem("Health", hp);
		}
		if (true)
		{
			int mana = PlayerData.getPlayerData(p.getName()).getManasystem().curmana;
			scoreboard.setItem("Mana", mana);
		}
		scoreboard.showToPlayer(p);
		scoreboardenabled = true;
	}

	public static Scoreboard getPlayersScoreboard(Player p)
	{
		String scoreboardname = "Ancient"+p.getName();
		if(scoreboardname.length() > 16)
			scoreboardname = scoreboardname.substring(0, 16);
		for (Scoreboard s : ScoreboardAPI.getInstance().getScoreboards()) {
			if (s.getName().equalsIgnoreCase(scoreboardname)) {
				return s;
			}
		}
		return null;
	}
	*/
}