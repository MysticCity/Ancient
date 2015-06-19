/*
 * This file is part of Ancient
 *
 * Copyright (C) 2011-2013 Keyle
 * Ancient is licensed under the GNU Lesser General Public License.
 *
 * Ancient is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ancient is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ancientshores.Ancient.Classes.Spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Commands.*;
import com.ancientshores.Ancient.Classes.Spells.Commands.Effects.*;

public class Command extends ICodeSection {
    ICommand command;
    Parameter param[];
    public String commandString;
    public int lineNumber;
    public static HashMap<String, ICommand> registeredCommands = new HashMap<String, ICommand>();
    final ICodeSection parentsection;

    public Command(String line, Spell p, int lineNumber, ICodeSection parent) {
        super(null, null, p);
        this.parseLine(line);
        parentsection = parent;
        lineNumber++;
    }

    public void executeCommand(Player mPlayer, SpellInformationObject so) {
        EffectArgs e = new EffectArgs(mPlayer, mSpell, so, this);
        for (Parameter aParam : param) {
            aParam.parseParameter(e, mPlayer);
        }
        if (command == null) {
            so.canceled = true;
            return;
        }
        if (so.canceled) {
            return;
        } else {
            so.canceled = !command.playCommand(e);
        }
        if (parentsection != null && !so.finished) {
            parentsection.executeCommand(mPlayer, so);
        } else {
            so.finished = true;
            AncientClass.executedSpells.remove(so);
        }
    }

    public synchronized static void putDefaults() {
    	//registriert die verschiedenen kommandos, die es gibt
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

        // effects from effectlib
        registeredCommands.put("animatedball", new AnimatedBall());
        registeredCommands.put("arc", new Arc());
        registeredCommands.put("atom", new Atom());
        registeredCommands.put("bigbang", new BigBang());
        registeredCommands.put("bleed", new Bleed());
        registeredCommands.put("circle", new Circle());
        registeredCommands.put("coloredimage", new ColoredImage());
        registeredCommands.put("cone", new Cone());
        registeredCommands.put("cube", new Cube());
        registeredCommands.put("cylinder", new Cylinder());
        registeredCommands.put("discoball", new DiscoBall());
        registeredCommands.put("dna", new DNA());
        registeredCommands.put("donut", new Donut());
        registeredCommands.put("dragon", new Dragon());
        registeredCommands.put("earth", new Earth());
        registeredCommands.put("exlodeeffect", new ExplodeEffect());
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
        registeredCommands.put("trace", new Trace());
        registeredCommands.put("turn", new Turn());
        registeredCommands.put("vortex", new Vortex());
        registeredCommands.put("warpeffect", new WarpEffect());
        registeredCommands.put("wave", new Wave());
        
    }

    public void parseLine(String line) {
        line = line.trim();
        ArrayList<String> args = new ArrayList<String>();
        int start = 0;
        int pos = 0;
        int openbraces = 0;
        boolean openq = false;
        while (pos < line.length()) {
            if (line.charAt(pos) == ',' && openbraces == 0 && !openq) {
                args.add(line.substring(start, pos).trim());
                start = pos + 1;
            }
            if (line.charAt(pos) == '(' && !openq) {
                openbraces++;
            }
            if (line.charAt(pos) == ')' && !openq) {
                openbraces--;
            }
            if (line.charAt(pos) == '"') {
                openq = !openq;
            }
            pos++;
        }
        args.add(line.substring(start, pos).trim());
        String[] buffer = args.toArray(new String[args.size()]);
        String commandString = buffer[0].trim();
        this.commandString = commandString;
        ICommand linecommand = registeredCommands.get(commandString.toLowerCase());
        this.command = linecommand;
        if (linecommand == null) {
            Ancient.plugin.getLogger().log(Level.SEVERE, "Ancient: error in command " + commandString);
        }
        param = new Parameter[buffer.length - 1];
        for (int i = 1; i < buffer.length; i++)
            param[i - 1] = new Parameter(this, buffer[i].trim(), i - 1, this.command);
    }
}