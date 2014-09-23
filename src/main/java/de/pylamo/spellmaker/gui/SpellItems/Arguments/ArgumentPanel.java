package de.pylamo.spellmaker.gui.SpellItems.Arguments;

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
import javax.swing.JComponent;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;

public class ArgumentPanel extends IParameter {
    private static final long serialVersionUID = 1L;
    public final String name;
    public final Parameter p;
    private final TopInformation ti;
    public final ParameterSlot[] parameters;
    Parameter[] params;
    String[] paramdesc;
    private String s;
    String description;
    Window w;

    @Override
    public IParameter clone() {
        ArgumentPanel is = null;
        if (preview) {
            is = new ArgumentPanel(name, p, description, params, paramdesc, s, w);
        } else {
            is = new ArgumentPanel(name, p, description, params, paramdesc, w);
        }
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].content != null) {
                is.parameters[i].add(parameters[i].content.clone());
                is.parameters[i].content = parameters[i].content.clone();
            }
        }
        return is;
    }

    public ArgumentPanel(String name, Parameter p, String desc, Parameter[] params, String[] paramdesc, String s, Window w) {
        super(true);
        this.s = s;
        this.w = w;
        this.p = p;
        this.params = params;
        this.paramdesc = paramdesc;
        this.name = name;
        ti = new TopInformation(name);
        this.description = desc;
        ti.setDescription(desc);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        // this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(p.getColor());
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        this.add(ti);
        parameters = new ParameterSlot[params.length];
        for (int i = 0; i < params.length; i++) {
            parameters[i] = new ParameterSlot(params[i], paramdesc[i], true, w);
            this.add(parameters[i]);
        }
        DragSource ds = new DragSource();
        ParameterDragGestureListener pdgl = new ParameterDragGestureListener();
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

    public ArgumentPanel(String name, Parameter p, String desc, Parameter[] params, String[] paramdesc, Window w) {
        super(false);
        this.setOpaque(true);
        this.setBackground(p.getColor());
        this.params = params;
        this.paramdesc = paramdesc;
        this.description = desc;
        this.w = w;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        this.name = name;
        parameters = new ParameterSlot[params.length];
        ti = new TopInformation(name);
        this.p = p;
        ti.setDescription(desc);
        ti.l.addMouseListener(this);
        this.add(ti);
        for (int i = 0; i < params.length; i++) {
            parameters[i] = new ParameterSlot(params[i], paramdesc[i], false, w);
            this.add(parameters[i]);
        }
    }

    public String getString() {
        String s = ", (" + name;
        for (ParameterSlot param : parameters) {
            if (param.content != null) {
                s += param.content.getString();
            }
        }
        s += ")";
        return s;
    }

    @Override
    public void revalidate() {
        this.setSize(this.getPreferredSize());
        if (this.getParent() != null && this.getParent() instanceof JComponent) {
            ((JComponent) this.getParent()).revalidate();
        }
    }

    private class ParameterDragGestureListener implements DragGestureListener, DragSourceMotionListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            Cursor cursor = null;
            ArgumentPanel pp = (ArgumentPanel) dge.getComponent();
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paint(bi.getGraphics());
            ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
            String s = "[ARGUMENT]" + pp.s;
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