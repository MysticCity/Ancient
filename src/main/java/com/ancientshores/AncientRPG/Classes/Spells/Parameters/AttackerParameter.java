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
        if (!ea.p.active && ea.so.mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) ea.so.mEvent;
            Entity ent = event.getDamager();
            if (ent instanceof Projectile) {
                ent = ((Projectile) ent).getShooter();
            }
            switch (pt) {
                case Player:
                    if (ent instanceof Player) {
                        Player[] p = new Player[1];
                        p[0] = (Player) ent;
                        ea.params.addLast(p);
                    }
                    return;
                case Entity:
                    Entity[] e = new Entity[1];
                    e[0] = ent;
                    ea.params.addLast(e);
                    return;
                case Location:
                    Location[] l = new Location[1];
                    l[0] = ent.getLocation();
                    ea.params.addLast(l);
                    return;
                case String:
                    if (ent instanceof Player) {
                        Player p = (Player) ent;
                        ea.params.addLast(p.getName());
                    }
                    return;
                default:
                    break;
            }
        } else {
            AncientRPG.plugin.getLogger().log(Level.SEVERE,
                    "Invalid usage of attacker parameter in Command " + ea.mCommand.commandString + " in spell " + ea.mCommand.mSpell.name + " in line " + ea.mCommand.lineNumber);
        }
    }

    @Override
    public String getName() {
        return "attacker";
    }

    @Override
    public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) so.mEvent;
            Entity[] e = new Entity[1];
            e[0] = event.getDamager();
            return e;
        }
        return null;
    }
}