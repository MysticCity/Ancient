package com.ancientshores.AncientRPG.Listeners;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.ChangeAggroCommand;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.AncientRPGHP;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class AncientRPGEntityListener implements Listener {
	public static final Collection<UUID> StunList = Collections.newSetFromMap(new ConcurrentHashMap<UUID, Boolean>());
	public static final Collection<UUID> invulnerableList = Collections.newSetFromMap(new ConcurrentHashMap<UUID, Boolean>());
	public static final ConcurrentHashMap<UUID, UUID> scheduledXpList = new ConcurrentHashMap<UUID, UUID>();

	public static AncientRPG plugin;

	public AncientRPGEntityListener(AncientRPG instance) {
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static void stun(Entity e) {
		StunList.add(e.getUniqueId());
	}

	public static void unstun(Entity e) {
		StunList.remove(e.getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void damageMonitor(final EntityDamageEvent event) {
	/*	Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
			@Override
			public void run()
			{
				if(event.getEntity() instanceof Player && ScoreboardInterface.scoreboardenabled)
				{
					Scoreboard sb = ScoreboardInterface.getPlayersScoreboard((Player)event.getEntity());
					ScoreboardAPI.getInstance().removeScoreboard(sb);
					ScoreboardInterface.showScoreboard((Player) event.getEntity());
				}
			}
		});*/
	}

	// ======
	// MonsterHP Listener
	// ======

	public static final boolean ignored = false;
	public static boolean ignoreNextHpEvent = false;

	@EventHandler(priority = EventPriority.LOWEST)
	public void monsterHpListener(EntityDamageEvent event) {
		if (event.isCancelled() || event.getDamage() == Integer.MAX_VALUE) {
			return;
		}
		double damage = event.getDamage();
		if (!ignoreNextHpEvent && CreatureHp.isEnabled(event.getEntity().getWorld()) && DamageConverter.isEnabled() && event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof HumanEntity)) {
			if (event.getCause() != DamageCause.CUSTOM) {
				damage = DamageConverter.reduceDamageByArmor(DamageConverter.convertDamageByEventForCreatures(event), (LivingEntity) event.getEntity());
			}
		}
		event.setDamage(damage);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMonsterDespawn(EntityDeathEvent event) {
		if (CreatureHp.isEnabled(event.getEntity().getWorld()) && event.getEntity() != null && !(event.getEntity() instanceof HumanEntity)) {
			LivingEntity c = event.getEntity();
			if (CreatureHp.containsCreature(c)) {
				CreatureHp.removeCreature(c);
			}
		}
	}

	// ======
	// Other listeners
	// ======
	@EventHandler(priority = EventPriority.HIGHEST)
	public void entityChangeTarget(EntityTargetLivingEntityEvent event) {
		if (event.getEntity() instanceof Creature) {
			if (ChangeAggroCommand.tauntedEntities.containsKey(event.getEntity().getUniqueId())) {
				Entity entity = null;
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(ChangeAggroCommand.tauntedEntities.get(event.getEntity().getUniqueId())) == 0) {
							entity = e;
						}
					}
				}
				((Creature) event.getEntity()).setTarget((LivingEntity) entity);
				event.setCancelled(true);
			}
		}
	}

	public static void setinvulnerable(Entity e, boolean invurnable) {
		if (invurnable) {
			invulnerableList.add(e.getUniqueId());
		} else {
			invulnerableList.remove(e.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		double amount = event.getAmount();
		if (event.getEntity() instanceof Player && !event.isCancelled() && DamageConverter.isEnabled()) {
			Player mPlayer = (Player) event.getEntity();
			if (!DamageConverter.isWorldEnabled((Player) event.getEntity()) || !DamageConverter.isEnabled()) {
				return;
			}
			if (event.getRegainReason() == RegainReason.SATIATED) {
				amount = 0;
			}
			if (event.getRegainReason() != RegainReason.CUSTOM) {
				if (event.getRegainReason() == RegainReason.MAGIC_REGEN) {
					for (PotionEffect pe : mPlayer.getActivePotionEffects()) {
						if (mPlayer.hasPotionEffect(PotionEffectType.REGENERATION)) {
							amount = DamageConverter.regenPotionHp * (pe.getAmplifier() + 1);
						}
					}
				} else if (event.getRegainReason() == RegainReason.MAGIC) {
					if (AncientRPGPlayerListener.healpotions.containsKey(event.getEntity().getUniqueId())) {
						amount = AncientRPGPlayerListener.healpotions.get(event.getEntity().getUniqueId());
					}
				}
			}
		}
		event.setAmount(amount);
	}


	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		HashSet<Entity> entities = new HashSet<Entity>();
		entities.addAll(event.getAffectedEntities());
		for (Entity e : entities) {
			if (e instanceof Player) {
				Player p = (Player) e;
				PotionEffect pe = getPotionEffectByType(PotionEffectType.HEAL, event.getPotion().getEffects());
				if (pe != null) {
					AncientRPGHP.addHpByUUID(p.getUniqueId(), DamageConverter.healPotionHp * (pe.getAmplifier() + 1));
				}
			}
		}
	}

	public PotionEffect getPotionEffectByType(PotionEffectType type, Collection<PotionEffect> pe) {
		for (PotionEffect p : pe) {
			if (p.getType().equals(type)) {
				return p;
			}
		}
		return null;
	}

	public void checkForFriendlyFire(Player attacker, Player attacked, EntityDamageByEntityEvent event) {
		if (attacked == attacker) {
			return;
		}
		AncientRPGGuild attackerGuild = AncientRPGGuild.getPlayersGuild(attacker.getUniqueId());
		AncientRPGGuild attackedGuild = AncientRPGGuild.getPlayersGuild(attacked.getUniqueId());
		if (attackerGuild != null && attackerGuild.equals(attackedGuild)) {
			if (!attackedGuild.friendlyFire) {
				event.setCancelled(true);
			}
		}
		AncientRPGParty attackerParty = AncientRPGParty.getPlayersParty(attacked.getUniqueId());
		AncientRPGParty attackedParty = AncientRPGParty.getPlayersParty(attacked.getUniqueId());
		if (attackerParty != null && attackerParty.equals(attackedParty)) {
			if (!attackerParty.isFriendlyFireEnabled()) {
				event.setCancelled(true);
			}
		}
	}


	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(final EntityDeathEvent event) {
		Entity deathEntity = null;
		unstun(event.getEntity());
		for (UUID uuid : scheduledXpList.keySet()) {
			if (uuid.compareTo(event.getEntity().getUniqueId()) == 0) {
				deathEntity = event.getEntity();
			}
		}
		if (deathEntity == null) {
			return;
		}
		Player p = Bukkit.getServer().getPlayer(scheduledXpList.get(deathEntity.getUniqueId()));
		scheduledXpList.remove(deathEntity.getUniqueId());
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		CommandPlayer.alreadyDead.add(event.getEntity().getUniqueId());
		AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
			@Override
			public void run() {
				CommandPlayer.alreadyDead.remove(event.getEntity().getUniqueId());
			}
		}, 250);
		if (pd.getXpSystem() != null) {
			pd.getXpSystem().addXP(AncientRPGExperience.getXPOfEntity(deathEntity), true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void entityChangeAggro(EntityTargetEvent event) {
		if (event.getEntity() instanceof Creature) {
			if (ChangeAggroCommand.tauntedEntities.containsKey(event.getEntity().getUniqueId())) {
				Entity entity = null;
				for (World w : Bukkit.getWorlds()) {
					for (Entity e : w.getEntities()) {
						if (e.getUniqueId().compareTo(ChangeAggroCommand.tauntedEntities.get(event.getEntity().getUniqueId())) == 0) {
							entity = e;
						}
					}
				}
				event.setTarget(entity);
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void xpListener(EntityDamageEvent event) {
		if (AncientRPGExperience.isEnabled() && event instanceof EntityDamageByEntityEvent) {
			AncientRPGExperience.processEntityDamageByEntityEvent((EntityDamageByEntityEvent) event);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getDamage() == Integer.MAX_VALUE) {
			return;
		}
		if (invulnerableList.contains(event.getEntity().getUniqueId())) {
			event.setCancelled(true);
			return;
		}
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent ede = (EntityDamageByEntityEvent) event;
			Entity damager = ede.getDamager();
			if (ede.getDamager() instanceof Projectile && ((Projectile) ede.getDamager()).getShooter() != null) {
				damager = ede.getDamager();
			}
			if (event.getEntity() instanceof Player && damager instanceof Player) {
				checkForFriendlyFire((Player) event.getEntity(), (Player) damager, (EntityDamageByEntityEvent) event);
				if (event.isCancelled()) {
					return;
				}
			}
			if (StunList.contains(damager.getUniqueId())) {
				event.setCancelled(true);
				return;
			}
		}
		if (event.getEntity() instanceof Player) {
			if (event.isCancelled()) {
				return;
			}
			if (!ignoreNextHpEvent && DamageConverter.isEnabled() && DamageConverter.isWorldEnabled((Player) event.getEntity())) {
				processHpSystem(event);
			}
		}
	}


	private void processHpSystem(EntityDamageEvent event) {
		double damage = event.getDamage();
		PlayerData.getPlayerData(((Player) event.getEntity()).getUniqueId());
		if (DamageConverter.isEnabled()) {
			if (event.getDamage() < 0.0 || event.isCancelled()) {
				return;
			}
			Player mPlayer = (Player) event.getEntity();
			if (!mPlayer.getGameMode().equals(GameMode.CREATIVE) && !event.isCancelled()) {
				if (!mPlayer.isDead()) {
					if (event.getCause() == DamageCause.FALL || event.getCause() == DamageCause.STARVATION) {
						damage = DamageConverter.convertDamageByCause(event.getCause(), mPlayer, event.getDamage(), event);
					} else if (event.getCause() == DamageCause.MAGIC) {
						if (event instanceof EntityDamageByEntityEvent) {
							Entity e = ((EntityDamageByEntityEvent) event).getDamager();
							if (e instanceof ThrownPotion) {
								ThrownPotion p = (ThrownPotion) e;
								for (PotionEffect pe : p.getEffects()) {
									if (pe.getType().equals(PotionEffectType.HARM)) {
										damage = DamageConverter.harmPotionDamage * (pe.getAmplifier() + 1);
									}
								}
							} else if (e instanceof Potion) {
								Potion p = (Potion) e;
								for (PotionEffect pe : p.getEffects()) {
									if (pe.getType().equals(PotionEffectType.HARM)) {
										damage = DamageConverter.harmPotionDamage * (pe.getAmplifier() + 1);
									}
								}
							}
						}
					} else if (event.getCause() == DamageCause.POISON) {
						for (PotionEffect pe : mPlayer.getActivePotionEffects()) {
							if (pe.getType().equals(PotionEffectType.POISON)) {
								damage = DamageConverter.poisonPotionDamage * (pe.getAmplifier() + 1);
							}
						}
					} else if (event.getCause() == DamageCause.WITHER) {
						damage = DamageConverter.witherDamage;
					} else if (event instanceof EntityDamageByEntityEvent) {
						EntityDamageByEntityEvent damagerevent = ((EntityDamageByEntityEvent) event);
						if (damagerevent.getDamager() instanceof Player) {
							Player attackingPlayer = (Player) damagerevent.getDamager();
							if (Math.abs(PlayerData.getPlayerData(mPlayer.getUniqueId()).getHpsystem().lastAttackDamage - System.currentTimeMillis()) < DamageConverter.minTimeBetweenAttacks) {
								damage = 0;
							} else {
								damage = DamageConverter.getPlayerDamageOfItem(mPlayer, attackingPlayer.getItemInHand().getType(), attackingPlayer, DamageConverter.damageOfFists);
							}
							PlayerData.getPlayerData(mPlayer.getUniqueId()).getHpsystem().lastAttackDamage = System.currentTimeMillis();
						} else if (damagerevent.getDamager() instanceof LivingEntity) {
							LivingEntity c = (LivingEntity) damagerevent.getDamager();
							damage = DamageConverter.convertDamageByCreature(c, mPlayer, event.getDamage(), event);
						} else {
							damage = DamageConverter.convertDamageByCause(event.getCause(), mPlayer, event.getDamage(), event);
						}
					} else {
						damage = DamageConverter.convertDamageByCause(event.getCause(), mPlayer, event.getDamage(), event);
					}
					event.setDamage(Math.round(damage));
				}
			}
		}
	}
}