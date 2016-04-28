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
