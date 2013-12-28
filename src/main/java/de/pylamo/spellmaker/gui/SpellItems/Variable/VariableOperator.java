package de.pylamo.spellmaker.gui.SpellItems.Variable;

public enum VariableOperator {
    Assign("=", "Assigns a value to a variable"), PlusAssign("+=", "Adds the value to the variable"), MinusAssign("-=", "Substracts the value from the variable"), MultiplyAssign("*=", "Multiplies the variable with the value"), DividedAssign("/=", "Divides the variable by the value");

    private final String token;
    private final String tooltip;

    private VariableOperator(String token, String tooltip) {
        this.token = token;
        this.tooltip = tooltip;
    }

    public String getToken() {
        return token;
    }

    public static VariableOperator getOperatorByToken(String s) {
        if (s.equals("=")) {
            return Assign;
        } else if (s.equals("+=")) {
            return PlusAssign;
        } else if (s.equals("-=")) {
            return MinusAssign;
        } else if (s.equals("*=")) {
            return MultiplyAssign;
        } else if (s.equals("/=")) {
            return DividedAssign;
        }
        return null;
    }

    public String getTooltip() {
        return tooltip;
    }
}