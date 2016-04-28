package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.AndItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.CompareOperator;
import de.pylamo.spellmaker.gui.SpellItems.Condition.CompareOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ConditionComparePanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.OrItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.Window;

class ConditionParser
{
  private final int end = 0;
  private int type = 0;
  private String operatorstring;
  private IParameter left = null;
  private IParameter right = null;
  private IParameter subparam = null;
  private Window w;
  
  public IParameter parse(String line, Window w)
  {
    this.w = w;
    
    line = line.trim();
    String subs = line.trim();
    int and = 2;
    int or = 1;
    if (line.contains("||"))
    {
      String nextconds = line.substring(line.indexOf("||") + 2);
      subs = line.substring(0, line.indexOf("||"));
      
      ConditionParser subcond = new ConditionParser();
      this.subparam = subcond.parse(nextconds, w);
      this.type = or;
    }
    else if (line.contains("&&"))
    {
      String nextconds = line.substring(line.indexOf("&&") + 2);
      subs = line.substring(0, line.indexOf("&&"));
      ConditionParser subcond = new ConditionParser();
      this.subparam = subcond.parse(nextconds, w);
      this.type = and;
    }
    if (line.contains("<="))
    {
      this.operatorstring = "<=";
      String[] sides = subs.split("<=");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (line.contains(">="))
    {
      this.operatorstring = ">=";
      String[] sides = subs.split(">=");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (line.contains("<"))
    {
      this.operatorstring = "<";
      String[] sides = subs.split("<");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (line.contains(">"))
    {
      this.operatorstring = ">";
      String[] sides = subs.split(">");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (line.contains("=="))
    {
      this.operatorstring = "==";
      String[] sides = subs.split("==");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    else if (line.contains("!="))
    {
      this.operatorstring = "!=";
      String[] sides = subs.split("!=");
      parseRighthand(sides[1]);
      parseLefthand(sides[0]);
    }
    if (this.type == 0)
    {
      ConditionComparePanel ccp = new ConditionComparePanel(false, w);
      if (this.left != null)
      {
        ccp.lefthand.content = this.left;
        ccp.lefthand.add(this.left);
      }
      if (this.right != null)
      {
        ccp.righthand.content = this.right;
        ccp.righthand.add(this.right);
      }
      if (this.operatorstring != null)
      {
        ccp.operator.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(this.operatorstring), false);
        ccp.operator.add(ccp.operator.content);
      }
      return ccp;
    }
    if (this.type == or)
    {
      ConditionComparePanel ccp = new ConditionComparePanel(false, w);
      if (this.left != null)
      {
        ccp.lefthand.content = this.left;
        ccp.lefthand.add(this.left);
      }
      if (this.right != null)
      {
        ccp.righthand.content = this.right;
        ccp.righthand.add(this.right);
      }
      if (this.operatorstring != null)
      {
        ccp.operator.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(this.operatorstring), false);
        ccp.operator.add(ccp.operator.content);
      }
      OrItem oi = new OrItem(false, w);
      oi.lefthand.content = ccp;
      oi.lefthand.add(oi.lefthand.content);
      oi.righthand.content = this.subparam;
      oi.righthand.add(oi.righthand.content);
      return oi;
    }
    if (this.type == and)
    {
      ConditionComparePanel ccp = new ConditionComparePanel(false, w);
      if (this.left != null)
      {
        ccp.lefthand.content = this.left;
        ccp.lefthand.add(this.left);
      }
      if (this.right != null)
      {
        ccp.righthand.content = this.right;
        ccp.righthand.add(this.right);
      }
      if (this.operatorstring != null)
      {
        ccp.operator.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(this.operatorstring), false);
        ccp.operator.add(ccp.operator.content);
      }
      AndItem ai = new AndItem(false, w);
      ai.lefthand.content = ccp;
      ai.lefthand.add(ai.lefthand.content);
      ai.righthand.content = this.subparam;
      ai.righthand.add(ai.righthand.content);
      return ai;
    }
    return null;
  }
  
  void parseRighthand(String line)
  {
    ArgumentParser ap = new ArgumentParser(this.w);
    ap.parse(line);
    this.right = ap.getArgumentPanel(this.w);
  }
  
  void parseLefthand(String line)
  {
    ArgumentParser ap = new ArgumentParser(this.w);
    ap.parse(line);
    this.left = ap.getArgumentPanel(this.w);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\ConditionParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */