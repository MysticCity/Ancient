package de.pylamo.spellmaker.gui.SpellItems;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.event.MouseInputListener;

public class ToolTipItem
  extends JPanel
  implements MouseInputListener, Cloneable, Serializable
{
  private static final long serialVersionUID = 1L;
  private BufferedImage img;
  
  public ToolTipItem()
  {
    ToolTipManager.sharedInstance().setInitialDelay(0);
    setPreferredSize(new Dimension(15, 15));
    setSize(new Dimension(15, 15));
    try
    {
      this.img = ImageIO.read(ToolTipItem.class.getResource("tooltip.png"));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void setDescription(String desc)
  {
    setToolTipText(desc);
  }
  
  public void paintComponent(Graphics g)
  {
    int w = getWidth();
    int h = getHeight();
    if (w < h) {
      h = w;
    }
    if (h < w) {
      w = h;
    }
    g.drawImage(this.img, 0, 0, w, h, this);
  }
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {}
  
  public void mouseDragged(MouseEvent arg0) {}
  
  public void mouseMoved(MouseEvent arg0) {}
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\ToolTipItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */