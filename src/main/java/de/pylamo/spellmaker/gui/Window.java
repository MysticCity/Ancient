package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.HelpFrame;
import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.SettingsReader;
import de.pylamo.spellmaker.SettingsReader.UpdateType;
import de.pylamo.spellmaker.examples.ExampleSpellLoader;
import de.pylamo.spellmaker.gui.SpellItems.InsertWindow;
import de.pylamo.spellmaker.parser.SpellParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Window extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static int opensessions = 0;
    public static File choserpath = null;

    private final SearchPanel sp = new SearchPanel(this);
    public final HashMap<String, JComponent> spellItems = new HashMap<String, JComponent>();
    private final JTabbedPane tabpane = new JTabbedPane();
    private boolean searchpaneladded = false;
    public final MainPanel mp;
    public final JScrollPane mainScrollPane;
    private final HelpFrame hf = new HelpFrame();
    public Menu m;
    public InsertWindow iw = new InsertWindow(this);
    public VariablePreview vp = null;

    public Window(Menu m) {
        this.setSize(1000, 800);
        this.m = m;
        this.addWindowListener(wl);
        try {
            this.setIconImage(ImageIO.read(Window.class.getResource("arpg.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        createMenu();
        mp = new MainPanel(this);
        mainScrollPane = new JScrollPane(mp);
        this.setLocation(20, 20);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel leftpane = new JPanel();
        BoxLayout bl = new BoxLayout(leftpane, BoxLayout.Y_AXIS);
        final JTextField searchbar = new JTextField();
        searchbar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!searchpaneladded) {
                    tabpane.addTab("Search", sp);
                    searchpaneladded = true;
                }
                tabpane.setSelectedComponent(sp);
                sp.search(searchbar.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (e.getDocument().getLength() == 0) {
                    if (searchpaneladded) {
                        tabpane.removeTabAt(tabpane.getTabCount() - 1);
                        searchpaneladded = false;
                    }
                } else {
                    sp.search(searchbar.getText().trim());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        leftpane.setLayout(bl);
        searchbar.setMinimumSize(new Dimension(1, 25));
        searchbar.setMaximumSize(new Dimension(1000, 25));
        final SpellPreview spp = new SpellPreview(this);
        final JScrollPane jp = new JScrollPane(spp);
        jp.getVerticalScrollBar().setUnitIncrement(20);
        jp.setMinimumSize(new Dimension(250, 100));
        jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        final JScrollPane jp3 = new JScrollPane(new ParameterPreview(this));
        jp3.getVerticalScrollBar().setUnitIncrement(20);
        jp3.setMinimumSize(new Dimension(250, 100));
        jp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        vp = new VariablePreview(this);
        final JScrollPane jp4 = new JScrollPane(vp);
        jp4.getVerticalScrollBar().setUnitIncrement(20);
        jp4.setMinimumSize(new Dimension(250, 100));
        jp4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        final JScrollPane jp5 = new JScrollPane(new ConditionPreview(this));
        jp5.getVerticalScrollBar().setUnitIncrement(20);
        jp5.setMinimumSize(new Dimension(250, 100));
        jp5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        final JScrollPane jp6 = new JScrollPane(new ArgumentPreview(this));
        jp6.getVerticalScrollBar().setUnitIncrement(20);
        jp6.setMinimumSize(new Dimension(250, 100));
        jp6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tabpane.addTab("Arguments", jp6);
                tabpane.addTab("Parameters", jp3);
                tabpane.addTab("Variables", jp4);
                tabpane.addTab("Conditions", jp5);
                tabpane.insertTab("Commands", null, jp, "Commands", 0);
            }
        });
        leftpane.add(searchbar);
        leftpane.add(tabpane);
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftpane, mainScrollPane);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        sp.setDividerSize(5);
        sp.setDividerLocation(this.getWidth() / 4);
        sp.setContinuousLayout(true);
        sp.setOneTouchExpandable(true);
        this.add(sp);
        this.setTitle("Spellmaker - " + m.spellname);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window.this.setVisible(true);
            }
        });
        Window.opensessions++;
    }

    private static final WindowListener wl = new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent we) {
            Window.opensessions--;
            if (Window.opensessions == 0) {
                System.exit(0);
            }
            if (((Window) we.getWindow()).m != null) {
                ((Window) we.getWindow()).m.dispose();
                ((Window) we.getWindow()).m.w = null;
            }
        }

        @Override
        public void windowOpened(WindowEvent we) {
        }
    };

    private final JFileChooser chooser = new JFileChooser();

    void createMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menubar.add(menu);
        JMenuItem saveItem = new JMenuItem("Save");
        menu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mp.save();
            }
        });
        JMenuItem loadItem = new JMenuItem("Load");
        menu.add(loadItem);
        loadItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (choserpath != null) {
                    chooser.setCurrentDirectory(choserpath);
                }
                chooser.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        File f = chooser.getSelectedFile();
                        choserpath = f.getParentFile();
                        chooser.setCurrentDirectory(choserpath);
                        Menu m = new Menu();
                        Window w = new Window(m);
                        SpellParser sp = new SpellParser(w.mp.startItem, w);
                        sp.parse(f);
                    }
                });
                chooser.showOpenDialog(Window.this);
            }
        });
        JMenuItem showmenuitem = new JMenuItem("Show menu");
        menu.add(showmenuitem);
        menubar.add(ExampleSpellLoader.addMenu());
        showmenuitem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                m.showMenu();
            }
        });
        JMenu updateTypeMenu = new JMenu("Updatetype");
        menu.add(updateTypeMenu);
        ButtonGroup bg = new ButtonGroup();
        JRadioButtonMenuItem releaseradiobutton = new JRadioButtonMenuItem("Release");
        if (SettingsReader.type == UpdateType.Release) {
            releaseradiobutton.setSelected(true);
        }
        releaseradiobutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                SettingsReader.type = UpdateType.Release;
                SettingsReader.writeSettings();
            }
        });
        bg.add(releaseradiobutton);
        updateTypeMenu.add(releaseradiobutton);
        JRadioButtonMenuItem devradiobutton = new JRadioButtonMenuItem("Development");
        if (SettingsReader.type == UpdateType.Development) {
            devradiobutton.setSelected(true);
        }
        devradiobutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                SettingsReader.type = UpdateType.Development;
                SettingsReader.writeSettings();
            }
        });
        bg.add(devradiobutton);
        updateTypeMenu.add(devradiobutton);
        JRadioButtonMenuItem customradiobutton = new JRadioButtonMenuItem("No updates");
        if (SettingsReader.type == UpdateType.Custom) {
            customradiobutton.setSelected(true);
        }
        customradiobutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                SettingsReader.type = UpdateType.Custom;
                SettingsReader.writeSettings();
            }
        });
        bg.add(customradiobutton);
        updateTypeMenu.add(customradiobutton);
        JMenu help = new JMenu("Help");
        menubar.add(help);
        JMenuItem generalhelp = new JMenuItem("General");
        generalhelp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                hf.setContent("general.html");
                hf.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                hf.setVisible(true);
            }
        });
        help.add(generalhelp);
        JMenuItem colorhelp = new JMenuItem("Color Help");
        help.add(colorhelp);
        colorhelp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                hf.setContent("colors.html");
                hf.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                hf.setVisible(true);
            }
        });
        JMenu insert = new JMenu("Insert");
        menubar.add(insert);
        JMenuItem manualinsert = new JMenuItem("Manually");
        insert.add(manualinsert);
        manualinsert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!iw.isVisible()) {
                    iw.setLocation(MouseInfo.getPointerInfo().getLocation());
                    iw.setTitle("Code parser");
                    iw.setVisible(true);
                }
            }
        });
        this.setJMenuBar(menubar);
    }
}
