package de.pylamo.spellmaker.examples;

import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.parser.SpellParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ExampleSpellLoader
{
  public static JMenu addMenu()
  {
    JMenu menu = new JMenu("Examples");
    CodeSource src = ExampleSpellLoader.class.getProtectionDomain().getCodeSource();
    
    JMenu submenu = null;
    if (src != null) {
      try
      {
        URL jar = src.getLocation();
        ZipInputStream zip = new ZipInputStream(jar.openStream());
        ZipEntry entry = zip.getNextEntry();
        while ((entry != null) && ((!entry.isDirectory()) || (!entry.getName().endsWith("examples/")))) {
          entry = zip.getNextEntry();
        }
        entry = zip.getNextEntry();
        while ((entry != null) && (entry.getName().contains("examples")))
        {
          if (entry.isDirectory())
          {
            String name = entry.getName().substring(0, entry.getName().length() - 1);
            name = name.substring(name.lastIndexOf('/') + 1, name.length());
            submenu = new JMenu(name);
            menu.add(submenu);
          }
          else if (submenu != null)
          {
            String name = entry.getName().substring(0, entry.getName().length() - 1);
            name = name.substring(name.lastIndexOf('/') + 1, name.lastIndexOf('.'));
            JMenuItem jmi = new JMenuItem(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(zip));
            String ges = "";
            String s = br.readLine();
            while (s != null)
            {
              ges = ges + s + "\n";
              s = br.readLine();
            }
            String lol = ges;
            jmi.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent arg0)
              {
                Menu m = new Menu();
                Window w = new Window(m);
                SpellParser sp = new SpellParser(w.mp.startItem, w);
                sp.parse(this.val$lol);
              }
            });
            submenu.add(jmi);
          }
          entry = zip.getNextEntry();
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return menu;
  }
}
