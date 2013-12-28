package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CommandPlayer extends Thread {
    public static final HashSet<Entity> alreadyDead = new HashSet<Entity>();
    static Lock lockVar = new ReentrantLock();

    public CommandPlayer() {
    }

    public static void scheduleSpell(final Spell p, final Player mPlayer, final Event e, final Player buffPlayer) {
        p.execute(mPlayer, buffPlayer, e);
    }

    public static void scheduleSpell(final Spell p, final Player mPlayer) {
        p.execute(mPlayer, mPlayer, null);
    }
}