package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.gui.Argument;
import de.pylamo.spellmaker.gui.ArgumentPreview;
import de.pylamo.spellmaker.gui.ParameterPreview;
import de.pylamo.spellmaker.gui.ParameterPreview.param;
import de.pylamo.spellmaker.gui.SpellItems.Arguments.ArgumentPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.BooleanParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.NumberParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.StringParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;
import de.pylamo.spellmaker.gui.VariablePreview;
import de.pylamo.spellmaker.gui.Window;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

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
    this.args = new LinkedList();
    int curpos = 0;
    int openbrackets = 0;
    boolean openqmark = false;
    int lastindex = 0;
    if ((line.startsWith("(")) && (line.endsWith(")"))) {
      line = line.substring(1, line.length() - 1);
    }
    while (curpos < line.length())
    {
      if ((line.charAt(curpos) == ',') && (openbrackets == 0) && (!openqmark))
      {
        this.args.add(line.substring(lastindex, curpos));
        lastindex = curpos + 1;
      }
      if ((line.charAt(curpos) == '(') && (!openqmark)) {
        openbrackets++;
      }
      if ((line.charAt(curpos) == ')') && (!openqmark)) {
        openbrackets--;
      }
      if (line.charAt(curpos) == '"') {
        openqmark = !openqmark;
      }
      curpos++;
    }
    this.args.add(line.substring(lastindex, curpos));
  }
  
  private ParameterPanel parseParameter(String curline)
  {
    curline = curline.trim();
    for (ParameterPreview.param param : ParameterPreview.parameters) {
      if (curline.toLowerCase().startsWith(param.name.toLowerCase())) {
        return new ParameterPanel(param.name, param.type, param.am, false);
      }
    }
    return null;
  }
  
  public IParameter getArgumentPanel(Window w)
  {
    IParameter returnparam = parseParameter((String)this.args.get(0));
    if (returnparam != null)
    {
      ParameterPanel pp = (ParameterPanel)returnparam;
      if (((String)this.args.get(0)).contains(":"))
      {
        String[] split = ((String)this.args.get(0)).split(Pattern.quote(":"));
        for (int i = 1; i < split.length; i++) {
          if (pp.fields.size() >= i) {
            ((JTextField)pp.fields.get(i - 1)).setText(split[i]);
          }
        }
      }
      return returnparam;
    }
    returnparam = parseArgument((String)this.args.get(0), w);
    if (returnparam != null)
    {
      ArgumentPanel ap = (ArgumentPanel)returnparam;
      for (int i = 1; i < this.args.size(); i++) {
        if (ap.parameters.length <= i)
        {
          ArgumentParser apars = new ArgumentParser(w);
          apars.parse((String)this.args.get(i));
          IParameter ip = apars.getArgumentPanel(w);
          if (ip != null)
          {
            ap.parameters[(i - 1)].add(ip);
            ap.parameters[(i - 1)].content = ip;
          }
        }
      }
      return returnparam;
    }
    return parsePrimitivePanel((String)this.args.get(0));
  }
  
  private IParameter parsePrimitivePanel(String curline)
  {
    curline = curline.trim();
    for (String s : this.w.m.variables) {
      if (s.equalsIgnoreCase(curline)) {
        return new VariablePanel(s, false, this.w);
      }
    }
    try
    {
      double d = Double.parseDouble(curline);
      NumberParameterPanel npp = new NumberParameterPanel(false);
      ((JTextField)npp.fields.get(0)).setText("" + d);
      return npp;
    }
    catch (Exception ignored)
    {
      try
      {
        if ((curline.equalsIgnoreCase("false")) || (curline.equalsIgnoreCase("true")))
        {
          boolean b = Boolean.parseBoolean(curline);
          BooleanParameterPanel bpp = new BooleanParameterPanel(false);
          bpp.boolbox.setSelected(b);
          return bpp;
        }
      }
      catch (Exception ignored) {}
      if ((curline.startsWith("\"")) && (curline.endsWith("\""))) {
        curline = curline.substring(1, curline.length() - 1);
      }
      for (String s : this.w.vp.vars) {
        if (s.trim().equalsIgnoreCase(curline.trim())) {
          return new VariablePanel(s, false, this.w);
        }
      }
      StringParameterPanel spp = new StringParameterPanel(false);
      ((JTextField)spp.fields.get(0)).setText(curline);
      return spp;
    }
  }
  
  private ArgumentPanel parseArgument(String curline, Window w)
  {
    curline = curline.trim();
    for (Argument a : ArgumentPreview.arguments) {
      if (curline.equalsIgnoreCase(a.name.toLowerCase())) {
        return new ArgumentPanel(a.name, a.param, a.desc, a.parameters, a.paramdesc, w);
      }
    }
    return null;
  }
}
