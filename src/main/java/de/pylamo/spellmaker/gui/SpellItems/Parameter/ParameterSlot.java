package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.Argument;
import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.Arguments.ArgumentPanel;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.*;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperationItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperator;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.regex.Pattern;

public class ParameterSlot extends JPanel implements Cloneable {
    private static final long serialVersionUID = 1L;
    private final Parameter param;
    private final String desc;
    public IParameter content;
    final JLabel l = new JLabel();
    private final Window w;

    public ParameterSlot(Parameter p, String s, boolean preview, Window w) {
        this.w = w;
        this.desc = s;
        if (!preview) {
            new ParameterSlotDropTargetListener(this);
        }
        param = p;
        l.setText(desc);
        this.setLayout(new BorderLayout());
        this.add(l, BorderLayout.CENTER);
        this.setBackground(p.getColor());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (this.getParent() != null && this.getParent() instanceof JComponent) {
            ((JComponent) this.getParent()).revalidate();
        }
    }

    @Override
    public Object clone() {
        return new ParameterSlot(this.param, this.desc, false, w);
    }

    @Override
    public Component add(Component c) {
        if (this.getParent() instanceof SpellItem) {
            ((ISpellItem) this.getParent()).recalculateSize();
            if (getParent() != null && getParent() instanceof JComponent) {
                ((JComponent) getParent()).revalidate();
            }
        } else if (this.getParent().getParent() instanceof VariableOperationItem) {
            ((ISpellItem) this.getParent().getParent()).recalculateSize();
            if (getParent().getParent() != null && getParent().getParent() instanceof JComponent) {
                ((JComponent) getParent().getParent()).revalidate();
            }
            this.getParent().getParent().doLayout();
        }
        return super.add(c);
    }

    @Override
    public void add(Component c, Object i) {
        super.add(c, i);
        if (this.getParent() instanceof SpellItem) {
            ((ISpellItem) this.getParent()).recalculateSize();
            if (getParent() != null && getParent() instanceof JComponent) {
                ((JComponent) getParent()).revalidate();
            }
        }
        if (this.getParent() instanceof ConditionStartPanel) {
            if (getParent() != null && getParent() instanceof JComponent) {
                ((JComponent) getParent()).revalidate();
            }
        } else if (this.getParent() != null && this.getParent().getParent() instanceof VariableOperationItem) {
            ((ISpellItem) this.getParent().getParent()).recalculateSize();
            this.getParent().getParent().doLayout();
            if (getParent() != null && getParent() instanceof JComponent) {
                ((JComponent) getParent()).revalidate();
            }
            this.getParent().doLayout();
            if (getParent().getParent() != null && getParent().getParent() instanceof JComponent) {
                ((JComponent) getParent().getParent()).revalidate();
            }
        }
    }

    class ParameterSlotDropTargetListener extends DropTargetAdapter implements DropTargetListener {
        @SuppressWarnings("unused")
        private DropTarget dropTarget;
        private final JPanel panel;

        public ParameterSlotDropTargetListener(JPanel panel) {
            this.panel = panel;
            dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
        }

        public void drop(DropTargetDropEvent event) {
            try {
                if (!event.isDataFlavorSupported(SimpleDragObject.dataFlavor)) {
                    return;
                }
                Transferable tr = event.getTransferable();
                String s = ((SimpleDragObject) tr.getTransferData(SimpleDragObject.dataFlavor)).s;
                if (s.startsWith("[COMPAREOPERATOR]")) {
                    if (ParameterSlot.this.param != Parameter.CompareOperator) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (content != null) {
                        remove(content);
                    }
                    s = s.replace("[COMPAREOPERATOR]", "");
                    content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(s), false);
                    this.panel.remove(l);
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[VARIABLEOPERATOR]")) {
                    if (ParameterSlot.this.param != Parameter.Operator) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (content != null) {
                        remove(content);
                    }
                    s = s.replace("[VARIABLEOPERATOR]", "");
                    content = new VariableOperatorPanel(VariableOperator.getOperatorByToken(s), VariableOperator.getOperatorByToken(s).getTooltip(), false);
                    this.panel.remove(l);
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[CONDITION]")) {
                    if (ParameterSlot.this.param != Parameter.Condition) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (content != null) {
                        remove(content);
                    }
                    content = new ConditionComparePanel(false, w);
                    this.panel.remove(l);
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[ANDITEM]")) {
                    if (ParameterSlot.this.param != Parameter.Condition) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (content != null) {
                        remove(content);
                    }
                    content = new AndItem(false, w);
                    this.panel.remove(l);
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[ORITEM]")) {
                    if (ParameterSlot.this.param != Parameter.Condition) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (content != null) {
                        remove(content);
                    }
                    content = new OrItem(false, w);
                    this.panel.remove(l);
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[VARIABLE]")) {
                    if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.Variable)) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    s = s.replace("[VARIABLE]", "");
                    if (content != null) {
                        remove(content);
                    }
                    content = new VariablePanel(s.replace("[VARIABLE]", ""), false, w);
                    this.panel.remove(l);
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[ARGUMENT]")) {
                    s = s.replace("[ARGUMENT]", "");
                    Argument a = new Argument(s);
                    final ArgumentPanel p = new ArgumentPanel(a.name, a.param, a.desc, a.parameters, a.paramdesc, w);
                    if (!ParameterSlot.this.param.getPossibleTypes().contains(p.p)) {
                        event.rejectDrop();
                        JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (content != null) {
                        remove(content);
                    }
                    remove(l);
                    content = p;
                    add(content, BorderLayout.CENTER);
                    event.dropComplete(true);
                    content.revalidate();
                    if (getParent() != null && getParent() instanceof JComponent) {
                        ((JComponent) getParent()).revalidate();
                    }
                    return;
                }
                if (s.startsWith("[PARAMETER]")) {
                    s = s.replace("[PARAMETER]", "");
                    String[] args = s.split(Pattern.quote("|"));
                    if (args.length == 4) {
                        args[0] = args[0].trim();
                        if (args[0].equalsIgnoreCase("Number:")) {
                            if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.Number)) {
                                event.rejectDrop();
                                JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            content = new NumberParameterPanel(false);
                            content.setLocation(0, 0);
                            event.acceptDrop(DnDConstants.ACTION_COPY);
                            add(content, BorderLayout.CENTER);
                            content.revalidate();
                            if (this.panel.getParent() instanceof ISpellItem) {
                                ((ISpellItem) this.panel.getParent()).recalculateSize();
                            }
                            this.panel.remove(l);
                            content.doLayout();
                            content.revalidate();
                            if (getParent() != null && getParent() instanceof JComponent) {
                                ((JComponent) getParent()).revalidate();
                            }
                            event.dropComplete(true);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("Boolean:")) {
                            if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.Boolean)) {
                                event.rejectDrop();
                                JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            content = new BooleanParameterPanel(false);
                            content.setLocation(0, 0);
                            event.acceptDrop(DnDConstants.ACTION_COPY);
                            add(content, BorderLayout.CENTER);
                            content.revalidate();
                            if (this.panel.getParent() instanceof ISpellItem) {
                                ((ISpellItem) this.panel.getParent()).recalculateSize();
                            }
                            this.panel.remove(l);
                            content.doLayout();
                            content.revalidate();
                            if (getParent() != null && getParent() instanceof JComponent) {
                                ((JComponent) getParent()).revalidate();
                            }
                            event.dropComplete(true);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("String:")) {
                            if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.String)) {
                                event.rejectDrop();
                                JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            content = new StringParameterPanel(false);
                            content.setLocation(0, 0);
                            event.acceptDrop(DnDConstants.ACTION_COPY);
                            this.panel.remove(l);
                            add(content, BorderLayout.CENTER);
                            content.revalidate();
                            ((ISpellItem) this.panel.getParent()).recalculateSize();
                            event.dropComplete(true);
                            content.revalidate();
                            if (getParent() != null && getParent() instanceof JComponent) {
                                ((JComponent) getParent()).revalidate();
                            }
                            return;
                        }
                        if (content != null) {
                            remove(content);
                        }
                        args[1] = args[1].trim();
                        args[2] = args[2].trim();
                        args[3] = args[3].trim();
                        final ParameterPanel p = new ParameterPanel(args[0], Parameter.getParameterByName(args[2]), Integer.parseInt(args[1]), false);
                        p.setTooltip(args[3]);
                        p.setBorder(null);
                        content = p;
                        if (p.p.getPossibleTypes().contains(ParameterSlot.this.param)) {
                            p.setLocation(0, 0);
                            event.acceptDrop(DnDConstants.ACTION_COPY);
                            this.panel.remove(l);
                            this.panel.add(p, BorderLayout.CENTER);
                            if (this.panel.getParent() instanceof SpellItem) {
                                ((SpellItem) this.panel.getParent()).recalculateSize();
                            }
                            p.revalidate();
                            content.revalidate();
                            if (getParent() != null && getParent() instanceof JComponent) {
                                ((JComponent) getParent()).revalidate();
                            }
                            event.dropComplete(true);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(w, "The parametertype does not match", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                event.rejectDrop();
            } catch (Exception e) {
                e.printStackTrace();
                event.rejectDrop();
            }
        }
    }
}