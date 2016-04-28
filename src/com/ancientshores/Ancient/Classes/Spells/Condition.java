package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import org.bukkit.entity.Player;

public class Condition
{
  Operators operator;
  Condition subcond;
  Operators subcondoperator;
  ArgumentInformationObject lefthand;
  ArgumentInformationObject righthand;
  boolean opposite = false;
  final Spell mSpell;
  
  public Condition(String cond, Spell mSpell)
  {
    this.mSpell = mSpell;
    cond = cond.trim();
    String subs = cond.trim();
    if (cond.contains("||"))
    {
      String nextconds = cond.substring(cond.indexOf("||") + 2);
      cond = cond.substring(0, cond.indexOf("||") - 1).trim();
      this.subcond = new Condition(nextconds, mSpell);
      this.subcondoperator = Operators.or;
    }
    if (cond.contains("&&"))
    {
      String nextconds = cond.substring(cond.indexOf("&&") + 2);
      cond = cond.substring(0, cond.indexOf("&&") - 1).trim();
      this.subcond = new Condition(nextconds, mSpell);
      this.subcondoperator = Operators.and;
    }
    subs = cond;
    if (cond.contains("==="))
    {
      this.operator = Operators.isinstanceof;
      String[] sides = subs.split("===");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (cond.contains("<="))
    {
      this.operator = Operators.smallerequals;
      String[] sides = subs.split("<=");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (cond.contains(">="))
    {
      this.operator = Operators.largerequals;
      String[] sides = subs.split(">=");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (cond.contains("<"))
    {
      this.operator = Operators.smaller;
      String[] sides = subs.split("<");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (cond.contains(">"))
    {
      this.operator = Operators.larger;
      String[] sides = subs.split(">");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (cond.contains("=="))
    {
      this.operator = Operators.equals;
      String[] sides = subs.split("==");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (cond.contains("!="))
    {
      this.operator = Operators.notequals;
      String[] sides = subs.split("!=");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    if (cond.trim().startsWith("!")) {
      this.opposite = true;
    }
  }
  
  public boolean conditionFulfilled(Player mPlayer, SpellInformationObject so)
  {
    boolean fulfilled = false;
    try
    {
      Object l = this.lefthand.getArgument(so, mPlayer);
      Object r = this.righthand.getArgument(so, mPlayer);
      if (((l instanceof Number)) && ((r instanceof Number)))
      {
        double li = ((Number)l).doubleValue();
        double ri = ((Number)r).doubleValue();
        switch (this.operator)
        {
        case smaller: 
          fulfilled = li < ri;
          break;
        case smallerequals: 
          fulfilled = li <= ri;
          break;
        case equals: 
          fulfilled = li == ri;
          break;
        case larger: 
          fulfilled = li > ri;
          break;
        case largerequals: 
          fulfilled = li >= ri;
          break;
        case notequals: 
          fulfilled = li != ri;
          break;
        }
      }
      if (((l instanceof String)) && ((r instanceof String)))
      {
        String ls = (String)l;
        String rs = (String)r;
        switch (this.operator)
        {
        case equals: 
          fulfilled = ls.equalsIgnoreCase(rs);
          break;
        case notequals: 
          fulfilled = !ls.equalsIgnoreCase(rs);
          break;
        }
      }
      if (((l instanceof Boolean)) && ((r instanceof Boolean)))
      {
        boolean ls = ((Boolean)l).booleanValue();
        boolean rs = ((Boolean)r).booleanValue();
        switch (this.operator)
        {
        case equals: 
          fulfilled = ls == rs;
          break;
        case notequals: 
          fulfilled = ls != rs;
          break;
        }
      }
      if (l.getClass().equals(r.getClass())) {
        switch (this.operator)
        {
        case equals: 
          fulfilled = l.equals(r);
          break;
        case notequals: 
          fulfilled = !l.equals(r);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (this.subcond != null) {
      switch (this.subcondoperator)
      {
      case or: 
        fulfilled = (fulfilled) || (this.subcond.conditionFulfilled(mPlayer, so));
        break;
      case and: 
        fulfilled = (fulfilled) && (this.subcond.conditionFulfilled(mPlayer, so));
        break;
      }
    }
    return fulfilled;
  }
  
  void parseRighthand(String right)
  {
    right = right.trim();
    if (right.startsWith("!")) {
      right = right.substring(1);
    }
    right = right.trim();
    if (right.startsWith("(")) {
      right = right.substring(1);
    }
    this.righthand = IArgument.parseArgumentByString(right, this.mSpell);
  }
  
  void parseLefthand(String left)
  {
    left = left.trim();
    if (left.startsWith("!")) {
      left = left.substring(1);
    }
    left = left.trim();
    if (left.startsWith("(")) {
      left = left.substring(1);
    }
    this.lefthand = IArgument.parseArgumentByString(left, this.mSpell);
  }
  
  static enum Operators
  {
    smaller,  smallerequals,  equals,  larger,  largerequals,  notequals,  isinstanceof,  or,  and;
    
    private Operators() {}
  }
}
