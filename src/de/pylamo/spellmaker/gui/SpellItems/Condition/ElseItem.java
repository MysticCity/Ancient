package de.pylamo.spellmaker.gui.SpellItems.Condition;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLayeredPane;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;

public class ElseItem extends ComplexItem {
    private static final long serialVersionUID = 1L;
    private SimpleStartPanel istp;
    private SidePanel isp = new SidePanel();
    private EndPanel eip = new EndPanel("endelse");

    @Override
    public ISpellItem clone() {
        return new ElseItem(!b, w);
    }

    public ElseItem(boolean preview, Window w) {
        super(w);
        this.setLayout(null);
        this.b = !preview;
        this.setOpaque(false);
        if (preview) {
            istp = new SimpleStartPanel("else");
            createDragSource();
        } else {
            istp = new SimpleStartPanel("else");
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
        IfItemDragGestureListener sis = new IfItemDragGestureListener();
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
            isi = isi.getNext();
        }
        if (height == 0) {
            height = 25;
        }
        if (isp == null) {
        	isp = new SidePanel();
        }
        if (istp == null) {
        	istp = new SimpleStartPanel("else");
        }
        if (eip == null) {
        	eip = new EndPanel("endelse");
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
            isi = isi.getNext();
        }
        this.setSize(w, h);
        this.setPreferredSize(new Dimension(w, h));
        super.revalidate();
        if (this.previous != null) {
            this.previous.revalidate();
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
            isi = isi.getNext();
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
                isi = isi.getNext();
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
            isi = isi.getNext();
        }
        // this.revalidate();
    }

    @Override
    public String getItem() {
        String s = "else";
        ISpellItem isi = this.firstBlockItem;
        while (isi != null) {
            s += "\n";
            s += isi.getItem();
            isi = isi.getNext();
        }
        s += "\nendelse";
        return s;
    }

    private class IfItemDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            String s = "[ELSEITEM]";
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