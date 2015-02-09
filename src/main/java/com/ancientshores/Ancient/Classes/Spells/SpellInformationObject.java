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
	public UUID nearestPlayer;
	public UUID nearestEntity;
	public UUID[] nearestPlayers;
	public UUID[] partyMembers;
	public UUID[] hostilePlayers;
	public UUID[] nearestEntities;
	public UUID[] hostileEntities;
	public Location blockInSight;
	public UUID buffcaster;
	public int skipedCommands = 0;
	public int waittime = 0;
	public UUID nearestPlayerInSight;
	public UUID nearestEntityInSight;
	public Event mEvent;
	public boolean canceled;
	public Command command;
	public boolean working;
	public boolean finished = false;
	public final HashMap<String, Variable> variables = new HashMap<String, Variable>();
	public Spell mSpell;
	public String[] chatmessage;

 
	public Collection<UUID> removeEntitiesInSpellfreeZone(Collection<UUID> uuidset) {
		if (mSpell.ignorespellfreezones) {
			return uuidset;
		}
		HashSet<UUID> removeSet = new HashSet<UUID>();
		for (UUID uuid : uuidset) {
			for (World w : Bukkit.getWorlds()) {
				for (Entity e : w.getEntities()) {
					if (e.getUniqueId().compareTo(uuid) == 0) {
						if (!(e instanceof LivingEntity)) {
							removeSet.add(uuid);
							continue;
						}
						if (AddSpellFreeZoneCommand.isLocationInSpellfreeZone(e.getLocation())) {
							removeSet.add(uuid);
						}
					}	
				}
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

	public UUID getNearestEntity(final Player mPlayer, final int range) {
		Collection<UUID> nearbyuuids = new ArrayList<UUID>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			nearbyuuids.add(e.getUniqueId());
		}
		
		Collection<UUID> uuidset = removeEntitiesInSpellfreeZone(nearbyuuids);
		UUID nearestEntity = null;
		double curdif = 100000;
		try {
			for (UUID uuid : uuidset) {
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(uuid) != 0) {
							break; 
						}
						if (e instanceof Creature || e instanceof Player) {
							double dif = e.getLocation().distance(mPlayer.getLocation());
							if (dif < curdif && e != mPlayer) {
								curdif = dif;
								nearestEntity = uuid;
							}
						}
					}
				}
			}
		return nearestEntity;
		} catch (Exception ignored) {
		}
		return null;
	}

	public UUID[] getNearestEntities(final Location loc, final int range, int count) {
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : loc.getWorld().getEntities()) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		final UUID[] nearestEntity = new UUID[count];
		final HashSet<UUID> alreadyParsed = new HashSet<UUID>();
		for (int i = 0; i < count; i++) {
			double curdif = 100000;
			for (UUID uuid : entityset) {
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(uuid) != 0) {
							continue;
						}
						if (e instanceof Creature || e instanceof Player) {
							double dif = e.getLocation().distance(loc);
							if (dif < curdif && dif < range && !alreadyParsed.contains(e.getUniqueId())) {
								curdif = dif;
								nearestEntity[i] = e.getUniqueId();
								alreadyParsed.add(nearestEntity[i]);
							}
						}
					}
				}
				
			}
		   
		}
		return GlobalMethods.removeNullArrayCells(nearestEntity);
	}
	public UUID getNearestPlayer(final Player mPlayer, final int range) {
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		entityset.addAll(AncientSpellListener.deadPlayer);
		UUID nearestPlayer = null;
		double curdif = Double.MAX_VALUE;
		for (UUID uuid : entityset) {
			for (World w : Bukkit.getWorlds()) {
				for (Entity e : w.getEntities()) {
					if (e.getUniqueId().compareTo(uuid) != 0) {
						continue;
					}
					if (e instanceof Player) {
						double dif = e.getLocation().distance(mPlayer.getLocation());
						if (dif <= range && dif < curdif && e != mPlayer) {
							curdif = dif;
							nearestPlayer = e.getUniqueId();
						}
					}
				}
			}
		}
		return nearestPlayer;
	}

	@SuppressWarnings("deprecation")
	public UUID getNearestEntityInSight(final LivingEntity le, final int range) {
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
		
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : le.getNearbyEntities(range, range, range)) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		for (Block b : blocksInSight) {
			if (b.getLocation().distance(le.getLocation()) > 2.4f) {
				for (UUID uuid : entityset) {
					for (World w : Bukkit.getWorlds()) {
						for (Entity e : w.getEntities()) {
							if (e.getUniqueId().compareTo(uuid) != 0) {
								continue;
							}
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
			}
		}
		return nearestEntity.getUniqueId();
	}

	@SuppressWarnings("deprecation")
	public UUID getNearestPlayerInSight(final Player mPlayer, final int range) {
		HashSet<Byte> list = new HashSet<Byte>();
		list.add((byte) 9);
		list.add((byte) 8);
		list.add((byte) Material.AIR.getId());
		list.add((byte) Material.GRASS.getId());
		list.add((byte) Material.VINE.getId());
		list.add((byte) Material.SNOW.getId());
		list.add((byte) Material.ICE.getId());
		List<Block> blocksInSight = mPlayer.getLineOfSight(list, range);
		UUID nearestPlayer = null;
		
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		entityset.addAll(AncientSpellListener.deadPlayer);
		
		for (Block b : blocksInSight) {
			double curdif = 100000;
			for (UUID uuid : entityset) {
				if (b.getLocation().distance(mPlayer.getLocation()) > 2.4f) {
					for (World w : Bukkit.getWorlds()) {
						for (Entity e : w.getEntities()) {
							if (e.getUniqueId().compareTo(uuid) != 0) {
								continue;
							}
							if (e instanceof Player) {
								double dif = Math.abs(e.getLocation().distance(b.getLocation()));
								if (dif < 2.5f) {
									if (dif <= range && dif < curdif && e != mPlayer) {
										curdif = dif;
										nearestPlayer = e.getUniqueId();
									}
								}
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

	public UUID[] getNearestPlayers(final Player mPlayer, final int range, final int count) {
		final UUID[] nearestPlayer = new UUID[count];
		
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		HashSet<UUID> alreadyParsed = new HashSet<UUID>();
		
		double curdif = 100000;
		for (int i = 0; i < count; i++) {
			for (UUID uuid : entityset) {
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(uuid) != 0) {
							continue;
						}
						if (e instanceof Player) {
							double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(e.getLocation().getZ() - mPlayer.getLocation().getZ()));
							if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e.getUniqueId())) {
								curdif = dif;
								nearestPlayer[i] = e.getUniqueId();
								alreadyParsed.add(e.getUniqueId());
							}
						}
					}
				}
			}
			alreadyParsed.add(nearestPlayer[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestPlayer);
	}

	public UUID[] getNearestHostilePlayers(final Player mPlayer, final int range, final int count) {
		final UUID[] nearestPlayer = new UUID[count];
		
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		HashSet<UUID> alreadyParsed = new HashSet<UUID>();
		double curdif = 100000;
		AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
		HashSet<UUID> partyMembers = new HashSet<UUID>();
		if (mParty != null) {
			partyMembers.addAll(mParty.getMembers());
		}
		for (int i = 0; i < count; i++) {
			for (UUID uuid : entityset) {
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(uuid) != 0) {
							continue;
						}
						if (e instanceof Player) {
							double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(e.getLocation().getZ() - mPlayer.getLocation().getZ()));
							if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e.getUniqueId()) && !partyMembers.contains(e.getUniqueId())) {
								curdif = dif;
								nearestPlayer[i] = e.getUniqueId();
								alreadyParsed.add(e.getUniqueId());
							}
						}
					}
				}
			}
			alreadyParsed.add(nearestPlayer[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestPlayer);
	}

	public UUID[] getNearestEntities(final Player mPlayer, final int range, final int count) {
		Collection<UUID> entityset = new ArrayList<UUID>();
		for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
			entityset.add(e.getUniqueId());
		}
		entityset = removeEntitiesInSpellfreeZone(entityset);
		
		final UUID[] nearestEntity = new UUID[count];
		final HashSet<UUID> alreadyParsed = new HashSet<UUID>();
		for (int i = 0; i < count; i++) {
			double curdif = 100000;
			for (UUID uuid : entityset) {
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(uuid) != 0) {
							continue;
						}
						if (e instanceof Creature || e instanceof Player) {
							double dif = e.getLocation().distance(mPlayer.getLocation());
							if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e.getUniqueId())) {
								curdif = dif;
								nearestEntity[i] = e.getUniqueId();
							}
						}
					}
				}
			}
			alreadyParsed.add(nearestEntity[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestEntity);
	}

	public UUID[] getNearestHostileEntities(final Player mPlayer, final int range, final int count) {
		final List<Entity> entityset = mPlayer.getNearbyEntities(range, range, range);
		final UUID[] nearestEntity = new UUID[count];
		final HashSet<UUID> alreadyParsed = new HashSet<UUID>();
		AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
		HashSet<UUID> partyMembers = new HashSet<UUID>();
		if (mParty != null) {
			partyMembers.addAll(mParty.getMembers());
		}
		partyMembers = (HashSet<UUID>) removeEntitiesInSpellfreeZone(partyMembers);
		for (int i = 0; i < count; i++) {
			double curdif = 100000;
			for (Entity e : entityset) {
				if (e instanceof Creature || e instanceof Player) {
					double dif = e.getLocation().distance(mPlayer.getLocation());
					if (dif < curdif && e != mPlayer && !alreadyParsed.contains(e.getUniqueId()) && !partyMembers.contains(e.getUniqueId())) {
						curdif = dif;
						nearestEntity[i] = e.getUniqueId();
					}
				}
			}
			alreadyParsed.add(nearestEntity[i]);
		}
		return GlobalMethods.removeNullArrayCells(nearestEntity);
	}

	public UUID[] getPartyMembers(final Player mPlayer, final int range) {
		AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
		if (mParty == null) {
			return new UUID[0];
		} else {
			Collection<UUID> partyMembers = new HashSet<UUID>(mParty.getMembers());
			for (Iterator<UUID> iterator = partyMembers.iterator(); iterator.hasNext(); ) {
				UUID uuid = iterator.next();
				if (uuid == null || Bukkit.getPlayer(uuid) == null || Bukkit.getPlayer(uuid).getLocation().distance(mPlayer.getLocation()) > range)
					iterator.remove();
			}
			return GlobalMethods.removeNullArrayCells(partyMembers.toArray(new UUID[partyMembers.size()]));
		}
	}

	public int parseVariable(UUID uuid, String var) {
		return Variables.getParameterIntByString(uuid, var, this.mSpell.newConfigFile);
	}
}