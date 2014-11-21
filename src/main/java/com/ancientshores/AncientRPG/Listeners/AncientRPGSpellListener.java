package com.ancientshores.AncientRPG.Listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.API.AncientLevelupEvent;
import com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.HP.CreatureHp;

public class AncientRPGSpellListener implements Listener {
	public static AncientRPG plugin;
	public static final HashSet<Spell> damageEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> damageEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> attackEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> attackEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> damageByEntityEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> damageByEntityEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> ChangeBlockEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> ChangeBlockEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> joinEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> joinEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> interactEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> interactEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> chatEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> chatEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> regenEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> regenEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> moveEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> moveEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> playerDeathSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> playerDeathBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> LevelupEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> LevelupEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> ProjectileHitEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> ProjectileHitEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> UseBedEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> UseBedEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> killEntityEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> killEntityEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final HashSet<Spell> classChangeEventSpells = new HashSet<Spell>();
	public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> classChangeEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
	public static final ConcurrentHashMap<UUID, ItemStack> disarmList = new ConcurrentHashMap<UUID, ItemStack>();
	public static int buffId = 0;

	public static final HashSet<UUID> deadPlayer = new HashSet<UUID>();
	public static final ConcurrentHashMap<UUID, Location> revivePlayer = new ConcurrentHashMap<UUID, Location>();
	public static final ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> disruptOnMove = new ConcurrentHashMap<UUID, HashSet<SpellInformationObject>>();
	public static final ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> disruptOnDeath = new ConcurrentHashMap<UUID, HashSet<SpellInformationObject>>();

	public static final HashSet<Event> ignoredEvents = new HashSet<Event>();

	public static void clearAll() {
		AncientRPGSpellListener.damageEventSpells.clear();
		AncientRPGSpellListener.damageEventBuffs.clear();
		AncientRPGSpellListener.damageByEntityEventBuffs.clear();
		AncientRPGSpellListener.damageByEntityEventSpells.clear();
		AncientRPGSpellListener.joinEventBuffs.clear();
		AncientRPGSpellListener.joinEventSpells.clear();
		AncientRPGSpellListener.interactEventBuffs.clear();
		AncientRPGSpellListener.interactEventSpells.clear();
		AncientRPGSpellListener.attackEventBuffs.clear();
		AncientRPGSpellListener.attackEventSpells.clear();
		AncientRPGSpellListener.ChangeBlockEventBuffs.clear();
		AncientRPGSpellListener.ChangeBlockEventSpells.clear();
		AncientRPGSpellListener.chatEventBuffs.clear();
		AncientRPGSpellListener.chatEventSpells.clear();
		AncientRPGSpellListener.regenEventBuffs.clear();
		AncientRPGSpellListener.regenEventSpells.clear();
		AncientRPGSpellListener.moveEventBuffs.clear();
		AncientRPGSpellListener.moveEventSpells.clear();
		AncientRPGSpellListener.playerDeathBuffs.clear();
		AncientRPGSpellListener.playerDeathSpells.clear();
		AncientRPGSpellListener.LevelupEventBuffs.clear();
		AncientRPGSpellListener.LevelupEventSpells.clear();
		AncientRPGSpellListener.ProjectileHitEventBuffs.clear();
		AncientRPGSpellListener.ProjectileHitEventSpells.clear();
		AncientRPGSpellListener.classChangeEventBuffs.clear();
		AncientRPGSpellListener.classChangeEventSpells.clear();
		ignoredEvents.clear();
	}


	public AncientRPGSpellListener(AncientRPG instance) {
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> getAllBuffs() {
		ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> map = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();

		map.putAll(damageEventBuffs);
		map.putAll(attackEventBuffs);
		map.putAll(damageByEntityEventBuffs);
		map.putAll(ChangeBlockEventBuffs);
		map.putAll(joinEventBuffs);
		map.putAll(interactEventBuffs);
		map.putAll(chatEventBuffs);
		map.putAll(regenEventBuffs);
		map.putAll(moveEventBuffs);
		map.putAll(playerDeathBuffs);
		map.putAll(LevelupEventBuffs);
		map.putAll(ProjectileHitEventBuffs);
		map.putAll(classChangeEventBuffs);
	
		return map;
	}

	public static void addDisruptCommand(Player p, SpellInformationObject spell, ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> map) {
		if (spell == null) {
			return;
		}
		if (!map.containsKey(p.getUniqueId())) {
			map.put(p.getUniqueId(), new HashSet<SpellInformationObject>());
		}
		map.get(p.getUniqueId()).add(spell);
	}

	public static void removeDisruptCommand(Player p, SpellInformationObject spell, ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> map) {
		map.get(p.getUniqueId()).remove(spell);
	}

	public static int attachBuffToEvent(Spell s, ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> map, Player[] p) {
		if (s == null) {
			return Integer.MAX_VALUE;
		}
		if (!map.containsKey(s)) {
			map.put(s, new ConcurrentHashMap<UUID[], Integer>());
		}
		map.get(s).put(new UUID[]{p[0].getUniqueId(), p[1].getUniqueId()}, buffId);

		int oldbuffId = buffId;
		buffId++;
		if (buffId > Integer.MAX_VALUE / 2) {
			buffId = 0;
		}
		return oldbuffId;
	}

	public static void detachBuffToEvent(Spell s, ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> map, Player[] p, int id) {
		if (!map.containsKey(s)) {
			return;
		}
		ConcurrentHashMap<UUID[], Integer> innerMap = map.get(s);
		HashSet<UUID[]> removeBuffs = new HashSet<UUID[]>();
		if (innerMap == null) {
			return;
		}
		for (UUID[] uuids : innerMap.keySet()) {
			if (uuids.length == 2 && (uuids[0].compareTo(p[0].getUniqueId()) == 0 || uuids[1].compareTo(p[1].getUniqueId()) == 0)) {
				if (innerMap.get(uuids) != null && innerMap.get(uuids) == id) {
					removeBuffs.add(uuids);
				}
			}
		}

		for (UUID[] uuids : removeBuffs) {
			innerMap.remove(uuids);
		}
	}

	public static void detachBuffOfEvent(String buffEvent, Spell sp, Player p) {
		if (buffEvent == null) {
			return;
		}

		ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> buffs = null;

		if (buffEvent.equalsIgnoreCase("damageevent")) {
			buffs = AncientRPGSpellListener.damageEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("damagebyentityevent")) {
			buffs = AncientRPGSpellListener.damageByEntityEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("attackevent")) {
			buffs = AncientRPGSpellListener.attackEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("changeblockevent")) {
			buffs = AncientRPGSpellListener.ChangeBlockEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("joinevent")) {
			buffs = AncientRPGSpellListener.joinEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("interactevent")) {
			buffs = AncientRPGSpellListener.interactEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("chatevent")) {
			buffs = AncientRPGSpellListener.chatEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("regenevent")) {
			buffs = AncientRPGSpellListener.regenEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("moveevent")) {
			buffs = AncientRPGSpellListener.moveEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("levelupevent")) {
			buffs = AncientRPGSpellListener.LevelupEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("playerdeathevent")) {
			buffs = AncientRPGSpellListener.playerDeathBuffs;
		} else if (buffEvent.equalsIgnoreCase("usebedevents")) {
			buffs = AncientRPGSpellListener.UseBedEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("projectilehitevent")) {
			buffs = AncientRPGSpellListener.ProjectileHitEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("killentityevent")) {
			buffs = AncientRPGSpellListener.killEntityEventBuffs;
		} else if (buffEvent.equalsIgnoreCase("classchangeevent")) {
			buffs = AncientRPGSpellListener.classChangeEventBuffs;
		}
		if (buffs != null) {
			if (!buffs.containsKey(sp)) {
				return;
			}
		}

		ConcurrentHashMap<UUID[], Integer> innerMap = buffs.get(sp);
		HashSet<UUID[]> removeBuffs = new HashSet<UUID[]>();
		if (innerMap == null) {
			return;
		}
		for (UUID[] uuids : innerMap.keySet()) {
			if (uuids.length == 2 && (uuids[0].compareTo(p.getUniqueId()) == 0 || uuids[1].compareTo(p.getUniqueId()) == 0)) {
				if (innerMap.get(uuids) != null) {
					removeBuffs.add(uuids);
				}
			}
		}

		for (UUID[] uuids : removeBuffs) {
			innerMap.remove(uuids);
		}
	}

	@EventHandler
	public void onPlayerRegainhealth(final EntityRegainHealthEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		if (event.getEntity() instanceof Player) {
			Player mPlayer = (Player) event.getEntity();
			PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
			HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
			for (Spell p : regenEventSpells) {
				if (AncientRPGClass.spellAvailable(p, pd)) {
					spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
				}
			}
			for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : regenEventBuffs.entrySet()) {
				for (UUID uuids[] : e.getValue().keySet()) {
					if (uuids[0].compareTo(mPlayer.getUniqueId()) == 0) {
						spells.put(e.getKey(), uuids);
					}
				}
			}

			LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
			for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
				CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(final PlayerDeathEvent event) {
		if (disruptOnDeath.containsKey(event.getEntity().getUniqueId()) && disruptOnDeath.get(event.getEntity().getUniqueId()) != null) {
			for (SpellInformationObject sp : disruptOnDeath.get(event.getEntity().getUniqueId())) {
				sp.canceled = true;
				sp.finished = true;
				event.getEntity().sendMessage("Spell cancelled");
				removeDisruptCommand(event.getEntity(), sp, disruptOnDeath);
			}
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		Player mPlayer = event.getEntity();
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : playerDeathSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : playerDeathBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {
				if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}

		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event) {
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		if (!(event.getEntity().getShooter() instanceof Player)) {
			return;
		}
		PlayerData pd = PlayerData.getPlayerData(((Player) event.getEntity().getShooter()).getUniqueId());
		Player mPlayer = (Player) event.getEntity().getShooter();
		
		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : ProjectileHitEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : ProjectileHitEventBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {
				if (uuids[0].compareTo(mPlayer.getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}

		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	public static final HashSet<UUID> alreadyDead = new HashSet<UUID>();

	// ===========================================================================
	// Damage Events
	// ===========================================================================
	
	public void onPlayerKill(final EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (alreadyDead.contains(event.getEntity().getUniqueId())) {
			return;
		}
		if (event.getEntity() instanceof Player) {
		} else if (event.getEntity() instanceof LivingEntity) {
			if (CreatureHp.isEnabled(event.getEntity().getWorld())) {
				return;
			}
		}
		if (damager instanceof Player && !AncientRPGExperience.alreadyDead.contains(event.getEntity().getUniqueId())) {
			if (event.getEntity() instanceof LivingEntity && ((LivingEntity) event.getEntity()).getHealth() - event.getDamage() <= 0) {
				alreadyDead.add(event.getEntity().getUniqueId());
				/*
				 * Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin
				 * , new Runnable() { public void run() {
				 * alreadyKilled.remove(event.getEntity()); } }, 200);
				 */
				Player mPlayer = (Player) damager;
				PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

				HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
				for (Spell p : killEntityEventSpells) {
					if (AncientRPGClass.spellAvailable(p, pd)) {
						spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
					}
				}
				for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : killEntityEventBuffs.entrySet()) {
					for (UUID uuids[] : e.getValue().keySet()) {
						if (uuids[0].compareTo(mPlayer.getUniqueId()) == 0) {
							spells.put(e.getKey(), uuids);
						}
					}
				}

				LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
				for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
					CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
				}
			}
		}
	}

	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (event.getDamager() instanceof Projectile) {
			damager = (Entity) ((Projectile) event.getDamager()).getShooter();
		}
		if (damager instanceof Player) {
			Player mPlayer = (Player) damager;
			PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

			HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
			for (Spell p : attackEventSpells) {
				if (AncientRPGClass.spellAvailable(p, pd)) {
					spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
				}
			}
			for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : attackEventBuffs.entrySet()) {
				for (UUID uuids[] : e.getValue().keySet()) {
					if (uuids[0].compareTo(damager.getUniqueId()) == 0) {
						spells.put(e.getKey(), uuids);
					}
				}
			}

			LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
			for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
				CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(final EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event) || event.getDamage() == 0) {
			return;
		}
		if (event instanceof EntityDamageByEntityEvent) {
			onPlayerKill((EntityDamageByEntityEvent) event);
		}
		if (event.getDamage() == Integer.MAX_VALUE) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		if (event.getEntity() instanceof Player) {
			Player mPlayer = (Player) event.getEntity();
			PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
			if (event.getCause().equals(DamageCause.FIRE)) {
				if (!(Math.abs(pd.lastFireDamageSpell - System.currentTimeMillis()) >= 1000)) {
					return;
				}
				pd.lastFireDamageSpell = System.currentTimeMillis();
			} else if (event.getCause().equals(DamageCause.LAVA)) {
				if (!(Math.abs(pd.lastLavaDamageSpell - System.currentTimeMillis()) >= 1000)) {
					return;
				}
				pd.lastLavaDamageSpell = System.currentTimeMillis();
			} else if (event.getCause().equals(DamageCause.CONTACT)) {
				if (!(Math.abs(pd.lastCactusDamageSpell - System.currentTimeMillis()) >= 1000)) {
					return;
				}
				pd.lastCactusDamageSpell = System.currentTimeMillis();
			}

			HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
			for (Spell p : damageEventSpells) {
				if (AncientRPGClass.spellAvailable(p, pd)) {
					spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
				}
			}
			for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : damageEventBuffs.entrySet()) {
				for (UUID uuids[] : e.getValue().keySet()) {
					if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
						spells.put(e.getKey(), uuids);
					}
				}
			}

			LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
			for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
				CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
			}
			if (event instanceof EntityDamageByEntityEvent) {
				onPlayerDamageByEntity((EntityDamageByEntityEvent) event);
			}
		}
		if (event instanceof EntityDamageByEntityEvent) {
			onPlayerAttack((EntityDamageByEntityEvent) event);
		}
	}

	public void onPlayerDamageByEntity(final EntityDamageByEntityEvent event) {
		Entity Damager = event.getEntity();
		if (event.getDamager() instanceof Arrow) {
			Damager = (Entity) ((Arrow) event.getDamager()).getShooter();
		}
		if (Damager instanceof Creature || Damager instanceof Player) {
			Player mPlayer = (Player) event.getEntity();
			PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

			HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
			for (Spell p : damageByEntityEventSpells) {
				if (AncientRPGClass.spellAvailable(p, pd)) {
					spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
				}
			}
			for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : damageByEntityEventBuffs.entrySet()) {
				for (UUID uuids[] : e.getValue().keySet()) {
					if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
						spells.put(e.getKey(), uuids);
					}
				}
			}

			LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
			for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
				CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
			}
		}
	}

	// ===========================================================================
	// Other events
	// ===========================================================================

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(final PlayerMoveEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		if (event.getTo().getWorld() != event.getPlayer().getWorld() || event.getTo().distance(event.getPlayer().getLocation()) > 0.1) {
			HashSet<SpellInformationObject> soobs = new HashSet<SpellInformationObject>();
			if (disruptOnMove.containsKey(event.getPlayer().getUniqueId())) {
				for (SpellInformationObject sp : disruptOnMove.get(event.getPlayer().getUniqueId())) {
					sp.canceled = true;
					sp.finished = true;
					event.getPlayer().sendMessage("Spell cancelled");
					soobs.add(sp);
				}
			}
			for (SpellInformationObject so : soobs) {
				removeDisruptCommand(event.getPlayer(), so, disruptOnMove);
			}

			if (!lastMoved.containsKey(event.getPlayer().getUniqueId())) {
				lastMoved.put(event.getPlayer().getUniqueId(), 0L);
			}
			if (Math.abs(lastMoved.get(event.getPlayer().getUniqueId()) - System.currentTimeMillis()) >= 1000) {
				lastMoved.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
				Player mPlayer = event.getPlayer();
				PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
				HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
				for (Spell p : moveEventSpells) {
					if (AncientRPGClass.spellAvailable(p, pd)) {
						spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
					}
				}
				for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : moveEventBuffs.entrySet()) {
					for (UUID uuids[] : e.getValue().keySet()) {
						if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
							spells.put(e.getKey(), uuids);
						}
					}
				}

				LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
				for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
					CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		deadPlayer.remove(event.getPlayer().getUniqueId());
		revivePlayer.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		if (revivePlayer.containsKey(event.getPlayer().getUniqueId())) {
			final Location l = revivePlayer.get(event.getPlayer().getUniqueId());
			AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
				public void run() {
					event.getPlayer().teleport(l);
				}
				
			}, 1);
		}
		
		deadPlayer.remove(event.getPlayer().getUniqueId());
		revivePlayer.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChangeBlock(final BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		Player mPlayer = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : ChangeBlockEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : ChangeBlockEventBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {
				if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}
		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		Player mPlayer = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : joinEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : joinEventBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {
				if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}

		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		Player mPlayer = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : interactEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : interactEventBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {

				if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}

		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPGSpellListener.plugin, new Runnable() {

			@Override
			public void run() {
				if (ignoredEvents.contains(event)) {
					return;
				} else {
					ignoredEvents.add(event);
					Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

						@Override
						public void run() {
							ignoredEvents.remove(event);
						}
					}, 20);
				}
				Player mPlayer = event.getPlayer();
				PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

				HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
				for (Spell p : chatEventSpells) {
					if (AncientRPGClass.spellAvailable(p, pd)) {
						spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
					}
				}
				for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : chatEventBuffs.entrySet()) {
					for (UUID uuids[] : e.getValue().keySet()) {
						if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
							spells.put(e.getKey(), uuids);
						}
					}
				}
				LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
				for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
					CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
				}
			}
		});
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLevelUp(final AncientLevelupEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}

		UUID uuid = event.uuid;
		PlayerData pd = PlayerData.getPlayerData(uuid);
		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : LevelupEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{uuid, uuid});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : LevelupEventBuffs.entrySet()) {
			for (UUID p[] : e.getValue().keySet()) {
				if (p[0].compareTo(uuid) == 0) {
					spells.put(e.getKey(), p);
				}
			}
		}
		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerClassChange(final AncientRPGClassChangeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}

		UUID uuid = event.uuid;
		PlayerData pd = PlayerData.getPlayerData(uuid);
		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : classChangeEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{uuid, uuid});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : classChangeEventBuffs.entrySet()) {
			for (UUID p[] : e.getValue().keySet()) {
				if (p[0].compareTo(uuid) == 0) {
					spells.put(e.getKey(), p);
				}
			}
		}
		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBedUse(final PlayerBedEnterEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (ignoredEvents.contains(event)) {
			return;
		} else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}
		Player mPlayer = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : UseBedEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : UseBedEventBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {
				if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}

		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(final EntityDeathEvent event) {
		if (ignoredEvents.contains(event)) return;
		else {
			ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

				@Override
				public void run() {
					ignoredEvents.remove(event);
				}
			}, 20);
		}

		UUID entityUUID = null;
		for (UUID uuid : AncientRPGEntityListener.scheduledXpList.keySet()) {
			if (uuid.compareTo(event.getEntity().getUniqueId()) == 0) {
				entityUUID = uuid;			
				break;
			}
		}
		if (entityUUID == null) {
			return;
		}
		Player mPlayer = Bukkit.getServer().getPlayer(AncientRPGEntityListener.scheduledXpList.get(entityUUID));
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
		HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();
		for (Spell p : killEntityEventSpells) {
			if (AncientRPGClass.spellAvailable(p, pd)) {
				spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
			}
		}
		for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : killEntityEventBuffs.entrySet()) {
			for (UUID uuids[] : e.getValue().keySet()) {
				if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
					spells.put(e.getKey(), uuids);
				}
			}
		}

		LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
		}
	}

	public static void addIgnoredEvent(final Event event) {
		ignoredEvents.add(event);
		Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

			@Override
			public void run() {
				ignoredEvents.remove(event);
			}
		}, 20);
	}

	public LinkedList<Entry<Spell, UUID[]>> getSortedList(HashMap<Spell, UUID[]> collection) {
		LinkedList<Entry<Spell, UUID[]>> sortedlist = new LinkedList<Entry<Spell, UUID[]>>();
		for (Entry<Spell, UUID[]> entry : collection.entrySet()) {
			if (sortedlist.size() == 0) {
				sortedlist.addLast(entry);
				continue;
			}
			boolean added = false;
			for (int i = 0; i < sortedlist.size(); i++) {
				if (sortedlist.get(i).getKey().priority > entry.getKey().priority) {
					sortedlist.add(i, entry);
					added = true;
					break;
				}
			}
			if (!added) {
				sortedlist.addLast(entry);
			}
		}
		return sortedlist;
	}

	public static final ConcurrentHashMap<UUID, Long> lastMoved = new ConcurrentHashMap<UUID, Long>();
}