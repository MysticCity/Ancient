package de.pylamo.spellmaker;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

public class HelpFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JEditorPane pane = new JEditorPane();

    public HelpFrame() {
        JScrollPane scroll = new JScrollPane(pane);
        pane.setDocument(new HTMLDocument());
        this.setContentPane(scroll);
        this.setSize(450, 500);
        pane.setEditable(false);
    }

    public void setContent(String filename) {
        try {
            pane.setPage(HelpFrame.class.getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}