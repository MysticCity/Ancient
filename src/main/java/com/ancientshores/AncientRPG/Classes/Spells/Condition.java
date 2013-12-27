package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Conditions.IArgument;
import org.bukkit.entity.Player;

public class Condition {
    Operators operator;
    Condition subcond;
    Operators subcondoperator;
    ArgumentInformationObject lefthand;
    ArgumentInformationObject righthand;
    boolean opposite = false;
    final Spell mSpell;

    public Condition(String cond, Spell mSpell) {
        this.mSpell = mSpell;
        cond = cond.trim();
        String subs = cond.trim();
        if (cond.contains("||")) {
            String nextconds = cond.substring(cond.indexOf("||") + 2);
            cond = cond.substring(0, cond.indexOf("||") - 1).trim();
            subcond = new Condition(nextconds, mSpell);
            subcondoperator = Operators.or;
        }
        if (cond.contains("&&")) {
            String nextconds = cond.substring(cond.indexOf("&&") + 2);
            cond = cond.substring(0, cond.indexOf("&&") - 1).trim();
            subcond = new Condition(nextconds, mSpell);
            subcondoperator = Operators.and;
        }
        subs = cond;
        if (cond.contains("===")) {
            operator = Operators.isinstanceof;
            String[] sides = subs.split("===");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (cond.contains("<=")) {
            operator = Operators.smallerequals;
            String[] sides = subs.split("<=");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (cond.contains(">=")) {
            operator = Operators.largerequals;
            String[] sides = subs.split(">=");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (cond.contains("<")) {
            operator = Operators.smaller;
            String[] sides = subs.split("<");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (cond.contains(">")) {
            operator = Operators.larger;
            String[] sides = subs.split(">");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (cond.contains("==")) {
            operator = Operators.equals;
            String[] sides = subs.split("==");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        } else if (cond.contains("!=")) {
            operator = Operators.notequals;
            String[] sides = subs.split("!=");
            parseRighthand(sides[1]);
            parseLefthand(sides[0]);
        }
        if (cond.trim().startsWith("!")) {
            opposite = true;
        }
    }

    public boolean conditionFulfilled(Player mPlayer, SpellInformationObject so) {
        boolean fulfilled = false;
        try {
            Object l = lefthand.getArgument(so, mPlayer);
            Object r = righthand.getArgument(so, mPlayer);
            if ((l instanceof Number) && (r instanceof Number)) {
                double li = ((Number) l).doubleValue();
                double ri = ((Number) r).doubleValue();
                switch (operator) {
                    case smaller:
                        fulfilled = (li < ri);
                        break;
                    case smallerequals:
                        fulfilled = (li <= ri);
                        break;
                    case equals:
                        fulfilled = (li == ri);
                        break;
                    case larger:
                        fulfilled = (li > ri);
                        break;
                    case largerequals:
                        fulfilled = (li >= ri);
                        break;
                    case notequals:
                        fulfilled = (li != ri);
                        break;
                    default:
                        break;
                }
            }
            if (l instanceof String && r instanceof String) {
                String ls = (String) l;
                String rs = (String) r;
                switch (operator) {
                    case equals:
                        fulfilled = (ls.equalsIgnoreCase(rs));
                        break;
                    case notequals:
                        fulfilled = !(ls.equalsIgnoreCase(rs));
                        break;
                    default:
                        break;
                }
            }
            if (l instanceof Boolean && r instanceof Boolean) {
                boolean ls = (Boolean) l;
                boolean rs = (Boolean) r;
                switch (operator) {
                    case equals:
                        fulfilled = (ls == rs);
                        break;
                    case notequals:
                        fulfilled = !(ls == rs);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (subcond != null) {
            switch (subcondoperator) {
                case or:
                    fulfilled = (fulfilled || subcond.conditionFulfilled(mPlayer, so));
                    break;
                case and:
                    fulfilled = (fulfilled && subcond.conditionFulfilled(mPlayer, so));
                    break;
                default:
                    break;
            }
        }
        return fulfilled;
    }

    void parseRighthand(String right) {
        right = right.trim();
        if (right.startsWith("!")) {
            right = right.substring(1);
        }
        right = right.trim();
        if (right.startsWith("(")) {
            right = right.substring(1);
        }
        this.righthand = IArgument.parseArgumentByString(right, mSpell);
    }

    void parseLefthand(String left) {
        left = left.trim();
        if (left.startsWith("!")) {
            left = left.substring(1);
        }
        left = left.trim();
        if (left.startsWith("(")) {
            left = left.substring(1);
        }
        this.lefthand = IArgument.parseArgumentByString(left, mSpell);
    }

    enum Operators {
        smaller, smallerequals, equals, larger, largerequals, notequals, isinstanceof, or, and;
    }
}
