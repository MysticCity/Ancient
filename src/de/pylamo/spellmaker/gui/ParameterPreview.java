package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.Parameters;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.BooleanParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.NumberParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.StringParameterPanel;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JPanel;

public class ParameterPreview
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public static final HashSet<param> parameters = new HashSet();
  
  public class param
  {
    public final String name;
    public final int am;
    public final Parameter type;
    
    public param(String name, int am, Parameter type)
    {
      this.name = name;
      this.am = am;
      this.type = type;
    }
  }
  
  public ParameterPreview(Window w)
  {
    setLayout(new WrapLayout());
    for (Parameters param : Parameters.values())
    {
      String returntype = param.getReturntype();
      int amount = param.getAmount();
      
      parameters.add(new param(param.name(), amount, Parameter.getParameterByName(returntype)));
      ParameterPanel pp = new ParameterPanel(param.name(), Parameter.getParameterByName(returntype), amount, true);
      add(pp);
      w.spellItems.put(pp.name, pp);
    }
    NumberParameterPanel npp = new NumberParameterPanel(true);
    add(npp);
    w.spellItems.put("number", npp);
    StringParameterPanel spp = new StringParameterPanel(true);
    add(spp);
    w.spellItems.put("string", spp);
    BooleanParameterPanel bpp = new BooleanParameterPanel(true);
    add(bpp);
    w.spellItems.put("boolean", bpp);
  }
}
