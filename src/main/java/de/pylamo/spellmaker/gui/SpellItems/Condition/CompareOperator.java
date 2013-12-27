package de.pylamo.spellmaker.gui.SpellItems.Condition;


public enum CompareOperator {
    Equals("=="), NotEquals("!="), Smaller("<"), Larger(">"), SmallerEquals("<="), LargerEquals(">=");

    private final String token;

    private CompareOperator(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static CompareOperator getOperatorByToken(String s) {
        if (s.equals("==")) {
            return Equals;
        }
        if (s.equals("!=")) {
            return NotEquals;
        } else if (s.equals(">=")) {
            return LargerEquals;
        } else if (s.equals("<=")) {
            return SmallerEquals;
        } else if (s.equals(">")) {
            return Larger;
        } else if (s.equals("<")) {
            return Smaller;
        }
        return null;
    }
}
