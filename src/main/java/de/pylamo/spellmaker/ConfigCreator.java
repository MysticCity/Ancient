package de.pylamo.spellmaker;

import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigCreator extends JFrame {
    private static final long serialVersionUID = 1L;
    private final int count;
    private final String[] vars;
    private final JTextField[][] fields;
    private final JPanel content = new JPanel();
    private final JTextField desc = new JTextField();
    private final JTextField shortdesc = new JTextField();
    private final JCheckBox ignoresfz = new JCheckBox();
    private final JCheckBox hidden = new JCheckBox();
    private final Window w;

    public ConfigCreator(Window w) {
        this.w = w;
        count = w.m.variables.size();
        vars = w.m.variables.toArray(new String[0]);
        fields = new JTextField[w.m.maxlevel][count];
        this.setSize(640, 530);
        this.setLocation(100, 100);
        JScrollPane jsp = new JScrollPane(content);
        this.setContentPane(jsp);
        createOptionNodes();
        content.setLayout(new ColumnLayout());
        if (w.m.variables.size() > 0) {
            createRows();
        }
        this.setVisible(true);
        createMenu();
    }

    void createOptionNodes() {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0, 2));
        container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Spell options"));
        JLabel descl = new JLabel("Description", JLabel.CENTER);
        descl.setPreferredSize(new Dimension(50, 20));
        desc.setMinimumSize(new Dimension(300, 20));
        desc.setPreferredSize(new Dimension(300, 20));
        desc.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel shortdescl = new JLabel("Short description", JLabel.CENTER);
        shortdesc.setMinimumSize(new Dimension(300, 20));
        shortdesc.setPreferredSize(new Dimension(300, 20));
        shortdesc.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel ignoresfzl = new JLabel("ignore spellfreezones?", JLabel.CENTER);
        JLabel hiddenl = new JLabel("hidden?", JLabel.CENTER);
        container.add(descl);
        container.add(desc);
        container.add(shortdescl);
        container.add(shortdesc);
        container.add(ignoresfzl);
        container.add(ignoresfz);
        container.add(hiddenl);
        container.add(hidden);
        content.add(container);
    }

    void createRows() {
        JPanel container = new JPanel();
        container.setLayout(new ColumnLayout());
        container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Variablen"));
        JPanel topPanel = new JPanel();
        JLabel ll = new JLabel("Level", JLabel.CENTER);
        ll.setPreferredSize(new Dimension(100, 20));
        topPanel.add(ll);
        for (String s : vars) {
            JLabel label = new JLabel(s, JLabel.CENTER);
            label.setPreferredSize(new Dimension(100, 20));
            topPanel.add(label);
        }
        container.add(topPanel);
        for (int i = 0; i < w.m.maxlevel; i++) {
            JPanel line = new JPanel();
            JLabel l = new JLabel("Level " + (i + 1) + ":", JLabel.CENTER);
            l.setPreferredSize(new Dimension(100, 20));
            line.add(l);
            for (int x = 0; x < count; x++) {
                JTextField jtf = new JTextField();
                fields[i][x] = jtf;
                jtf.setPreferredSize(new Dimension(100, 20));
                line.add(jtf);
            }
            container.add(line);
        }
        content.add(container);
    }

    private String description = "";

    void save() {
        if (!desc.getText().equals("description")) {
            description = desc.getText();
        }
        File f = new File(w.m.spellname + ".cfg");
        Map<String, Object[]> map = new HashMap<String, Object[]>();
        if (w.m.variables.size() > 0) {
            for (String s : vars) {
                map.put(s, new Object[w.m.maxlevel]);
            }
        }
        try {
            if (w.m.variables.size() > 0) {
                for (int i = 1; i <= w.m.maxlevel; i++) {
                    for (int x = 0; x < vars.length; x++) {
                        try {
                            map.get(vars[x])[i - 1] = Integer.parseInt(fields[i - 1][x].getText());
                        } catch (Exception e) {
                            map.get(vars[x])[i - 1] = fields[i - 1][x].getText();
                        }
                    }
                }
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
            bw.write(w.m.spellname + ":");
            bw.write('\n' + "  description: " + "'" + description + "'");
            bw.write('\n' + "  shortdescription: " + "'" + shortdesc.getText() + "'");
            bw.write('\n' + "  ignorespellfreezones: " + ignoresfz.isSelected());
            bw.write('\n' + "  hidden: " + hidden.isSelected());
            bw.write('\n' + "  variables:");
            if (w.m.variables.size() > 0) {
                for (Map.Entry<String, Object[]> vals : map.entrySet()) {
                    bw.write('\n' + "    " + vals.getKey() + ":");
                    for (int i = 0; i < vals.getValue().length; i++) {
                        bw.write('\n' + "      level" + (i + 1) + ":" + " " + vals.getValue()[i]);
                    }
                }
            }
            bw.close();
            JOptionPane.showMessageDialog(w, "Spellconfig successfully saved  to: " + f.getAbsolutePath(), "Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void createMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menubar.add(menu);
        JMenuItem saveItem = new JMenuItem("save");
        menu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        this.setJMenuBar(menubar);
    }
}