package com.ancientshores.Ancient.Classes.Spells;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;

import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.Ancient.Util.GlobalMethods;

public class SpellInformationObject {
	public Entity nearestPlayer;
	public Entity nearestEntity;
	public Entity[] nearestPlayers;
	public Entity[] partyMembers;
	public Entity[] hostilePlayers;
	public Entity[] nearestEntities;
	public Entity[] hostileEntities;
	public Location blockInSight;
	public Entity buffcaster;
	public int skipedCommands = 0;
	public int waittime = 0;
	public Entity nearestPlayerInSight;
	public Entity nearestEntityInSight;
	public Event mEvent;
	public boolean canceled;
	public Command command;
	public boolean working;
	public boolean finished = false;
	public final HashMap<String, Variable> variables = new HashMap<String, Variable>();
	public Spell mSpell;
	public String[] chatmessage;

 
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
	
	public Collection<UUID> removeUUIDsInSpellfreeZone(Collection<UUID> uuidset) {
		if (mSpell.ignorespellfreezones) {
			return uuidset;
		}
		HashSet<UUID> removeSet = new HashSet<UUID>();
		for (UUID uuid : uuidset) {
			Entity e = null;
			for (World w : Bukkit.getWorlds()) {
				for (Entity ent : w.getEntities()) {
					if (ent.getUniqueId().compareTo(uuid) == 0) {
						e = ent;
						break;
					}
				}
			}
			if (e == null ||!(e instanceof LivingEntity) || AddSpellFreeZoneCommand.isLocationInSpellfreeZone(e.getLocation())) {
				removeSet.add(uuid);
			}
		}
		uuidset.removeAll(removeSet);
		return uuidset;
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
		Collection<Entity> nearbyentities = new ArrayList<Entity>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			nearbyentities.add(e);
		}
		
		Collection<Entity> entityset = removeEntitiesInSpellfreeZone(nearbyentities);
		Entity nearestEntity = null;
		double curdif = 100000;
		try {
			for (Entity e : entityset) {
				if (e instanceof Creature || e instanceof Player) {
					double dif = e.getLocation().distance(mPlayer.getLocation());
					if (dif < curdif && e != mPlayer) {
						curdif = dif;
						nearestEntity = e;
					}
				}
			}
			return nearestEntity;
		} catch (Exception ignored) {
		}
		return null;
	}

	public Entity[] getNearestEntities(final Location loc, final int range, int count) {
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : loc.getWorld().getEntities()) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		final Entity[] nearestEntity = new Entity[count];
		final HashSet<Entity> alreadyParsed = new HashSet<Entity>();
		for (int i = 0; i < count; i++) {
			double curdif = 100000;
			for (Entity e : entityset) {
				if (e instanceof Creature || e instanceof Player) {
					double dif = e.getLocation().distance(loc);
					if (dif < curdif && dif < range && !alreadyParsed.contains(e)) {
						curdif = dif;
						nearestEntity[i] = e;
						alreadyParsed.add(nearestEntity[i]);
					}
				}
			}
		}
		
		return GlobalMethods.removeNullArrayCells(nearestEntity);
	}
	public Entity getNearestPlayer(final Player mPlayer, final int range) {
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		entityset.addAll(AncientSpellListener.deadPlayer);
		
		Entity nearestPlayer = null;
		double curdif = Double.MAX_VALUE;
		for (Entity e : entityset) {
			if (e instanceof Player) {
				double dif = e.getLocation().distance(mPlayer.getLocation());
				if (dif <= range && dif < curdif && e != mPlayer) {
					curdif = dif;
					nearestPlayer = e;
				}
			}
		}
		
		return nearestPlayer;
	}

	@SuppressWarnings("deprecation")
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
		
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : le.getNearbyEntities(range, range, range)) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
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
		return nearestEntity;
	}

	@SuppressWarnings("deprecation")
	public Entity getNearestPlayerInSight(final Player mPlayer, final int range) {
		HashSet<Byte> list = new HashSet<Byte>();
		list.add((byte) 9);
		list.add((byte) 8);
		list.add((byte) Material.AIR.getId());
		list.add((byte) Material.GRASS.getId());
		list.add((byte) Material.VINE.getId());
		list.add((byte) Material.SNOW.getId());
		list.add((byte) Material.ICE.getId());
		List<Block> blocksInSight = mPlayer.getLineOfSight(list, range);
		
		Entity nearestPlayer = null;
		
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		entityset.addAll(AncientSpellListener.deadPlayer);
		
		for (Block b : blocksInSight) {
			double curdif = 100000;
			for (Entity e : entityset) {
				if (b.getLocation().distance(mPlayer.getLocation()) > 2.4f) {
					if (e instanceof Player) {
						double dif = Math.abs(e.getLocation().distance(b.getLocation()));
						if (dif < 2.5f) {
							if (dif <= range && dif < curdif && e != mPlayer) {
								curdif = dif;
								nearestPlayer = e;
							}
						}
					}
				}
			}
		}
		return nearestPlayer;
	}

	@SuppressWarnings("deprecation")
	public Location getBlockInSight(final Player mPlayer, final int range) {
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
				return b.getLocation();
			}
		}
		return null;
	}

	public Entity[] getNearestPlayers(final Player mPlayer, final int range, final int count) {
		final Entity[] nearestPlayer = new Entity[count];
		
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		HashSet<Entity> alreadyParsed = new HashSet<Entity>();
		
		double curdif = 100000;
		for (int i = 0; i < count; i++) {
			for (Entity e : entityset) {
				if (e instanceof Player) {
					double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(e.getLocation().getZ() - mPlayer.getLocation().getZ()));
					if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e)) {
						curdif = dif;
						nearestPlayer[i] = e;
						alreadyParsed.add(e);
					}
				}
			}
			alreadyParsed.add(nearestPlayer[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestPlayer);
	}

	public Entity[] getNearestHostilePlayers(final Player mPlayer, final int range, final int count) {
		final Entity[] nearestPlayer = new Entity[count];
		
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		HashSet<Entity> alreadyParsed = new HashSet<Entity>();
		double curdif = 100000;
		AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
		HashSet<UUID> partyMembers = new HashSet<UUID>();
		if (mParty != null) {
			partyMembers.addAll(mParty.getMembers());
		}
		for (int i = 0; i < count; i++) {
			for (Entity e : entityset) {
				if (e instanceof Player) {
					double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(e.getLocation().getZ() - mPlayer.getLocation().getZ()));
					if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e) && !partyMembers.contains(e.getUniqueId())) {
						curdif = dif;
						nearestPlayer[i] = e;
						alreadyParsed.add(e);
					}
				}
			}
			
			alreadyParsed.add(nearestPlayer[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestPlayer);
	}

	public Entity[] getNearestEntities(final Player mPlayer, final int range, final int count) {
		Collection<Entity> entityset = new ArrayList<Entity>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e);
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		final Entity[] nearestEntity = new Entity[count];
		final HashSet<Entity> alreadyParsed = new HashSet<Entity>();
		for (int i = 0; i < count; i++) {
			double curdif = 100000;
			for (Entity e : entityset) {
				if (e instanceof Creature || e instanceof Player) {
					double dif = e.getLocation().distance(mPlayer.getLocation());
					if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e)) {
						curdif = dif;
						nearestEntity[i] = e;
					}
				}
			}
			alreadyParsed.add(nearestEntity[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestEntity);
	}

	public Entity[] getNearestHostileEntities(final Player mPlayer, final int range, final int count) {
		final List<Entity> entityset = mPlayer.getNearbyEntities(range, range, range);
		final Entity[] nearestEntity = new Entity[count];
		final HashSet<Entity> alreadyParsed = new HashSet<Entity>();
		AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
		HashSet<UUID> partyMembers = new HashSet<UUID>();
		if (mParty != null) {
			partyMembers.addAll(mParty.getMembers());
		}
		partyMembers = (HashSet<UUID>) removeUUIDsInSpellfreeZone(partyMembers);
		for (int i = 0; i < count; i++) {
			double curdif = 100000;
			for (Entity e : entityset) {
				if (e instanceof Creature || e instanceof Player) {
					double dif = e.getLocation().distance(mPlayer.getLocation());
					if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e) && !partyMembers.contains(e.getUniqueId())) {
						curdif = dif;
						nearestEntity[i] = e;
					}
				}
			}
			alreadyParsed.add(nearestEntity[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestEntity);
	}

	public Entity[] getPartyMembers(final Player mPlayer, final int range) {
		AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
		if (mParty == null) {
			return new Entity[0];
		} else {
			Collection<Entity> entities = new HashSet<Entity>();
			Collection<UUID> partyMembers = new HashSet<UUID>(mParty.getMembers());
			for (Iterator<UUID> iterator = partyMembers.iterator(); iterator.hasNext(); ) {
				UUID uuid = iterator.next();
				if (uuid == null || Bukkit.getPlayer(uuid) == null || Bukkit.getPlayer(uuid).getLocation().distance(mPlayer.getLocation()) > range)
					entities.add(Bukkit.getPlayer(uuid));
			}
			return GlobalMethods.removeNullArrayCells(entities.toArray(new Entity[entities.size()]));
		}
	}

	public int parseVariable(UUID uuid, String var) {
		return Variables.getParameterIntByString(uuid, var, this.mSpell.newConfigFile);
	}
}