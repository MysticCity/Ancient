package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.logging.Level;

@ParameterDescription(amount = 0, description = "<html>returns the player who is casting the spell</html>", returntype = "Player", name = "Player")
public class PlayerParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        Player nPlayer = mPlayer;
        switch (pt) {
            case Player:
                Player[] p = {nPlayer};
                ea.params.addLast(p);
                break;
            case Entity:
                Entity[] e = {nPlayer};
                ea.params.addLast(e);
                break;
            case Location:
                Location[] l = {nPlayer.getLocation()};
                ea.params.addLast(l);
                break;
            case String:
                ea.params.addLast(nPlayer.getName());
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
        }
    }

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        Player[] p = {mPlayer};
        return p;
    }
}