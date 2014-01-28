package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class ConditionComparePanel extends IParameter {
    private static final long serialVersionUID = 1L;

    public final ParameterSlot lefthand;
    public final ParameterSlot operator;
    public final ParameterSlot righthand;

    Window w;

    public IParameter clone() {
        ConditionComparePanel ccp = new ConditionComparePanel(preview, w);
        if (lefthand.content != null) {
            ccp.lefthand.add(lefthand.content.clone());
            ccp.lefthand.content = lefthand.content.clone();
        }
        if (operator.content != null) {
            ccp.operator.add(operator.content.clone());
            ccp.operator.content = operator.content.clone();
        }
        if (righthand.content != null) {
            ccp.righthand.add(righthand.content.clone());
            ccp.righthand.content = righthand.content.clone();
        }
        return ccp;
    }

    public ConditionComparePanel(boolean preview, Window w) {
        super(preview);
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        this.w = w;
        lefthand = new ParameterSlot(Parameter.CompareItem, "lefthand", preview, w);
        this.add(lefthand);
        operator = new ParameterSlot(Parameter.CompareOperator, "op", preview, w);
        this.add(operator);
        righthand = new ParameterSlot(Parameter.CompareItem, "righthand", preview, w);
        this.add(righthand);
        ToolTipItem tti = new ToolTipItem();
        tti.setToolTipText("<html>This is a conditioncompareitem</html>");
        this.add(tti);
        this.setBackground(new Color(108, 45, 199));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        if (preview) {

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
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (this.getParent() != null) {
            this.getParent().revalidate();
        }
    }

    @Override
    public String getString() {
        String left = "";
        if (lefthand.content != null) {
            left = lefthand.content.getString();
            left = left.substring(left.indexOf(',') + 1).trim();
        }
        String middle = "";
        if (operator.content != null) {
            middle = operator.content.getString();
        }
        String right = "";
        if (righthand.content != null) {
            right = righthand.content.getString();
            right = right.substring(right.indexOf(',') + 1).trim();
        }
        return left + " " + middle + " " + right;
    }

    private class CompareOperatorDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[CONDITION]";
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