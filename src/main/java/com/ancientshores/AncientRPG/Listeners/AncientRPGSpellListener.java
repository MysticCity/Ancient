package com.ancientshores.AncientRPG.Listeners;

import com.ancientshores.AncientRPG.API.AncientLevelupEvent;
import com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AncientRPGSpellListener implements Listener {
    public static AncientRPG plugin;
    public static final HashSet<Spell> damageEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> damageEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> attackEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> attackEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> damageByEntityEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> damageByEntityEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> ChangeBlockEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> ChangeBlockEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> joinEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> joinEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> interactEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> interactEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> chatEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> chatEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> regenEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> regenEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> moveEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> moveEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> playerDeathSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> playerDeathBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> LevelupEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> LevelupEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> ProjectileHitEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> ProjectileHitEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> UseBedEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> UseBedEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> killEntityEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> killEntityEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final HashSet<Spell> classChangeEventSpells = new HashSet<Spell>();
    public static final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> classChangeEventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public static final ConcurrentHashMap<Player, ItemStack> disarmList = new ConcurrentHashMap<Player, ItemStack>();
    public static int buffId = 0;

    public static final HashSet<Player> deadPlayer = new HashSet<Player>();
    public static final ConcurrentHashMap<Player, Location> revivePlayer = new ConcurrentHashMap<Player, Location>();
    public static final ConcurrentHashMap<Player, HashSet<SpellInformationObject>> disruptOnMove = new ConcurrentHashMap<Player, HashSet<SpellInformationObject>>();
    public static final ConcurrentHashMap<Player, HashSet<SpellInformationObject>> disruptOnDeath = new ConcurrentHashMap<Player, HashSet<SpellInformationObject>>();
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

    public static ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> getAllBuffs() {
        ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> map = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
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

    public static void addDisruptCommand(Player p, SpellInformationObject spell, ConcurrentHashMap<Player, HashSet<SpellInformationObject>> map) {
        if (spell == null) {
            return;
        }
        if (!map.containsKey(p)) {
            map.put(p, new HashSet<SpellInformationObject>());
        }
        map.get(p).add(spell);
    }

    public static void removeDisruptCommand(Player p, SpellInformationObject spell, ConcurrentHashMap<Player, HashSet<SpellInformationObject>> map) {
        map.get(p).remove(spell);
    }

    public static int attachBuffToEvent(Spell s, ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> map, Player[] p) {
        if (s == null) {
            return Integer.MAX_VALUE;
        }
        if (!map.containsKey(s)) {
            map.put(s, new ConcurrentHashMap<Player[], Integer>());
        }
        map.get(s).put(p, buffId);
        int oldbuffId = buffId;
        buffId++;
        if (buffId > Integer.MAX_VALUE / 2) {
            buffId = 0;
        }
        return oldbuffId;
    }

    public static void detachBuffToEvent(Spell s, ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> map, Player[] p, int id) {
        if (!map.containsKey(s)) {
            return;
        }
        ConcurrentHashMap<Player[], Integer> innerMap = map.get(s);
        HashSet<Player[]> removeBuffs = new HashSet<Player[]>();
        if (innerMap == null) {
            return;
        }
        for (Player[] players : innerMap.keySet()) {
            if (players.length == 2 && players[0] == p[0] && players[1] == p[1]) {
                if (innerMap.get(players) != null && innerMap.get(players) == id) {
                    removeBuffs.add(players);
                }
            }
        }
        for (Player[] pl : removeBuffs) {
            innerMap.remove(pl);
        }
    }

    public static void detachBuffOfEvent(String buffEvent, Spell sp, Player p) {
        if (buffEvent == null) {
            return;
        }
        ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> buffs = null;
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
        ConcurrentHashMap<Player[], Integer> innerMap = buffs.get(sp);
        HashSet<Player[]> removeBuffs = new HashSet<Player[]>();
        if (innerMap == null) {
            return;
        }
        for (Player[] players : innerMap.keySet()) {
            if (players.length == 2 && (players[0] == p || players[1] == p)) {
                if (innerMap.get(players) != null) {
                    removeBuffs.add(players);
                }
            }
        }
        for (Player[] pl : removeBuffs) {
            innerMap.remove(pl);
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
            PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
            HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
            for (Spell p : regenEventSpells) {
                if (AncientRPGClass.spellAvailable(p, pd)) {
                    spells.put(p, new Player[]{mPlayer, mPlayer});
                }
            }
            for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : regenEventBuffs.entrySet()) {
                for (Player p[] : e.getValue().keySet()) {
                    if (p[0].equals(mPlayer)) {
                        spells.put(e.getKey(), p);
                    }
                }
            }
            LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
            for (Entry<Spell, Player[]> sortedspell : sortedspells) {
                CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (disruptOnDeath.containsKey(event.getEntity()) && disruptOnDeath.get(event.getEntity()) != null) {
            for (SpellInformationObject sp : disruptOnDeath.get(event.getEntity())) {
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
        PlayerData pd = PlayerData.getPlayerData(event.getEntity().getName());
        Player mPlayer = event.getEntity();
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : playerDeathSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : playerDeathBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.getEntity())) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
        PlayerData pd = PlayerData.getPlayerData(((Player) event.getEntity().getShooter()).getName());
        Player mPlayer = (Player) event.getEntity().getShooter();
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : ProjectileHitEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : ProjectileHitEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(mPlayer)) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
            CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
        }
    }

    public static final HashSet<Entity> alreadyDead = new HashSet<Entity>();

    // ===========================================================================
    // Damage Events
    // ===========================================================================

    public void onPlayerKill(final EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (alreadyDead.contains(event.getEntity())) {
            return;
        }
        if (event.getEntity() instanceof Player) {
        } else if (event.getEntity() instanceof LivingEntity) {
            if (CreatureHp.isEnabled(event.getEntity().getWorld())) {
                return;
            }
        }
        if (damager instanceof Player && !AncientRPGExperience.alreadyDead.contains(event.getEntity())) {
            if (event.getEntity() instanceof LivingEntity && ((LivingEntity) event.getEntity()).getHealth() - event.getDamage() <= 0) {
                alreadyDead.add(event.getEntity());
                /*
                 * Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin
				 * , new Runnable() { public void run() {
				 * alreadyKilled.remove(event.getEntity()); } }, 200);
				 */
                Player mPlayer = (Player) damager;
                PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
                HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
                for (Spell p : killEntityEventSpells) {
                    if (AncientRPGClass.spellAvailable(p, pd)) {
                        spells.put(p, new Player[]{mPlayer, mPlayer});
                    }
                }
                for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : killEntityEventBuffs.entrySet()) {
                    for (Player p[] : e.getValue().keySet()) {
                        if (p[0].equals(mPlayer)) {
                            spells.put(e.getKey(), p);
                        }
                    }
                }
                LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
                for (Entry<Spell, Player[]> sortedspell : sortedspells) {
                    CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
                }
            }
        }
    }

    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        Entity Damager = event.getDamager();
        if (event.getDamager() instanceof Projectile) {
            Damager = (Entity) ((Projectile) event.getDamager()).getShooter();
        }
        if (Damager instanceof Player) {
            Player mPlayer = (Player) Damager;
            PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
            HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
            for (Spell p : attackEventSpells) {
                if (AncientRPGClass.spellAvailable(p, pd)) {
                    spells.put(p, new Player[]{mPlayer, mPlayer});
                }
            }
            for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : attackEventBuffs.entrySet()) {
                for (Player p[] : e.getValue().keySet()) {
                    if (p[0].equals(Damager)) {
                        spells.put(e.getKey(), p);
                    }
                }
            }
            LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
            for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
            PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
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
            HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
            for (Spell p : damageEventSpells) {
                if (AncientRPGClass.spellAvailable(p, pd)) {
                    spells.put(p, new Player[]{mPlayer, mPlayer});
                }
            }
            for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : damageEventBuffs.entrySet()) {
                for (Player p[] : e.getValue().keySet()) {
                    if (p[0].equals(event.getEntity())) {
                        spells.put(e.getKey(), p);
                    }
                }
            }
            LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
            for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
            PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
            HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
            for (Spell p : damageByEntityEventSpells) {
                if (AncientRPGClass.spellAvailable(p, pd)) {
                    spells.put(p, new Player[]{mPlayer, mPlayer});
                }
            }
            for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : damageByEntityEventBuffs.entrySet()) {
                for (Player p[] : e.getValue().keySet()) {
                    if (p[0].equals(event.getEntity())) {
                        spells.put(e.getKey(), p);
                    }
                }
            }
            LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
            for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
            if (disruptOnMove.containsKey(event.getPlayer())) {
                for (SpellInformationObject sp : disruptOnMove.get(event.getPlayer())) {
                    sp.canceled = true;
                    sp.finished = true;
                    event.getPlayer().sendMessage("Spell cancelled");
                    soobs.add(sp);
                }
            }
            for (SpellInformationObject so : soobs) {
                removeDisruptCommand(event.getPlayer(), so, disruptOnMove);
            }
            if (!lastMoved.containsKey(event.getPlayer())) {
                lastMoved.put(event.getPlayer(), 0L);
            }
            if (Math.abs(lastMoved.get(event.getPlayer()) - System.currentTimeMillis()) >= 1000) {
                lastMoved.put(event.getPlayer(), System.currentTimeMillis());
                Player mPlayer = event.getPlayer();
                PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
                HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
                for (Spell p : moveEventSpells) {
                    if (AncientRPGClass.spellAvailable(p, pd)) {
                        spells.put(p, new Player[]{mPlayer, mPlayer});
                    }
                }
                for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : moveEventBuffs.entrySet()) {
                    for (Player p[] : e.getValue().keySet()) {
                        if (p[0].equals(event.getPlayer())) {
                            spells.put(e.getKey(), p);
                        }
                    }
                }
                LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
                for (Entry<Spell, Player[]> sortedspell : sortedspells) {
                    CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(final PlayerQuitEvent event) {
        deadPlayer.remove(event.getPlayer());
        revivePlayer.remove(event.getPlayer());
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
        if (revivePlayer.containsKey(event.getPlayer())) {
            final Location l = revivePlayer.get(event.getPlayer());
            AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                public void run() {
                    event.getPlayer().teleport(l);
                }
            }, 1);
        }
        deadPlayer.remove(event.getPlayer());
        revivePlayer.remove(event.getPlayer());
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
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : ChangeBlockEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : ChangeBlockEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.getPlayer())) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : joinEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : joinEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.getPlayer())) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : interactEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : interactEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.getPlayer())) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
                PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
                HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
                for (Spell p : chatEventSpells) {
                    if (AncientRPGClass.spellAvailable(p, pd)) {
                        spells.put(p, new Player[]{mPlayer, mPlayer});
                    }
                }
                for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : chatEventBuffs.entrySet()) {
                    for (Player p[] : e.getValue().keySet()) {
                        if (p[0].equals(event.getPlayer())) {
                            spells.put(e.getKey(), p);
                        }
                    }
                }
                LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
                for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
        Player mPlayer = event.p;
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : LevelupEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : LevelupEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.p)) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
        Player mPlayer = event.p;
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : classChangeEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : classChangeEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.p)) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : UseBedEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : UseBedEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.getPlayer())) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
            CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(final EntityDeathEvent event) {
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
        Entity ea = null;
        for (Entity e : AncientRPGEntityListener.scheduledXpList.keySet()) {
            if (e.equals(event.getEntity())) {
                ea = e;
            }
        }
        if (ea == null) {
            return;
        }
        Player mPlayer = AncientRPGEntityListener.scheduledXpList.get(ea);
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
        for (Spell p : killEntityEventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
                spells.put(p, new Player[]{mPlayer, mPlayer});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : killEntityEventBuffs.entrySet()) {
            for (Player p[] : e.getValue().keySet()) {
                if (p[0].equals(event.getEntity())) {
                    spells.put(e.getKey(), p);
                }
            }
        }
        LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, Player[]> sortedspell : sortedspells) {
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

    public LinkedList<Entry<Spell, Player[]>> getSortedList(HashMap<Spell, Player[]> collection) {
        LinkedList<Entry<Spell, Player[]>> sortedlist = new LinkedList<Entry<Spell, Player[]>>();
        for (Entry<Spell, Player[]> entry : collection.entrySet()) {
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

    public static final ConcurrentHashMap<Player, Long> lastMoved = new ConcurrentHashMap<Player, Long>();
}