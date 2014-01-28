package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class CompareOperatorPanel extends IParameter {
    private static final long serialVersionUID = 1L;
    private final CompareOperator op;

    public IParameter clone() {
        return new CompareOperatorPanel(op, preview);
    }

    public CompareOperatorPanel(CompareOperator operator, boolean preview) {
        super(preview);
        this.op = operator;
        JLabel l = new JLabel(" " + operator.getToken() + " ");
        this.setLayout(new BorderLayout());
        this.add(l);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        Parameter param = Parameter.CompareOperator;
        this.setBackground(param.getColor());
        DragSource ds = new DragSource();
        CompareOperatorDragGestureListener pdgl = new CompareOperatorDragGestureListener();
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
        return " " + op.getToken() + " ";
    }

    private class CompareOperatorDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            CompareOperatorPanel pp = (CompareOperatorPanel) dge.getComponent();
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[COMPAREOPERATOR]" + pp.op.getToken();
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