package com.ancientshores.AncientRPG.Party;

import java.util.Timer;
import java.util.TimerTask;

public class QuitTimer extends Timer{
	public QuitTimer(final String playerName, final AncientRPGParty mParty){
		this.schedule(new TimerTask(){
			@Override
			public void run(){
				mParty.removeByName(playerName, false);
				AncientRPGParty.disconnects.remove(playerName);
			}
		}, 60000);
	}
}
