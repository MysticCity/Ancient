package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class SearchPanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  private final Window w;
  
  public SearchPanel(Window w)
  {
    this.w = w;
    setLayout(new WrapLayout());
  }
  
  public void search(String searchterm)
  {
    removeAll();
    for (Map.Entry<String, JComponent> entry : this.w.spellItems.entrySet()) {
      if (((String)entry.getKey()).toLowerCase().contains(searchterm.toLowerCase())) {
        if ((entry.getValue() instanceof IParameter)) {
          add(((IParameter)entry.getValue()).clone());
        } else if ((entry.getValue() instanceof ISpellItem)) {
          add(((ISpellItem)entry.getValue()).clone());
        }
      }
    }
    revalidate();
    repaint();
  }
}
