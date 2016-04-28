package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.HelpFrame;
import de.pylamo.spellmaker.Main;
import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.SettingsReader;
import de.pylamo.spellmaker.SettingsReader.UpdateType;
import de.pylamo.spellmaker.examples.ExampleSpellLoader;
import de.pylamo.spellmaker.gui.SpellItems.InsertWindow;
import de.pylamo.spellmaker.parser.SpellParser;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.apache.commons.io.IOUtils;

public class Window
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  public static int opensessions = 0;
  public static File choserpath = null;
  private final SearchPanel sp = new SearchPanel(this);
  public final HashMap<String, JComponent> spellItems = new HashMap();
  private final JTabbedPane tabpane = new JTabbedPane();
  private boolean searchpaneladded = false;
  public final MainPanel mp;
  public final JScrollPane mainScrollPane;
  private final HelpFrame hf = new HelpFrame();
  public Menu m;
  public InsertWindow iw = new InsertWindow(this);
  public VariablePreview vp;
  
  public Window(Menu m)
  {
    setSize(1000, 800);
    this.m = m;
    addWindowListener(wl);
    setIconImage(Main.icon);
    createMenu();
    this.mp = new MainPanel(this);
    this.mainScrollPane = new JScrollPane(this.mp);
    setLocation(20, 20);
    setDefaultCloseOperation(2);
    JPanel leftpane = new JPanel();
    BoxLayout bl = new BoxLayout(leftpane, 1);
    final JTextField searchbar = new JTextField();
    searchbar.getDocument().addDocumentListener(new DocumentListener()
    {
      public void insertUpdate(DocumentEvent e)
      {
        if (!Window.this.searchpaneladded)
        {
          Window.this.tabpane.addTab("Search", Window.this.sp);
          Window.this.searchpaneladded = true;
        }
        Window.this.tabpane.setSelectedComponent(Window.this.sp);
        Window.this.sp.search(searchbar.getText().trim());
      }
      
      public void removeUpdate(DocumentEvent e)
      {
        if (e.getDocument().getLength() == 0)
        {
          if (Window.this.searchpaneladded)
          {
            Window.this.tabpane.removeTabAt(Window.this.tabpane.getTabCount() - 1);
            Window.this.searchpaneladded = false;
          }
        }
        else {
          Window.this.sp.search(searchbar.getText().trim());
        }
      }
      
      public void changedUpdate(DocumentEvent e) {}
    });
    leftpane.setLayout(bl);
    searchbar.setMinimumSize(new Dimension(1, 25));
    searchbar.setMaximumSize(new Dimension(1000, 25));
    
    SpellPreview spp = new SpellPreview(this);
    final JScrollPane jPaneCommands = new JScrollPane(spp);
    jPaneCommands.getVerticalScrollBar().setUnitIncrement(20);
    jPaneCommands.setMinimumSize(new Dimension(250, 100));
    jPaneCommands.setHorizontalScrollBarPolicy(31);
    
    final JScrollPane jPaneParameters = new JScrollPane(new ParameterPreview(this));
    jPaneParameters.getVerticalScrollBar().setUnitIncrement(20);
    jPaneParameters.setMinimumSize(new Dimension(250, 100));
    jPaneParameters.setHorizontalScrollBarPolicy(31);
    
    this.vp = new VariablePreview(this);
    final JScrollPane jPaneVariables = new JScrollPane(this.vp);
    jPaneVariables.getVerticalScrollBar().setUnitIncrement(20);
    jPaneVariables.setMinimumSize(new Dimension(250, 100));
    jPaneVariables.setHorizontalScrollBarPolicy(31);
    
    final JScrollPane jPaneConditions = new JScrollPane(new ConditionPreview(this));
    jPaneConditions.getVerticalScrollBar().setUnitIncrement(20);
    jPaneConditions.setMinimumSize(new Dimension(250, 100));
    jPaneConditions.setHorizontalScrollBarPolicy(31);
    
    final JScrollPane jPaneArguments = new JScrollPane(new ArgumentPreview(this));
    jPaneArguments.getVerticalScrollBar().setUnitIncrement(20);
    jPaneArguments.setMinimumSize(new Dimension(250, 100));
    jPaneArguments.setHorizontalScrollBarPolicy(31);
    
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          Window.this.tabpane.insertTab("Commands", new ImageIcon(Toolkit.getDefaultToolkit().createImage(IOUtils.toByteArray(Main.class.getResource("gui/animated-icons/Test-1.gif")))), jPaneCommands, "Commands", 0);
          Window.this.tabpane.insertTab("Arguments", new ImageIcon(Toolkit.getDefaultToolkit().createImage(IOUtils.toByteArray(Main.class.getResource("gui/animated-icons/Ancient-Arg.gif")))), jPaneArguments, "Arguments", 1);
          Window.this.tabpane.insertTab("Parameters", new ImageIcon(Toolkit.getDefaultToolkit().createImage(IOUtils.toByteArray(Main.class.getResource("gui/animated-icons/Ancient-Par.gif")))), jPaneParameters, "Parameters", 2);
          Window.this.tabpane.insertTab("Variables", new ImageIcon(Toolkit.getDefaultToolkit().createImage(IOUtils.toByteArray(Main.class.getResource("gui/animated-icons/Ancient-Var.gif")))), jPaneVariables, "Variables", 3);
          Window.this.tabpane.insertTab("Conditions", new ImageIcon(Toolkit.getDefaultToolkit().createImage(IOUtils.toByteArray(Main.class.getResource("gui/animated-icons/Ancient-Com.gif")))), jPaneConditions, "Conditions", 4);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        Window.this.tabpane.insertTab("Commands", null, jPaneCommands, "Commands", 0);
        Window.this.tabpane.insertTab("Arguments", null, jPaneArguments, "Arguments", 1);
        Window.this.tabpane.insertTab("Parameters", null, jPaneParameters, "Parameters", 2);
        Window.this.tabpane.insertTab("Variables", null, jPaneVariables, "Variables", 3);
        Window.this.tabpane.insertTab("Conditions", null, jPaneConditions, "Conditions", 4);
        
        Window.this.tabpane.setSelectedIndex(0);
      }
    });
    leftpane.add(searchbar);
    leftpane.add(this.tabpane);
    JSplitPane sp = new JSplitPane(1, leftpane, this.mainScrollPane);
    this.mainScrollPane.getVerticalScrollBar().setUnitIncrement(20);
    sp.setDividerSize(5);
    sp.setDividerLocation(getWidth() / 4);
    sp.setContinuousLayout(true);
    sp.setOneTouchExpandable(true);
    add(sp);
    setTitle("Spellmaker - " + m.spellname);
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        Window.this.setVisible(true);
      }
    });
    opensessions += 1;
    
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        Window.this.tabpane.repaint();
      }
    }, 0L, 50L);
  }
  
  private static final WindowListener wl = new WindowAdapter()
  {
    public void windowClosed(WindowEvent we)
    {
      Window.opensessions -= 1;
      if (Window.opensessions == 0) {
        System.exit(0);
      }
      if (((Window)we.getWindow()).m != null)
      {
        ((Window)we.getWindow()).m.dispose();
        ((Window)we.getWindow()).m.w = null;
      }
    }
    
    public void windowOpened(WindowEvent we) {}
  };
  private final JFileChooser chooser = new JFileChooser();
  
  void createMenu()
  {
    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menubar.add(menu);
    JMenuItem saveItem = new JMenuItem("Save");
    menu.add(saveItem);
    saveItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        Window.this.mp.save();
      }
    });
    JMenuItem loadItem = new JMenuItem("Load");
    menu.add(loadItem);
    loadItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (Window.choserpath != null) {
          Window.this.chooser.setCurrentDirectory(Window.choserpath);
        }
        Window.this.chooser.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent arg0)
          {
            File f = Window.this.chooser.getSelectedFile();
            Window.choserpath = f.getParentFile();
            Window.this.chooser.setCurrentDirectory(Window.choserpath);
            Menu m = new Menu();
            Window w = new Window(m);
            SpellParser sp = new SpellParser(w.mp.startItem, w);
            sp.parse(f);
          }
        });
        Window.this.chooser.showOpenDialog(Window.this);
      }
    });
    JMenuItem showmenuitem = new JMenuItem("Show menu");
    menu.add(showmenuitem);
    menubar.add(ExampleSpellLoader.addMenu());
    showmenuitem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        Window.this.m.showMenu();
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
    bg.add(customradiobutton);
    updateTypeMenu.add(customradiobutton);
    JMenu help = new JMenu("Help");
    menubar.add(help);
    JMenuItem generalhelp = new JMenuItem("General");
    generalhelp.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        Window.this.hf.setContent("general.html");
        Window.this.hf.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
        Window.this.hf.setVisible(true);
      }
    });
    help.add(generalhelp);
    JMenuItem colorhelp = new JMenuItem("Color Help");
    help.add(colorhelp);
    colorhelp.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        Window.this.hf.setContent("colors.html");
        Window.this.hf.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
        Window.this.hf.setVisible(true);
      }
    });
    JMenu insert = new JMenu("Insert");
    menubar.add(insert);
    JMenuItem manualinsert = new JMenuItem("Manually");
    insert.add(manualinsert);
    manualinsert.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (!Window.this.iw.isVisible())
        {
          Window.this.iw.setLocation(MouseInfo.getPointerInfo().getLocation());
          Window.this.iw.setTitle("Code parser");
          Window.this.iw.setVisible(true);
        }
      }
    });
    setJMenuBar(menubar);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\Window.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */