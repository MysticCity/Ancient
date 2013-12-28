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

public class AndItem extends IParameter {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final ParameterSlot lefthand;
    public final ParameterSlot righthand;
    Window w;

    public IParameter clone() {
        AndItem ai = new AndItem(preview, w);
        if (lefthand.content != null) {
            ai.lefthand.add(lefthand.content.clone());
            ai.lefthand.content = lefthand.content.clone();
        }
        if (righthand.content != null) {
            ai.righthand.add(righthand.content.clone());
            ai.righthand.content = righthand.content.clone();
        }
        return ai;
    }

    public AndItem(boolean preview, Window w) {
        super(preview);
        this.w = w;
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        lefthand = new ParameterSlot(Parameter.Condition, "lefthand", preview, w);
        this.add(lefthand);
        JLabel la = new JLabel(" AND ");
        this.add(la);
        righthand = new ParameterSlot(Parameter.Condition, "righthand", preview, w);
        this.add(righthand);
        ToolTipItem tti = new ToolTipItem();
        tti.setToolTipText(
                "<html>This is an if item, if the specified condition <br> is fulfilled, the following block will be executed</html>");
        this.add(tti);
        this.setBackground(new Color(108, 45, 199));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        if (preview) {
            DragSource ds = new DragSource();
            ParameterDragGestureListener pdgl = new ParameterDragGestureListener();
            ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, pdgl);
            ds.addDragSourceMotionListener(pdgl);
            ds.addDragSourceListener(new DragSourceListener() {

                @Override
                public void dropActionChanged(DragSourceDragEvent dsde) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void dragOver(DragSourceDragEvent dsde) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void dragExit(DragSourceEvent dse) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void dragEnter(DragSourceDragEvent dsde) {
                    // TODO Auto-generated method stub

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
        if (lefthand != null && lefthand.content != null) {
            left = lefthand.content.getString();
        }
        String right = "";
        if (righthand != null && righthand.content != null) {
            right = righthand.content.getString();
        }
        return left + " && " + right;
    }

    private class ParameterDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[ANDITEM]";
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