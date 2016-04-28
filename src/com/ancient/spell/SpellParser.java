package com.ancient.spell;

import com.ancient.exceptions.AncientIncorrectLineException;
import com.ancient.spell.datatypes.BooleanDataType;
import com.ancient.spell.datatypes.EntityDataType;
import com.ancient.spell.datatypes.LocationDataType;
import com.ancient.spell.datatypes.MaterialDataType;
import com.ancient.spell.datatypes.NumberDataType;
import com.ancient.spell.datatypes.PlayerDataType;
import com.ancient.spell.datatypes.StringDataType;
import com.ancient.spellmaker.Parameterizable;

public class SpellParser
{
  private static String trimLine(String line)
  {
    String returnLine = line.trim();
    while ((returnLine.charAt(0) == '(') && (returnLine.charAt(returnLine.length() - 1) == ')'))
    {
      returnLine = returnLine.substring(1, returnLine.length() - 2);
      returnLine = returnLine.trim();
    }
    if (!Character.isAlphabetic(returnLine.charAt(0))) {
      try
      {
        throw new AncientIncorrectLineException(line);
      }
      catch (AncientIncorrectLineException ex)
      {
        ex.printStackTrace();
      }
    }
    return returnLine;
  }
  
  public static SpellItem parseLine(String line, SpellSection section)
  {
    line = trimLine(line);
    line = line.trim();
    
    int commandStartIndex = 0;int commandEndIndex = 0;
    for (int i = 0; i < line.length(); i++) {
      if ((line.charAt(i) == ' ') || (line.charAt(i) == ':'))
      {
        commandEndIndex = i;
        break;
      }
    }
    String commandName = line.substring(commandStartIndex, commandEndIndex).trim();
    if (((commandName == null) || (!commandName.equalsIgnoreCase(""))) || 
    
      (Character.isLowerCase(commandName.charAt(0))))
    {
      if (!commandName.equals("if")) {
        if (!commandName.equals("while")) {
          if (!commandName.equals("else")) {
            if (!commandName.equals("for")) {
              if (!commandName.equals("switch")) {
                if (!commandName.equals("case")) {
                  if (!commandName.equals("var")) {
                    if (!commandName.equals("p")) {
                      if (!commandName.equals("e")) {
                        if (!commandName.equals("s")) {
                          if (!commandName.equals("b")) {
                            if (!commandName.equals("l")) {
                              if (!commandName.equals("m")) {
                                if (!commandName.equals("n")) {}
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    else {
      return parseCommand(commandName, line.substring(commandEndIndex + 1));
    }
    return null;
  }
  
  private static SpellItem parseCommand(String commandName, String arguments)
  {
    SpellItem item = null;
    try
    {
      item = (SpellItem)Class.forName(SpellItems.getFullName(commandName)).newInstance();
    }
    catch (InstantiationException ex)
    {
      ex.printStackTrace();
    }
    catch (IllegalAccessException ex)
    {
      ex.printStackTrace();
    }
    catch (ClassNotFoundException ex)
    {
      ex.printStackTrace();
    }
    if (arguments.equalsIgnoreCase(""))
    {
      if ((item instanceof Parameterizable)) {
        return null;
      }
    }
    else if (!((Parameterizable)item).validParameters(arguments)) {
      return null;
    }
    return item;
  }
  
  public static boolean isCommand(String s)
  {
    s = trimLine(s);
    if (s.contains(":")) {
      return true;
    }
    String[] splitted = s.split(":");
    if (SpellItems.getFullName(splitted[0].trim()) != null) {
      return true;
    }
    return false;
  }
  
  public static SpellItem parse(String line, int lineNumber)
  {
    line = line.trim();
    char[] chars = line.toCharArray();
    int commandStart = 0;int commandEnd = 0;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == ':')
      {
        commandEnd = i;
        break;
      }
    }
    String command = line.substring(commandStart, commandEnd);
    String arguments = line.substring(commandEnd + 1);
    if ((command != null) && (command == "")) {}
    char startChar = chars[0];
    if ((Character.isLowerCase(startChar)) && 
      (!line.startsWith("var:"))) {
      if (!line.startsWith("while:")) {
        if (!line.startsWith("for:")) {
          if (!line.startsWith("if:")) {
            if (!line.startsWith("else:")) {
              if (!line.startsWith("switch:")) {
                if (!line.startsWith("case:"))
                {
                  if (line.startsWith("p:")) {
                    return new PlayerDataType(lineNumber, arguments);
                  }
                  if (line.startsWith("e:")) {
                    return new EntityDataType(lineNumber, arguments);
                  }
                  if (line.startsWith("n:")) {
                    return new NumberDataType(lineNumber, arguments);
                  }
                  if (line.startsWith("s:")) {
                    return new StringDataType(lineNumber, arguments);
                  }
                  if (line.startsWith("b:")) {
                    return new BooleanDataType(lineNumber, arguments);
                  }
                  if (line.startsWith("l:")) {
                    return new LocationDataType(lineNumber, arguments);
                  }
                  if (line.startsWith("m:")) {
                    return new MaterialDataType(lineNumber, arguments);
                  }
                }
              }
            }
          }
        }
      }
    }
    return null;
  }
}
