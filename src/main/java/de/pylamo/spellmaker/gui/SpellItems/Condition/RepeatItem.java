package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RepeatItem extends ComplexItem {
    private static final long serialVersionUID = 1L;

    public final RepeatStartPanel istp;
    private final SidePanel isp = new SidePanel();
    private final EndPanel eip = new EndPanel("endrepeat");

    @Override
    public ISpellItem clone() {
        RepeatItem ri = new RepeatItem(!b, w);
        if (istp.amount.content != null) {
            ri.istp.amount.add(istp.amount.content.clone());
            ri.istp.amount.content = istp.amount.content.clone();
        }
        return ri;
    }

    public RepeatItem(boolean preview, Window w) {
        super(w);
        this.setLayout(null);
        this.b = !preview;
        this.setOpaque(false);
        if (preview) {
            istp = new RepeatStartPanel(true, w);
            createDragSource();
        } else {
            istp = new RepeatStartPanel(false, w);
            istp.addMouseListener(this);
            istp.addMouseMotionListener(this);
            isp.addMouseListener(this);
            isp.addMouseMotionListener(this);
            eip.addMouseListener(this);
            eip.addMouseMotionListener(this);
        }
        this.add(istp);
        this.add(isp);
        this.add(eip);
        eip.setPreferredSize(istp.getPreferredSize());
        eip.revalidate();
        eip.repaint();
        istp.setLocation(0, 0);
        istp.setVisible(true);
        this.revalidate();
    }

    @Override
    public EndPanel getEndPanel() {
        return eip;
    }

    @Override
    public boolean isInVisiblePart(Point p) {
        int x = this.getX();
        int y = this.getY();
        if (p.getX() < x + this.istp.getWidth() && p.getY() < y + istp.getHeight()) {
            return true;
        } else if (p.getX() < x + 25 && p.getY() < y + isp.getY() + isp.getHeight() && p.getY() > y + isp.getY()) {
            return true;
        } else if (p.getX() < x + eip.getWidth() && p.getY() > y + eip.getY()) {
            return true;
        }
        return false;
    }

    void createDragSource() {

        DragSource ds = new DragSource();
        RepeatItemDragGestureListener sis = new RepeatItemDragGestureListener();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, sis);
        ds.addDragSourceMotionListener(sis);
        ds.addDragSourceListener(new DragSourceListener() {

            @Override
            public void dropActionChanged(DragSourceDragEvent dsde) {
            }

            @Override
            public void dragOver(DragSourceDragEvent dsde) {
            }

            @Override
            public void dragExit(DragSourceEvent dse) {
            }

            @Override
            public void dragEnter(DragSourceDragEvent dsde) {
            }

            @Override
            public void dragDropEnd(DragSourceDropEvent dsde) {
                ImageMover.stop();
            }
        });
    }

    @Override
    public void revalidate() {
        int w = 0;
        int h;
        int height = 0;
        ISpellItem isi = firstBlockItem;
        while (isi != null) {
            height += isi.getHeight();
            isi = isi.getNachfolger();
        }
        if (height == 0) {
            height = 25;
        }
        isp.setSize(25, height);
        isp.setLocation(0, istp.getPreferredSize().height);
        istp.setSize(istp.getPreferredSize());
        eip.setSize(eip.getPreferredSize().width + 70, eip.getPreferredSize().height);
        eip.setLocation(0, isp.getY() + isp.getHeight());
        h = this.eip.getY() + this.eip.getHeight();
        for (Component com : getComponents()) {
            if (com.getPreferredSize().width + com.getX() > w) {
                w = com.getPreferredSize().width + com.getX();
            }
        }
        isi = firstBlockItem;
        ISpellItem vo = null;
        while (isi != null) {
            if (vo != null) {
                isi.setLocation(vo.getX(), vo.getY() + vo.getHeight());
            } else {
                isi.setLocation(this.getX() + 25, this.getY() + istp.getHeight());
            }
            vo = isi;
            isi = isi.getNachfolger();
        }
        this.setSize(w, h);
        this.setPreferredSize(new Dimension(w, h));
        super.revalidate();
        if (this.vorgaenger != null) {
            this.vorgaenger.revalidate();
        }
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        ISpellItem isi = firstBlockItem;
        ISpellItem vo = null;
        while (isi != null) {
            if (vo != null) {
                isi.setLocation(vo.getX(), vo.getY() + vo.getHeight());
            } else {
                isi.setLocation(this.getX() + 25, this.getY() + istp.getHeight());
            }
            vo = isi;
            isi = isi.getNachfolger();
        }
        // this.revalidate();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (b) {
            p = e.getPoint();
            ((JLayeredPane) this.getParent()).moveToFront(this);
            ISpellItem isi = this.firstBlockItem;
            while (isi != null) {
                ((JLayeredPane) this.getParent()).moveToFront(isi);
                isi = isi.getNachfolger();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        ISpellItem isi = firstBlockItem;
        ISpellItem vo = null;
        while (isi != null) {
            if (vo != null) {
                isi.setLocation(vo.getX(), vo.getY() + vo.getHeight());
            } else {
                isi.setLocation(this.getX() + 25, this.getY() + istp.getHeight());
            }
            vo = isi;
            isi = isi.getNachfolger();
        }
        // this.revalidate();
    }

    @Override
    public String getItem() {
        String amount = "";
        if (istp.amount.content != null) {
            amount = istp.amount.content.getString();
        }
        String s = "repeat, " + amount.substring(amount.indexOf(",") + 1).trim();
        ISpellItem isi = this.firstBlockItem;
        while (isi != null) {
            s += "\n";
            s += isi.getItem();
            isi = isi.getNachfolger();
        }
        s += "\nendrepeat";
        return s;
    }

    private class RepeatItemDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            String s = "[REPEATITEM]";
            SimpleDragObject sdo = new SimpleDragObject(s);
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            Cursor cursor = null;
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            if (dge.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }
            dge.startDrag(cursor, new SimpleDragObject.TransferableSimpleDragObject(sdo));
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            ImageMover.w.setLocation(new Point(dsde.getLocation().x + 2, dsde.getLocation().y + 4));
        }
    }
}