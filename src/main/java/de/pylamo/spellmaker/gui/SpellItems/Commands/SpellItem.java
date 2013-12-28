package de.pylamo.spellmaker.gui.SpellItems.Commands;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;

public class SpellItem extends ISpellItem {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final String name;
    public final ParameterSlot[] parameters;
    public final TopInformation ti;
    private static final Color bgc = Color.WHITE;
    private String s;
    boolean preview = false;
    Parameter[] params;
    String[] paramdesc;

    @Override
    public ISpellItem clone() {
        SpellItem is = null;
        if (preview) {
            is = new SpellItem(name, params, paramdesc, s, w);
        } else {
            is = new SpellItem(name, params, paramdesc, w);
        }
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].content != null) {
                is.parameters[i].add(parameters[i].content.clone());
                is.parameters[i].content = parameters[i].content.clone();
            }
        }
        return is;
    }

    public SpellItem(String name, Parameter[] params, String[] paramdesc, String s, Window w) {
        super(w);
        this.s = s;
        preview = true;
        this.params = params;
        this.paramdesc = paramdesc;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(bgc);
        this.name = name;
        ti = new TopInformation(name);
        this.add(ti);
        parameters = new ParameterSlot[params.length];
        for (int i = 0; i < params.length; i++) {
            parameters[i] = new ParameterSlot(params[i], paramdesc[i], true, w);
            this.add(parameters[i]);
        }
        DragSource ds = new DragSource();
        SpellItemDragGestureListener sis = new SpellItemDragGestureListener();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, sis);
        ds.addDragSourceMotionListener(sis);
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

    public SpellItem(String name, Parameter[] params, String[] paramdesc, Window w) {
        super(w);
        w.mp.registeredItems.add(this);
        this.setOpaque(true);
        this.setBackground(bgc);
        this.params = params;
        this.paramdesc = paramdesc;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addMouseListener(this);
        this.name = name;
        parameters = new ParameterSlot[params.length];
        ti = new TopInformation(name);
        this.add(ti);
        this.addMouseMotionListener(this);
        this.b = true;
        for (int i = 0; i < params.length; i++) {
            parameters[i] = new ParameterSlot(params[i], paramdesc[i], false, w);
            this.add(parameters[i]);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        // if (name != null)
        // g.drawString(name, 20, 20);
    }

    @Override
    public String getItem() {
        // TODO Auto-generated method stub
        String s = this.name;
        for (ParameterSlot ps : parameters) {
            if (ps.content != null) {
                s += ps.content.getString();
            }
        }
        return s;
    }


    private class SpellItemDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            String s = "[SPELLITEM]" + SpellItem.this.s;
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