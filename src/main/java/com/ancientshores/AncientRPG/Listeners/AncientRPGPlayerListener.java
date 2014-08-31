package com.ancientshores.AncientRPG.Listeners;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.BindingData;
import com.ancientshores.AncientRPG.Classes.Commands.ClassCastCommand;
import com.ancientshores.AncientRPG.Classes.Commands.ClassResetCommand;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
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
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AncientRPGPlayerListener implements Listener {
	public static final Collection<Player> toggleguildlist = Collections.newSetFromMap(new ConcurrentHashMap<Player, Boolean>());
	public static final Collection<Player> togglepartylist = Collections.newSetFromMap(new ConcurrentHashMap<Player, Boolean>());
	public static final HashMap<Player, Integer> invisibleList = new HashMap<Player, Integer>();
	public static final HashMap<Entity, Player> summonedCreatures = new HashMap<Entity, Player>();
	public static EventPriority guildSpawnPriority = EventPriority.HIGHEST;
	public static EventPriority raceSpawnPriority = EventPriority.HIGHEST;
	public static AncientRPG plugin;
	public static int invisId = 0;

	public AncientRPGPlayerListener(AncientRPG instance) {
		plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static int addInvis(Player p) {
		invisibleList.put(p, invisId);
		int oldinvisid = invisId;
		invisId++;
		if (invisId > Integer.MAX_VALUE / 2) {
			invisId = 0;
		}
		return oldinvisid;
	}

	public static void setAllVisible(Player p) {
		for (Player pa : invisibleList.keySet()) {
			p.showPlayer(pa);
		}
	}

	public static void setVisibleToAll(Player p) {
		for (Player pa : AncientRPG.plugin.getServer().getOnlinePlayers()) {
			pa.showPlayer(p);
		}
	}

	public static boolean removeInvis(Player p, int id) {
		if (invisibleList.get(p) != null && invisibleList.get(p) == id) {
			invisibleList.remove(p);
			return true;
		}
		return false;
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		Player p = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		AncientRPGClass c = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		if (c != null) {
			AncientRPGClass stance = c.stances.get(pd.getStance());
			if (!c.isWorldEnabled(p)) {
				AncientRPGClass oldClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
				ClassResetCommand.reset(p, oldClass, pd);
			}
			if (stance != null && !stance.isWorldEnabled(p)) {
				pd.setStance("");
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerConnect(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		for (Player pl : invisibleList.keySet()) {
			p.hidePlayer(pl);
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
		PlayerData.getPlayerData(p.getUniqueId()).getHpsystem().setMaxHp();
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
		if (mClass == null) {
			ClassResetCommand.reset(p, null, PlayerData.getPlayerData(p.getUniqueId()));
		} else if (mClass.permGroup != null && !mClass.permGroup.equals("")) {
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
		if (summonedCreatures.containsValue(p)) {
			HashSet<Entity> removeentity = new HashSet<Entity>();
			for (Entity e : summonedCreatures.keySet()) {
				if (summonedCreatures.get(e).equals(p)) {
					e.remove();
					removeentity.add(e);
				}
			}
			for (Entity e : removeentity) {
				summonedCreatures.remove(e);
			}
		}
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		PlayerData.playerData.remove(pd);
		pd.save();
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
		AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(event.getPlayer().getName());
		if (mGuild != null) {
			if (AncientRPGGuild.spawnEnabled && mGuild.spawnLocation != null) {
				event.setRespawnLocation(mGuild.spawnLocation.toLocation());
			}
		}
		pd.getHpsystem().health = pd.getHpsystem().maxhp;
	}

	@EventHandler
	public void onInventoryClose(final InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player)) {
			return;
		}
		if (event.getInventory().getHolder() instanceof Player) {
			Player p = (Player) event.getInventory().getHolder();
			AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
			if (mClass == null) {
				return;
			}
			if (!mClass.isWorldEnabled(p.getWorld())) {
				return;
			}
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
		if (mClass == null) {
			return;
		}
		if (!mClass.isWorldEnabled(p.getWorld())) {
			return;
		}
		if (mClass.blacklistedMats.contains(m)) {
			ItemStack oldStack = event.getPlayer().getInventory().getItemInHand();
			int fslot = event.getPlayer().getInventory().firstEmpty();
			if (fslot == -1) {
				event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), oldStack);
			} else {
				event.getPlayer().getInventory().setItem(fslot, oldStack);
			}
			if (newslot != -1) {
				event.getPlayer().getInventory().clear(newslot);
			}
			((CommandSender) event.getPlayer()).sendMessage("Your class can't use this item");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickupItem(final PlayerPickupItemEvent event) {
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(event.getPlayer().getUniqueId()).getClassName().toLowerCase());
		int free = 0;
		for (ItemStack s : event.getPlayer().getInventory().getContents()) {
			if (s == null) {
				free++;
			}
		}
		if (free < 2) {
			if (mClass != null && mClass.isWorldEnabled(event.getPlayer())) {
				if (mClass.blacklistedMats.contains(event.getItem().getItemStack().getType())) {
					event.setCancelled(true);
				}
			}
			return;
		}
		AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
			public void run() {
				Player p = event.getPlayer();
				AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getUniqueId()).getClassName().toLowerCase());
				if (mClass == null) {
					return;
				}
				if (!mClass.isWorldEnabled(p.getWorld())) {
					return;
				}
				Material m;
				int newslot = event.getPlayer().getInventory().getHeldItemSlot();
				try {
					m = event.getPlayer().getInventory().getItemInHand().getType();
				} catch (Exception e) {
					return;
				}
				if (mClass.blacklistedMats.contains(m)) {
					ItemStack oldStack = event.getPlayer().getInventory().getItemInHand();
					event.getPlayer().getInventory().setItem(event.getPlayer().getInventory().firstEmpty(), oldStack);
					if (newslot != -1) {
						event.getPlayer().getInventory().clear(newslot);
					}
					event.getPlayer().sendMessage("Your class can't equip use item");
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
		if (mClass == null) {
			return;
		}
		if (!mClass.isWorldEnabled(p.getWorld())) {
			return;
		}
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
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (!event.getMessage().startsWith("/")) {
			if (toggleguildlist.contains(p)) {
				AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(p.getName());
				if (mGuild != null) {
					mGuild.sendMessage(event.getMessage(), p);
					event.setCancelled(true);
				}
			}
			if (togglepartylist.contains(p)) {
				AncientRPGParty mParty = AncientRPGParty.getPlayersParty(p);
				if (mParty != null) {
					String[] s = {event.getMessage()};
					mParty.sendMessage(s, p);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (AncientRPGEntityListener.StunList.contains(event.getPlayer())) {
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
					ClassCastCommand.processCast(pd, p, pd.getBindings().get(new BindingData(p.getItemInHand())), ClassCastCommand.castType.Left);
				}
			}
		}
	}

	public static LinkedHashMap<Player, Integer> healpotions = new LinkedHashMap<Player, Integer>();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerItemConsume(final PlayerItemConsumeEvent event) {
		final Player p = event.getPlayer();
		if (event.isCancelled()) {
			return;
		}
		ItemStack item = event.getItem();
		if (item != null && item.getType() == Material.POTION) {
			Potion potion = Potion.fromItemStack(item);
			switch (potion.getType()) {
				case INSTANT_HEAL: {
					healpotions.put(p, DamageConverter.healPotionHp * (potion.getLevel() + 1));
					Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
						@Override
						public void run() {
							healpotions.remove(p);
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
		if (AncientRPGEntityListener.StunList.contains(p)) {
			event.setCancelled(true);
			return;
		}
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		if (pd != null && (!AncientRPGClass.rightClick || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && pd.getBindings().containsKey(new BindingData(p.getItemInHand()))) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ClassCastCommand.processCast(pd, p, pd.getBindings().get(new BindingData(p.getItemInHand())), ClassCastCommand.castType.Right);
			} else {
				ClassCastCommand.processCast(pd, p, pd.getBindings().get(new BindingData(p.getItemInHand())), ClassCastCommand.castType.Left);
			}
		} else if (pd != null && (!AncientRPGClass.rightClick || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && pd.getSlotbinds().containsKey(p.getInventory().getHeldItemSlot())) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ClassCastCommand.processCast(pd, p, pd.getSlotbinds().get(p.getInventory().getHeldItemSlot()), ClassCastCommand.castType.Right);
			} else {
				ClassCastCommand.processCast(pd, p, pd.getSlotbinds().get(p.getInventory().getHeldItemSlot()), ClassCastCommand.castType.Left);
			}
		}
	}
}