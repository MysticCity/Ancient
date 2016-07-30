package com.ancientshores.Ancient.Classes.Spells;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;

public class Variable extends ICodeSection {
    public static final HashMap<String, Variable> globVars = new HashMap<String, Variable>();
    public static final HashMap<UUID, HashMap<String, Variable>> playerVars = new HashMap<UUID, HashMap<String, Variable>>();
    public String name;
    IParameter param;
    public Object obj;
    String[] subParams = new String[0];
    ICodeSection parent;
    Operator operator;
    ArgumentInformationObject aio;
    String line;

    public Variable(String line, ICodeSection parent, Spell p) {
        super(null, null, p);
        this.parent = parent;
        this.line = line;
        parseLine(line, p);

    }

    public Variable(String name) {
        super(null, null, null);
        this.name = name;
    }

    void parseLine(String line, Spell mSpell) {
        String[] sides = null;
        if (line.contains("+=")) {
            sides = line.split("\\+=");
            operator = Operator.ADD;
        } else if (line.contains("-=")) {
            sides = line.split("\\-=");
            operator = Operator.SUBTRACT;
        } else if (line.contains("*=")) {
            sides = line.split("\\*=");
            operator = Operator.MULTIPLY;
        } else if (line.contains("/=")) {
            sides = line.split("/=");
            operator = Operator.DIVIDE;
        } else if (line.contains("=")) {
            sides = line.split("=");
            operator = Operator.ASSIGN;
        }
        name = sides[0].trim();
        if (sides.length == 1) {
            return;
        }
        String[] ps = sides[1].split(":");
        if (Parameter.registeredParameters.size() <= 10) {
            Parameter.registerDefaultParameters();
        }
        for (IParameter p : Parameter.registeredParameters) {
            if (p.getName().toLowerCase().startsWith(ps[0].trim().toLowerCase())) {
                param = p;
                break;
            }
        }
        ps = sides;
        if (param == null) {
            this.aio = IArgument.parseArgumentByString(sides[1], mSpell);
            // this.argument =
            // IArgument.getArgumentByString(ps[0].trim().toLowerCase().split("\\(")[0].split(",")[0]);
            if (this.aio == null) {
                try {
                    int i = Integer.parseInt(ps[0].trim());
                    obj = i;
                } catch (Exception e) {
                    try {
                        float i = Float.parseFloat(ps[0].trim());
                        obj = i;
                    } catch (Exception e1) {
                        obj = ps[0].trim();
                    }
                }
            }
            if (!mSpell.variables.contains(name)) {
                mSpell.variables.add(name);
            }
            return;
        }
        if (ps.length > 1) {
            String[] sp = ps[1].trim().split(Pattern.quote(":"));
            subParams = new String[sp.length - 1];
            System.arraycopy(sp, 1, subParams, 0, subParams.length);
        }
        if (!mSpell.variables.contains(name)) {
            mSpell.variables.add(name);
        }
    }

    public void saveParameter(Player mPlayer, SpellInformationObject so) {
        parseLine(line, mSpell);
        if (obj == null) {
            if (param != null) {
                obj = param.parseParameter(mPlayer, subParams, so);
            }
            if (aio != null) {
                obj = aio.getArgument(so, mPlayer);
            }
        }
        if (obj instanceof Player) {
            obj = new Player[]{(Player) obj};
        }
        if (obj instanceof Entity) {
            obj = new Entity[]{(Entity) obj};
        }
        if (obj instanceof Location) {
            obj = new Location[]{(Location) obj};
        }
    }

    public Object getVariableObject() {
        if (obj instanceof Player) {
            obj = new Player[]{(Player) obj};
        }
        if (obj instanceof Entity) {
            obj = new Entity[]{(Entity) obj};
        }
        if (obj instanceof Location) {
            obj = new Location[]{(Location) obj};
        }
        return obj;
    }

    @SuppressWarnings("deprecation")
	public void AutoCast(ParameterType pt, EffectArgs ea) {
        switch (pt) {
            case Number: {
                if (obj instanceof Number) {
                    ea.getParams().addLast(((Number) obj).doubleValue());
                }
                break;
            }
            case String: {
                if (obj instanceof String) {
                    ea.getParams().addLast(obj);
                } else if (obj instanceof Number) {
                    ea.getParams().addLast("" + ((Number) obj).doubleValue());
                }
                break;
            }
            case Entity: {
                if (obj instanceof Entity[]) {
                    ea.getParams().addLast(obj);
                    return;
                } else if (obj instanceof Entity) {
                    Entity[] e = new Entity[1];
                    e[0] = (Entity) obj;
                    ea.getParams().addLast(e);
                } else if (obj instanceof Player[]) {
                    Entity[] e = (Player[]) obj;
                    ea.getParams().addLast(e);
                }
                break;
            }
            case Player: {
                if (obj instanceof Player[]) {
                    ea.getParams().addLast(obj);
                } else if (obj instanceof Player) {
                    Player[] p = new Player[1];
                    p[0] = (Player) obj;
                    ea.getParams().addLast(p);
                }
                break;
            }
            case Material: {
                if (obj instanceof Number) {
                    Material m = Material.getMaterial((int) ((Number) obj).doubleValue());
                    ea.getParams().addLast(m);
                }
            }
            case Boolean: {
                if (obj instanceof Boolean) {
                    ea.getParams().addLast(obj);
                }
                break;
            }
            case Location: {
                if (obj instanceof Location[]) {
                    ea.getParams().addLast(obj);
                } else if (obj instanceof Entity[]) {
                    Entity[] arr = (Entity[]) obj;
                    Location[] l = new Location[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] != null) {
                            l[i] = arr[i].getLocation();
                        }
                    }
                    ea.getParams().addLast(l);
                } else if (obj instanceof Entity) {
                    Location[] arr = new Location[1];
                    arr[0] = ((Entity) obj).getLocation();
                    ea.getParams().addLast(arr);
                } else if (obj instanceof Player[]) {
                    Player[] arr = (Player[]) obj;
                    Location[] l = new Location[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i] != null) {
                            l[i] = arr[i].getLocation();
                        }
                    }
                    ea.getParams().addLast(l);
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
    }

    public void reAssign(String line, Spell mSpell) {
        parseLine(line, mSpell);
    }

    @Override
    public void executeCommand(Player mPlayer, SpellInformationObject so) {
        this.obj = null;
        saveParameter(mPlayer, so);
        if (this.operator == Operator.ADD) {
            if (so.variables.containsKey(name.toLowerCase())) {
                Object s = so.variables.get(name.toLowerCase()).obj;
                if (s instanceof Number && this.obj instanceof Number) {
                    Number n = (Number) s;
                    Number no = (Number) this.obj;
                    this.obj = n.doubleValue() + no.doubleValue();
                    so.variables.put(name.toLowerCase(), this);
                }
                if (s instanceof String) {
                    String st = (String) s;
                    this.obj = st + this.obj;
                    so.variables.put(name.toLowerCase(), this);
                }
            }
        }
        if (this.operator == Operator.SUBTRACT) {
            if (so.variables.containsKey(name.toLowerCase())) {
                Object s = so.variables.get(name.toLowerCase()).obj;
                if (s instanceof Number && this.obj instanceof Number) {
                    Number n = (Number) s;
                    Number no = (Number) this.obj;
                    this.obj = n.doubleValue() - no.doubleValue();
                    so.variables.put(name.toLowerCase(), this);
                }
            }
        }
        if (this.operator == Operator.DIVIDE) {
            if (so.variables.containsKey(name.toLowerCase())) {
                Object s = so.variables.get(name.toLowerCase()).obj;
                if (s instanceof Number && this.obj instanceof Number) {
                    Number n = (Number) s;
                    Number no = (Number) this.obj;
                    this.obj = n.doubleValue() / no.doubleValue();
                    so.variables.put(name.toLowerCase(), this);
                }
            }
        }
        if (this.operator == Operator.MULTIPLY) {
            if (so.variables.containsKey(name.toLowerCase())) {
                Object s = so.variables.get(name.toLowerCase()).obj;
                if (s instanceof Number && this.obj instanceof Number) {
                    Number n = (Number) s;
                    Number no = (Number) this.obj;
                    this.obj = n.doubleValue() * no.doubleValue();
                    so.variables.put(name.toLowerCase(), this);
                }
            }
        } else if (this.operator == Operator.ASSIGN) {
            so.variables.put(name.toLowerCase(), this);
        }
        if (Variable.globVars.containsKey(this.name)) {
            Variable.globVars.get(this.name).obj = obj;
        }
        if (Variable.playerVars.containsKey(so.buffcaster) && Variable.playerVars.get(so.buffcaster).containsKey(this.name.toLowerCase())) {
            Variable.playerVars.get(so.buffcaster).get(this.name.toLowerCase()).obj = this.obj;
            so.variables.put(this.name.toLowerCase(), Variable.playerVars.get(so.buffcaster).get(this.name.toLowerCase()));
//			Variable.playerVars.get(so.buffcaster.getPlayer().getName()).put(this.name.toLowerCase(), this);
        }
        if (obj instanceof Player) {
            obj = new Player[]{(Player) obj};
        }
        if (obj instanceof Entity) {
            obj = new Entity[]{(Entity) obj};
        }
        if (obj instanceof Location) {
            obj = new Location[]{(Location) obj};
        }
        if (parent != null) {
            parent.executeCommand(mPlayer, so);
        }
    }

    enum Operator {
        ASSIGN, ADD, SUBTRACT, MULTIPLY, DIVIDE;
    }
}