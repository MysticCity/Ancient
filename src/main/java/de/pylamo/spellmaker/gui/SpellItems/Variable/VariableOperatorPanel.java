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

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;

public class VariableOperatorPanel extends IParameter {
    private static final long serialVersionUID = 1L;
    private final VariableOperator op;
    private String tooltip;

    public IParameter clone() {
        return new VariableOperatorPanel(op, tooltip, preview);
    }

    public VariableOperatorPanel(VariableOperator operator, String tooltip, boolean preview) {
        super(preview);
        this.op = operator;
        this.tooltip = tooltip;
        this.setLayout(new FlowLayout());
        ToolTipItem tti = new ToolTipItem();
        tti.setDescription(tooltip);
        JLabel l = new JLabel(" " + operator.getToken() + " ");
        this.add(l);
        this.add(tti);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        Parameter param = Parameter.Operator;
        this.setBackground(param.getColor());
        DragSource ds = new DragSource();
        VariableOperatorDragGestureListener pdgl = new VariableOperatorDragGestureListener();
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

    private class VariableOperatorDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            VariableOperatorPanel pp = (VariableOperatorPanel) dge.getComponent();
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[VARIABLEOPERATOR]" + pp.op.getToken();
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