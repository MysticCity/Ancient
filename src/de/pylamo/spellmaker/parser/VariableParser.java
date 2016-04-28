package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperationItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperator;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;
import de.pylamo.spellmaker.gui.Window;
import java.util.ArrayList;
import java.util.regex.Pattern;

class VariableParser
{
  private final Window w;
  
  public VariableParser(Window w)
  {
    this.w = w;
  }
  
  public ISpellItem getVarPanel(String line)
  {
    line = line.trim();
    VariableOperator operator = null;
    String[] sides = null;
    if (line.contains(" += "))
    {
      sides = line.split(Pattern.quote(" += "));
      operator = VariableOperator.PlusAssign;
    }
    else if (line.contains(" -= "))
    {
      sides = line.split(Pattern.quote(" -= "));
      operator = VariableOperator.MinusAssign;
    }
    else if (line.contains(" *= "))
    {
      sides = line.split(Pattern.quote(" *= "));
      operator = VariableOperator.MultiplyAssign;
    }
    else if (line.contains(" /= "))
    {
      sides = line.split(Pattern.quote(" /= "));
      operator = VariableOperator.DividedAssign;
    }
    else if (line.contains(" = "))
    {
      sides = line.split(Pattern.quote(" = "));
      operator = VariableOperator.Assign;
    }
    VariablePanel vp = null;
    if ((sides != null) && (sides.length > 0))
    {
      String varname = sides[0].substring(3).trim();
      vp = new VariablePanel(varname, false, this.w);
    }
    try
    {
      VariableOperatorPanel voperator = null;
      if (operator != null) {
        voperator = new VariableOperatorPanel(operator, operator.getTooltip(), false);
      }
      VariableOperationItem vop = new VariableOperationItem(false, this.w);
      ((ParameterSlot)vop.slots.get(1)).content = voperator;
      if (voperator != null) {
        ((ParameterSlot)vop.slots.get(1)).add(voperator);
      }
      if (vp != null) {
        ((ParameterSlot)vop.slots.get(0)).add(vp);
      }
      ((ParameterSlot)vop.slots.get(0)).content = vp;
      if ((sides != null) && (sides.length > 1))
      {
        ArgumentParser ap = new ArgumentParser(this.w);
        ap.parse(sides[1]);
        IParameter righthand = ap.getArgumentPanel(this.w);
        ((ParameterSlot)vop.slots.get(2)).content = righthand;
        if (righthand != null) {
          ((ParameterSlot)vop.slots.get(2)).add(righthand);
        }
      }
      return vop;
    }
    catch (Exception ignored) {}
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\VariableParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */