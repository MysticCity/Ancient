package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CastTo extends IArgument {
    @ArgumentDescription(
            description = "casts the collection of players to the specified type.",
            parameterdescription = {"playercollection", "newtype"}, returntype = ParameterType.Void, rparams = {ParameterType.Player, ParameterType.String})
    public CastTo() {
        this.pt = ParameterType.Void;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
        this.name = "castto";
    }

    @Override
    public Object getArgument(Object[] obj, SpellInformationObject so) {
        if (obj.length == 2 && obj[0] instanceof Object && obj[1] instanceof String) {
            String name = (String) obj[1];
            ParameterType type = null;
            for (ParameterType pt : ParameterType.values()) {
                if (pt.toString().equalsIgnoreCase(name)) {
                    type = pt;
                    break;
                }
            }
            return AutoCast(type, obj[1]);
        }
        return null;
    }

    public Object AutoCast(ParameterType pt, Object obj) {
        switch (pt) {
            case Number: {
                if (obj instanceof Number) {
                    return obj;
                }
                break;
            }
            case String: {
                if (obj instanceof String) {
                    return obj.toString();
                } else if (obj instanceof Number) {
                    return ("" + ((Number) obj).doubleValue());
                }
                break;
            }
            case Entity: {
                if (obj instanceof Entity[]) {
                    return obj;
                } else if (obj instanceof Entity) {
                    Entity[] e = new Entity[1];
                    e[0] = (Entity) obj;
                    return e;
                } else if (obj instanceof Player[])

                {
                    Player[] arr = (Player[]) obj;
                    Entity[] e = new Entity[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] != null) {
                            e[i] = arr[i];
                        }
                    }
                    return e;
                }
                break;
            }
            case Player: {
                if (obj instanceof Player[]) {
                    return obj;
                } else if (obj instanceof Player) {
                    Player[] p = new Player[1];
                    p[0] = (Player) obj;
                    return p;
                }
                break;
            }
            case Material: {
                if (obj instanceof Number) {
                    Material m = Material.getMaterial((int) ((Number) obj).doubleValue());
                    return m;
                }
            }
            case Boolean: {
                if (obj instanceof Boolean) {
                    return obj;
                }
                break;
            }
            case Location: {
                if (obj instanceof Location[]) {
                    return obj;
                } else if (obj instanceof Entity[]) {
                    Entity[] arr = (Entity[]) obj;
                    Location[] l = new Location[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] != null) {
                            l[i] = arr[i].getLocation();
                        }
                    }
                    return l;
                } else if (obj instanceof Entity) {
                    Location[] arr = new Location[1];
                    arr[0] = ((Entity) obj).getLocation();
                    return arr;
                } else if (obj instanceof Player[])

                {
                    Player[] arr = (Player[]) obj;
                    Location[] l = new Location[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] != null) {
                            l[i] = arr[i].getLocation();
                        }
                    }
                    return l;
                }
                break;
            }
            case Locx:
                break;
            case Locy:
                break;
            case Locz:
                break;
            case Void:
                break;
            default:
                break;
        }
        return obj;
    }

}
