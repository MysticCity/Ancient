package com.ancientshores.Ancient.Classes.Spells;

import java.io.Serializable;
import java.util.HashSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import com.ancientshores.Ancient.Classes.Spells.Parameters.ArgumentParameterWrapper;
import com.ancientshores.Ancient.Classes.Spells.Parameters.AttackedEntityParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.AttackerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.BlockInSightParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.ChangedBlockParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestEntitiesParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestEntityInSightParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestEntityParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostileEntitiesParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostileEntityInSight;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostileEntityParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostilePlayerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestHostilePlayersParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestPlayerInSightParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestPlayerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.NearestPlayersParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.PartyMembersParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.PlayerParameter;
import com.ancientshores.Ancient.Classes.Spells.Parameters.TargetInSight;

public class Parameter implements Serializable {
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
		}
		this.mCommand = c;
		try {
			pt = effect.paramTypes[number];
		} catch (ArrayIndexOutOfBoundsException e) {
			Ancient.plugin.getLogger().log(Level.SEVERE, "Wrong number of parameters in command: " + c.commandString);
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
		// fügt zu der Liste verschiedene Parameter hinzu
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
		if (Variable.playerVars.containsKey(so.buffcaster) && Variable.playerVars.get(so.buffcaster).containsKey(name.toLowerCase())) {
			return Variable.playerVars.get(so.buffcaster).get(name.toLowerCase());
		}
		if (so.variables.containsKey(name.toLowerCase())) {
			return so.variables.get(name.toLowerCase());
		}
		return null;
	}

	public void parseParameter(EffectArgs ea, Player mPlayer) {
		// if global variable
		if (ea.getSpellInfo().variables.containsKey(s.toLowerCase())) {
			Variable v = getVariable(ea.getSpellInfo(), s.toLowerCase());
			v.AutoCast(pt, ea);
			return;
		}
		// if spell variable
		if (ea.getSpell() != null && ea.getSpell().variables.contains(s.toLowerCase())) {
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
				int value = Variables.getParameterIntByString(ea.getSpellInfo().buffcaster.getUniqueId(), var, ea.getSpell().newConfigFile);
				ea.getParams().addLast(value);
				break;
			case String:
				int v = Variables.getParameterIntByString(ea.getSpellInfo().buffcaster.getUniqueId(), var, ea.getSpell().newConfigFile);
				ea.getParams().addLast("" + v);
			default:
				break;
		}
	}

	@SuppressWarnings("deprecation")
	void parsePrimitive(EffectArgs ea) {
		switch (pt) {
			case String:
				ea.getParams().addLast(s);
				return;
			case Material:
				try {
					int t = Integer.parseInt(s);
					ea.getParams().addLast(Material.getMaterial(t));
					return;
				} catch (Exception ignored) {
				}
				break;
			case Number:
				try {
					double t = Double.parseDouble(s);
					ea.getParams().addLast(t);
					return;
				} catch (Exception ignored) {
				}
				break;
			case Boolean:
				try {
					boolean b = Boolean.parseBoolean(s);
					ea.getParams().addLast(b);
				} catch (Exception ignored) {

				}
				break;
			default:
				Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + mCommand.commandString);
		}
	}

	public EffectArgs getArgs(EffectArgs ea, Player mPlayer) {
		parseParameter(ea, mPlayer);
		return ea;
	}
}