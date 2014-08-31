package com.ancientshores.AncientRPG.API;

import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Command;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.ICommand;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.Parameter;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

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

    public PlayerData getPlayerData(Player mPlayer) {
        return PlayerData.getPlayerData(mPlayer.getUniqueId());
    }

    public AncientRPGClass getPlayerClass(PlayerData pd) {
        AncientRPGClass playerClass = AncientRPGClass.classList.get(pd.getClassName());
        return playerClass;
    }

    public AncientRPGParty getPlayerParty(Player p) {
        AncientRPGParty party = AncientRPGParty.getPlayersParty(p);
        return party;
    }

    public AncientRPGGuild getPlayerGuild(String playername) {
        AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(playername);
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