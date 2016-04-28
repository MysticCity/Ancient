package de.pylamo.spellmaker;

public enum Parameters
{
  AttackedEntity(0, "<html>returns the attacked entity, can only be used in attack/damagebyentity events</html>", "Entity"),  Attacker(0, "<html>returns the attacker, can only be used in damage/attack events</html>", "Entity"),  BlockInSight(1, "<html>returns the nearest block in sight of the player</html>", "Location"),  ChangedBlock(0, "Returns the location of the broken block of a blockbreakevent", "Location"),  NearestEntities(2, "<html>returns the nearest entities of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", "Entity"),  NearestEntityInSight(1, "<html>returns the nearest hostile entity in sight of of the caster<br> Textfield: range of parameter</html>", "Entity"),  NearestEntity(1, "<html>returns the nearest entity of the caster<br> Textfield: range of parameter</html>", "Entity"),  NearestHostileEntities(2, "<html>returns the nearest hostile entities of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", "Entity"),  NearestHostileEntityInSight(1, "<html>returns the nearest hostile entity in sight of the caster<br> Textfield 1: range of parameter</html>", "Entity"),  NearestHostilePlayers(2, "<html>returns the nearest hostile players of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", "Player"),  NearestPlayerInSight(1, "<html>returns the nearest players in sight of the caster<br> Textfield: range of parameter</html>", "Player"),  NearestPlayer(1, "<html>returns the nearest player of the caster<br> Textfield: range of parameter</html>", "Player"),  NearestPlayers(2, "<html>returns the nearest players of the caster<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", "Player"),  PartyMembers(2, "<html>returns members of the party<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", "Player"),  Player(0, "<html>returns the player who is casting the spell</html>", "Player"),  TargetInSight(1, "<html>returns the locaiton of the first target in sight (either an entity or the block you look at)<br> Textfield 1: range of parameter</html>", "Location");
  
  private int amount;
  private String description;
  private String returntype;
  
  private Parameters(int amount, String description, String returntype)
  {
    this.amount = amount;
    this.description = description;
    this.returntype = returntype;
  }
  
  public int getAmount()
  {
    return this.amount;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getReturntype()
  {
    return this.returntype;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\Parameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */