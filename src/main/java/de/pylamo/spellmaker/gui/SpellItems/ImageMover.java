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
	
	public static void start(final BufferedImage imga, Point p)
	{
		if (w == null)
		{
			w = new Window(new Frame())
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g)
				{
					super.paint(g);
					if (img != null)
						g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			};
		}
		w.setLocation(p);
		w.repaint();
		img = imga;
		try
		{
			Class<?> awtUtilClass = Class.forName("com.sun.awt.AWTUtilities");
			Method setWindowOpacityMethod = awtUtilClass.getMethod("setWindowOpacity", Window.class, float.class);
			setWindowOpacityMethod.invoke(null, w, 0.7f);
		}
		catch(Exception ignored)
		{
			
		}
		w.setSize(imga.getWidth(), imga.getHeight());
		w.setVisible(true);
	}

	public static void stop()
	{
		w.setVisible(false);
	}
}
