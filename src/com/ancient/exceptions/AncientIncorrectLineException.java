package com.ancient.exceptions;

public class AncientIncorrectLineException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  
  public AncientIncorrectLineException(String string)
  {
    super("Incorrect line: " + string + "\nThe spell can't be parsed.");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\exceptions\AncientIncorrectLineException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */