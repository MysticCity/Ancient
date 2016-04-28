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
