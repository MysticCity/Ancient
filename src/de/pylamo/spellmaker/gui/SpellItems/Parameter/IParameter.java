package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;

public abstract class IParameter extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private final JPopupMenu menu = new JPopupMenu();

    protected boolean preview;

    public abstract IParameter clone();

    protected IParameter(boolean preview) {
        this.preview = preview;
        if (!preview) {
            JMenuItem item = new JMenuItem("delete");
            menu.add(item);
            ActionListener al = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IParameter.this.delete();
                }
            };
            item.addActionListener(al);
            this.addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            menu.show((Component) e.getSource(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    void delete() {
        if (this.getParent() instanceof ParameterSlot) {
            ParameterSlot ps = (ParameterSlot) this.getParent();
            ps.remove(this);
            ps.content = null;
            ps.add(ps.l);
            ps.revalidate();
            if (ps.getParent() instanceof ISpellItem) {
                ((ISpellItem) ps.getParent()).recalculateSize();
            }
        }
    }

    public abstract String getString();
}