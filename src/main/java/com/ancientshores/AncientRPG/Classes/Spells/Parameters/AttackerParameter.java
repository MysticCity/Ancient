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
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.logging.Level;

@ParameterDescription(amount = 0, description = "<html>returns the attacker, can only be used in damage/attack events</html>", returntype = "Entity", name = "Attacker")
public class AttackerParameter implements IParameter {

    @Override
    public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt) {
        if (!ea.getSpell().active && ea.getSpellInfo().mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) ea.getSpellInfo().mEvent;
            Entity ent = event.getDamager();
            if (ent instanceof Projectile) {
                ent = ((Projectile) ent).getShooter();
            }
            switch (pt) {
                case Player:
                    if (ent instanceof Player) {
                        Player[] p = new Player[1];
                        p[0] = (Player) ent;
                        ea.getParams().addLast(p);
                    }
                    return;
                case Entity:
                    Entity[] e = new Entity[1];
                    e[0] = ent;
                    ea.getParams().addLast(e);
                    return;
                case Location:
                    Location[] l = new Location[1];
                    l[0] = ent.getLocation();
                    ea.getParams().addLast(l);
                    return;
                case String:
                    if (ent instanceof Player) {
                        Player p = (Player) ent;
                        ea.getParams().addLast(p.getName());
                    }
                    return;
                default:
                    break;
            }
        } else {
            AncientRPG.plugin.getLogger().log(Level.SEVERE,
                    "Invalid usage of attacker parameter in Command " + ea.getCommand().commandString + " in spell " + ea.getCommand().mSpell.name + " in line " + ea.getCommand().lineNumber);
        }
    }

    @Override
    public String getName() {
        return "attacker";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject informationObject) {
        if (informationObject.mEvent != null && informationObject.mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) informationObject.mEvent;
            return new Entity[]{event.getDamager()};
        }
        return null;
    }
}