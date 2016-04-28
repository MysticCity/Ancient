package de.pylamo.spellmaker.gui.SpellItems.Condition;

public enum CompareOperator
{
  Equals("=="),  NotEquals("!="),  Smaller("<"),  Larger(">"),  SmallerEquals("<="),  LargerEquals(">=");
  
  private final String token;
  
  private CompareOperator(String token)
  {
    this.token = token;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public static CompareOperator getOperatorByToken(String s)
  {
    if (s.equalsIgnoreCase("==")) {
      return Equals;
    }
    if (s.equalsIgnoreCase("!=")) {
      return NotEquals;
    }
    if (s.equalsIgnoreCase(">=")) {
      return LargerEquals;
    }
    if (s.equalsIgnoreCase("<=")) {
      return SmallerEquals;
    }
    if (s.equalsIgnoreCase(">")) {
      return Larger;
    }
    if (s.equalsIgnoreCase("<")) {
      return Smaller;
    }
    return null;
  }
}
