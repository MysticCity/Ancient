package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import java.util.regex.Pattern;

public class Argument
{
  public String name;
  public Parameter[] parameters;
  public String[] paramdesc;
  public String desc;
  public Parameter param;
  
  public Argument(String line)
  {
    parseLine(line);
  }
  
  public static String getLine(String name, Parameter[] parameters, String[] paramdesc, String desc, Parameter param)
  {
    String line = name + " | " + param.name() + " | ";
    for (Parameter parameter : parameters) {
      line = line + parameter.name() + ", ";
    }
    if (line.endsWith(", ")) {
      line = line.substring(0, line.length() - 2);
    }
    line = line + " | ";
    for (String aParamdesc : paramdesc) {
      line = line + aParamdesc + ", ";
    }
    if (line.endsWith(", ")) {
      line = line.substring(0, line.length() - 2);
    }
    line = line + "//" + desc;
    return line;
  }
  
  void parseLine(String line)
  {
    if (line.contains("//"))
    {
      this.desc = line.substring(line.indexOf("//") + 2);
      line = line.substring(0, line.indexOf("//"));
    }
    String[] args = line.split(Pattern.quote("|"));
    for (int i = 0; i < args.length; i++) {
      args[i] = args[i].trim();
    }
    if (args.length >= 4)
    {
      this.name = args[0].trim();
      this.param = Parameter.getParameterByName(args[1].trim());
      String[] params = args[2].split(",");
      String[] paramdescs = args[3].split(",");
      this.parameters = new Parameter[params.length];
      this.paramdesc = new String[paramdescs.length];
      for (int i = 0; i < paramdescs.length; i++)
      {
        this.parameters[i] = getParameterType(params[i].trim());
        this.paramdesc[i] = paramdescs[i].trim();
      }
    }
  }
  
  private static Parameter getParameterType(String param)
  {
    if (param.toLowerCase().equals("player")) {
      return Parameter.Player;
    }
    if (param.toLowerCase().equals("entity")) {
      return Parameter.Entity;
    }
    if (param.toLowerCase().equals("string")) {
      return Parameter.String;
    }
    if (param.toLowerCase().equals("location")) {
      return Parameter.Location;
    }
    if (param.toLowerCase().equals("number")) {
      return Parameter.Number;
    }
    if (param.toLowerCase().equals("material")) {
      return Parameter.Material;
    }
    if (param.toLowerCase().equals("boolean")) {
      return Parameter.Boolean;
    }
    return Parameter.Void;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\Argument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */