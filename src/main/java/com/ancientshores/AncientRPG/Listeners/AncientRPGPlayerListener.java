package com.ancientshores.AncientRPG.Listeners;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import com.ancient.util.PlayerFinder;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.BindingData;
import com.ancientshores.AncientRPG.Classes.Commands.ClassCastCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassResetCommand;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;

public class AncientRPGPlayerListener implements Listener {

	public static final Collection<UUID> toggleguildlist = Collections.newSetFromMap(new ConcurrentHashMap<UUID, Boolean>());
	public static final Collection<UUID> togglepartylist = Collections.newSetFromMap(new ConcurrentHashMap<UUID, Boolean>());
	public static LinkedHashMap<UUID, Double> healpotions = new LinkedHashMap<UUID, Double>();
	public static final HashMap<UUID, Integer> invisibleList = new HashMap<UUID, Integer>();
	public static final HashMap<UUID, UUID> summonedCreatures = new HashMap<UUID, UUID>();

	public static EventPriority guildSpawnPriority = EventPriority.HIGHEST;
	public static EventPriority raceSpawnPriority = EventPriority.HIGHEST;
	public static AncientRPG plugin;
	public static int invisId = 0;

	public AncientRPGPlayerListener(AncientRPG instance) {
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static int addInvis(Player p) {
		invisibleList.put(p.getUniqueId(), invisId);
		int oldinvisid = invisId;
		invisId++;
		if (invisId > Integer.MAX_VALUE / 2) {
			invisId = 0;
		}
		return oldinvisid;
	}

	public static void setAllVisible(Player p) {
		for (UUID uuid : invisibleList.keySet()) {
			p.showPlayer(Bukkit.getPlayer(uuid));
		}
	}

	public static void setVisibleToAll(Player p) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.showPlayer(p);
		}
	}

	public static boolean removeInvis(Player p, int id) {
		if (invisibleList.containsKey(p.getUniqueId()) && invisibleList.get(p.getUniqueId()) == id) {
			invisibleList.remove(p.getUniqueId());
			return true;
		}
		return false;
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		Player p = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		AncientRPGClass c = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		pd.getHpsystem().setMaxHP();
		pd.getHpsystem().setHpRegen();
		if (c != null) {
			AncientRPGClass stance = c.stances.get(pd.getStance());
			if (!c.isWorldEnabled(p.getWorld())) {
				AncientRPGClass oldClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
				ClassResetCommand.reset(p, oldClass, pd);
			}
			if (stance != null && !stance.isWorldEnabled(p.getWorld())) pd.setStance("");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerConnect(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		for (UUID uuid : invisibleList.keySet()) {
			p.hidePlayer(Bukkit.getPlayer(uuid));
		}
		AncientRPGGuild.setTag(p);
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
		if (mClass != null && mClass.permGroup != null && !mClass.permGroup.equals("")) {
			if (AncientRPG.permissionHandler != null) {
				try {
					AncientRPG.permissionHandler.playerAddGroup(p, mClass.permGroup);
					for (Map.Entry<String, AncientRPGClass> entry : mClass.stances.entrySet()) {
						try {
							AncientRPG.permissionHandler.playerAddGroup(p, entry.getValue().permGroup);
						} catch (Exception ignored) {

						}
					}
				} catch (Exception ignored) {

				}
			}
		}
		PlayerData.getPlayerData(p.getUniqueId()).getHpsystem().playerUUID = p.getUniqueId();
		PlayerData.getPlayerData(p.getUniqueId()).getXpSystem().addXP(0, false);
		PlayerData.getPlayerData(p.getUniqueId()).getHpsystem().setMaxHP();
		
		PlayerFinder.addPlayer(p.getUniqueId(), p.getName());
		/*
		Plugin p = Bukkit.getServer().getPluginManager().getPlugin("ScoreboardAPI");
		if (p != null) {
			ScoreboardInterface.showScoreboard(p);
		}
		*/
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		setVisibleToAll(p);
		setAllVisible(p);
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
//		if (mClass == null) {
//			Erstmal auskommentiert. Führt dazu, dass alle XP gelöscht werden, wenn man sich keine Klasse ausgewählt hat.
//			ClassResetCommand.reset(p, null, PlayerData.getPlayerData(p.getUniqueId()));
		/*} else*/
		if (mClass != null && mClass.permGroup != null && !mClass.permGroup.equals("")) {
			if (AncientRPG.permissionHandler != null) {
				try {
					AncientRPG.permissionHandler.playerRemoveGroup(p, mClass.permGroup);
					for (Map.Entry<String, AncientRPGClass> entry : mClass.stances.entrySet()) {
						AncientRPG.permissionHandler.playerRemoveGroup(p, entry.getValue().permGroup);
					}
				} catch (Exception ignored) {
				}
			}
		}
		if (summonedCreatures.containsValue(p.getUniqueId())) {
			HashSet<UUID> removeentity = new HashSet<UUID>();
			for (UUID uuid : summonedCreatures.keySet()) {
				if (summonedCreatures.get(uuid).compareTo(p.getUniqueId()) == 0) {
					for (World w : Bukkit.getWorlds()) {
						for (Entity e : w.getEntities()) {
							if (e.getUniqueId().compareTo(uuid) == 0) {
								e.remove();
							}
						}
					}
					removeentity.add(uuid);
				}
			}
			for (UUID uuid : removeentity) {
				summonedCreatures.remove(uuid);
			}
		}
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		pd.save();
		PlayerData.playerData.remove(pd);
		pd.dispose();
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		AncientRPGRace mRace = AncientRPGRace.getRaceByName(pd.getRacename());
		if (mRace != null) {
			if (mRace.spawnLoc != null) {
				event.setRespawnLocation(mRace.spawnLoc.toLocation());
			}
		}
		AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(event.getPlayer().getUniqueId());
		if (mGuild != null) {
			if (AncientRPGGuild.spawnEnabled && mGuild.spawnLocation != null) {
				event.setRespawnLocation(mGuild.spawnLocation.toLocation());
			}
		}
		pd.getHpsystem().health = pd.getHpsystem().maxhp;
	}

	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof Player) {
			Player p = (Player) event.getInventory().getHolder();
			AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
			if (mClass == null) return;
			
//			if (!mClass.isWorldEnabled(p.getWorld())) {
//				return;
//			}
			for (ItemStack is : p.getInventory().getArmorContents()) {
				boolean canEquip = !mClass.blacklistedArmor.contains(is.getType());
				if (!canEquip) {
					if (is.equals(p.getInventory().getBoots())) {
						p.getInventory().setBoots(null);
					}
					if (is.equals(p.getInventory().getChestplate())) {
						p.getInventory().setChestplate(null);
					}
					if (is.equals(p.getInventory().getLeggings())) {
						p.getInventory().setLeggings(null);
					}
					if (is.equals(p.getInventory().getHelmet())) {
						p.getInventory().setHelmet(null);
					}
					p.getInventory().addItem(is);
					p.sendMessage("Your class can't equip this item");
				}
			}
		}

		Material m;
		int newslot = event.getPlayer().getInventory().getHeldItemSlot();
		try {
			m = event.getPlayer().getInventory().getItemInHand().getType();
		} catch (Exception e) {
			return;
		}
		Player p = (Player) event.getPlayer();
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
		if (mClass == null) return;
		
//		if (!mClass.isWorldEnabled(p.getWorld())) {
//			return;
//		}
		if (mClass.blacklistedMats.contains(m)) {
			ItemStack oldStack = p.getInventory().getItemInHand();
			int fslot = p.getInventory().firstEmpty();
			if (fslot == -1) {
				p.getWorld().dropItem(event.getPlayer().getLocation(), oldStack);
			} else {
				p.getInventory().setItem(fslot, oldStack);
			}
			if (newslot != -1) {
				p.getInventory().clear(newslot);
			}
			p.sendMessage("Your class can't use this item");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickupItem(final PlayerPickupItemEvent event) {
		final Player p = event.getPlayer();
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
		int free = 0;
		for (ItemStack s : p.getInventory().getContents()) {
			if (s == null) {
				free++;
			}
		}
		if (free < 2) {
			if (mClass != null /* && mClass.isWorldEnabled(p.getWorld())*/) {
				if (mClass.blacklistedMats.contains(event.getItem().getItemStack().getType())) {
					event.setCancelled(true);
				}
			}
			return;
		}
		AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
			public void run() {
				AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
				if (mClass == null) return;
				
//				if (!mClass.isWorldEnabled(p.getWorld())) {
//					return;
//				}
				Material m;
				int newslot = p.getInventory().getHeldItemSlot();
				try {
					m = p.getInventory().getItemInHand().getType();
				} catch (Exception e) {
					return;
				}
				if (mClass.blacklistedMats.contains(m)) {
					ItemStack oldStack = p.getInventory().getItemInHand();
					p.getInventory().setItem(p.getInventory().firstEmpty(), oldStack);
					if (newslot != -1) {
						p.getInventory().clear(newslot);
					}
					p.sendMessage("Your class can't equip use item");
				}
			}
		});
	}

	@EventHandler
	public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
		Player p = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (pd.getBindings() != null && pd.getBindings().size() >= 1 && p.getInventory().getItem(event.getNewSlot()) != null && p.getInventory().getItem(event.getNewSlot()).getType() != Material.AIR) {
			if (pd.getBindings().containsKey(new BindingData(p.getInventory().getItem(event.getNewSlot())))) {
				p.sendMessage("This item is bound to the spell: " + pd.getBindings().get(new BindingData(p.getInventory().getItem(event.getNewSlot()))));
			}
		}
		if (pd.getSlotbinds() != null && pd.getSlotbinds().size() >= 1) {
			if (pd.getSlotbinds().containsKey(event.getNewSlot())) {
				p.sendMessage("This slot is bound to the spell: " + pd.getSlotbinds().get(event.getNewSlot()));
			}
		}
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
		if (mClass == null) return;
		
//		if (!mClass.isWorldEnabled(p.getWorld())) {
//			return;
//		}
		Material m;
		int newslot = event.getNewSlot();
		try {
			m = p.getInventory().getItem(newslot).getType();
			if (mClass.blacklistedMats.contains(m)) {
				ItemStack oldStack = p.getInventory().getItem(newslot);
				int fslot = p.getInventory().firstEmpty();
				if (fslot == -1) {
					p.getWorld().dropItem(p.getLocation(), oldStack);
				} else {
					p.getInventory().setItem(fslot, oldStack);
				}
				if (newslot != -1) {
					p.getInventory().clear(newslot);
				}
				p.sendMessage("Your class can't equip use item");
			}
		}
		catch (Exception ex) {
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (!event.getMessage().startsWith("/")) {
			if (toggleguildlist.contains(p.getUniqueId())) {
				AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(p.getUniqueId());
				if (mGuild != null) {
					mGuild.sendMessage(event.getMessage(), p);
					event.setCancelled(true);
				}
			}
			if (togglepartylist.contains(p.getUniqueId())) {
				AncientRPGParty mParty = AncientRPGParty.getPlayersParty(p.getUniqueId());
				if (mParty != null) {
					String[] s = {event.getMessage()}; // wozu ???
					mParty.sendMessage(s, p);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (AncientRPGEntityListener.StunList.contains(event.getPlayer().getUniqueId())) {
			event.setCancelled(true);
		}
	}

	public static boolean damageignored = false;

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getDamage() == Integer.MAX_VALUE) {
			return;
		}
		if (event instanceof EntityDamageByEntityEvent && event.getCause() == DamageCause.ENTITY_ATTACK) {
			EntityDamageByEntityEvent damageevent = (EntityDamageByEntityEvent) event;
			if (damageevent.getDamager() instanceof Player) {
				Player p = (Player) damageevent.getDamager();
				PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
				if (pd != null && !AncientRPGClass.rightClick && p.getInventory().getItemInHand() != null && pd.getBindings().containsKey(new BindingData(p.getItemInHand())) && !damageignored) {
					ClassCastCommand.processCast(pd, p, pd.getBindings().get(new BindingData(p.getItemInHand())), ClassCastCommand.castType.LEFT);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		final Player p = event.getPlayer();

		ItemStack item = event.getItem();
		if (item != null && item.getType() == Material.POTION) {
			Potion potion = Potion.fromItemStack(item);
			switch (potion.getType()) {
				case INSTANT_HEAL: {
					healpotions.put(p.getUniqueId(), DamageConverter.getHealPotionHP() * (potion.getLevel() + 1));
					Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
						@Override
						public void run() {
							healpotions.remove(p.getUniqueId());
						}
					});
					break;
				}
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (AncientRPGEntityListener.StunList.contains(p.getUniqueId())) {
			event.setCancelled(true);
			return;
		}
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (pd != null && (!AncientRPGClass.rightClick || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && pd.getBindings().containsKey(new BindingData(p.getItemInHand()))) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ClassCastCommand.processCast(pd, p, pd.getBindings().get(new BindingData(p.getItemInHand())), ClassCastCommand.castType.RIGHT);
			} else {
				ClassCastCommand.processCast(pd, p, pd.getBindings().get(new BindingData(p.getItemInHand())), ClassCastCommand.castType.LEFT);
			}
		} else if (pd != null && (!AncientRPGClass.rightClick || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && pd.getSlotbinds().containsKey(p.getInventory().getHeldItemSlot())) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ClassCastCommand.processCast(pd, p, pd.getSlotbinds().get(p.getInventory().getHeldItemSlot()), ClassCastCommand.castType.RIGHT);
			} else {
				ClassCastCommand.processCast(pd, p, pd.getSlotbinds().get(p.getInventory().getHeldItemSlot()), ClassCastCommand.castType.LEFT);
			}
		}
	}
}