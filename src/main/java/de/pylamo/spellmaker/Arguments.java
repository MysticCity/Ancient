package de.pylamo.spellmaker;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public enum Arguments {
    AreEntities("Checks a collection of locations if all of them are entities.", new String[]{"collection"}, "AreEntities", new ParameterType[]{ParameterType.Location}, ParameterType.Boolean),
    AreLocations("Checks a collection of locations if all of them are locations.", new String[]{"collection"}, "AreLocations", new ParameterType[]{ParameterType.Location}, ParameterType.Boolean),
    ArePlayers("Checks a collection of locations if all of them are players.", new String[]{"collection"}, "ArePlayers", new ParameterType[]{ParameterType.Location}, ParameterType.Boolean),
    BlockExistsInRange("if the specified block exists in the range returns true", new String[]{"location", "material", "range"}, "BlockExistsInRange", new ParameterType[]{ParameterType.Location, ParameterType.Material, ParameterType.Number}, ParameterType.Boolean),
    CastTo("casts the collection of players to the specified type.", new String[]{"playercollection", "newtype"}, "CastTo", new ParameterType[]{ParameterType.Player, ParameterType.String}, ParameterType.Void),
    EntitiesInRage("returns the amount of entities in the range from the location.", new String[]{"location", "range"}, "EntitiesInRage", new ParameterType[]{ParameterType.Location, ParameterType.Number}, ParameterType.Number),
    FirstGapAbove("Returns the first gap above the specified location with atleast the amount of free blocks", new String[]{"location", "free blocks"}, "FirstGapAbove", new ParameterType[]{ParameterType.Location, ParameterType.Number}, ParameterType.Location),
    FirstGapBelow("Returns the first gap below the specified location with atleast the amount of free blocks", new String[]{"location", "free blocks"}, "FirstGapBelow", new ParameterType[]{ParameterType.Location, ParameterType.Number}, ParameterType.Location),
    GetAmountOf("Returns the amount of elements in the collection", new String[]{"collection"}, "GetAmountOf", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetAttackedEntity("Returns the type of the attacked entity", new String[]{}, "GetAttackedEntity", new ParameterType[]{}, ParameterType.Entity),
    GetAttacker("Returns the type of the attacker", new String[]{}, "GetAttacker", new ParameterType[]{}, ParameterType.String),
    GetBlockRelative("Returns the block relative to the player.", new String[]{"location", "forward", "sideward", "upward"}, "GetBlockRelative", new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number}, ParameterType.Location),
    GetBoots("Returns the id of the players boots", new String[]{"player"}, "GetBoots", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetBrokenBlockType("Returns the id of the broken block, only usable in block break event", new String[]{}, "GetBrokenBlockType", new ParameterType[]{}, ParameterType.Material),
    GetChatArgument("<html>Returns the specified chat argument, starting with 0 <br>arguments are split by spaces in the command, can only be used in chatcommands</html>", new String[]{"index"}, "GetChatArgument", new ParameterType[]{ParameterType.Number}, ParameterType.Number),
    GetChatArgumentLength("Returns the amount of arguments in the chat command, can only be used in chat Commands", new String[]{}, "GetChatArgumentLength", new ParameterType[]{}, ParameterType.Number),
    GetChestplate("Returns the id of the chestplate", new String[]{"player"}, "GetChestplate", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetClassName("Returns the name of the players class", new String[]{"player"}, "GetClassName", new ParameterType[]{ParameterType.Player}, ParameterType.String),
    GetCube("<html>returns the outline of a cube with the specified radius at the given locaiton<br>Parameter 1: the middle point of the Coboid<br>Parameter 2: the radius of the cube</html>", new String[]{"location", "radius"}, "GetCube", new ParameterType[]{ParameterType.Location, ParameterType.Number}, ParameterType.Location),
    GetCuboid("<html>returns the outline of a cuboid with the specified dimensions at the given location<br>Parameter 1: the middle point of the cuboid<br>Parameter 2: the length foreward and backward<br>Parameter 3: the length up and downwards<br>Parameter 4: the length to the left and the right of the location</html>", new String[]{"location", "foreward", "upward", "sideward"}, "GetCuboid", new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number}, ParameterType.Location),
    GetDamage("returns the damage of the damage event", new String[]{}, "GetDamage", new ParameterType[]{}, ParameterType.Number),
    GetDamageCause("Returns the name of the damage cause", new String[]{}, "GetDamageCause", new ParameterType[]{}, ParameterType.String),
    GetDayTime("Returns the time at the specified location", new String[]{"world"}, "GetDayTime", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetDistance("Returns the distance between two locations in the same world", new String[]{"location1", "location2"}, "GetDistance", new ParameterType[]{ParameterType.Location, ParameterType.Location}, ParameterType.Number),
    GetEntitiesAround("Returns the amount of entities around the location within the specified range", new String[]{"location", "range", "amount"}, "GetEntitiesAround", new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number}, ParameterType.Entity),
    GetEntityId("Gets the entity id of the entity", new String[]{"entity"}, "GetEntityId", new ParameterType[]{ParameterType.Entity}, ParameterType.String),
    GetEntityName("Returns the name of the entity", new String[]{"entity"}, "GetEntityName", new ParameterType[]{ParameterType.Entity}, ParameterType.String),
    GetEventProperty("Returns the class object of the event with the specified name", new String[]{"objectname"}, "GetEventProperty", new ParameterType[]{ParameterType.String}, ParameterType.Void),
    GetFoodLevel("Returns the food level of the player", new String[]{"player"}, "GetFoodLevel", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetGainedExperience("Returns the gained experience of an ancientrpg gainexperience event", new String[]{}, "GetGainedExperience", new ParameterType[]{}, ParameterType.Number),
    GetGuildName("Returns the guild name of the player", new String[]{"player"}, "GetGuildName", new ParameterType[]{ParameterType.Player}, ParameterType.String),
    GetHealth("Returns the health of the player", new String[]{"entity"}, "GetHealth", new ParameterType[]{ParameterType.Entity}, ParameterType.Number),
    GetHealthPercentage("Returns the health percentage of the entity", new String[]{"entity"}, "GetHealthPercentage", new ParameterType[]{ParameterType.Entity}, ParameterType.Number),
    GetHeldSlotNumber("Returns the slot number of the slot the player currently holds", new String[]{"player"}, "GetHeldSlotNumber", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetHelmet("Returns the id of the helmet the player currently wears", new String[]{"player"}, "GetHelmet", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetItemAmountInItemSlot("Returns the amount of items in the specified item slot", new String[]{"player", "slot"}, "GetItemAmountInItemSlot", new ParameterType[]{ParameterType.Player, ParameterType.Number}, ParameterType.Number),
    GetItemCount("Returns the amount of items of the specified type", new String[]{"player", "material"}, "GetItemCount", new ParameterType[]{ParameterType.Player, ParameterType.Material}, ParameterType.Number),
    GetItemDataInArmorSlot("Returns the data of the item in the specified armor slot", new String[]{"player", "slot"}, "GetItemDataInArmorSlot", new ParameterType[]{ParameterType.Player, ParameterType.Number}, ParameterType.Number),
    GetItemDataInItemSlot("Returns the data of the item in the specified item slot", new String[]{"player", "slot"}, "GetItemDataInItemSlot", new ParameterType[]{ParameterType.Player, ParameterType.Number}, ParameterType.Number),
    GetItemInHands("Returns the amount of items of the specified id in the players inventory", new String[]{"player"}, "GetItemInHands", new ParameterType[]{ParameterType.Player}, ParameterType.Player),
    GetItemInItemSlot("Returns the item type of the item in the specified slot", new String[]{"player", "slot"}, "GetItemInItemSlot", new ParameterType[]{ParameterType.Player, ParameterType.Number}, ParameterType.Number),
    GetItemName("Returns the name of the itemstack in the specified slot of the player", new String[]{"player", "slot"}, "GetItemName", new ParameterType[]{ParameterType.Player, ParameterType.Number}, ParameterType.String),
    GetLeggings("Returns the id of the players leggings", new String[]{"player"}, "GetLeggings", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetLevel("Returns the level of the player", new String[]{"player"}, "GetLevel", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetLightLevel("Returns the light level at the specified location", new String[]{"location"}, "GetLightLevel", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetLocationAt("Returns the location in the specified world with the specified coordinates", new String[]{"worldname", "xcoord", "ycoord", "zcoord"}, "GetLocationAt", new ParameterType[]{ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number}, ParameterType.Location),
    GetMana("Returns the current amount of mana of the player", new String[]{"player"}, "GetMana", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetMaxHealth("Returns the maximum health of the given entity", new String[]{"entity"}, "GetMaxHealth", new ParameterType[]{ParameterType.Entity}, ParameterType.Number),
    GetMaxMana("Returns the maximum mana of the given player", new String[]{"player"}, "GetMaxMana", new ParameterType[]{ParameterType.Player}, ParameterType.Number),
    GetNearbyEntities("Returns the amount of entities nearby the location in the specified range", new String[]{"location", "range", "amount"}, "GetNearbyEntities", new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number}, ParameterType.Entity),
    GetPitch("Returns the pitch of the location", new String[]{"location"}, "GetPitch", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetPlayerByName("finds an online player on the server with the specified name", new String[]{"name"}, "GetPlayerByName", new ParameterType[]{ParameterType.String}, ParameterType.Player),
    GetPlayerName("Returns the name of the player", new String[]{"player"}, "GetPlayerName", new ParameterType[]{ParameterType.String}, ParameterType.String),
    GetProjectileHitLocation("Returns the location the projectile hit, only usable in projectilehitevent", new String[]{}, "GetProjectileHitLocation", new ParameterType[]{}, ParameterType.Location),
    GetProjectileName("Returns the name of the projectile, only usable in projectilehitevent", new String[]{}, "GetProjectileName", new ParameterType[]{}, ParameterType.String),
    GetRandom("Returns a random number between 0 and 100 (both inclusive)", new String[]{}, "GetRandom", new ParameterType[]{}, ParameterType.Number),
    GetRandomBetween("Returns a random number between 2 numbers", new String[]{"number1", "number2"}, "GetRandomBetween", new ParameterType[]{ParameterType.Number, ParameterType.Number}, ParameterType.Number),
    GetShooterOrAttacker("Returns the damager, for example if a player hits you with an arrow the player is returned.", new String[]{}, "GetShooterOrAttacker", new ParameterType[]{}, ParameterType.String),
    GetTime("Returns the time of the world", new String[]{"world"}, "GetTime", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetTimeMillis("Returns the system time in milliseconds", new String[]{}, "GetTimeMillis", new ParameterType[]{}, ParameterType.Number),
    GetUniqueEntityId("Gets the unique entity id of the entity", new String[]{"entity"}, "GetUniqueEntityId", new ParameterType[]{ParameterType.Entity}, ParameterType.String),
    GetWall("<html>returns a wall with the specified dimensions around the location<br>Parameter 1: the middle point of the wall<br>Parameter 2: the length foreward and backward<br>Parameter 3: the length up and downwards<br>Parameter 4: the length to the left and the right of the location</html>", new String[]{"location", "x", "y", "z"}, "GetWall", new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number}, ParameterType.Location),
    GetWorld("Returns the name of the world of the specified location", new String[]{"location"}, "GetWorld", new ParameterType[]{ParameterType.Location}, ParameterType.String),
    GetXCoordinate("Returns the x coordinate of a location", new String[]{"location"}, "GetXCoordinate", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetYaw("Returns the yaw of a location", new String[]{"location"}, "GetYaw", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetYCoordinate("Returns the y coordinate of a location", new String[]{"location"}, "GetYCoordinate", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    GetZCoordinate("Returns the z coordinate of a location", new String[]{"location"}, "GetZCoordinate", new ParameterType[]{ParameterType.Location}, ParameterType.Number),
    HasBuff("Returns true if the player has the specified buff activated, false otherwise", new String[]{"player", "buffname"}, "HasBuff", new ParameterType[]{ParameterType.Player, ParameterType.String}, ParameterType.Boolean),
    HasPermission("Returns true if the player has the specified permission, false otherwise", new String[]{"player", "permission"}, "HasPermission", new ParameterType[]{ParameterType.Player, ParameterType.String}, ParameterType.Number),
    HasPotionEffect("Returns if the entity has the specified potioneffect", new String[]{"entity", "potioneffecttype"}, "HasPotionEffect", new ParameterType[]{ParameterType.Entity, ParameterType.String}, ParameterType.Boolean),
    IsBehind("Returns true if the location is behind the entity, false otherwise.", new String[]{"location", "entity"}, "IsBehind", new ParameterType[]{ParameterType.Location, ParameterType.Entity}, ParameterType.Boolean),
    IsBurning("Returns true if the entity is buring, false otherwise", new String[]{"entity"}, "IsBurning", new ParameterType[]{ParameterType.Entity}, ParameterType.Boolean),
    IsCooldownReady("Returns true if the cooldown with the name is ready", new String[]{"cooldownname"}, "IsCooldownReady", new ParameterType[]{ParameterType.String}, ParameterType.Boolean),
    IsInAir("Returns true if the entity is in air, false otherwise", new String[]{"entity"}, "IsInAir", new ParameterType[]{ParameterType.Entity}, ParameterType.Boolean),
    IsInPredefinedZone("Returns true if the given location is in the defined bounds of the zone", new String[]{"location", "world", "x1", "y1", "z1", "x2", "y2", "z2"}, "IsInPredefinedZone", new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number}, ParameterType.Number),
    IsInSameGuild("Returns true if the 2 players are in the same guild, false otherwise", new String[]{"player1", "player2"}, "IsInSameGuild", new ParameterType[]{ParameterType.Player, ParameterType.Player}, ParameterType.Boolean),
    IsInSameParty("Returns true if the 2 players are in the same party, false otherwise", new String[]{"player1", "player2"}, "IsInSameParty", new ParameterType[]{ParameterType.Player, ParameterType.Player}, ParameterType.Boolean),
    IsInvisible("true if player is invisible, false otherwise", new String[]{"player"}, "IsInvisible", new ParameterType[]{ParameterType.Player}, ParameterType.Boolean),
    IsInWater("Returns true if the location is in water", new String[]{"location"}, "IsInWater", new ParameterType[]{ParameterType.Location}, ParameterType.Boolean),
    IsInZone("Returns true if the location is in a zone specified by 2 other locations", new String[]{"target", "start", "end"}, "IsInZone", new ParameterType[]{ParameterType.Location, ParameterType.Location, ParameterType.Location}, ParameterType.Number),
    IsLookingAt("Returns true if the entity is looking at the other entity", new String[]{"entity", "entity2", "range"}, "IsLookingAt", new ParameterType[]{ParameterType.Entity, ParameterType.Entity, ParameterType.Number}, ParameterType.Boolean),
    IsRightClicked("Returns true if the interactevent is right clicked", new String[]{}, "IsRightClicked", new ParameterType[]{}, ParameterType.Boolean),
    IsSneaking("Returns true if the player is sneaking, false otherwise", new String[]{"player"}, "IsSneaking", new ParameterType[]{ParameterType.Player}, ParameterType.Boolean),
    ParseNumber("Parses a string and tries to convert it into a number", new String[]{"numberasstring"}, "ParseNumber", new ParameterType[]{ParameterType.String}, ParameterType.Number),
    PlayerExists("Returns true if the player with the specified exists and is online on the server, false otherwise", new String[]{"playername"}, "PlayerExists", new ParameterType[]{ParameterType.String}, ParameterType.Boolean),
    PlayersInRange("Returns the amount of players in range", new String[]{"location", "range"}, "PlayersInRange", new ParameterType[]{ParameterType.Location, ParameterType.Number}, ParameterType.Number),
    Replace("Replaces the searchstring in the source by the replacestring", new String[]{"source", "searchstring", "replacestring"}, "Replace", new ParameterType[]{ParameterType.String, ParameterType.String, ParameterType.String}, ParameterType.String),
    Round("Rounds the specified number", new String[]{"number"}, "Round", new ParameterType[]{ParameterType.Number}, ParameterType.Number),
    TopBlockAt("Returns the top block at the specified location", new String[]{"location"}, "TopBlockAt", new ParameterType[]{ParameterType.Location}, ParameterType.Location);

    private String description;
    private String[] argnames;
    private String name;
    private ParameterType[] params;
    private ParameterType returntype;

    private Arguments(String description, String[] argnames, String name, ParameterType[] params, ParameterType returntype) {
        this.description = description;
        this.argnames = argnames;
        this.name = name;
        this.params = params;
        this.returntype = returntype;
    }

    public String[] getArgnames() {
        return argnames;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ParameterType[] getParams() {
        return params;
    }

    public ParameterType getReturnType() {
        return returntype;
    }
}