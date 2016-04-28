package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import java.awt.Color;
import java.util.HashSet;

public enum Parameter
{
  All(Color.WHITE),  Player(Color.RED),  Condition(new Color(108, 45, 199)),  CompareItem(new Color(255, 106, 0)),  String(Color.PINK),  Entity(Color.CYAN),  Location(Color.YELLOW),  Number(Color.GREEN),  Material(new Color(189, 22, 255)),  Boolean(Color.ORANGE),  Void(Color.WHITE),  Variable(Color.LIGHT_GRAY),  Operator(Color.MAGENTA),  CompareOperator(new Color(255, 168, 236)),  UUID(new Color(164, 114, 83));
  
  private final Color c;
  
  private Parameter(Color bg)
  {
    this.c = bg;
  }
  
  public Color getColor()
  {
    return this.c;
  }
  
  public static Parameter getParameterByName(String s)
  {
    for (Parameter p : ) {
      if (p.name().equalsIgnoreCase(s)) {
        return p;
      }
    }
    return null;
  }
  
  public HashSet<Parameter> getPossibleTypes()
  {
    HashSet<Parameter> set = new HashSet();
    switch (this)
    {
    case All: 
      set.add(Player);
      set.add(Location);
      set.add(Boolean);
      set.add(Material);
      set.add(Entity);
      set.add(Number);
      set.add(String);
      set.add(Variable);
      set.add(Operator);
      set.add(All);
      break;
    case Variable: 
      set.add(Variable);
      set.add(All);
      break;
    case Boolean: 
      set.add(Boolean);
      set.add(Variable);
      set.add(All);
      break;
    case Entity: 
      set.add(Entity);
      set.add(Location);
      set.add(Variable);
      set.add(All);
      break;
    case Location: 
      set.add(Location);
      set.add(Variable);
      set.add(All);
      break;
    case Material: 
      set.add(Number);
      set.add(Material);
      set.add(Variable);
      set.add(All);
      break;
    case Number: 
      set.add(Number);
      set.add(Variable);
      set.add(All);
      break;
    case Player: 
      set.add(Player);
      set.add(Location);
      set.add(Entity);
      set.add(Variable);
      set.add(All);
      break;
    case CompareItem: 
      set.add(String);
      set.add(Number);
      set.add(Boolean);
      set.add(Variable);
      set.add(All);
    case String: 
      set.add(String);
      set.add(Variable);
      set.add(All);
      break;
    case Void: 
      break;
    }
    return set;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Parameter\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */