package com.ancient.parameter;

public class Parameter
{
  private ParameterType type;
  private String alias;
  private boolean array;
  
  public Parameter(ParameterType type, String alias, boolean array)
  {
    this.type = type;
    this.alias = alias;
    this.array = array;
  }
  
  public Parameter(ParameterType type, boolean array)
  {
    this.type = type;
    this.array = array;
    this.alias = "";
  }
  
  public String getAlias()
  {
    return this.alias;
  }
  
  public ParameterType getType()
  {
    return this.type;
  }
  
  public boolean isArray()
  {
    return this.array;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\parameter\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */