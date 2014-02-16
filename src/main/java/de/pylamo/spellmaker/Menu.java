package de.pylamo.spellmaker;

import de.pylamo.spellmaker.SettingsReader.UpdateType;
import de.pylamo.spellmaker.examples.ExampleSpellLoader;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.parser.SpellParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Menu extends JFrame {
    private static final long serialVersionUID = 1L;

    // public static Menu instance;
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
    public final HashSet<String> variables = new HashSet<String>();
    private final JButton OkayButton = new JButton("Okay");
    private final JList events = new JList(new String[]{"damageevent", "damagebyentityevent", "attackevent", "changeblockevent", "joinevent", "interactevent", "regenevent", "moveevent",
            "levelupevent", "playerdeathevent", "killentityevent", "projectilehitevent", "classchangeevent", "playerbedenterevent", "playerbedleaveevent", "playerchatevent", "playerdropitemevent",
            "playereggthrowevent", "playerkickevent", "playerpickupitemevent", "playerportalevent", "playerteleportevent", "playertogglesneakevent"});
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

    public Menu() {
        try {
            this.setIconImage(ImageIO.read(Window.class.getResource("arpg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addComponentListener(cl);
        JScrollPane jsp = new JScrollPane(content);
        jsp.getVerticalScrollBar().setUnitIncrement(20);
        OkayButton.setPreferredSize(new Dimension(70, 25));
        this.setContentPane(jsp);
        content.setLayout(new ColumnLayout());
        nameBox.setText("name");
        content.add(nameBox);
        nameBox.setPreferredSize(new Dimension(100, 20));
        JPanel vpanel = new JPanel();
        final JTextField vars = new JTextField("variables");
        vars.setPreferredSize(new Dimension(100, 20));
        vpanel.add(vars);
        JButton vb = new JButton("Add");
        vb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (vars.getText().equals("variables") || vars.getText().equals("") || vars.getText().equals("must be a valid string")) {
                    vars.setText("must be a valid string");
                    return;
                }
                variables.add(vars.getText());
                vars.setText("");
            }
        });
        vpanel.add(vb);
        content.add(vpanel);
        JPanel minpanel = new JPanel();
        minfield.setPreferredSize(new Dimension(100, 20));
        minpanel.add(minfield);
        JButton mb = new JButton("Set");
        mb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    minlevel = Integer.parseInt(minfield.getText());
                    minfield.setEnabled(false);
                } catch (Exception asd) {
                    minfield.setText("must be a number");
                }

            }
        });
        minpanel.add(mb);
        content.add(minpanel);
        JPanel maxpanel = new JPanel();
        maxfield.setPreferredSize(new Dimension(100, 20));
        maxpanel.add(maxfield);
        JButton maxb = new JButton("Set");
        maxb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    maxlevel = Integer.parseInt(maxfield.getText());
                    maxfield.setEnabled(false);
                } catch (Exception asd) {
                    maxfield.setText("must be a number");
                }

            }
        });
        maxpanel.add(maxb);
        content.add(maxpanel);
        JPanel permpanel = new JPanel();
        permfield.setPreferredSize(new Dimension(100, 20));
        permpanel.add(permfield);
        final JButton permb = new JButton("Set");
        permb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (permfield.getText().equals("") || permfield.getText().equals("must be a valid string") || permfield.getText().equals("permission")) {
                    permfield.setText("must be a valid string");
                } else {
                    permfield.setEnabled(false);
                    permission = permfield.getText();
                }
            }
        });
        permpanel.add(permb);
        content.add(permpanel);
        JPanel rpanel = new JPanel();
        rpanel.setLayout(new ColumnLayout());
        rpanel.setPreferredSize(new Dimension(100, 160));
        ButtonGroup bg = new ButtonGroup();
        activer.setSelected(true);
        activer.setActionCommand("active");
        passiver.setActionCommand("passive");
        chat.setActionCommand("chatcommand");
        buffr.setActionCommand("buff");
        repetitiver.setActionCommand("repetitive");
        activer.addActionListener(al);
        passiver.addActionListener(al);
        buffr.addActionListener(al);
        chat.addActionListener(al);
        repetitiver.addActionListener(al);
        bg.add(activer);
        bg.add(passiver);
        bg.add(chat);
        bg.add(buffr);
        bg.add(repetitiver);
        rpanel.add(activer);
        rpanel.add(passiver);
        rpanel.add(buffr);
        rpanel.add(chat);
        rpanel.add(repetitiver);
        content.add(rpanel);
        ActionListener buttonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameBox.getText().equals("") || nameBox.getText().equals("name") || nameBox.getText().equals(
                        "must be a valid name")) {
                    nameBox.setText("must be a valid name");
                    return;
                }
                if (!active || buff) {
                    event = Menu.this.events.getSelectedValue().toString();
                }
                spellname = nameBox.getText();
                OkayButton.setEnabled(false);
                if (w == null) {
                    w = new Window(Menu.this);
                }
                w.setTitle("Spellmaker - " + spellname);
                setVisible(false);
            }
        };
        OkayButton.addActionListener(buttonListener);
        content.add(OkayButton);
        rtf.setPreferredSize(new Dimension(100, 25));
        final JButton rjb = new JButton("Okay");
        repetitivepanel.add(rtf);
        repetitivepanel.add(rjb);
        chatpanel.add(tf);
        rjb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    repetitiveint = Integer.parseInt(rtf.getText());
                    event = "repetitive:" + repetitiveint;
                    spellname = nameBox.getText();
                    rjb.setEnabled(false);
                    if (w == null) {
                        w = new Window(Menu.this);
                    }
                    w.setTitle("Spellmaker - " + spellname);
                    setVisible(false);
                } catch (Exception e1) {
                    rtf.setText("must be a number");
                }
            }
        });
        final JButton jb = new JButton("Okay");
        chatpanel.add(tf);
        chatpanel.add(jb);
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tf.getText().equals("") || tf.getText().equals("chatcommand") || tf.getText().equals("must be a valid name")) {
                    tf.setText("must be a valid name");
                    return;
                }
                if (!active || buff) {
                    chatcommand = tf.getText();
                    event = "chatcommand:" + tf.getText();
                }
                jb.setEnabled(false);
                spellname = nameBox.getText();
                if (w == null) {
                    w = new Window(Menu.this);
                }
                w.setTitle("Spellmaker - " + spellname);
                setVisible(false);
            }
        });
        // eventPanel.setVisible(true);
        eventPanel.setLayout(new ColumnLayout());
        JLabel lb = new JLabel("Priority/Cast order: 1 = first; 10 = last", JLabel.CENTER);
        lb.setVerticalAlignment(SwingConstants.CENTER);
        lb.setPreferredSize(new Dimension(186, 12));
        lb.setToolTipText("Priority: 1 = first; 10 = last");
        eventPanel.add(lb);
        events.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        events.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JSlider slider = new JSlider(1, 10, 5);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setToolTipText("Priority: 1 = first; 10 = last");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        eventPanel.add(slider);
        eventPanel.add(events);
        JButton eventOkayButton = new JButton("Okay");
        eventOkayButton.addActionListener(buttonListener);
        eventPanel.add(eventOkayButton);
        this.setLocation(100, 100);
        SettingsReader.readSettings();
        createMenu();
        this.pack();
    }

    void createMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menubar.add(menu);
        /*
         * JMenuItem saveItem = new JMenuItem("Save"); menu.add(saveItem);
		 * saveItem.addActionListener(new ActionListener() {
		 *
		 * @Override public void actionPerformed(ActionEvent e) {
		 * MainPanel.save(); } });
		 */
        JMenuItem loadItem = new JMenuItem("Load");
        menu.add(loadItem);
        loadItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                if (Window.choserpath != null) {
                    chooser.setCurrentDirectory(Window.choserpath);
                }
                chooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        File f = chooser.getSelectedFile();
                        chooser.setCurrentDirectory(f);
                        Window.choserpath = f.getParentFile();
                        setVisible(false);
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
        updateTypeMenu.add(customradiobutton);
        menubar.add(ExampleSpellLoader.addMenu());
        if (SettingsReader.type == UpdateType.Custom) {
            customradiobutton.setSelected(true);
        } else if (SettingsReader.type == UpdateType.Development) {
            devradiobutton.setSelected(true);
        }
        this.setJMenuBar(menubar);
    }

    public void showMenu() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        if (active) {
            al.actionPerformed(new ActionEvent(activer, 0, "active"));
            activer.setSelected(true);
        } else if (buff) {
            al.actionPerformed(new ActionEvent(buffr, 0, "buff"));
            buffr.setSelected(true);
        } else if (event.startsWith("chatcommand")) {
            al.actionPerformed(new ActionEvent(chat, 0, "chatcommand"));
            chat.setSelected(true);
        } else if (event.startsWith("repetitive")) {
            al.actionPerformed(new ActionEvent(repetitiver, 0, "repetitive"));
            repetitiver.setSelected(true);
        } else {
            al.actionPerformed(new ActionEvent(passiver, 0, "passive"));
            passiver.setSelected(true);
        }
        for (int i = 0; i < events.getModel().getSize(); i++) {
            if (events.getModel().getElementAt(i).toString().equalsIgnoreCase(event)) {
                events.setSelectedIndex(i);
                break;
            }
        }
        this.nameBox.setText(spellname);
        this.minfield.setText("" + minlevel);
        this.maxfield.setText("" + maxlevel);
        this.permfield.setText(permission);
        this.rtf.setText("" + repetitiveint);
        this.tf.setText(chatcommand);
        this.pack();
        this.setVisible(true);
    }

    private final ActionListener al = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("active")) {
                active = true;
                buff = false;
                content.remove(eventPanel);
                content.remove(chatpanel);
                content.remove(repetitivepanel);
                content.add(OkayButton);
                pack();
                setVisible(true);
            } else if (e.getActionCommand().equals("passive")) {
                active = false;
                buff = false;
                content.remove(OkayButton);
                content.remove(eventPanel);
                content.remove(repetitivepanel);
                content.add(eventPanel);
                // setSize(210, 525);
                pack();
                setVisible(true);
            } else if (e.getActionCommand().equals("buff")) {
                active = false;
                buff = true;
                content.remove(OkayButton);
                content.remove(chatpanel);
                content.remove(repetitivepanel);
                content.add(eventPanel);
                // setSize(210, 525);
                pack();
                setVisible(true);
            } else if (e.getActionCommand().equals("chatcommand")) {
                active = false;
                buff = false;
                content.remove(OkayButton);
                content.remove(eventPanel);
                content.remove(repetitivepanel);
                content.add(chatpanel);
                // setSize(210, 525);
                pack();
                setVisible(true);
            } else if (e.getActionCommand().equals("repetitive")) {
                active = false;
                buff = false;
                content.remove(OkayButton);
                content.remove(eventPanel);
                content.remove(chatpanel);
                content.add(repetitivepanel);
                // setSize(210, 525);
                pack();
                setVisible(true);
            }
        }
    };

    private static final ComponentListener cl = new ComponentListener() {
        @Override
        public void componentShown(ComponentEvent e) {
            Window.opensessions++;
        }

        @Override
        public void componentResized(ComponentEvent e) {
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            Window.opensessions--;
            if (Window.opensessions == 0) {
                System.exit(0);
            }
            Menu m = (Menu) e.getComponent();
            if (m.w != null && !m.w.isVisible()) {
                m.w.m = null;
                m.w.dispose();
                m.w = null;
            }
        }
    };
}