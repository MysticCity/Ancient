package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.Argument;
import de.pylamo.spellmaker.gui.ArgumentPreview;
import de.pylamo.spellmaker.gui.ParameterPreview;
import de.pylamo.spellmaker.gui.ParameterPreview.param;
import de.pylamo.spellmaker.gui.SpellItems.Arguments.ArgumentPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.*;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;
import de.pylamo.spellmaker.gui.Window;

import java.util.LinkedList;
import java.util.regex.Pattern;

class ArgumentParser
{
	private LinkedList<String> args;
	private final Window w;

	public ArgumentParser(Window w)
	{
		this.w = w;
	}

	public void parse(String line)
	{
		line = line.trim();
		args = new LinkedList<String>();
		int curpos = 0;
		int openbrackets = 0;
		boolean openqmark = false;
		int lastindex = 0;
		if (line.startsWith("(") && line.endsWith(")"))
		{
			line = line.substring(1, line.length() - 1);
		}
		while (curpos < line.length())
		{
			if (line.charAt(curpos) == ',' && openbrackets == 0 && !openqmark)
			{
				args.add(line.substring(lastindex, curpos));
				lastindex = curpos + 1;
			}
			if (line.charAt(curpos) == '(' && !openqmark)
			{
				openbrackets++;
			}
			if (line.charAt(curpos) == ')' && !openqmark)
			{
				openbrackets--;
			}
			if (line.charAt(curpos) == '"')
			{
				openqmark = !openqmark;
			}
			curpos++;
		}
		args.add(line.substring(lastindex, curpos));
	}

	private ParameterPanel parseParameter(String curline)
	{
		curline = curline.trim();
		for (param param : ParameterPreview.parameters)
		{
			if (curline.toLowerCase().startsWith(param.name.toLowerCase()))
			{
				return new ParameterPanel(param.name, param.type, param.am, false);
			}
		}
		return null;
	}

	public IParameter getArgumentPanel(Window w)
	{
		IParameter returnparam = parseParameter(args.get(0));
		if (returnparam != null)
		{
			ParameterPanel pp = (ParameterPanel) returnparam;
			if (args.get(0).contains(":"))
			{
				String[] split = args.get(0).split(Pattern.quote(":"));
				for (int i = 1; i < split.length; i++)
				{
					if (pp.fields.size() >= i)
					{
						pp.fields.get(i - 1).setText(split[i]);
					}
				}
			}
			return returnparam;
		}
		returnparam = parseArgument(args.get(0), w);
		if (returnparam != null)
		{
			ArgumentPanel ap = (ArgumentPanel) returnparam;
			for (int i = 1; i < args.size(); i++)
			{
				if (ap.parameters.length <= i)
				{
					ArgumentParser apars = new ArgumentParser(w);
					apars.parse(args.get(i));
					IParameter ip = apars.getArgumentPanel(w);
					if (ip != null)
					{
						ap.parameters[i - 1].add(ip);
						ap.parameters[i - 1].content = ip;
					}
				}
			}
			return returnparam;
		}
		return parsePrimitivePanel(args.get(0));
	}

	private IParameter parsePrimitivePanel(String curline)
	{
		curline = curline.trim();
		for (String s : w.m.variables)
		{
			if (s.equalsIgnoreCase(curline))
			{
				return new VariablePanel(s, false, w);
			}
		}
		try
		{
			double d = Double.parseDouble(curline);
			NumberParameterPanel npp = new NumberParameterPanel(false);
			npp.fields.get(0).setText("" + d);
			return npp;
		} catch (Exception ignored)
		{

		}
		try
		{
			if (curline.equalsIgnoreCase("false") || curline.equalsIgnoreCase("true"))
			{
				boolean b = Boolean.parseBoolean(curline);
				BooleanParameterPanel bpp = new BooleanParameterPanel(false);
				bpp.boolbox.setSelected(b);
				return bpp;
			}
		} catch (Exception ignored)
		{

		}
		if (curline.startsWith("\"") && curline.endsWith("\""))
		{
			curline = curline.substring(1, curline.length() - 1);
		}
		for(String s : w.vp.vars)
		{
			if(s.trim().equalsIgnoreCase(curline.trim()))
			{
				VariablePanel vp = new VariablePanel(s, false, w);
				return vp;
			}
		}
		StringParameterPanel spp = new StringParameterPanel(false);
		spp.fields.get(0).setText(curline);
		return spp;
	}

	private ArgumentPanel parseArgument(String curline, Window w)
	{
		curline = curline.trim();
		for (Argument a : ArgumentPreview.arguments)
		{
			if (curline.equalsIgnoreCase(a.name.toLowerCase()))
			{
				return new ArgumentPanel(a.name, a.param, a.desc, a.parameters, a.paramdesc, w);
			}
		}
		return null;
	}
}
