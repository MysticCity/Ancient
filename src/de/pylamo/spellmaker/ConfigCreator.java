package de.pylamo.spellmaker;

import de.pylamo.spellmaker.gui.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ConfigCreator
  extends JFrame
{
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
  
  public ConfigCreator(Window w)
  {
    this.w = w;
    this.count = w.m.variables.size();
    this.vars = ((String[])w.m.variables.toArray(new String[w.m.variables.size()]));
    this.fields = new JTextField[w.m.maxlevel][this.count];
    setSize(640, 530);
    setLocation(100, 100);
    JScrollPane jsp = new JScrollPane(this.content);
    setContentPane(jsp);
    createOptionNodes();
    this.content.setLayout(new ColumnLayout());
    if (w.m.variables.size() > 0) {
      createRows();
    }
    setVisible(true);
    createMenu();
  }
  
  void createOptionNodes()
  {
    JPanel container = new JPanel();
    container.setLayout(new GridLayout(0, 2));
    container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "Spell options"));
    JLabel descl = new JLabel("Description", 0);
    descl.setPreferredSize(new Dimension(50, 20));
    this.desc.setMinimumSize(new Dimension(300, 20));
    this.desc.setPreferredSize(new Dimension(300, 20));
    this.desc.setBorder(BorderFactory.createLineBorder(Color.black));
    JLabel shortdescl = new JLabel("Short description", 0);
    this.shortdesc.setMinimumSize(new Dimension(300, 20));
    this.shortdesc.setPreferredSize(new Dimension(300, 20));
    this.shortdesc.setBorder(BorderFactory.createLineBorder(Color.black));
    JLabel ignoresfzl = new JLabel("ignore spellfreezones?", 0);
    JLabel hiddenl = new JLabel("hidden?", 0);
    container.add(descl);
    container.add(this.desc);
    container.add(shortdescl);
    container.add(this.shortdesc);
    container.add(ignoresfzl);
    container.add(this.ignoresfz);
    container.add(hiddenl);
    container.add(this.hidden);
    this.content.add(container);
  }
  
  void createRows()
  {
    JPanel container = new JPanel();
    container.setLayout(new ColumnLayout());
    container.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "Variablen"));
    JPanel topPanel = new JPanel();
    JLabel ll = new JLabel("Level", 0);
    ll.setPreferredSize(new Dimension(100, 20));
    topPanel.add(ll);
    for (String s : this.vars)
    {
      JLabel label = new JLabel(s, 0);
      label.setPreferredSize(new Dimension(100, 20));
      topPanel.add(label);
    }
    container.add(topPanel);
    for (int i = 0; i < this.w.m.maxlevel; i++)
    {
      JPanel line = new JPanel();
      JLabel l = new JLabel("Level " + (i + 1) + ":", 0);
      l.setPreferredSize(new Dimension(100, 20));
      line.add(l);
      for (int x = 0; x < this.count; x++)
      {
        JTextField jtf = new JTextField();
        this.fields[i][x] = jtf;
        jtf.setPreferredSize(new Dimension(100, 20));
        line.add(jtf);
      }
      container.add(line);
    }
    this.content.add(container);
  }
  
  private String description = "";
  
  void save()
  {
    if (!this.desc.getText().equals("description")) {
      this.description = this.desc.getText();
    }
    File f = new File(this.w.m.spellname + ".cfg");
    Map<String, Object[]> map = new HashMap();
    if (this.w.m.variables.size() > 0) {
      for (String s : this.vars) {
        map.put(s, new Object[this.w.m.maxlevel]);
      }
    }
    try
    {
      if (this.w.m.variables.size() > 0) {
        for (int i = 1; i <= this.w.m.maxlevel; i++) {
          for (int x = 0; x < this.vars.length; x++) {
            try
            {
              ((Object[])map.get(this.vars[x]))[(i - 1)] = Integer.valueOf(Integer.parseInt(this.fields[(i - 1)][x].getText()));
            }
            catch (Exception e)
            {
              ((Object[])map.get(this.vars[x]))[(i - 1)] = this.fields[(i - 1)][x].getText();
            }
          }
        }
      }
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
      bw.write(this.w.m.spellname + ":");
      bw.write("\n  description: '" + this.description + "'");
      bw.write("\n  shortdescription: '" + this.shortdesc.getText() + "'");
      bw.write("\n  ignorespellfreezones: " + this.ignoresfz.isSelected());
      bw.write("\n  hidden: " + this.hidden.isSelected());
      bw.write("\n  variables:");
      if (this.w.m.variables.size() > 0) {
        for (Map.Entry<String, Object[]> vals : map.entrySet())
        {
          bw.write("\n    " + (String)vals.getKey() + ":");
          for (int i = 0; i < ((Object[])vals.getValue()).length; i++) {
            bw.write("\n      level" + (i + 1) + ":" + " " + ((Object[])vals.getValue())[i]);
          }
        }
      }
      bw.close();
      JOptionPane.showMessageDialog(this.w, "Spellconfig successfully saved  to: " + f.getAbsolutePath(), "Information", 1);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  void createMenu()
  {
    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menubar.add(menu);
    JMenuItem saveItem = new JMenuItem("save");
    menu.add(saveItem);
    saveItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ConfigCreator.this.save();
      }
    });
    setJMenuBar(menubar);
  }
}
