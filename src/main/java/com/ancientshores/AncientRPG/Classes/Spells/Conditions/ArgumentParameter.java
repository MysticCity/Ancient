package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.*;
import com.ancientshores.AncientRPG.Classes.Spells.Parameters.ArgumentParameterWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ArgumentParameter {
    IParameter parameter;
    Object c;
    boolean variable;
    public final String s;
    public final String[] subparam;
    final String varname = "";

    public ArgumentParameter(String s, Spell mSpell) {
        s = s.trim();
        if (s.contains(":") && !s.startsWith("(")) {
            String[] buffer = s.split(":");
            this.s = buffer[0];
            subparam = new String[buffer.length - 1];
            System.arraycopy(buffer, 1, subparam, 0, buffer.length - 1);
        } else {
            this.s = s;
            this.subparam = null;
        }
        for (IParameter p : Parameter.registeredParameters) {
            if (p.getName().toLowerCase().equalsIgnoreCase(this.s)) {
                this.parameter = p;
                break;
            }
        }
        if (this.parameter == null) {
            this.parameter = new ArgumentParameterWrapper(IArgument.parseArgumentByString(s, mSpell));
        }
    }

    public Object parseParameter(ParameterType pt, SpellInformationObject so, Player mPlayer, Spell spell) {
        if (variable) {
            try {
                c = so.variables.get(varname).obj;
            } catch (Exception ignored) {

            }
        }
        if (parameter != null) {
            c = parameter.parseParameter(mPlayer, subparam, so);
        }
        if (c != null && parameter == null) {
            switch (pt) {
                case Player: {
                    if (c instanceof Player[]) {
                        Player[] players = (Player[]) c;
                        return players[0];
                    }
                }
                case Location: {
                    if (c instanceof Location[]) {
                        Location[] locs = (Location[]) c;
                        return locs[0];
                    }
                }
                case Entity: {
                    if (c instanceof Entity[]) {
                        Entity[] locs = (Entity[]) c;
                        return locs[0];
                    }
                }
                case String:
                    return c.toString();
                default:
                    break;
            }
            return c;
        }
        EffectArgs ea = new EffectArgs(mPlayer, spell, so, null);
        if (pt == ParameterType.Void || parameter == null) {
            Player[] p = {mPlayer};
            ea.getParams().addLast(p);
            switch (pt) {
                case Player: {
                    return ea.getParams().get(0);
                }
                case Location: {
                    return ea.getParams().get(0);
                }
                case Entity: {
                    return ea.getParams().get(0);
                }
                default:
                    break;
            }
            return mPlayer;
        } else {
            parameter.parseParameter(ea, mPlayer, subparam, pt);
        }
        try {
            if (ea.getParams().size() == 1) {
                switch (pt) {
                    case Player: {
                        Player[] players = (Player[]) ea.getParams().get(0);
                        return players;
                    }
                    case Location: {
                        Location[] locs = (Location[]) ea.getParams().get(0);
                        return locs;
                    }
                    case Entity: {
                        Entity[] locs = (Entity[]) ea.getParams().get(0);
                        return locs;
                    }
                    default:
                        break;
                }
            }
        } catch (Exception ignored) {
        }
        return c;
    }
}