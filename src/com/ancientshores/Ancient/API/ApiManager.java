package com.ancientshores.Ancient.API;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameter;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Party.AncientParty;

public class ApiManager {
	private static ApiManager instance;

	public ApiManager() {
	}

	public static ApiManager getApiManager() {
		if (instance == null) {
			instance = new ApiManager();
		}
		return instance;
	}

	public PlayerData getPlayerData(UUID uuid) {
		return PlayerData.getPlayerData(uuid);
	}

	public AncientClass getPlayerClass(PlayerData pd) {
		AncientClass playerClass = AncientClass.classList.get(pd.getClassName());
		return playerClass;
	}

	/** Get the players current party by player object.
	 * 
	 * @deprecated Use getPlayersParty(UUID) instead.
	 * @param p The player for which to get the party
	 * @return The player p's party
	 */
	@Deprecated
	public AncientParty getPlayerParty(Player p) {
		return AncientParty.getPlayersParty(p.getUniqueId());
	}
	
	public AncientParty getPlayerParty(UUID uuid) {
		return AncientParty.getPlayersParty(uuid);
	}

	public AncientGuild getPlayerGuild(UUID uuid) {
		AncientGuild guild = AncientGuild.getPlayersGuild(uuid);
		return guild;
	}

	public void registerSpellParameter(final IParameter param) {
		Parameter.registeredParameters.add(param);
	}

	public void registerSpellCommand(final ICommand param, final String name) {
		Command.registeredCommands.put(name.toLowerCase(), param);
	}

	public AncientExperience getXpSystem(PlayerData pd) {
		return pd.getXpSystem();
	}
}