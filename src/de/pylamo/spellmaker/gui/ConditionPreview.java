package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.Condition.AndItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.CompareOperator;
import de.pylamo.spellmaker.gui.SpellItems.Condition.CompareOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ConditionComparePanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseIfItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ForeachItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.IfItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.OrItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.RepeatItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.TryItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.WhileItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.BooleanParameterPanel;
import java.util.HashMap;
import javax.swing.JPanel;

public class ConditionPreview
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  public ConditionPreview(Window w)
  {
    setLayout(new WrapLayout());
    IfItem comp = new IfItem(true, w);
    add(comp);
    w.spellItems.put("if", comp);
    ElseIfItem comp1 = new ElseIfItem(true, w);
    add(comp1);
    w.spellItems.put("elseif", comp1);
    ElseItem comp2 = new ElseItem(true, w);
    add(comp2);
    w.spellItems.put("else", comp2);
    WhileItem comp3 = new WhileItem(true, w);
    add(comp3);
    w.spellItems.put("while", comp3);
    RepeatItem comp4 = new RepeatItem(true, w);
    add(comp4);
    w.spellItems.put("repeat", comp4);
    TryItem comp5 = new TryItem(true, w);
    add(comp5);
    w.spellItems.put("try", comp5);
    ForeachItem comp6 = new ForeachItem(true, w);
    add(comp6);
    w.spellItems.put("foreach", comp6);
    CompareOperatorPanel comp10 = new CompareOperatorPanel(CompareOperator.Equals, true);
    add(comp10);
    w.spellItems.put("==", comp10);
    CompareOperatorPanel comp11 = new CompareOperatorPanel(CompareOperator.NotEquals, true);
    add(comp11);
    w.spellItems.put("!=", comp1);
    CompareOperatorPanel comp12 = new CompareOperatorPanel(CompareOperator.Smaller, true);
    add(comp12);
    w.spellItems.put("<", comp12);
    CompareOperatorPanel comp13 = new CompareOperatorPanel(CompareOperator.SmallerEquals, true);
    add(comp13);
    w.spellItems.put("<=", comp13);
    CompareOperatorPanel comp14 = new CompareOperatorPanel(CompareOperator.Larger, true);
    add(comp14);
    w.spellItems.put(">", comp14);
    CompareOperatorPanel comp15 = new CompareOperatorPanel(CompareOperator.LargerEquals, true);
    add(comp15);
    w.spellItems.put(">=", comp15);
    BooleanParameterPanel comp16 = new BooleanParameterPanel(true);
    add(comp16);
    AndItem comp7 = new AndItem(true, w);
    add(comp7);
    w.spellItems.put("and", comp7);
    OrItem comp8 = new OrItem(true, w);
    add(comp8);
    w.spellItems.put("or", comp8);
    ConditionComparePanel comp9 = new ConditionComparePanel(true, w);
    add(comp9);
    w.spellItems.put("compare", comp9);
  }
}
