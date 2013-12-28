package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.Condition.*;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.BooleanParameterPanel;

import javax.swing.*;

public class ConditionPreview extends JPanel {
    private static final long serialVersionUID = 1L;

    public ConditionPreview(Window w) {
        this.setLayout(new WrapLayout());
        final IfItem comp = new IfItem(true, w);
        this.add(comp);
        w.spellItems.put("if", comp);
        final ElseIfItem comp1 = new ElseIfItem(true, w);
        this.add(comp1);
        w.spellItems.put("elseif", comp1);
        final ElseItem comp2 = new ElseItem(true, w);
        this.add(comp2);
        w.spellItems.put("else", comp2);
        final WhileItem comp3 = new WhileItem(true, w);
        this.add(comp3);
        w.spellItems.put("while", comp3);
        final RepeatItem comp4 = new RepeatItem(true, w);
        this.add(comp4);
        w.spellItems.put("repeat", comp4);
        final TryItem comp5 = new TryItem(true, w);
        this.add(comp5);
        w.spellItems.put("try", comp5);
        final ForeachItem comp6 = new ForeachItem(true, w);
        this.add(comp6);
        w.spellItems.put("foreach", comp6);
        final CompareOperatorPanel comp10 = new CompareOperatorPanel(CompareOperator.Equals, true);
        this.add(comp10);
        w.spellItems.put("==", comp10);
        final CompareOperatorPanel comp11 = new CompareOperatorPanel(CompareOperator.NotEquals, true);
        this.add(comp11);
        w.spellItems.put("!=", comp1);
        final CompareOperatorPanel comp12 = new CompareOperatorPanel(CompareOperator.Smaller, true);
        this.add(comp12);
        w.spellItems.put("<", comp12);
        final CompareOperatorPanel comp13 = new CompareOperatorPanel(CompareOperator.SmallerEquals, true);
        this.add(comp13);
        w.spellItems.put("<=", comp13);
        final CompareOperatorPanel comp14 = new CompareOperatorPanel(CompareOperator.Larger, true);
        this.add(comp14);
        w.spellItems.put(">", comp14);
        CompareOperatorPanel comp15 = new CompareOperatorPanel(CompareOperator.LargerEquals, true);
        this.add(comp15);
        w.spellItems.put(">=", comp15);
        final BooleanParameterPanel comp16 = new BooleanParameterPanel(true);
        this.add(comp16);
        final AndItem comp7 = new AndItem(true, w);
        this.add(comp7);
        w.spellItems.put("and", comp7);
        final OrItem comp8 = new OrItem(true, w);
        this.add(comp8);
        w.spellItems.put("or", comp8);
        final ConditionComparePanel comp9 = new ConditionComparePanel(true, w);
        this.add(comp9);
        w.spellItems.put("compare", comp9);
    }
}