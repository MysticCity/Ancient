package de.pylamo.spellmaker.gui.SpellItems;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.parser.SpellParser;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class InsertWindow
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  JTextArea textArea = new JTextArea();
  
  public InsertWindow(final Window w)
  {
    setLayout(null);
    setSize(400, 430);
    this.textArea.setSize(390, 360);
    this.textArea.setLocation(0, 0);
    JButton jb = new JButton();
    jb.setSize(80, 25);
    jb.setText("Parse!");
    jb.setLocation(160, 360);
    add(this.textArea);
    add(jb);
    setResizable(false);
    setDefaultCloseOperation(1);
    jb.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        InsertWindow.this.setTitle("Code parser");
        SpellParser sp = new SpellParser(w, new Point(20, 20));
        BufferedReader br = new BufferedReader(new StringReader(InsertWindow.this.textArea.getText()));
        try
        {
          sp.startCodeParse(br, br.readLine(), 0);
          InsertWindow.this.setVisible(false);
        }
        catch (Exception ignored)
        {
          InsertWindow.this.setTitle("Code parser - Error in statement");
        }
      }
    });
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\InsertWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */