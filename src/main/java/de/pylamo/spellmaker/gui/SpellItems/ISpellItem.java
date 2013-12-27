package de.pylamo.spellmaker.gui.SpellItems;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ComplexItem;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class ISpellItem extends JPanel implements MouseListener, MouseMotionListener {
    /**
     *
     */
    protected boolean b;
    private static final long serialVersionUID = 1L;
    protected ISpellItem vorgaenger;
    protected ISpellItem nachfolger;
    private final JPopupMenu menu = new JPopupMenu();
    protected final Window w;

    protected ISpellItem(final Window w) {
        this.w = w;
        JMenuItem menuItem = new JMenuItem("delete");
        JMenuItem menuItem2 = new JMenuItem("copy");
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ISpellItem.this.delete();
            }
        };
        menuItem.addActionListener(al);
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ISpellItem si = ISpellItem.this.clone();
                si.setSize(si.getPreferredSize());
                si.setLocation(getX() + 30, getY() + 20);
                si.revalidate();
                w.mp.add(si);
                w.mp.revalidate();
                ;
                w.mp.repaint();
                si.revalidate();
            }
        });
        menu.add(menuItem);
        menu.add(menuItem2);

    }

    public abstract ISpellItem clone();

    void delete() {
        if (this.getParent() != null) {
            JComponent c = (JComponent) this.getParent();
            c.remove(this);
            w.mp.registeredItems.remove(this);
            if (this.vorgaenger instanceof ComplexItem && ((ComplexItem) this.vorgaenger).firstBlockItem == this) {
                if (((ComplexItem) this.vorgaenger).firstBlockItem == this) {
                    ((ComplexItem) this.vorgaenger).firstBlockItem = null;
                    this.vorgaenger.revalidate();
                }
            } else if (this.getVorgaenger() != null) {
                this.getVorgaenger().setNachfolger(this.getNachfolger());
                this.getVorgaenger().revalidate();
            }
            if (this.getNachfolger() != null) {
                this.getNachfolger().revalidate();
                this.getNachfolger().setVorgaenger(this.getVorgaenger());
                if (this.getVorgaenger() != null) {
                    this.getVorgaenger().setNachfolgerLocation();
                }
            }
            c.repaint();
        }
    }

    public abstract String getItem();

    public ISpellItem getNachfolger() {
        return nachfolger;
    }

    public void recalculateSize() {
        this.revalidate();
        int y = this.getHeight() + this.getY();
        ISpellItem isi = this.nachfolger;
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNachfolger();
        }
    }

    public ISpellItem getVorgaenger() {
        // TODO Auto-generated method stub
        return vorgaenger;
    }

    Point menuloc = null;

    public void setNachfolger(ISpellItem is) {
        if (is == this) {
            return;
        }
        this.nachfolger = is;
        ISpellItem isi = this.vorgaenger;
        while (isi != null) {
            isi.revalidate();
            isi = isi.getVorgaenger();
        }
    }

    public void setVorgaenger(ISpellItem is) {
        if (is == this) {
            return;
        }
        this.vorgaenger = is;
    }

    ISpellItem getRootItem() {
        ISpellItem is = this;
        while (is.getVorgaenger() != null) {
            is = is.getVorgaenger();
        }
        return is;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (b) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                menuloc = e.getPoint();
                menu.show((Component) e.getSource(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean dragEnabled = false;

    @Override
    public void mousePressed(MouseEvent e) {
        if (b) {
            p = e.getPoint();
            ((JLayeredPane) this.getParent()).moveToFront(this);
            ISpellItem isi = this.nachfolger;
            while (isi != null) {
                ((JLayeredPane) this.getParent()).moveToFront(isi);
                isi = isi.getNachfolger();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (b) {
            dragEnabled = false;
            ((MainPanel) this.getParent()).setScrollBars();
            ISpellItem is = w.mp.getSpellItemBeneath(this, new Point(this.getX() + e.getX(), this.getY() + e.getY()));
            if (is != null) {
                ISpellItem n = is.getNachfolger();
                if (is instanceof ComplexItem) {
                    ((ComplexItem) is).setNachfolger(this, new Point(e.getX() + this.getX(), e.getY() + this.getY()));
                    this.vorgaenger = is;
                } else {
                    is.setNachfolger(this);
                    this.vorgaenger = is;
                    this.setLocation(this.vorgaenger.getX(), this.vorgaenger.getY() + this.vorgaenger.getHeight());
                }
                if (n != null && !(is instanceof ComplexItem && this == ((ComplexItem) is).firstBlockItem)) {
                    ISpellItem isi = this.nachfolger;
                    while (isi != null && isi.getNachfolger() != null) {
                        isi = isi.getNachfolger();
                    }
                    if (isi != null) {
                        isi.setNachfolger(n);
                        n.setVorgaenger(isi);
                    } else {
                        this.nachfolger = n;
                        n.setVorgaenger(this);
                    }
                }
                ISpellItem isi = this.vorgaenger;
                while (isi != null) {
                    isi.revalidate();
                    isi = isi.getVorgaenger();
                }
                isi = this.nachfolger;
                this.revalidate();
                isi = this.getRootItem();
                isi.setNachfolgerLocation();
            }
        }
    }

    public void setNachfolgerLocation() {
        int y = this.getY() + this.getHeight();
        ISpellItem isi = this.getNachfolger();
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNachfolger();
        }
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (vorgaenger != null) {
            vorgaenger.revalidate();
        }
        /*
         * int w = 0; int h = 0; for (Component c : this.getComponents()) { h +=
		 * c.getPreferredSize().height; if (w < c.getPreferredSize().width) { w
		 * = c.getPreferredSize().width; } } this.setSize(w, h + 2);
		 */
        if (b) {
            this.setSize(this.getPreferredSize());
        }
    }

    public boolean isInVisiblePart(Point p) {
        return true;
    }

    protected Point p;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (b && (dragEnabled || Math.sqrt(Math.pow(p.getX() - e.getX(), 2) + Math.pow(p.getY() - e.getY(), 2)) > 3)) {
            dragEnabled = true;
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
            y = this.getHeight() + this.getY();
            ISpellItem isi = this.nachfolger;
            while (isi != null) {
                isi.setLocation(this.getX(), y);
                y += isi.getHeight();
                isi = isi.getNachfolger();
            }
            if (this.vorgaenger != null) {
                this.revalidate();
                if (this.vorgaenger instanceof ComplexItem && ((ComplexItem) this.vorgaenger).firstBlockItem == this) {
                    ((ComplexItem) this.vorgaenger).firstBlockItem = null;
                    this.vorgaenger.revalidate();
                    this.vorgaenger = null;
                    return;
                }
                this.vorgaenger.setNachfolger(null);
                this.vorgaenger = null;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
