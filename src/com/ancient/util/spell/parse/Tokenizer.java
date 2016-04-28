package com.ancient.util.spell.parse;

import com.ancient.util.chat.ChatParser;
import com.ancient.util.spell.item.SpellItem;
import com.ancient.util.spell.item.method.Methods;
import com.ancient.util.spell.item.variable.Variable;
import com.ancient.util.spell.operations.Mathematic;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Tokenizer
{
  public static SpellItem[] tokenize(InputStream inStream)
  {
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
      
      String text = "";
      String line;
      while ((line = br.readLine()) != null) {
        text = text + line + "\n";
      }
      return tokenize(text);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static SpellItem[] tokenize(String text)
  {
    try
    {
      LinkedList<SpellItem> itemList = new LinkedList();
      while (!text.equals(""))
      {
        String textOld = text;
        for (TokenType t : TokenType.values())
        {
          Pattern pattern = Pattern.compile(t.getRegex());
          Matcher matcher = pattern.matcher(text);
          
          String found = null;
          if (matcher.find()) {
            found = matcher.group();
          }
          if ((found != null) && 
            (text.indexOf(found) == 0))
          {
            found = ChatParser.escapeAll(found);
            
            itemList.add((SpellItem)t.clazz.getMethod(t.method, new Class[] { String.class }).invoke(null, new Object[] { found }));
            
            text = text.replaceFirst(found, "").trim();
            break;
          }
        }
        if (textOld.equals(text)) {
          throw new IllegalArgumentException("Fehler in Zeile: " + text.split("\n")[0]);
        }
      }
      return (SpellItem[])itemList.toArray(new SpellItem[itemList.size()]);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    String text = " 5 - 5 * (5 - 5)";
    
    Stack<String> stack = new Stack();
    
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    String foo = "5+5,33.9-5-f55.5";
    try
    {
      System.out.println(engine.eval(foo));
    }
    catch (ScriptException ex)
    {
      ex.printStackTrace();
    }
    for (TokenType t : TokenType.values())
    {
      Pattern pattern = Pattern.compile(t.getRegex());
      Matcher matcher = pattern.matcher(text);
      
      String found = null;
      if (matcher.find()) {
        found = matcher.group();
      }
      if ((found != null) && 
        (text.indexOf(found) == 0))
      {
        found = ChatParser.escapeAll(found);
        
        text = text.replaceFirst(found, "").trim();
        System.out.println(t);
        break;
      }
    }
  }
  
  private static enum TokenType
  {
    VARIABLE_DECLERATION("\\s*var\\s*\\w+\\s*=\\s*.+\\s*;+\\s*", Variable.class, "declare"),  VARIABLE_CHANGE("\\s*\\w+\\s*=\\s*.+\\s*;\\s*", Variable.class, "change"),  METHOD_CALL("\\s*\\w+\\s*\\([[a-zA-Z0-9_\\s]*\\,?]*\\)\\s*;+\\s*", Methods.class, "call"),  OPERATION("\\s*\\w+\\s*[[\\+|-|\\*|/\\^]\\s*\\w+\\s*]", Mathematic.class, "operation");
    
    private String regex;
    private Class<?> clazz;
    private String method;
    
    private TokenType(String regex, Class<?> clazz, String method)
    {
      this.regex = regex;
      this.clazz = clazz;
      this.method = method;
    }
    
    public static TokenType getByRegex(String text)
    {
      for (TokenType t : ) {
        if (text.contains(t.regex)) {
          return t;
        }
      }
      return null;
    }
    
    public String getRegex()
    {
      return this.regex;
    }
  }
}
