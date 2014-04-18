package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.*;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.Window;

class ConditionParser {
    private final int end = 0;
    private int type = end;
    private String operatorstring;

    private IParameter left = null;
    private IParameter right = null;
    private IParameter subparam = null;
    private Window w;

    public IParameter parse(String line, Window w) {
        this.w = w;
        ConditionParser subcond;
        line = line.trim();
        String subs = line.trim();
        int and = 2;
        int or = 1;
        if (line.contains("||")) {
            String nextconds = line.substring(line.indexOf("||") + 2);
            subs = line.substring(0, line.indexOf("||"));

            subcond = new ConditionParser();
            subparam = subcond.parse(nextconds, w);
            type = or;
        } else if (line.contains("&&")) {
            String nextconds = line.substring(line.indexOf("&&") + 2);
            subs = line.substring(0, line.indexOf("&&"));
            subcond = new ConditionParser();
            subparam = subcond.parse(nextconds, w);
            type = and;
        }
        if (line.contains("<=")) {
            operatorstring = "<=";
            String[] sides = subs.split("<=");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (line.contains(">=")) {
            operatorstring = ">=";
            String[] sides = subs.split(">=");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (line.contains("<")) {
            operatorstring = "<";
            String[] sides = subs.split("<");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (line.contains(">")) {
            operatorstring = ">";
            String[] sides = subs.split(">");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (line.contains("==")) {
            operatorstring = "==";
            String[] sides = subs.split("==");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (line.contains("!=")) {
            operatorstring = "!=";
            String[] sides = subs.split("!=");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        }
        if (type == end) {
            ConditionComparePanel ccp = new ConditionComparePanel(false, w);
            if (left != null) {
                ccp.lefthand.content = left;
                ccp.lefthand.add(left);
            }
            if (right != null) {
                ccp.righthand.content = right;
                ccp.righthand.add(right);
            }
            if (operatorstring != null) {
                ccp.operator.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(operatorstring), false);
                ccp.operator.add(ccp.operator.content);
            }
            return ccp;
        } else if (type == or) {
            ConditionComparePanel ccp = new ConditionComparePanel(false, w);

            if (left != null) {
                ccp.lefthand.content = left;
                ccp.lefthand.add(left);
            }
            if (right != null) {
                ccp.righthand.content = right;
                ccp.righthand.add(right);
            }
            if (operatorstring != null) {
                ccp.operator.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(operatorstring), false);
                ccp.operator.add(ccp.operator.content);
            }
            OrItem oi = new OrItem(false, w);
            oi.lefthand.content = ccp;
            oi.lefthand.add(oi.lefthand.content);
            oi.righthand.content = subparam;
            oi.righthand.add(oi.righthand.content);
            return oi;
        } else if (type == and) {
            ConditionComparePanel ccp = new ConditionComparePanel(false, w);
            if (left != null) {
                ccp.lefthand.content = left;
                ccp.lefthand.add(left);
            }
            if (right != null) {
                ccp.righthand.content = right;
                ccp.righthand.add(right);
            }

            if (operatorstring != null) {
                ccp.operator.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(operatorstring), false);
                ccp.operator.add(ccp.operator.content);

            }
            AndItem ai = new AndItem(false, w);
            ai.lefthand.content = ccp;
            ai.lefthand.add(ai.lefthand.content);
            ai.righthand.content = subparam;
            ai.righthand.add(ai.righthand.content);
            return ai;
        }
        return null;
    }

    void parseRighthand(String line) {
        ArgumentParser ap = new ArgumentParser(w);
        ap.parse(line);
        right = ap.getArgumentPanel(w);
    }

    void parseLefthand(String line) {
        ArgumentParser ap = new ArgumentParser(w);
        ap.parse(line);
        left = ap.getArgumentPanel(w);
    }
}