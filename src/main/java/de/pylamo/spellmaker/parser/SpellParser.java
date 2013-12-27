package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Commands.Command;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ComplexItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.StartItem;
import de.pylamo.spellmaker.gui.SpellPreview;
import de.pylamo.spellmaker.gui.Window;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class SpellParser {
    private StartItem startitem;
    private ISpellItem lastItem = null;
    private final Window w;
    private boolean newspell;
    Point p;

    public SpellParser(StartItem si, Window w) {
        this.startitem = si;
        this.newspell = true;
        this.w = w;
    }

    public SpellParser(Window w, Point p) {
        this.w = w;
        this.newspell = false;
        this.p = p;
    }

    public void parse(File f) {
        try {
            int lineNumber = 1;
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line = bf.readLine();
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            w.m.spellname = line;
            line = bf.readLine();
            lineNumber++;
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("passive")) {
                w.m.active = false;
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
                line = line.trim();
                w.m.event = line;
                // attachToEvent(line);
            } else if (line.toLowerCase().startsWith("active")) {
                w.m.active = true;
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
            } else if (line.toLowerCase().startsWith("buff")) {
                w.m.active = false;
                w.m.buff = true;
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
                line = line.trim();
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    line = args[0];
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
                w.m.event = line.trim().toLowerCase();
            } else {
                bf.close();
                return;
            }
            line = bf.readLine();
            lineNumber++;

            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.startsWith("variables")) {
                try {
                    String vars = line.split(":")[1].trim();
                    for (String s : vars.split(",")) {
                        w.m.variables.add(s.trim().toLowerCase());
                    }
                } catch (Exception e) {
                    bf.close();
                    return;
                }
                line = bf.readLine();
            }
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("minlevel:")) {
                String[] minlevelS = line.split(":");
                if (minlevelS.length == 2) {
                    try {
                        w.m.minlevel = Integer.parseInt(minlevelS[1].trim());
                    } catch (Exception ignored) {
                    }
                } else {
                }
                line = bf.readLine();
                lineNumber++;
            }
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("permission:")) {
                String[] perms = line.split(":");
                if (perms.length == 2) {
                    w.m.permission = perms[1].trim();
                } else {
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
            }
            startCodeParse(bf, line, lineNumber);
            bf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ISpellItem si = w.mp.startItem;
        if (si != null) {
            revalidateAllNachfolger(si);
        }
    }

    void revalidateAllNachfolger(ISpellItem si) {
        while (si != null) {
            if (si instanceof ComplexItem) {
                revalidateAllNachfolger(((ComplexItem) si).firstBlockItem);
            }
            si.revalidate();
            si = si.getNachfolger();
        }
    }

    public void parse(final String file) {
        try {
            int lineNumber = 1;
            BufferedReader bf = new BufferedReader(new StringReader(file));
            String line = bf.readLine();
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            w.m.spellname = line;
            line = bf.readLine();
            lineNumber++;
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("passive")) {
                w.m.active = false;
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
                line = line.trim();
                w.m.event = line;
                // attachToEvent(line);
            } else if (line.toLowerCase().startsWith("active")) {
                w.m.active = true;
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
            } else if (line.toLowerCase().startsWith("buff")) {
                w.m.active = false;
                w.m.buff = true;
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
                line = line.trim();
                if (line.contains(":")) {
                    String[] args = line.split(Pattern.quote(":"));
                    line = args[0];
                    try {
                        w.m.priority = Integer.parseInt(args[1]);
                    } catch (Exception ignored) {

                    }
                }
                w.m.event = line.trim().toLowerCase();
            } else {
                bf.close();
                return;
            }
            line = bf.readLine();
            lineNumber++;

            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.startsWith("variables")) {
                try {
                    String vars = line.split(":")[1].trim();
                    for (String s : vars.split(",")) {
                        w.m.variables.add(s.trim().toLowerCase());
                    }
                } catch (Exception e) {
                    bf.close();
                    return;
                }
                line = bf.readLine();
            }
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("minlevel:")) {
                String[] minlevelS = line.split(":");
                if (minlevelS.length == 2) {
                    try {
                        w.m.minlevel = Integer.parseInt(minlevelS[1].trim());
                    } catch (Exception ignored) {
                    }
                } else {
                }
                line = bf.readLine();
                lineNumber++;
            }
            while (line.startsWith("//") && line != null) {
                line = bf.readLine();
                lineNumber++;
            }
            if (line.toLowerCase().startsWith("permission:")) {
                String[] perms = line.split(":");
                if (perms.length == 2) {
                    w.m.permission = perms[1].trim();
                } else {
                }
                line = bf.readLine();
                lineNumber++;
                while (line.startsWith("//") && line != null) {
                    line = bf.readLine();
                    lineNumber++;
                }
            }
            startCodeParse(bf, line, lineNumber);
            bf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void startCodeParse(BufferedReader bf, String curline, int linenumber) throws IOException {
        while (curline != null && !curline.equalsIgnoreCase("")) {
            linenumber++;
            if (curline.startsWith("//")) {
                try {
                    curline = bf.readLine();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            }

            if (curline.toLowerCase().trim().startsWith("repeat")) {
                RepeatStatement rs = new RepeatStatement(this, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("if")) {
                IfStatement rs = new IfStatement(this, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("elseif")) {
                ElseIfStatement rs = new ElseIfStatement(this, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("else")) {
                ElseStatement rs = new ElseStatement(this, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("while")) {
                WhileStatement rs = new WhileStatement(this, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("foreach")) {
                ForeachStatement rs = new ForeachStatement(w, this);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("try")) {
                TryStatement rs = new TryStatement(this, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                if (newspell) {
                    addSpellItem(spi);
                } else {
                    addSpellItem(spi, p);
                }
            } else if (curline.toLowerCase().trim().startsWith("var")) {
                VariableParser vp = new VariableParser(w);
                if (newspell) {
                    addSpellItem(vp.getVarPanel(curline));
                } else {
                    addSpellItem(vp.getVarPanel(curline), p);
                }
            } else {
                if (newspell) {
                    addSpellItem(parseSpellItem(curline));
                } else {
                    addSpellItem(parseSpellItem(curline), p);
                }
            }
            try {
                curline = bf.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (startitem != null) {
            startitem.setNachfolgerLocation();
        }
        w.mp.setScrollBars();
        w.setTitle("Spellmaker - " + w.m.spellname);
    }

    public SpellItem parseSpellItem(String line) {
        line = line.trim();
        LinkedList<String> args = new LinkedList<String>();
        int curpos = 0;
        int openbrackets = 0;
        boolean openqmark = false;
        int lastindex = 0;
        if (line.startsWith("(") && line.endsWith(")")) {
            line = line.substring(1, line.length() - 1);
        }
        while (curpos < line.length()) {
            if (line.charAt(curpos) == ',' && openbrackets == 0 && !openqmark) {
                args.add(line.substring(lastindex, curpos).trim());
                lastindex = curpos + 1;
            }
            if (line.charAt(curpos) == '(' && !openqmark) {
                openbrackets++;
            }
            if (line.charAt(curpos) == ')' && !openqmark) {
                openbrackets--;
            }
            if (line.charAt(curpos) == '"') {
                openqmark = !openqmark;
            }
            curpos++;
        }
        args.add(line.substring(lastindex, curpos));
        for (Command c : SpellPreview.commands) {
            if (args.get(0).equalsIgnoreCase(c.name.toLowerCase())) {
                SpellItem si = new SpellItem(c.name, c.parameters, c.paramdesc, w);
                for (int i = 0; i < si.parameters.length; i++) {
                    if (args.size() > i + 1) {
                        ArgumentParser ap = new ArgumentParser(w);
                        ap.parse(args.get(i + 1));
                        IParameter ip = ap.getArgumentPanel(w);
                        if (ip != null) {
                            si.parameters[i].add(ip);
                            si.parameters[i].content = ip;
                        }
                    }
                }
                return si;
            }
        }
        return null;
    }

    void addSpellItem(ISpellItem si) {
        if (si == null) {
            return;
        }
        w.mp.add(si);
        w.mp.registeredItems.add(si);
        si.setSize(si.getPreferredSize());
        if (lastItem == null) {
            si.setVorgaenger(this.startitem);
            si.setLocation(si.getVorgaenger().getX(), si.getVorgaenger().getY() + si.getVorgaenger().getHeight());
            this.startitem.setNachfolger(si);
            this.startitem.setNachfolgerLocation();
            lastItem = si;
        } else {
            si.setVorgaenger(this.lastItem);
            si.setLocation(si.getVorgaenger().getX(), si.getVorgaenger().getY() + si.getVorgaenger().getHeight());
            lastItem.setNachfolger(si);
            lastItem = si;
        }
    }

    void addSpellItem(ISpellItem si, Point p) {
        if (si == null) {
            return;
        }
        w.mp.add(si);
        w.mp.registeredItems.add(si);
        si.setSize(si.getPreferredSize());
        if (lastItem == null) {
            si.setLocation(p);
            lastItem = si;
        } else {
            si.setVorgaenger(this.lastItem);
            si.setLocation(si.getVorgaenger().getX(), si.getVorgaenger().getY() + si.getVorgaenger().getHeight());
            lastItem.setNachfolger(si);
            lastItem = si;
        }
    }
}