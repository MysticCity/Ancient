package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ParameterPanel extends IParameter {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final String name;
    public final Parameter p;
    private String desc;
    private final int subparams;
    final ToolTipItem tip;
    public final ArrayList<JTextField> fields = new ArrayList<JTextField>();


    @Override
    public IParameter clone() {
        ParameterPanel pp = new ParameterPanel(this.name, p, subparams, preview);
        for (int i = 0; i < fields.size(); i++) {
            pp.fields.get(i).setText(fields.get(i).getText());
        }
        return pp;
    }

    public ParameterPanel(String name, Parameter p, int subparams, boolean preview) {
        super(preview);
        this.p = p;
        this.subparams = subparams;
        // possibleTypes = p.getPossibleTypes();
        this.name = name;
        tip = new ToolTipItem();
        // this.setLayout(null);
        // this.setSize(new Dimension(100, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel l = new JLabel(name);
        l.setOpaque(false);
        this.add(l);
        // this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(p.getColor());
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
        for (int i = 0; i < subparams; i++) {
            JTextField field = new JTextField();
            field.setPreferredSize(new Dimension(25, 15));
            this.add(field);
            this.fields.add(field);
        }
        this.add(tip);
        if (!preview) {
            l.addMouseListener(this);
        }
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

    public String getString() {
        String s = ", " + name;
        for (JTextField field : fields) {
            if (field.getText() != null && !field.getText().equals("")) {
                s += ":" + field.getText();
            }
        }
        return s;
    }

    public void setTooltip(String s) {
        this.desc = s;
        tip.setDescription(s);
    }

    private class ParameterDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            ParameterPanel pp = (ParameterPanel) dge.getComponent();
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[PARAMETER]" + pp.name + " | " + pp.subparams + " | " + pp.p.name() + " | " + pp.desc;
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
