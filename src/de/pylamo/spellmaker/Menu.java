package de.pylamo.spellmaker;

import com.apple.eawt.Application;
import de.pylamo.spellmaker.examples.ExampleSpellLoader;
import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.parser.SpellParser;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.HashSet;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class Menu
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  public boolean active = true;
  public boolean buff = false;
  private int repetitiveint = 0;
  public String spellname;
  public String event = "";
  public int minlevel = 0;
  public int maxlevel = 10;
  public String permission = "";
  public int priority = 5;
  private String chatcommand = "";
  public final HashSet<String> variables = new HashSet();
  private final JButton OkayButton = new JButton("Okay");
  private final JList<String> events = new JList(new String[] { "damageevent", "damagebyentityevent", "attackevent", "changeblockevent", "joinevent", "interactevent", "regenevent", "moveevent", "levelupevent", "playerdeathevent", "killentityevent", "projectilehitevent", "classchangeevent", "playerbedenterevent", "playerbedleaveevent", "playerchatevent", "playerdropitemevent", "playereggthrowevent", "playerkickevent", "playerpickupitemevent", "playerportalevent", "playerteleportevent", "playertogglesneakevent" });
  private final JPanel chatpanel = new JPanel();
  private final JPanel repetitivepanel = new JPanel();
  private final JPanel eventPanel = new JPanel();
  private final JTextField nameBox = new JTextField();
  private final JTextField minfield = new JTextField("minlevel");
  private final JTextField maxfield = new JTextField("maxlevel");
  private final JTextField permfield = new JTextField("permission");
  private final JTextField rtf = new JTextField("interval");
  private final JTextField tf = new JTextField("chatcommand");
  private final JRadioButton activer = new JRadioButton("active");
  private final JRadioButton passiver = new JRadioButton("passive");
  private final JRadioButton chat = new JRadioButton("chatcommand");
  private final JRadioButton buffr = new JRadioButton("buff");
  private final JRadioButton repetitiver = new JRadioButton("repetitive");
  private final JPanel content = new JPanel();
  public Window w;
  
  public Menu()
  {
    if (System.getProperty("os.name").startsWith("Mac")) {
      Application.getApplication().setDockIconImage(Main.icon);
    } else {
      setIconImage(Main.icon);
    }
    addComponentListener(cl);
    JScrollPane jsp = new JScrollPane(this.content);
    jsp.getVerticalScrollBar().setUnitIncrement(20);
    this.OkayButton.setPreferredSize(new Dimension(70, 25));
    setContentPane(jsp);
    this.content.setLayout(new ColumnLayout());
    this.nameBox.setText("name");
    this.content.add(this.nameBox);
    this.nameBox.setPreferredSize(new Dimension(100, 20));
    JPanel vpanel = new JPanel();
    final JTextField vars = new JTextField("variables");
    vars.setPreferredSize(new Dimension(100, 20));
    vpanel.add(vars);
    JButton vb = new JButton("Add");
    vb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if ((vars.getText().equals("variables")) || (vars.getText().equals("")) || (vars.getText().equals("must be a valid string")))
        {
          vars.setText("must be a valid string");
          return;
        }
        Menu.this.variables.add(vars.getText());
        vars.setText("");
      }
    });
    vpanel.add(vb);
    this.content.add(vpanel);
    JPanel minpanel = new JPanel();
    this.minfield.setPreferredSize(new Dimension(100, 20));
    minpanel.add(this.minfield);
    JButton mb = new JButton("Set");
    mb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          Menu.this.minlevel = Integer.parseInt(Menu.this.minfield.getText());
          Menu.this.minfield.setEnabled(false);
        }
        catch (Exception asd)
        {
          Menu.this.minfield.setText("must be a number");
        }
      }
    });
    minpanel.add(mb);
    this.content.add(minpanel);
    JPanel maxpanel = new JPanel();
    this.maxfield.setPreferredSize(new Dimension(100, 20));
    maxpanel.add(this.maxfield);
    JButton maxb = new JButton("Set");
    maxb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          Menu.this.maxlevel = Integer.parseInt(Menu.this.maxfield.getText());
          Menu.this.maxfield.setEnabled(false);
        }
        catch (Exception asd)
        {
          Menu.this.maxfield.setText("must be a number");
        }
      }
    });
    maxpanel.add(maxb);
    this.content.add(maxpanel);
    JPanel permpanel = new JPanel();
    this.permfield.setPreferredSize(new Dimension(100, 20));
    permpanel.add(this.permfield);
    JButton permb = new JButton("Set");
    permb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if ((Menu.this.permfield.getText().equals("")) || (Menu.this.permfield.getText().equals("must be a valid string")) || (Menu.this.permfield.getText().equals("permission")))
        {
          Menu.this.permfield.setText("must be a valid string");
        }
        else
        {
          Menu.this.permfield.setEnabled(false);
          Menu.this.permission = Menu.this.permfield.getText();
        }
      }
    });
    permpanel.add(permb);
    this.content.add(permpanel);
    JPanel rpanel = new JPanel();
    rpanel.setLayout(new ColumnLayout());
    rpanel.setPreferredSize(new Dimension(100, 160));
    ButtonGroup bg = new ButtonGroup();
    this.activer.setSelected(true);
    this.activer.setActionCommand("active");
    this.passiver.setActionCommand("passive");
    this.chat.setActionCommand("chatcommand");
    this.buffr.setActionCommand("buff");
    this.repetitiver.setActionCommand("repetitive");
    this.activer.addActionListener(this.al);
    this.passiver.addActionListener(this.al);
    this.buffr.addActionListener(this.al);
    this.chat.addActionListener(this.al);
    this.repetitiver.addActionListener(this.al);
    bg.add(this.activer);
    bg.add(this.passiver);
    bg.add(this.chat);
    bg.add(this.buffr);
    bg.add(this.repetitiver);
    rpanel.add(this.activer);
    rpanel.add(this.passiver);
    rpanel.add(this.buffr);
    rpanel.add(this.chat);
    rpanel.add(this.repetitiver);
    this.content.add(rpanel);
    ActionListener buttonListener = new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if ((Menu.this.nameBox.getText().equals("")) || (Menu.this.nameBox.getText().equals("name")) || (Menu.this.nameBox.getText().equals("must be a valid name")))
        {
          Menu.this.nameBox.setText("must be a valid name");
          return;
        }
        if ((!Menu.this.active) || (Menu.this.buff)) {
          Menu.this.event = ((String)Menu.this.events.getSelectedValue()).toString();
        }
        Menu.this.spellname = Menu.this.nameBox.getText();
        Menu.this.OkayButton.setEnabled(false);
        if (Menu.this.w == null) {
          Menu.this.w = new Window(Menu.this);
        }
        Menu.this.w.setTitle("Spellmaker - " + Menu.this.spellname);
        Menu.this.setVisible(false);
      }
    };
    this.OkayButton.addActionListener(buttonListener);
    this.content.add(this.OkayButton);
    this.rtf.setPreferredSize(new Dimension(100, 25));
    final JButton rjb = new JButton("Okay");
    this.repetitivepanel.add(this.rtf);
    this.repetitivepanel.add(rjb);
    this.chatpanel.add(this.tf);
    rjb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          Menu.this.repetitiveint = Integer.parseInt(Menu.this.rtf.getText());
          Menu.this.event = ("repetitive:" + Menu.this.repetitiveint);
          Menu.this.spellname = Menu.this.nameBox.getText();
          rjb.setEnabled(false);
          if (Menu.this.w == null) {
            Menu.this.w = new Window(Menu.this);
          }
          Menu.this.w.setTitle("Spellmaker - " + Menu.this.spellname);
          Menu.this.setVisible(false);
        }
        catch (Exception e1)
        {
          Menu.this.rtf.setText("must be a number");
        }
      }
    });
    final JButton jb = new JButton("Okay");
    this.chatpanel.add(this.tf);
    this.chatpanel.add(jb);
    jb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if ((Menu.this.tf.getText().equals("")) || (Menu.this.tf.getText().equals("chatcommand")) || (Menu.this.tf.getText().equals("must be a valid name")))
        {
          Menu.this.tf.setText("must be a valid name");
          return;
        }
        if ((!Menu.this.active) || (Menu.this.buff))
        {
          Menu.this.chatcommand = Menu.this.tf.getText();
          Menu.this.event = ("chatcommand:" + Menu.this.tf.getText());
        }
        jb.setEnabled(false);
        Menu.this.spellname = Menu.this.nameBox.getText();
        if (Menu.this.w == null) {
          Menu.this.w = new Window(Menu.this);
        }
        Menu.this.w.setTitle("Spellmaker - " + Menu.this.spellname);
        Menu.this.setVisible(false);
      }
    });
    this.eventPanel.setLayout(new ColumnLayout());
    JLabel lb = new JLabel("Priority/Cast order: 1 = first; 10 = last", 0);
    lb.setVerticalAlignment(0);
    lb.setPreferredSize(new Dimension(186, 12));
    lb.setToolTipText("Priority: 1 = first; 10 = last");
    this.eventPanel.add(lb);
    this.events.setSelectionMode(0);
    this.events.setLayoutOrientation(2);
    JSlider slider = new JSlider(1, 10, 5);
    slider.setMinorTickSpacing(1);
    slider.setMajorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setToolTipText("Priority: 1 = first; 10 = last");
    setDefaultCloseOperation(1);
    this.eventPanel.add(slider);
    this.eventPanel.add(this.events);
    JButton eventOkayButton = new JButton("Okay");
    eventOkayButton.addActionListener(buttonListener);
    this.eventPanel.add(eventOkayButton);
    setLocation(100, 100);
    SettingsReader.readSettings();
    createMenu();
    pack();
  }
  
  void createMenu()
  {
    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menubar.add(menu);
    
    JMenuItem loadItem = new JMenuItem("Load");
    menu.add(loadItem);
    loadItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        final JFileChooser chooser = new JFileChooser();
        if (Window.choserpath != null) {
          chooser.setCurrentDirectory(Window.choserpath);
        }
        chooser.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent arg0)
          {
            File f = chooser.getSelectedFile();
            chooser.setCurrentDirectory(f);
            Window.choserpath = f.getParentFile();
            Menu.this.setVisible(false);
            Window w = new Window(Menu.this);
            SpellParser sp = new SpellParser(w.mp.startItem, w);
            sp.parse(f);
          }
        });
        chooser.showOpenDialog(Menu.this);
      }
    });
    JMenu updateTypeMenu = new JMenu("Updatetype");
    menu.add(updateTypeMenu);
    ButtonGroup bg = new ButtonGroup();
    JRadioButtonMenuItem releaseradiobutton = new JRadioButtonMenuItem("Release");
    if (SettingsReader.type == SettingsReader.UpdateType.Release) {
      releaseradiobutton.setSelected(true);
    }
    releaseradiobutton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        SettingsReader.type = SettingsReader.UpdateType.Release;
        SettingsReader.writeSettings();
      }
    });
    bg.add(releaseradiobutton);
    updateTypeMenu.add(releaseradiobutton);
    JRadioButtonMenuItem devradiobutton = new JRadioButtonMenuItem("Development");
    if (SettingsReader.type == SettingsReader.UpdateType.Development) {
      devradiobutton.setSelected(true);
    }
    devradiobutton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        SettingsReader.type = SettingsReader.UpdateType.Development;
        SettingsReader.writeSettings();
      }
    });
    bg.add(devradiobutton);
    updateTypeMenu.add(devradiobutton);
    JRadioButtonMenuItem customradiobutton = new JRadioButtonMenuItem("No updates");
    if (SettingsReader.type == SettingsReader.UpdateType.Custom) {
      customradiobutton.setSelected(true);
    }
    customradiobutton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        SettingsReader.type = SettingsReader.UpdateType.Custom;
        SettingsReader.writeSettings();
      }
    });
    updateTypeMenu.add(customradiobutton);
    menubar.add(ExampleSpellLoader.addMenu());
    if (SettingsReader.type == SettingsReader.UpdateType.Custom) {
      customradiobutton.setSelected(true);
    } else if (SettingsReader.type == SettingsReader.UpdateType.Development) {
      devradiobutton.setSelected(true);
    }
    setJMenuBar(menubar);
  }
  
  public void showMenu()
  {
    setDefaultCloseOperation(1);
    if (this.active)
    {
      this.al.actionPerformed(new ActionEvent(this.activer, 0, "active"));
      this.activer.setSelected(true);
    }
    else if (this.buff)
    {
      this.al.actionPerformed(new ActionEvent(this.buffr, 0, "buff"));
      this.buffr.setSelected(true);
    }
    else if (this.event.startsWith("chatcommand"))
    {
      this.al.actionPerformed(new ActionEvent(this.chat, 0, "chatcommand"));
      this.chat.setSelected(true);
    }
    else if (this.event.startsWith("repetitive"))
    {
      this.al.actionPerformed(new ActionEvent(this.repetitiver, 0, "repetitive"));
      this.repetitiver.setSelected(true);
    }
    else
    {
      this.al.actionPerformed(new ActionEvent(this.passiver, 0, "passive"));
      this.passiver.setSelected(true);
    }
    for (int i = 0; i < this.events.getModel().getSize(); i++) {
      if (((String)this.events.getModel().getElementAt(i)).toString().equalsIgnoreCase(this.event))
      {
        this.events.setSelectedIndex(i);
        break;
      }
    }
    this.nameBox.setText(this.spellname);
    this.minfield.setText("" + this.minlevel);
    this.maxfield.setText("" + this.maxlevel);
    this.permfield.setText(this.permission);
    this.rtf.setText("" + this.repetitiveint);
    this.tf.setText(this.chatcommand);
    pack();
    setVisible(true);
  }
  
  private final ActionListener al = new ActionListener()
  {
    public void actionPerformed(ActionEvent e)
    {
      if (e.getActionCommand().equals("active"))
      {
        Menu.this.active = true;
        Menu.this.buff = false;
        Menu.this.content.remove(Menu.this.eventPanel);
        Menu.this.content.remove(Menu.this.chatpanel);
        Menu.this.content.remove(Menu.this.repetitivepanel);
        Menu.this.content.add(Menu.this.OkayButton);
        Menu.this.pack();
        Menu.this.setVisible(true);
      }
      else if (e.getActionCommand().equals("passive"))
      {
        Menu.this.active = false;
        Menu.this.buff = false;
        Menu.this.content.remove(Menu.this.OkayButton);
        Menu.this.content.remove(Menu.this.eventPanel);
        Menu.this.content.remove(Menu.this.repetitivepanel);
        Menu.this.content.add(Menu.this.eventPanel);
        
        Menu.this.pack();
        Menu.this.setVisible(true);
      }
      else if (e.getActionCommand().equals("buff"))
      {
        Menu.this.active = false;
        Menu.this.buff = true;
        Menu.this.content.remove(Menu.this.OkayButton);
        Menu.this.content.remove(Menu.this.chatpanel);
        Menu.this.content.remove(Menu.this.repetitivepanel);
        Menu.this.content.add(Menu.this.eventPanel);
        
        Menu.this.pack();
        Menu.this.setVisible(true);
      }
      else if (e.getActionCommand().equals("chatcommand"))
      {
        Menu.this.active = false;
        Menu.this.buff = false;
        Menu.this.content.remove(Menu.this.OkayButton);
        Menu.this.content.remove(Menu.this.eventPanel);
        Menu.this.content.remove(Menu.this.repetitivepanel);
        Menu.this.content.add(Menu.this.chatpanel);
        
        Menu.this.pack();
        Menu.this.setVisible(true);
      }
      else if (e.getActionCommand().equals("repetitive"))
      {
        Menu.this.active = false;
        Menu.this.buff = false;
        Menu.this.content.remove(Menu.this.OkayButton);
        Menu.this.content.remove(Menu.this.eventPanel);
        Menu.this.content.remove(Menu.this.chatpanel);
        Menu.this.content.add(Menu.this.repetitivepanel);
        
        Menu.this.pack();
        Menu.this.setVisible(true);
      }
    }
  };
  private static final ComponentListener cl = new ComponentListener()
  {
    public void componentShown(ComponentEvent e)
    {
      Window.opensessions += 1;
    }
    
    public void componentResized(ComponentEvent e) {}
    
    public void componentMoved(ComponentEvent e) {}
    
    public void componentHidden(ComponentEvent e)
    {
      Window.opensessions -= 1;
      if (Window.opensessions == 0) {
        System.exit(0);
      }
      Menu m = (Menu)e.getComponent();
      if ((m.w != null) && (!m.w.isVisible()))
      {
        m.w.m = null;
        m.w.dispose();
        m.w = null;
      }
    }
  };
}
