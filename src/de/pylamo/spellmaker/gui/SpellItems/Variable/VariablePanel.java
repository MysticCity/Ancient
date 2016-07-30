package de.pylamo.spellmaker.gui.SpellItems.Variable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;

public class VariablePanel extends IParameter {
    private static final long serialVersionUID = 1L;
    Window w;

    public IParameter clone() {
        return new VariablePanel(s, preview, w);
    }

    private final String s;

    public VariablePanel(String variableName, boolean preview, Window w) {
        super(preview);
        this.w = w;
        if (preview) {
            w.spellItems.put(variableName, this);
        }
        this.s = variableName;
        JLabel jl = new JLabel(variableName);
        this.setLayout(new BorderLayout());
        this.add(jl, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.lightGray);
        DragSource ds = new DragSource();
        VariableDragGestureListener pdgl = new VariableDragGestureListener();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, pdgl);
        ds.addDragSourceMotionListener(pdgl);
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
    public String getString() {
        return ", " + s;
    }

    private class VariableDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            VariablePanel pp = (VariablePanel) dge.getComponent();
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[VARIABLE]" + pp.s;
            if (dge.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }
            dge.startDrag(cursor, new SimpleDragObject.TransferableSimpleDragObject(new SimpleDragObject(s)));
        }

        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            ImageMover.w.setLocation(new Point(dsde.getLocation().x + 2, dsde.getLocation().y + 4));
        }
    }
}