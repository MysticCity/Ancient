package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.AncientRPG.Util.GlobalMethods;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SpellInformationObject {
    public Player nearestPlayer;
    public Entity nearestEntity;
    public Player[] nearestPlayers;
    public Player[] partyMembers;
    public Player[] hostilePlayers;
    public Entity[] nearestEntities;
    public Entity[] hostileEntities;
    public Location blockInSight;
    public Player buffcaster;
    public int skipedCommands = 0;
    public int waittime = 0;
    public Player nearestPlayerInSight;
    public Entity nearestEntityInSight;
    public Event mEvent;
    public boolean canceled;
    public Command command;
    public boolean working;
    public boolean finished = false;
    public final HashMap<String, Variable> variables = new HashMap<String, Variable>();
    public Spell mSpell;
    public String[] chatmessage;

    Entity returnEntity;
    boolean finishede = false;

    public Collection<Entity> removeEntitiesInSpellfreeZone(Collection<Entity> entityset) {
        if (mSpell.ignorespellfreezones) {
            return entityset;
        }
        HashSet<Entity> removeSet = new HashSet<Entity>();
        for (Entity e : entityset) {
            if (!(e instanceof LivingEntity)) {
                removeSet.add(e);
                continue;
            }
            if (AddSpellFreeZoneCommand.isLocationInSpellfreeZone(e.getLocation())) {
                removeSet.add(e);
            }
        }
        entityset.removeAll(removeSet);
        return entityset;
    }


    public Collection<Block> removeBlocksInSpellfreeZone(Collection<Block> blockset) {
        if (mSpell.ignorespellfreezones) {
            return blockset;
        }
        HashSet<Block> removeSet = new HashSet<Block>();
        for (Block b : blockset) {
            if (AddSpellFreeZoneCommand.isLocationInSpellfreeZone(b.getLocation())) {
                removeSet.add(b);
            }
        }
        blockset.removeAll(removeSet);
        return blockset;
    }


    public Entity getNearestEntity(final Player mPlayer, final int range) {
        Collection<Entity> entityset = removeEntitiesInSpellfreeZone(mPlayer.getNearbyEntities(range, range, range));
        Entity nearestEntity = null;
        double curdif = 100000;
        try {
            for (Entity e : entityset) {
                if (e instanceof Creature || e instanceof Player) {
                    double dif = e.getLocation().distance(mPlayer.getLocation());
                    if (dif < curdif && e != mPlayer && (e instanceof Creature || e instanceof Player)) {
                        curdif = dif;
                        nearestEntity = e;
                    }
                }
            }
            returnEntity = nearestEntity;
        } catch (Exception e) {

        }
        return returnEntity;
    }

    public Entity[] getNearestEntities(final Location loc, final int range, int count) {
        returnEntities = null;
        final Collection<Entity> entityset = removeEntitiesInSpellfreeZone(
                loc.getWorld().getEntities());
        final Entity[] nearestEntity = new Entity[count];
        final HashSet<Entity> alreadyParsed = new HashSet<Entity>();
        for (int i = 0; i < count; i++) {
            double curdif = 100000;
            for (Entity e : entityset) {
                if (e instanceof Creature || e instanceof Player) {
                    double dif = e.getLocation().distance(loc);
                    if (dif < curdif && dif < range && (e instanceof Creature || e instanceof Player) && !alreadyParsed.contains(e)) {
                        curdif = dif;
                        nearestEntity[i] = e;
                    }
                }
            }
            alreadyParsed.add(nearestEntity[i]);
        }
        return GlobalMethods.removeNullArrayCells(nearestEntity);
    }

    Player returnPlayer;
    boolean finishedp = false;

    public Player getNearestPlayer(final Player mPlayer, final int range) {
        finishedp = false;

        Runnable r = new Runnable() {
            public void run() {
                Collection<Entity> entityset = removeEntitiesInSpellfreeZone(
                        mPlayer.getNearbyEntities(range, range, range));
                entityset.addAll(AncientRPGSpellListener.deadPlayer);
                Player nearestPlayer = null;
                double curdif = 100000;
                for (Entity e : entityset) {
                    if (e instanceof Player) {
                        double dif = e.getLocation().distance(mPlayer.getLocation());
                        if (dif <= range && dif < curdif && e != mPlayer) {
                            curdif = dif;
                            nearestPlayer = (Player) e;
                        }
                    }
                }
                returnPlayer = nearestPlayer;
                finishedp = true;
            }
        };
        if (Thread.currentThread() != AncientRPG.plugin.bukkitThread) {
            AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, r);
        } else {
            r.run();
        }
        while (!finishedp) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return returnPlayer;
    }

    Entity returnEntityInSight;
    boolean finishednes = false;

    public Entity getNearestEntityInSight(final LivingEntity le, final int range) {
        HashSet<Byte> list = new HashSet<Byte>();
        list.add((byte) 9);
        list.add((byte) 8);
        list.add((byte) Material.AIR.getId());
        list.add((byte) Material.GRASS.getId());
        list.add((byte) Material.VINE.getId());
        list.add((byte) Material.SNOW.getId());
        list.add((byte) Material.ICE.getId());
        List<Block> blocksInSight = le.getLineOfSight(list, range);
        Entity nearestEntity = null;
        double curdif = 100000;
        Collection<Entity> entityset = removeEntitiesInSpellfreeZone(le.getNearbyEntities(range, range, range));
        for (Block b : blocksInSight) {
            if (b.getLocation().distance(le.getLocation()) > 2.4f) {
                for (Entity e : entityset) {
                    double dif = Math.abs(e.getLocation().distance(b.getLocation()));
                    if (dif < 2.5f) {
                        if (dif < curdif && e != le && (e instanceof Creature || e instanceof Player || e instanceof Ghast || e instanceof Slime)) {
                            curdif = dif;
                            nearestEntity = e;
                        }
                    }
                }
            }
        }
        returnEntityInSight = nearestEntity;
        return returnEntityInSight;
    }

    Player returnPlayerInSight;

    public Player getNearestPlayerInSight(final Player mPlayer, final int range) {
        returnPlayerInSight = null;
        HashSet<Byte> list = new HashSet<Byte>();
        list.add((byte) 9);
        list.add((byte) 8);
        list.add((byte) Material.AIR.getId());
        list.add((byte) Material.GRASS.getId());
        list.add((byte) Material.VINE.getId());
        list.add((byte) Material.SNOW.getId());
        list.add((byte) Material.ICE.getId());
        List<Block> blocksInSight = mPlayer.getLineOfSight(list, range);
        Player nearestPlayer = null;
        Collection<Entity> entityset = removeEntitiesInSpellfreeZone(mPlayer.getNearbyEntities(range, range, range));
        entityset.addAll(AncientRPGSpellListener.deadPlayer);
        for (Block b : blocksInSight) {
            double curdif = 100000;
            for (Entity e : entityset) {
                if (b.getLocation().distance(mPlayer.getLocation()) > 2.4f) {
                    if (e instanceof Player) {
                        double dif = Math.abs(e.getLocation().distance(b.getLocation()));
                        if (dif < 2.5f) {
                            if (dif <= range && dif < curdif && e != mPlayer) {
                                curdif = dif;
                                nearestPlayer = (Player) e;
                            }
                        }
                    }
                }
            }
        }
        returnPlayerInSight = nearestPlayer;
        return returnPlayerInSight;
    }

    Location returnBlock;
    boolean finshedbis = false;

    public Location getBlockInSight(final Player mPlayer, final int range) {
        returnPlayerInSight = null;
        finshedbis = false;
        HashSet<Byte> list = new HashSet<Byte>();
        list.add((byte) 9);
        list.add((byte) 8);
        list.add((byte) Material.AIR.getId());
        list.add((byte) Material.GRASS.getId());
        list.add((byte) Material.VINE.getId());
        list.add((byte) Material.SNOW.getId());
        Collection<Block> blocksInSight = removeBlocksInSpellfreeZone(mPlayer.getLastTwoTargetBlocks(list, range));
        for (Block b : blocksInSight) {
            if (b.getType() != Material.AIR) {
                returnBlock = b.getLocation();
                finshedbis = true;
                return returnBlock;
            }
        }
        returnBlock = null;
        finshedbis = true;
        return returnBlock;
    }

    Player[] returnPlayers;

    public Player[] getNearestPlayers(final Player mPlayer, final int range, final int count) {
        returnPlayers = null;
        final Player[] nearestPlayer = new Player[count];
        final Collection<Entity> entityset = removeEntitiesInSpellfreeZone(
                mPlayer.getNearbyEntities(range, range, range));
        HashSet<Entity> alreadyParsed = new HashSet<Entity>();
        double curdif = 100000;
        for (int i = 0; i < count; i++) {
            for (Entity e : entityset) {
                if (e instanceof Player) {
                    double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(
                            e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(
                            e.getLocation().getZ() - mPlayer.getLocation().getZ()));
                    if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e)) {
                        curdif = dif;
                        nearestPlayer[i] = (Player) e;
                        alreadyParsed.add(e);
                    }
                }
            }
            alreadyParsed.add(nearestPlayer[i]);
        }
        returnPlayers = nearestPlayer;
        if (returnPlayers == null) {
            returnPlayers = new Player[0];
        }
        returnPlayers = GlobalMethods.removeNullArrayCells(returnPlayers);
        return returnPlayers;
    }

    Player[] returnEnemyPlayers;

    public Player[] getNearestHostilePlayers(final Player mPlayer, final int range, final int count) {
        returnEnemyPlayers = null;
        final Player[] nearestPlayer = new Player[count];
        final Collection<Entity> entityset = removeEntitiesInSpellfreeZone(
                mPlayer.getNearbyEntities(range, range, range));
        HashSet<Entity> alreadyParsed = new HashSet<Entity>();
        double curdif = 100000;
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        HashSet<Player> partyMembers = new HashSet<Player>();
        if (mParty != null) {
            partyMembers.addAll(mParty.Member);
        }
        for (int i = 0; i < count; i++) {
            for (Entity e : entityset) {
                if (e instanceof Player) {
                    double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(
                            e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(
                            e.getLocation().getZ() - mPlayer.getLocation().getZ()));
                    if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e) && !partyMembers.contains(e)) {
                        curdif = dif;
                        nearestPlayer[i] = (Player) e;
                        alreadyParsed.add(e);
                    }
                }
            }
            alreadyParsed.add(nearestPlayer[i]);
        }
        returnEnemyPlayers = nearestPlayer;
        if (returnEnemyPlayers == null) {
            returnEnemyPlayers = new Player[0];
        }
        returnEnemyPlayers = GlobalMethods.removeNullArrayCells(returnEnemyPlayers);
        return returnEnemyPlayers;
    }

    Entity[] returnEntities;

    public Entity[] getNearestEntities(final Player mPlayer, final int range, final int count) {
        returnEntities = null;
        final Collection<Entity> entityset = removeEntitiesInSpellfreeZone(
                mPlayer.getNearbyEntities(range, range, range));
        final Entity[] nearestEntity = new Entity[count];
        final HashSet<Entity> alreadyParsed = new HashSet<Entity>();
        for (int i = 0; i < count; i++) {
            double curdif = 100000;
            for (Entity e : entityset) {
                if (e instanceof Creature || e instanceof Player) {
                    double dif = e.getLocation().distance(mPlayer.getLocation());
                    if (dif < curdif && e != mPlayer && (e instanceof Creature || e instanceof Player) && !alreadyParsed.contains(
                            e)) {
                        curdif = dif;
                        nearestEntity[i] = e;
                    }
                }
            }
            alreadyParsed.add(nearestEntity[i]);
        }
        returnEntities = nearestEntity;
        if (returnEntities == null) {
            returnEntities = new Entity[0];
        }
        returnEntities = GlobalMethods.removeNullArrayCells(returnEntities);
        return returnEntities;
    }

    Entity[] returnEnemyEntities;

    public Entity[] getNearestHostileEntities(final Player mPlayer, final int range, final int count) {
        returnEnemyEntities = null;
        final List<Entity> entityset = mPlayer.getNearbyEntities(range, range, range);
        final Entity[] nearestEntity = new Entity[count];
        final HashSet<Entity> alreadyParsed = new HashSet<Entity>();
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        HashSet<Entity> partyMembers = new HashSet<Entity>();
        if (mParty != null) {
            partyMembers.addAll(mParty.Member);
        }
        partyMembers = (HashSet<Entity>) removeEntitiesInSpellfreeZone(partyMembers);
        for (int i = 0; i < count; i++) {
            double curdif = 100000;
            for (Entity e : entityset) {
                if (e instanceof Creature || e instanceof Player) {
                    double dif = e.getLocation().distance(mPlayer.getLocation());
                    if (dif < curdif && e != mPlayer && (e instanceof Creature || e instanceof Player) && !alreadyParsed.contains(
                            e) && !partyMembers.contains(e)) {
                        curdif = dif;
                        nearestEntity[i] = e;
                    }
                }
            }
            alreadyParsed.add(nearestEntity[i]);
        }
        returnEnemyEntities = nearestEntity;
        if (returnEnemyEntities == null) {
            returnEnemyEntities = new Entity[0];
        }
        returnEnemyEntities = GlobalMethods.removeNullArrayCells(returnEnemyEntities);
        return returnEnemyEntities;
    }

    Player[] partymembers;

    public Player[] getPartyMembers(final Player mPlayer, final int range) {
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        int i = 0;
        if (mParty == null) {
            partymembers = new Player[0];
        } else {
            Player[] buffer = new Player[mParty.Member.size()];
            for (Player p : mParty.Member) {
                buffer[i] = p;
                i++;
            }
            int rightDistance = 0;
            for (Player p : mParty.Member) {
                if (p.getLocation().distance(mPlayer.getLocation()) <= range && p != mPlayer) {
                    rightDistance++;
                }
            }
            Player[] buffer2 = new Player[rightDistance];
            int x = 0;
            for (Player p : mParty.Member) {
                if (p.getLocation().distance(mPlayer.getLocation()) <= range && p != mPlayer) {
                    buffer2[x] = p;
                    x++;
                }
            }
            partymembers = buffer2;
        }
        partymembers = GlobalMethods.removeNullArrayCells(partymembers);
        return partymembers;
    }

    public int parseVariable(Player mPlayer, String var) {
        return Variables.getParameterIntByString(mPlayer, var, this.mSpell.newConfigFile);
    }
}