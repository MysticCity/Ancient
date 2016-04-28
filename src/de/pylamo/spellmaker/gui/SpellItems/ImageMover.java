package de.pylamo.spellmaker.gui.SpellItems;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

public class ImageMover
{
  private static Image img;
  public static Window w;
  
  public static void start(BufferedImage imga, Point p)
  {
    if (w == null) {
      w = new Window(new Frame())
      {
        private static final long serialVersionUID = 1L;
        
        public void paint(Graphics g)
        {
          super.paint(g);
          if (ImageMover.img != null) {
            g.drawImage(ImageMover.img, 0, 0, getWidth(), getHeight(), null);
          }
        }
      };
    }
    w.setLocation(p);
    w.repaint();
    img = imga;
    try
    {
      Class<?> awtUtilClass = Class.forName("com.sun.awt.AWTUtilities");
      Method setWindowOpacityMethod = awtUtilClass.getMethod("setWindowOpacity", new Class[] { Window.class, Float.TYPE });
      setWindowOpacityMethod.invoke(null, new Object[] { w, Float.valueOf(0.7F) });
    }
    catch (Exception ignored) {}
    w.setSize(imga.getWidth(), imga.getHeight());
    w.setVisible(true);
  }
  
  public static void stop()
  {
    w.setVisible(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\ImageMover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */