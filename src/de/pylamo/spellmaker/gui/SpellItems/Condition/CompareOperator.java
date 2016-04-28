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
        if (s.equalsIgnoreCase("==")) {
            return Equals;
        }
        if (s.equalsIgnoreCase("!=")) {
            return NotEquals;
        } else if (s.equalsIgnoreCase(">=")) {
            return LargerEquals;
        } else if (s.equalsIgnoreCase("<=")) {
            return SmallerEquals;
        } else if (s.equalsIgnoreCase(">")) {
            return Larger;
        } else if (s.equalsIgnoreCase("<")) {
            return Smaller;
        }
        return null;
    }
}