package com.ancientshores.Ancient.Quests;

public class Objective
{
  public boolean fulfilled;
  public ObjectiveType type;
  
  public Objective(String s)
  {
    parseObjective(s);
  }
  
  public void parseObjective(String line)
  {
    if (line.toLowerCase().startsWith("move")) {
      this.type = ObjectiveType.Move;
    } else if (line.toLowerCase().startsWith("kill")) {
      this.type = ObjectiveType.Kill;
    } else if (line.toLowerCase().startsWith("mine")) {
      this.type = ObjectiveType.Mine;
    }
  }
  
  public void checkObjective() {}
  
  public static enum ObjectiveType
  {
    Move,  Kill,  Mine;
    
    private ObjectiveType() {}
  }
}
