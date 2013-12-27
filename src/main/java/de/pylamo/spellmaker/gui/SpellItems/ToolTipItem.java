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

public class ToolTipItem extends JPanel implements MouseInputListener, Cloneable, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img;

	public ToolTipItem()
	{
		ToolTipManager.sharedInstance().setInitialDelay(0);
		this.setPreferredSize(new Dimension(15, 15));
		this.setSize(new Dimension(15, 15));
		try
		{
			img = ImageIO.read(ToolTipItem.class.getResource("tooltip.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDescription(String desc)
	{
		this.setToolTipText(desc);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		int w = this.getWidth();
		int h = this.getHeight();
		if(w < h)
			h = w;
		if(h < w)
			w = h;
		 g.drawImage(img, 0, 0, w, h, this);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}
