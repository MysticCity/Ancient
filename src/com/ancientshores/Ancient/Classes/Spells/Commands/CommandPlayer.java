package com.ancientshores.Ancient.Classes.Spells.Commands;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.event.Event;

import com.ancientshores.Ancient.Classes.Spells.Spell;

public class CommandPlayer extends Thread {
    public static final HashSet<UUID> alreadyDead = new HashSet<UUID>();
    static Lock lockVar = new ReentrantLock();

    public CommandPlayer() {
    }

    public static void scheduleSpell(Spell p, UUID uuidPlayer, Event e, UUID uuidBuffPlayer) {
        p.execute(uuidPlayer, uuidBuffPlayer, e);
    }

    public static void scheduleSpell(final Spell p, final UUID uuidPlayer) {
        p.execute(uuidPlayer, uuidPlayer, null);
    }
}