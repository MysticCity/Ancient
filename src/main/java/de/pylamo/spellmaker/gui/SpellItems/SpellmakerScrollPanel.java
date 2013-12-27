package de.pylamo.spellmaker.gui.SpellItems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 21.02.13
 * Time: 02:27
 */
public class SpellmakerScrollPanel extends JPanel
{
	JScrollBar jsb = new JScrollBar();
	public SpellmakerScrollPanel(final Container c)
	{
		this.setLayout(new BorderLayout());
		this.add(c, BorderLayout.CENTER);
		jsb.setMinimum(0);
		jsb.setMaximum(60);
		this.add(jsb, BorderLayout.EAST);
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e)
			{
				float ratio = SpellmakerScrollPanel.this.getHeight() / c.getHeight();
				jsb.setMaximum(Math.round(ratio*60));
				jsb.invalidate();
				System.out.println("lolol");
			}

			@Override
			public void componentMoved(ComponentEvent e)
			{
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void componentShown(ComponentEvent e)
			{
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void componentHidden(ComponentEvent e)
			{
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});
	}
}
