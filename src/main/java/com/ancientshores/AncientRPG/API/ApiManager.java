package com.ancientshores.AncientRPG.API;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Command;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.Parameter;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.ICommand;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

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

	public AncientRPGClass getPlayerClass(PlayerData pd) {
		AncientRPGClass playerClass = AncientRPGClass.classList.get(pd.getClassName());
		return playerClass;
	}

	/** Get the players current party by player object.
	 * 
	 * @deprecated Use getPlayersParty(UUID) instead.
	 * @param p The player for which to get the party
	 * @return The player p's party
	 */
	@Deprecated
	public AncientRPGParty getPlayerParty(Player p) {
		return AncientRPGParty.getPlayersParty(p.getUniqueId());
	}
	
	public AncientRPGParty getPlayerParty(UUID uuid) {
		return AncientRPGParty.getPlayersParty(uuid);
	}

	public AncientRPGGuild getPlayerGuild(UUID uuid) {
		AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(uuid);
		return guild;
	}

	public void registerSpellParameter(final IParameter param) {
		Parameter.registeredParameters.add(param);
	}

	public void registerSpellCommand(final ICommand param, final String name) {
		Command.registeredCommands.put(name.toLowerCase(), param);
	}

	public AncientRPGExperience getXpSystem(PlayerData pd) {
		return pd.getXpSystem();
	}
}