package de.pylamo.spellmaker.gui.SpellItems;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class StartItem extends ISpellItem implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private ISpellItem nachfolger;
    private final JLabel l = new JLabel("Start", null, JLabel.CENTER);

    public ISpellItem clone() {
        return null;
    }

    public StartItem(Window w, MainPanel mp) {
        super(w);
        mp.registeredItems.add(this);
        this.setBackground(Color.WHITE);
        //this.setLayout(new BorderLayout());
        l.addMouseMotionListener(this);
        l.addMouseListener(this);
        ToolTipItem tip = new ToolTipItem();
        tip.setDescription("The spellfile starts here, every item you want to use must be connected to this one");
        this.add(l);
        this.add(tip);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public String getItem() {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public ISpellItem getNachfolger() {
        // TODO Auto-generated method stub
        return nachfolger;
    }

    @Override
    public ISpellItem getVorgaenger() {
        // TODO Auto-generated method stub
        return null;
    }

    private Point p;

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = (int) (this.getX() + e.getX() - p.getX());
        int y = (int) (this.getY() + e.getY() - p.getY());
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        this.setLocation(x, y);
        this.getParent().invalidate();
        this.repaint();
        l.repaint();
        this.getParent().repaint();
        y = this.getHeight() + this.getY();
        ISpellItem isi = this.nachfolger;
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNachfolger();
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        p = e.getPoint();
        this.getParent().setComponentZOrder(this, 0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ((MainPanel) this.getParent()).setScrollBars();
    }

    @Override
    public void setNachfolger(ISpellItem is) {
        this.nachfolger = is;
    }

    @Override
    public void setVorgaenger(ISpellItem is) {
    }
}