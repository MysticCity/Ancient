package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class IParameter
  extends JPanel
  implements MouseListener
{
  private static final long serialVersionUID = 1L;
  private final JPopupMenu menu = new JPopupMenu();
  protected boolean preview;
  
  public abstract IParameter clone();
  
  protected IParameter(boolean preview)
  {
    this.preview = preview;
    if (!preview)
    {
      JMenuItem item = new JMenuItem("delete");
      this.menu.add(item);
      ActionListener al = new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          IParameter.this.delete();
        }
      };
      item.addActionListener(al);
      addMouseListener(this);
    }
  }
  
  public void mouseClicked(MouseEvent e)
  {
    if (e.getButton() == 3) {
      this.menu.show((Component)e.getSource(), e.getX(), e.getY());
    }
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {}
  
  void delete()
  {
    if ((getParent() instanceof ParameterSlot))
    {
      ParameterSlot ps = (ParameterSlot)getParent();
      ps.remove(this);
      ps.content = null;
      ps.add(ps.l);
      ps.revalidate();
      if ((ps.getParent() instanceof ISpellItem)) {
        ((ISpellItem)ps.getParent()).recalculateSize();
      }
    }
  }
  
  public abstract String getString();
}
