package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import java.awt.*;
import java.util.HashSet;

public enum Parameter {
    All(Color.white), Player(Color.red), Condition(new Color(108, 45, 199)), CompareItem(new Color(255, 106, 0)), String(Color.PINK), Entity(Color.CYAN), Location(Color.YELLOW), Number(Color.GREEN), Material(
            new Color(189, 22, 255)), Boolean(Color.ORANGE), Void(Color.WHITE), Variable(Color.LIGHT_GRAY), Operator(Color.MAGENTA), CompareOperator(new Color(255, 168, 236));

    private final Color c;

    private Parameter(Color bg) {
        this.c = bg;
    }

    public Color getColor() {
        return c;
    }

    public static Parameter getParameterByName(String s) {
        for (Parameter p : values()) {
            if (p.name().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }

    public HashSet<Parameter> getPossibleTypes() {
        HashSet<Parameter> set = new HashSet<Parameter>();
        switch (this) {
            case All:
                set.add(Player);
                set.add(Location);
                set.add(Boolean);
                set.add(Material);
                set.add(Entity);
                set.add(Number);
                set.add(String);
                set.add(Variable);
                set.add(Operator);
                set.add(All);
                break;
            case Variable:
                set.add(Variable);
                set.add(All);
                break;
            case Boolean:
                set.add(Boolean);
                set.add(Variable);
                set.add(All);
                break;
            case Entity:
                set.add(Entity);
                set.add(Location);
                set.add(Variable);
                set.add(All);
                break;
            case Location:
                set.add(Location);
                set.add(Variable);
                set.add(All);
                break;
            case Material:
                set.add(Number);
                set.add(Material);
                set.add(Variable);
                set.add(All);
                break;
            case Number:
                set.add(Number);
                set.add(Variable);
                set.add(All);
                break;
            case Player:
                set.add(Player);
                set.add(Location);
                set.add(Entity);
                set.add(Variable);
                set.add(All);
                break;
            case CompareItem:
                set.add(String);
                set.add(Number);
                set.add(Boolean);
                set.add(Variable);
                set.add(All);
            case String:
                set.add(String);
                set.add(Variable);
                set.add(All);
                break;
            case Void:
                break;
            default:
                break;
        }
        return set;
    }
}
