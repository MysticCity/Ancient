package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.ICommand;
import com.ancientshores.AncientRPG.Classes.Spells.Conditions.IArgument;
import com.ancientshores.AncientRPG.Classes.Spells.Parameters.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.HashSet;
import java.util.logging.Level;

public class Parameter implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static HashSet<IParameter> registeredParameters = new HashSet<IParameter>();
    public final String s;
    public ParameterType pt;
    public IParameter parameter;
    public final Command mCommand;
    public final String[] subparam;

    public Parameter(Command c, String s, int number, ICommand effect) {
        if (s.contains(":") && !s.startsWith("(")) {
            String[] buffer = s.split(":");
            this.s = buffer[0];
            subparam = new String[buffer.length - 1];
            System.arraycopy(buffer, 1, subparam, 0, buffer.length - 1);
        } else {
            this.s = s;
            this.subparam = null;
        }
        for (IParameter p : registeredParameters) {
            if (p.getName().toLowerCase().equalsIgnoreCase(this.s)) {
                this.parameter = p;
                break;
            }
        }
        if (this.parameter == null) {
            this.parameter = new ArgumentParameterWrapper(IArgument.parseArgumentByString(s, c.mSpell));
            /*
             * String arg = s.replace("(", ""); arg = arg.replace(")", "");
			 * String argstring = arg.split(Pattern.quote(","))[0]; for
			 * (IArgument i : IArgument.registeredArguments) { if
			 * (i.name.equalsIgnoreCase(argstring)) { ArgumentInformationObject
			 * aio = new ArgumentInformationObject(); aio.argument = i;
			 * ArrayList<String> args = new ArrayList<String>(); int start = 0;
			 * int pos = 0; int openbraces = 0; while (pos < arg.length()) { if
			 * (arg.charAt(pos) == ',' && openbraces == 0) {
			 * args.add(arg.substring(start, pos).trim()); start = pos + 1; } if
			 * (arg.charAt(pos) == '(') openbraces++; if (arg.charAt(pos) ==
			 * ')') openbraces--; pos++; } ArgumentParameter[] params = new
			 * ArgumentParameter[args.size()]; for (int x = 0; x <
			 * params.length; x++) { params[x] = new
			 * ArgumentParameter(args.get(x)); } this.parameter = new
			 * ArgumentParameterWrapper(aio); } }
			 */
        }
        this.mCommand = c;
        try {
            pt = effect.paramTypes[number];
        } catch (ArrayIndexOutOfBoundsException e) {
            AncientRPG.plugin.getLogger().log(Level.SEVERE, "Wrong number of parameters in command: " + c.commandString);
            pt = ParameterType.Void;
        }
    }

    public synchronized void parseParameter() {
        for (IParameter p : registeredParameters) {
            if (p.getName().toLowerCase().startsWith(s.toLowerCase())) {
                this.parameter = p;
                return;
            }
        }
    }

    public synchronized static void registerDefaultParameters() {
        registeredParameters.add(new AttackedEntityParameter());
        registeredParameters.add(new AttackerParameter());
        registeredParameters.add(new BlockInSightParameter());
        registeredParameters.add(new ChangedBlockParameter());
        registeredParameters.add(new NearestEntitiesParameter());
        registeredParameters.add(new NearestEntityInSightParameter());
        registeredParameters.add(new NearestEntityParameter());
        registeredParameters.add(new NearestHostileEntityParameter());
        registeredParameters.add(new NearestHostileEntitiesParameter());
        registeredParameters.add(new NearestHostilePlayerParameter());
        registeredParameters.add(new NearestHostileEntityInSight());
        registeredParameters.add(new NearestHostilePlayersParameter());
        registeredParameters.add(new NearestPlayerInSightParameter());
        registeredParameters.add(new NearestPlayerParameter());
        registeredParameters.add(new NearestPlayersParameter());
        registeredParameters.add(new PartyMembersParameter());
        registeredParameters.add(new PlayerParameter());
        registeredParameters.add(new TargetInSight());
        registeredParameters.add(new NearestHostileEntityInSight());
    }

    public Variable getVariable(SpellInformationObject so, String name) {
        if (Variable.playerVars.containsKey(so.buffcaster.getName()) && Variable.playerVars.get(so.buffcaster.getName()).containsKey(name.toLowerCase())) {
            return Variable.playerVars.get(so.buffcaster.getName()).get(name.toLowerCase());
        }
        if (so.variables.containsKey(name.toLowerCase())) {
            return so.variables.get(name.toLowerCase());
        }
        return null;
    }

    public void parseParameter(EffectArgs ea, Player mPlayer) {
        if (ea.so.variables.containsKey(s.toLowerCase())) {
            Variable v = getVariable(ea.so, s.toLowerCase());
            v.AutoCast(pt, ea);
            return;
        }
        if (ea.p != null && ea.p.variables.contains(s.toLowerCase())) {
            parseVariable(ea, mPlayer, s.toLowerCase());
            return;
        }
        if (parameter != null) {
            parameter.parseParameter(ea, mPlayer, subparam, pt);
        } else if (pt == ParameterType.String || pt == ParameterType.Number || pt == ParameterType.Material || pt == ParameterType.Boolean) {
            parsePrimitive(ea);
        } else {
            Bukkit.getLogger().log(Level.WARNING, "Parameter " + s + " not found");
        }
    }

    void parseVariable(EffectArgs ea, Player mPlayer, String var) {
        switch (pt) {
            case Number:
                int value = Variables.getParameterIntByString(ea.so.buffcaster, var, ea.p.newConfigFile);
                ea.params.addLast(value);
                break;
            case String:
                int v = Variables.getParameterIntByString(ea.so.buffcaster, var, ea.p.newConfigFile);
                ea.params.addLast("" + v);
            default:
                break;
        }
    }

    void parsePrimitive(EffectArgs ea) {
        switch (pt) {
            case String:
                ea.params.addLast(s);
                return;
            case Material:
                try {
                    int t = Integer.parseInt(s);
                    ea.params.addLast(Material.getMaterial(t));
                    return;
                } catch (Exception ignored) {
                }
                break;
            case Number:
                try {
                    double t = Double.parseDouble(s);
                    ea.params.addLast(t);
                    return;
                } catch (Exception ignored) {
                }
                break;
            case Boolean:
                try {
                    boolean b = Boolean.parseBoolean(s);
                    ea.params.addLast(b);
                } catch (Exception ignored) {

                }
                break;
            default:
                AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + mCommand.commandString);
        }
    }

    public EffectArgs getArgs(EffectArgs ea, Player mPlayer) {
        parseParameter(ea, mPlayer);
        return ea;
    }
}
