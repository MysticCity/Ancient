package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddExperienceCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddGlobalVariableCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddGroupCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddItemCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddManaCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddPermissionCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddPlayerVariableCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.AddPrefixCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.BlindCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.BroadcastCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.BuffForTimeCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.CancelEventCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.CancelSpellCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ChanceCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ChangeAggroCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ChargeCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ChargeEntityToCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ChatCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.CheckCooldownCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ConsoleCommandCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.CooldownCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.CopyVariableCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DamageCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DamageModifyCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DirectDamageCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DisarmCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DisruptOnDeathCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DisruptOnMoveCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.DropItemCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.AnimatedBall;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Arc;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Atom;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.BigBang;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Bleed;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Circle;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Cloud;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.ColoredImage;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Cone;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Cube;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Cylinder;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.DNA;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.DiscoBall;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Donut;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Dragon;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Earth;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.ExplodeEffect;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Flame;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Fountain;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Grid;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Heart;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Helix;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Hill;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Icon;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Image;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Jump;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Line;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Love;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.MusicNotes;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.ParticleText;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Shield;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Smoke;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Sphere;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Star;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Tornado;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Turn;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Vortex;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.WarpEffect;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.Wave;
import com.ancientshores.Ancient.Classes.Spells.Commands.EnchantCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ExecuteCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ExplosionCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.FireBallCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.GrenadeCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.HealCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.IgniteCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.InvisibleCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.InvulnerableCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.JumpHigherCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.KillCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.LaunchFirework;
import com.ancientshores.Ancient.Classes.Spells.Commands.LightningCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.LightningEffectCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.PlayEffectCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.PlayEntityEffect;
import com.ancientshores.Ancient.Classes.Spells.Commands.PoisonCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.PotionEffectCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.PrintToConsoleCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemoveBuffCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemoveGroupCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemoveItemCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemoveManaCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemovePermissionCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemovePotionEffectCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.RemoveProjectileCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ResetCooldownCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ReviveCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SendMessageCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetArmorCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetBlockCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetBlockTemporaryCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetDamageCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetFlyCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetFoodLevelCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetGainedExperience;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetHpCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetInvisibleCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetItemCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetItemNameCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetManaCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetMaxHitpointsCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetMaxManaCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetPitchCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetTimeCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetVelocityCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetWeatherCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SetYawCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ShootArrowCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ShootWitherSkull;
import com.ancientshores.Ancient.Classes.Spells.Commands.SilenceCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SkipCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SlowCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SmokeCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SpawnArrowCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SpawnCreatureCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SpeedupCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.StunCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.SummonCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.TameCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.TeleportCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.TeleportPlayerCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.TemporaryItemCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ThrowEggCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ThrowEnderpearlCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ThrowPotionCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ThrowSmallFireballCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.ThrowSnowballCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.WaitCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.WaterBreathingCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.WeakenCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

public class Command
  extends ICodeSection
{
  ICommand command;
  Parameter[] param;
  public String commandString;
  public int lineNumber;
  public static HashMap<String, ICommand> registeredCommands = new HashMap();
  final ICodeSection parentsection;
  
  public Command(String line, Spell p, int lineNumber, ICodeSection parent)
  {
    super(null, null, p);
    parseLine(line);
    this.parentsection = parent;
    lineNumber++;
  }
  
  public void executeCommand(Player mPlayer, SpellInformationObject so)
  {
    EffectArgs e = new EffectArgs(mPlayer, this.mSpell, so, this);
    for (Parameter aParam : this.param) {
      aParam.parseParameter(e, mPlayer);
    }
    if (this.command == null)
    {
      so.canceled = true;
      return;
    }
    if (so.canceled) {
      return;
    }
    so.canceled = (!this.command.playCommand(e));
    if ((this.parentsection != null) && (!so.finished))
    {
      this.parentsection.executeCommand(mPlayer, so);
    }
    else
    {
      so.finished = true;
      AncientClass.executedSpells.remove(so);
    }
  }
  
  public static synchronized void putDefaults()
  {
    registeredCommands.put("lightning", new LightningCommand());
    registeredCommands.put("cancelspell", new CancelSpellCommand());
    registeredCommands.put("explosion", new ExplosionCommand());
    registeredCommands.put("addpermission", new AddPermissionCommand());
    registeredCommands.put("wait", new WaitCommand());
    registeredCommands.put("stun", new StunCommand());
    registeredCommands.put("charge", new ChargeCommand());
    registeredCommands.put("setmaxhp", new SetMaxHitpointsCommand());
    registeredCommands.put("checkcooldown", new CheckCooldownCommand());
    registeredCommands.put("changeaggro", new ChangeAggroCommand());
    registeredCommands.put("lightningeffect", new LightningEffectCommand());
    registeredCommands.put("heal", new HealCommand());
    registeredCommands.put("damage", new DamageCommand());
    registeredCommands.put("addexperience", new AddExperienceCommand());
    registeredCommands.put("teleport", new TeleportCommand());
    registeredCommands.put("disarm", new DisarmCommand());
    registeredCommands.put("setblock", new SetBlockCommand());
    registeredCommands.put("sethp", new SetHpCommand());
    registeredCommands.put("sendmessage", new SendMessageCommand());
    registeredCommands.put("setblocktemporary", new SetBlockTemporaryCommand());
    registeredCommands.put("smoke", new SmokeCommand());
    registeredCommands.put("skip", new SkipCommand());
    registeredCommands.put("chance", new ChanceCommand());
    registeredCommands.put("broadcast", new BroadcastCommand());
    registeredCommands.put("invulnerable", new InvulnerableCommand());
    registeredCommands.put("chat", new ChatCommand());
    registeredCommands.put("invisible", new InvisibleCommand());
    registeredCommands.put("resetcooldown", new ResetCooldownCommand());
    registeredCommands.put("execute", new ExecuteCommand());
    registeredCommands.put("bufffortime", new BuffForTimeCommand());
    registeredCommands.put("summon", new SummonCommand());
    registeredCommands.put("revive", new ReviveCommand());
    registeredCommands.put("fireball", new FireBallCommand());
    registeredCommands.put("disruptonmove", new DisruptOnMoveCommand());
    registeredCommands.put("disruptondeath", new DisruptOnDeathCommand());
    registeredCommands.put("ignite", new IgniteCommand());
    registeredCommands.put("additem", new AddItemCommand());
    registeredCommands.put("removeitem", new RemoveItemCommand());
    registeredCommands.put("silence", new SilenceCommand());
    registeredCommands.put("kill", new KillCommand());
    registeredCommands.put("revive", new ReviveCommand());
    registeredCommands.put("grenade", new GrenadeCommand());
    registeredCommands.put("cooldown", new CooldownCommand());
    registeredCommands.put("dropitem", new DropItemCommand());
    registeredCommands.put("damagemodify", new DamageModifyCommand());
    registeredCommands.put("slow", new SlowCommand());
    registeredCommands.put("speedup", new SpeedupCommand());
    registeredCommands.put("cancelevent", new CancelEventCommand());
    registeredCommands.put("blind", new BlindCommand());
    registeredCommands.put("waterbreathing", new WaterBreathingCommand());
    registeredCommands.put("addgroup", new AddGroupCommand());
    registeredCommands.put("jumphigher", new JumpHigherCommand());
    registeredCommands.put("poison", new PoisonCommand());
    registeredCommands.put("weaken", new WeakenCommand());
    registeredCommands.put("removebuff", new RemoveBuffCommand());
    registeredCommands.put("teleportplayer", new TeleportPlayerCommand());
    registeredCommands.put("settime", new SetTimeCommand());
    registeredCommands.put("setfoodlevel", new SetFoodLevelCommand());
    registeredCommands.put("addprefix", new AddPrefixCommand());
    registeredCommands.put("throwegg", new ThrowEggCommand());
    registeredCommands.put("spawncreature", new SpawnCreatureCommand());
    registeredCommands.put("throwsnowball", new ThrowSnowballCommand());
    registeredCommands.put("shootarrow", new ShootArrowCommand());
    registeredCommands.put("throwenderpearl", new ThrowEnderpearlCommand());
    registeredCommands.put("spawncreature", new SpawnCreatureCommand());
    registeredCommands.put("addglobalvariable", new AddGlobalVariableCommand());
    registeredCommands.put("copyvariable", new CopyVariableCommand());
    registeredCommands.put("setinvisible", new SetInvisibleCommand());
    registeredCommands.put("tame", new TameCommand());
    registeredCommands.put("removeprojectile", new RemoveProjectileCommand());
    registeredCommands.put("setmana", new SetManaCommand());
    registeredCommands.put("addmana", new AddManaCommand());
    registeredCommands.put("setmaxmana", new SetMaxManaCommand());
    registeredCommands.put("removemana", new RemoveManaCommand());
    registeredCommands.put("throwsmallfireball", new ThrowSmallFireballCommand());
    registeredCommands.put("setdamage", new SetDamageCommand());
    registeredCommands.put("setvelocity", new SetVelocityCommand());
    registeredCommands.put("playeffect", new PlayEffectCommand());
    registeredCommands.put("spawnarrow", new SpawnArrowCommand());
    registeredCommands.put("playentityeffect", new PlayEntityEffect());
    registeredCommands.put("damagedirectly", new DirectDamageCommand());
    registeredCommands.put("addplayervariable", new AddPlayerVariableCommand());
    registeredCommands.put("setweather", new SetWeatherCommand());
    registeredCommands.put("potioneffect", new PotionEffectCommand());
    registeredCommands.put("enchant", new EnchantCommand());
    registeredCommands.put("shootwitherskull", new ShootWitherSkull());
    registeredCommands.put("setfly", new SetFlyCommand());
    registeredCommands.put("chargeentityto", new ChargeEntityToCommand());
    registeredCommands.put("temporaryitem", new TemporaryItemCommand());
    registeredCommands.put("removepermission", new RemovePermissionCommand());
    registeredCommands.put("throwpotion", new ThrowPotionCommand());
    registeredCommands.put("removegroup", new RemoveGroupCommand());
    registeredCommands.put("consolecommand", new ConsoleCommandCommand());
    registeredCommands.put("printtoconsole", new PrintToConsoleCommand());
    registeredCommands.put("launchfirework", new LaunchFirework());
    registeredCommands.put("setitem", new SetItemCommand());
    registeredCommands.put("setarmor", new SetArmorCommand());
    registeredCommands.put("removepotioneffect", new RemovePotionEffectCommand());
    registeredCommands.put("setitemname", new SetItemNameCommand());
    registeredCommands.put("setyaw", new SetYawCommand());
    registeredCommands.put("setpitch", new SetPitchCommand());
    registeredCommands.put("setgainedexperience", new SetGainedExperience());
    
    registeredCommands.put("animatedball", new AnimatedBall());
    registeredCommands.put("arc", new Arc());
    registeredCommands.put("atom", new Atom());
    registeredCommands.put("bigbang", new BigBang());
    registeredCommands.put("bleed", new Bleed());
    registeredCommands.put("circle", new Circle());
    registeredCommands.put("cloud", new Cloud());
    registeredCommands.put("coloredimage", new ColoredImage());
    registeredCommands.put("cone", new Cone());
    registeredCommands.put("cube", new Cube());
    registeredCommands.put("cylinder", new Cylinder());
    registeredCommands.put("discoball", new DiscoBall());
    registeredCommands.put("dna", new DNA());
    registeredCommands.put("donut", new Donut());
    registeredCommands.put("dragon", new Dragon());
    registeredCommands.put("earth", new Earth());
    registeredCommands.put("explodeeffect", new ExplodeEffect());
    registeredCommands.put("flame", new Flame());
    registeredCommands.put("fountain", new Fountain());
    registeredCommands.put("grid", new Grid());
    registeredCommands.put("heart", new Heart());
    registeredCommands.put("helix", new Helix());
    registeredCommands.put("hill", new Hill());
    registeredCommands.put("icon", new Icon());
    registeredCommands.put("image", new Image());
    registeredCommands.put("jump", new Jump());
    registeredCommands.put("line", new Line());
    registeredCommands.put("love", new Love());
    registeredCommands.put("musicnotes", new MusicNotes());
    registeredCommands.put("particletext", new ParticleText());
    registeredCommands.put("shield", new Shield());
    registeredCommands.put("smokeeffect", new Smoke());
    registeredCommands.put("sphere", new Sphere());
    registeredCommands.put("star", new Star());
    registeredCommands.put("tornado", new Tornado());
    
    registeredCommands.put("turn", new Turn());
    registeredCommands.put("vortex", new Vortex());
    registeredCommands.put("warpeffect", new WarpEffect());
    registeredCommands.put("wave", new Wave());
  }
  
  public void parseLine(String line)
  {
    line = line.trim();
    ArrayList<String> args = new ArrayList();
    int start = 0;
    int pos = 0;
    int openbraces = 0;
    boolean openq = false;
    while (pos < line.length())
    {
      if ((line.charAt(pos) == ',') && (openbraces == 0) && (!openq))
      {
        args.add(line.substring(start, pos).trim());
        start = pos + 1;
      }
      if ((line.charAt(pos) == '(') && (!openq)) {
        openbraces++;
      }
      if ((line.charAt(pos) == ')') && (!openq)) {
        openbraces--;
      }
      if (line.charAt(pos) == '"') {
        openq = !openq;
      }
      pos++;
    }
    args.add(line.substring(start, pos).trim());
    String[] buffer = (String[])args.toArray(new String[args.size()]);
    String commandString = buffer[0].trim();
    this.commandString = commandString;
    ICommand linecommand = (ICommand)registeredCommands.get(commandString.toLowerCase());
    this.command = linecommand;
    if (linecommand == null) {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Ancient: error in command " + commandString);
    }
    this.param = new Parameter[buffer.length - 1];
    for (int i = 1; i < buffer.length; i++) {
      this.param[(i - 1)] = new Parameter(this, buffer[i].trim(), i - 1, this.command);
    }
  }
}
