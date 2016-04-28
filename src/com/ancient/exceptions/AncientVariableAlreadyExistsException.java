package com.ancient.exceptions;

public class AncientVariableAlreadyExistsException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  
  public AncientVariableAlreadyExistsException(String spellName, String className, int line, String varName)
  {
    super(className + " in spell " + spellName + " in line " + line + " wanted to create an variable called " + varName + ", that already exists.");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\exceptions\AncientVariableAlreadyExistsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */