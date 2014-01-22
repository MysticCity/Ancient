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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.logging.Level;

@ParameterDescription(amount = 0,
        description = "<html>returns the attacked entity, can only be used in attack/damagebyentity events</html>",
        returntype = "Entity", name = "AttackedEntity")
public class AttackedEntityParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        if (!ea.getSpell().active && ea.getSpellInfo().mEvent instanceof EntityDamageEvent) {
            EntityDamageEvent event = (EntityDamageEvent) ea.getSpellInfo().mEvent;
            switch (pt) {
                case Player:
                    if (event.getEntity() instanceof Player) {
                        Player[] p = new Player[1];
                        p[0] = (Player) event.getEntity();
                        ea.getParams().addLast(p);
                    }
                    return;
                case Entity:
                    Entity[] e = new Entity[1];
                    e[0] = event.getEntity();
                    ea.getParams().addLast(e);
                    return;
                case Location:
                    Location[] l = new Location[1];
                    l[0] = event.getEntity().getLocation();
                    ea.getParams().addLast(l);
                    return;
                case String:
                    if (event.getEntity() instanceof Player) {
                        Player p = (Player) event.getEntity();
                        ea.getParams().addLast(p.getName());
                    }
                    return;
                default:
                    break;
            }
        } else {
            AncientRPG.plugin.getLogger().log(Level.SEVERE,
                    "Invalid usage of attackedentity parameter in Command " + ea.getCommand().commandString + " in spell " + ea.getCommand().mSpell.name + " in line " + ea.getCommand().lineNumber);
        }
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) so.mEvent;
            Entity[] e = new Entity[1];
            e[0] = event.getEntity();
            return e;
        }
        return null;
    }

    @Override
    public String getName() {
        return "attackedentity";
    }
}