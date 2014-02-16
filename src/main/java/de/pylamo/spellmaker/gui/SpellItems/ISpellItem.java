package de.pylamo.spellmaker.gui.SpellItems;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ComplexItem;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class ISpellItem extends JPanel implements MouseListener, MouseMotionListener {
    protected boolean b;
    private static final long serialVersionUID = 1L;
    protected ISpellItem previous;
    protected ISpellItem next;
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
            if (this.previous instanceof ComplexItem && ((ComplexItem) this.previous).firstBlockItem == this) {
                if (((ComplexItem) this.previous).firstBlockItem == this) {
                    ((ComplexItem) this.previous).firstBlockItem = null;
                    this.previous.revalidate();
                }
            } else if (this.getPrevious() != null) {
                this.getPrevious().setNext(this.getNext());
                this.getPrevious().revalidate();
            }
            if (this.getNext() != null) {
                this.getNext().revalidate();
                this.getNext().setPrevious(this.getPrevious());
                if (this.getPrevious() != null) {
                    this.getPrevious().setNextLocation();
                }
            }
            c.repaint();
        }
    }

    public abstract String getItem();

    public ISpellItem getNext() {
        return next;
    }

    public void recalculateSize() {
        this.revalidate();
        int y = this.getHeight() + this.getY();
        ISpellItem isi = this.next;
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNext();
        }
    }

    public ISpellItem getPrevious() {
        return previous;
    }

    Point menuloc = null;

    public void setNext(ISpellItem is) {
        if (is == this) {
            return;
        }
        this.next = is;
        ISpellItem isi = this.previous;
        while (isi != null) {
            isi.revalidate();
            isi = isi.getPrevious();
        }
    }

    public void setPrevious(ISpellItem is) {
        if (is == this) {
            return;
        }
        this.previous = is;
    }

    ISpellItem getRootItem() {
        ISpellItem is = this;
        while (is.getPrevious() != null) {
            is = is.getPrevious();
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private boolean dragEnabled = false;

    @Override
    public void mousePressed(MouseEvent e) {
        if (b) {
            p = e.getPoint();
            ((JLayeredPane) this.getParent()).moveToFront(this);
            ISpellItem isi = this.next;
            while (isi != null) {
                ((JLayeredPane) this.getParent()).moveToFront(isi);
                isi = isi.getNext();
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
                ISpellItem n = is.getNext();
                if (is instanceof ComplexItem) {
                    ((ComplexItem) is).setNachfolger(this, new Point(e.getX() + this.getX(), e.getY() + this.getY()));
                    this.previous = is;
                } else {
                    is.setNext(this);
                    this.previous = is;
                    this.setLocation(this.previous.getX(), this.previous.getY() + this.previous.getHeight());
                }
                if (n != null && !(is instanceof ComplexItem && this == ((ComplexItem) is).firstBlockItem)) {
                    ISpellItem isi = this.next;
                    while (isi != null && isi.getNext() != null) {
                        isi = isi.getNext();
                    }
                    if (isi != null) {
                        isi.setNext(n);
                        n.setPrevious(isi);
                    } else {
                        this.next = n;
                        n.setPrevious(this);
                    }
                }
                ISpellItem isi = this.previous;
                while (isi != null) {
                    isi.revalidate();
                    isi = isi.getPrevious();
                }
                isi = this.next;
                this.revalidate();
                isi = this.getRootItem();
                isi.setNextLocation();
            }
        }
    }

    public void setNextLocation() {
        int y = this.getY() + this.getHeight();
        ISpellItem isi = this.getNext();
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNext();
        }
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (previous != null) {
            previous.revalidate();
        }
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
            ISpellItem isi = this.next;
            while (isi != null) {
                isi.setLocation(this.getX(), y);
                y += isi.getHeight();
                isi = isi.getNext();
            }
            if (this.previous != null) {
                this.revalidate();
                if (this.previous instanceof ComplexItem && ((ComplexItem) this.previous).firstBlockItem == this) {
                    ((ComplexItem) this.previous).firstBlockItem = null;
                    this.previous.revalidate();
                    this.previous = null;
                    return;
                }
                this.previous.setNext(null);
                this.previous = null;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}