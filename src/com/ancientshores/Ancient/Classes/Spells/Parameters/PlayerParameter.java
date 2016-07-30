package com.ancientshores.Ancient.Classes.Spells.Parameters;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;

@ParameterDescription(amount = 0, description = "<html>returns the player who is casting the spell</html>", returntype = "Player", name = "Player")
public class PlayerParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player player, String[] subparam, ParameterType returnType) {
        switch (returnType) {
            case Player:
                Player[] p = {player};
                ea.getParams().addLast(p);
                break;
            case Entity:
                Entity[] e = {player};
                ea.getParams().addLast(e);
                break;
            case Location:
                Location[] l = {player.getLocation()};
                ea.getParams().addLast(l);
                break;
            case String:
                ea.getParams().addLast(player.getName());
                break;
            default:
                Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
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