package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;

import java.util.regex.Pattern;

public class Argument {
    public String name;
    public Parameter[] parameters;
    public String[] paramdesc;
    public String desc;
    public Parameter param;

    public Argument(String line) {
        parseLine(line);
    }

    public static String getLine(String name, Parameter[] parameters, String[] paramdesc, String desc, Parameter param) {
        String line = name + " | " + param.name() + " | ";
        for (Parameter parameter : parameters) {
            line += parameter.name() + ", ";
        }
        if (line.endsWith(", ")) {
            line = line.substring(0, line.length() - 2);
        }
        line += " | ";
        for (String aParamdesc : paramdesc) {
            line += aParamdesc + ", ";
        }
        if (line.endsWith(", ")) {
            line = line.substring(0, line.length() - 2);
        }
        line += "//" + desc;
        return line;
    }

    void parseLine(String line) {
        if (line.contains("//")) {
            desc = line.substring(line.indexOf("//") + 2);
            line = line.substring(0, line.indexOf("//"));
        }
        String[] args = line.split(Pattern.quote("|"));
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].trim();
        }
        if (args.length >= 4) {
            name = args[0].trim();
            param = Parameter.getParameterByName(args[1].trim());
            String[] params = args[2].split(",");
            String[] paramdescs = args[3].split(",");
            parameters = new Parameter[params.length];
            paramdesc = new String[paramdescs.length];
            for (int i = 0; i < paramdescs.length; i++) {
                parameters[i] = getParameterType(params[i].trim());
                paramdesc[i] = paramdescs[i].trim();
            }
        }
    }

    private static Parameter getParameterType(String param) {
        if (param.toLowerCase().equals("player")) {
            return Parameter.Player;
        } else if (param.toLowerCase().equals("entity")) {
            return Parameter.Entity;
        } else if (param.toLowerCase().equals("string")) {
            return Parameter.String;
        } else if (param.toLowerCase().equals("location")) {
            return Parameter.Location;
        } else if (param.toLowerCase().equals("number")) {
            return Parameter.Number;
        } else if (param.toLowerCase().equals("material")) {
            return Parameter.Material;
        } else if (param.toLowerCase().equals("boolean")) {
            return Parameter.Boolean;
        } else {
            return Parameter.Void;
        }
    }
}