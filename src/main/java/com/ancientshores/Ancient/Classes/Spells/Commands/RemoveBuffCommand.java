package com.ancientshores.Ancient.Classes.Spells.Commands;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;

public class RemoveBuffCommand extends ICommand {
	@CommandDescription(description = "<html>removes the specified buff of the player so it can't be triggered anymore</html>",
			argnames = {"player", "buffname"}, name = "RemoveBuff", parameters = {ParameterType.Player, ParameterType.String})

	public RemoveBuffCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
	}
	// TODO change to uuid
	@Override
	public boolean playCommand(final EffectArgs ca) {
		if (ca.getParams().size() == 2 && ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
			final Player[] players = (Player[]) ca.getParams().get(0);
			final String spellname = (String) ca.getParams().get(1);
			ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> affectedSpells = AncientSpellListener.getAllBuffs();
			boolean all = false;
			if (spellname.equalsIgnoreCase("all")) {
				all = true;
			}
			for (Player p : players) {
				if (p == null) {
					continue;
				}
				for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> entry : affectedSpells.entrySet()) {
					ConcurrentHashMap<UUID[], Integer> maps = new ConcurrentHashMap<UUID[], Integer>();
					for (Entry<UUID[], Integer> entry1 : entry.getValue().entrySet()) {
						if (entry1.getKey()[0].compareTo(p.getUniqueId()) == 0 && (all || entry.getKey().name.equalsIgnoreCase(spellname))) {
							AncientSpellListener.detachBuffOfEvent(entry.getKey().buffEvent, entry.getKey(), p);
						}
					}
					for (Entry<UUID[], Integer> entry1 : maps.entrySet()) {
						entry.getValue().remove(entry1.getKey());
					}
				}
			}
			return true;
		}
		return false;
	}
}