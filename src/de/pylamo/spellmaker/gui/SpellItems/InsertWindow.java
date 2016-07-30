package de.pylamo.spellmaker.gui.SpellItems;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.parser.SpellParser;

public class InsertWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	JTextArea textArea = new JTextArea();

    public InsertWindow(final Window w) {
        this.setLayout(null);
        this.setSize(400, 430);
        textArea.setSize(390, 360);
        textArea.setLocation(0, 0);
        JButton jb = new JButton();
        jb.setSize(80, 25);
        jb.setText("Parse!");
        jb.setLocation(160, 360);
        this.add(textArea);
        this.add(jb);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Code parser");
                SpellParser sp = new SpellParser(w, new Point(20, 20));
                BufferedReader br = new BufferedReader(new StringReader(textArea.getText()));
                try {
                    sp.startCodeParse(br, br.readLine(), 0);
                    setVisible(false);
                } catch (Exception ignored) {
                    setTitle("Code parser - Error in statement");
                }
                ;
            }
        });
    }
}