package de.pylamo.spellmaker.gui;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.pylamo.spellmaker.Arguments;
import de.pylamo.spellmaker.gui.SpellItems.Arguments.ArgumentPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ArgumentPreview
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public static final HashSet<Argument> arguments = new HashSet();
  
  public ArgumentPreview(Window w)
  {
    setVisible(true);
    setLayout(new WrapLayout());
    setBorder(BorderFactory.createLineBorder(Color.black));
    setMinimumSize(new Dimension(250, 200));
    for (Arguments argument : Arguments.values())
    {
      ParameterType returntype = argument.getReturnType();
      ParameterType[] pts = argument.getParams();
      Parameter param = null;
      for (Parameter p : Parameter.values()) {
        if (p.name().equalsIgnoreCase(returntype.name()))
        {
          param = p;
          break;
        }
      }
      Parameter[] rparams = new Parameter[pts.length];
      for (int i = 0; i < rparams.length; i++)
      {
        Parameter rparam = null;
        for (Parameter p : Parameter.values()) {
          if (p.name().equalsIgnoreCase(pts[i].name()))
          {
            rparam = p;
            break;
          }
        }
        rparams[i] = rparam;
      }
      try
      {
        Argument a = new Argument(Argument.getLine(argument.getName(), rparams, argument.getArgnames(), argument.getDescription(), Parameter.getParameterByName(returntype.name())));
        arguments.add(a);
      }
      catch (Exception e)
      {
        System.out.println(argument.getName());
      }
      ArgumentPanel si = new ArgumentPanel(argument.getName(), param, argument.getDescription(), rparams, argument.getArgnames(), Argument.getLine(argument.getName(), rparams, argument.getArgnames(), argument.getName(), Parameter.getParameterByName(returntype.name())), w);
      add(si);
      w.spellItems.put(si.name, si);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\ArgumentPreview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */