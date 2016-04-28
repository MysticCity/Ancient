package de.pylamo.spellmaker.gui.SpellItems.Commands;

import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;

public class Command
{
  public String name;
  public Parameter[] parameters;
  public String[] paramdesc;
  public String desc;
  
  public Command(String line)
  {
    parseLine(line);
  }
  
  public static String getLine(String name, Parameter[] parameters, String[] paramdesc, String desc)
  {
    String line = name;
    for (int i = 0; i < parameters.length; i++) {
      line = line + ", " + parameters[i].name() + " | " + paramdesc[i];
    }
    line = line + " // " + desc;
    return line;
  }
  
  void parseLine(String line)
  {
    if (line.contains("//"))
    {
      this.desc = line.substring(line.indexOf("//") + 2);
      line = line.substring(0, line.indexOf("//"));
    }
    line = line.trim();
    String[] args = line.split(",");
    for (int i = 0; i < args.length; i++) {
      args[i] = args[i].trim();
    }
    if (args.length == 0) {
      return;
    }
    if (args.length >= 1)
    {
      this.name = args[0];
      this.parameters = new Parameter[args.length - 1];
      this.paramdesc = new String[args.length - 1];
      for (int i = 1; i < args.length; i++) {
        if (args[i].contains("|"))
        {
          this.parameters[(i - 1)] = getParameterType(args[i].split(java.util.regex.Pattern.quote("|"))[0].trim());
          this.paramdesc[(i - 1)] = args[i].split(java.util.regex.Pattern.quote("|"))[1].trim();
        }
        else
        {
          this.parameters[(i - 1)] = getParameterType(args[i].trim());
        }
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
