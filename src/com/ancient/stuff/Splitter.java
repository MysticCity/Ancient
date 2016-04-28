package com.ancient.stuff;

import java.util.ArrayList;

public class Splitter
{
  public static String[] splitByArgument(String s)
  {
    ArrayList<String> arguments = new ArrayList();
    int openBraces = 0;int currentArgumentStartIndex = 0;
    boolean openQuotes = false;
    s = s.trim();
    if (s.indexOf("(") == 0)
    {
      s = s.substring(1, s.length());
      s = s.trim();
    }
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      if ((c == ',') && (!openQuotes) && (openBraces == 0))
      {
        arguments.add(s.substring(currentArgumentStartIndex, i).trim());
        currentArgumentStartIndex = i + 1;
      }
      if ((c == '(') && (!openQuotes)) {
        openBraces++;
      }
      if ((c == ')') && (!openQuotes)) {
        openBraces--;
      }
      if ((c == '"') && (s.charAt(i - 1) != '$')) {
        openQuotes ^= true;
      }
    }
    return (String[])arguments.toArray();
  }
  
  public static String[] splitArray(String argument)
  {
    ArrayList<String> arguments = new ArrayList();
    int openBraces = 0;int currentArgumentStartIndex = 0;
    boolean openQuotes = false;
    argument = argument.trim();
    if (argument.indexOf("[") == 0)
    {
      argument = argument.substring(1, argument.length());
      argument = argument.trim();
    }
    for (int i = 0; i < argument.length(); i++)
    {
      char c = argument.charAt(i);
      if ((c == ',') && (!openQuotes) && (openBraces == 0))
      {
        arguments.add(argument.substring(currentArgumentStartIndex, i).trim());
        currentArgumentStartIndex = i + 1;
      }
      if ((c == '(') && (!openQuotes)) {
        openBraces++;
      }
      if ((c == ')') && (!openQuotes)) {
        openBraces--;
      }
      if ((c == '"') && (argument.charAt(i - 1) != '$')) {
        openQuotes ^= true;
      }
    }
    return (String[])arguments.toArray();
  }
}
