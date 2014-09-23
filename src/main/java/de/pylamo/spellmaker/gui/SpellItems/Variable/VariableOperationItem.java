package de.pylamo.spellmaker.gui.SpellItems.Variable;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
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
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;

public class VariableOperationItem extends ISpellItem {
    private static final long serialVersionUID = 1L;

    public final ArrayList<ParameterSlot> slots = new ArrayList<ParameterSlot>();

    public ISpellItem clone() {
        VariableOperationItem voi = new VariableOperationItem(this.b, w);
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).content != null) {
                voi.slots.get(i).add(slots.get(i).content.clone());
                voi.slots.get(i).content = slots.get(i).content.clone();
            }
        }
        return voi;
    }

    public VariableOperationItem(boolean preview, Window w) {
        super(w);
        ParameterSlot p1 = new ParameterSlot(Parameter.Variable, "Variable", preview, w);
        ParameterSlot p2 = new ParameterSlot(Parameter.Operator, "Op", preview, w);
        ParameterSlot p3 = new ParameterSlot(Parameter.All, "Value", preview, w);
        slots.add(p1);
        slots.add(p2);
        slots.add(p3);
        this.b = !preview;
        JPanel j = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void revalidate() {
                super.revalidate();
                if (this.getParent() != null) {
                    this.getParent().repaint();
                    if (this.getParent() != null && this.getParent() instanceof JComponent) {
                        ((JComponent) this.getParent()).revalidate();
                    }
                }
            }
        };
        j.add(p1);
        j.add(p2);
        j.setBackground(Color.white);
        j.add(p3);
        j.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        TopInformation ti = new TopInformation("VariableOperation");
        ti.setDescription("A variable operation");
        this.add(ti);
        this.add(j);
        this.setBackground(Color.WHITE);
        if (preview) {
            DragSource ds = new DragSource();
            ItemDragGestureListener sis = new ItemDragGestureListener();
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
        } else {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }
    }

	/*
     * @Override public void revalidate() { super.revalidate(); if(j == null)
	 * return; int w = j.getPreferredSize().width; int h =
	 * j.getPreferredSize().height + ti.getPreferredSize().height + 2; if (w <
	 * ti.getPreferredSize().getWidth()) { w = (int)
	 * ti.getPreferredSize().getWidth(); } this.setSize(w, h); }
	 */

    @Override
    public String getItem() {
        String left = "";
        if (slots.get(0).content != null) {
            left = slots.get(0).content.getString();
        }
        if (left.trim().startsWith(",")) {
            left = left.substring(left.indexOf(',') + 1);
        }
        String right = "";
        if (slots.get(2).content != null) {
            right = slots.get(2).content.getString();
        }
        if (right.trim().startsWith(",")) {
            right = right.substring(right.indexOf(',') + 1);
        }
        String middle = "";
        if (slots.get(1).content != null) {
            middle = slots.get(1).content.getString();
        }
        return "var " + left.trim() + " " + middle.trim() + " " + right.trim();
    }

    private class ItemDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            String s = "[VariableOperationItem]";
            SimpleDragObject sdo = new SimpleDragObject(s);
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            Cursor cursor = null;
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            if (dge.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }
            revalidate();
            dge.startDrag(cursor, new SimpleDragObject.TransferableSimpleDragObject(sdo));
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            ImageMover.w.setLocation(new Point(dsde.getLocation().x + 2, dsde.getLocation().y + 4));
        }
    }
}