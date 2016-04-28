package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.SpellItems.Commands.Command;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ComplexItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.StartItem;
import de.pylamo.spellmaker.gui.SpellPreview;
import de.pylamo.spellmaker.gui.Window;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class SpellParser
{
  private StartItem startitem;
  private ISpellItem lastItem = null;
  private final Window w;
  private boolean newspell;
  Point p;
  
  public SpellParser(StartItem si, Window w)
  {
    this.startitem = si;
    this.newspell = true;
    this.w = w;
  }
  
  public SpellParser(Window w, Point p)
  {
    this.w = w;
    this.newspell = false;
    this.p = p;
  }
  
  public void parse(File f)
  {
    try
    {
      int lineNumber = 1;
      BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
      String line = bf.readLine();
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      this.w.m.spellname = line;
      line = bf.readLine();
      lineNumber++;
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("passive"))
      {
        this.w.m.active = false;
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
        line = line.trim();
        this.w.m.event = line;
      }
      else if (line.toLowerCase().startsWith("active"))
      {
        this.w.m.active = true;
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
      }
      else if (line.toLowerCase().startsWith("buff"))
      {
        this.w.m.active = false;
        this.w.m.buff = true;
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
        line = line.trim();
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          line = args[0];
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
        this.w.m.event = line.trim().toLowerCase();
      }
      else
      {
        bf.close();
        return;
      }
      line = bf.readLine();
      lineNumber++;
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.startsWith("variables"))
      {
        try
        {
          String vars = line.split(":")[1].trim();
          for (String s : vars.split(",")) {
            this.w.m.variables.add(s.trim().toLowerCase());
          }
        }
        catch (Exception e)
        {
          bf.close();
          return;
        }
        line = bf.readLine();
      }
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("minlevel:"))
      {
        String[] minlevelS = line.split(":");
        if (minlevelS.length == 2) {
          try
          {
            this.w.m.minlevel = Integer.parseInt(minlevelS[1].trim());
          }
          catch (Exception ignored) {}
        }
        line = bf.readLine();
        lineNumber++;
      }
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("permission:"))
      {
        String[] perms = line.split(":");
        if (perms.length == 2) {
          this.w.m.permission = perms[1].trim();
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
      }
      startCodeParse(bf, line, lineNumber);
      bf.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
    ISpellItem si = this.w.mp.startItem;
    revalidateAllNachfolger(si);
  }
  
  void revalidateAllNachfolger(ISpellItem si)
  {
    while (si != null)
    {
      if ((si instanceof ComplexItem)) {
        revalidateAllNachfolger(((ComplexItem)si).firstBlockItem);
      }
      si.revalidate();
      si = si.getNext();
    }
  }
  
  public void parse(String file)
  {
    try
    {
      int lineNumber = 1;
      BufferedReader bf = new BufferedReader(new StringReader(file));
      String line = bf.readLine();
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      this.w.m.spellname = line;
      line = bf.readLine();
      lineNumber++;
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("passive"))
      {
        this.w.m.active = false;
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
        line = line.trim();
        this.w.m.event = line;
      }
      else if (line.toLowerCase().startsWith("active"))
      {
        this.w.m.active = true;
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
      }
      else if (line.toLowerCase().startsWith("buff"))
      {
        this.w.m.active = false;
        this.w.m.buff = true;
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
        line = line.trim();
        if (line.contains(":"))
        {
          String[] args = line.split(Pattern.quote(":"));
          line = args[0];
          try
          {
            this.w.m.priority = Integer.parseInt(args[1]);
          }
          catch (Exception ignored) {}
        }
        this.w.m.event = line.trim().toLowerCase();
      }
      else
      {
        bf.close();
        return;
      }
      line = bf.readLine();
      lineNumber++;
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.startsWith("variables"))
      {
        try
        {
          String vars = line.split(":")[1].trim();
          for (String s : vars.split(",")) {
            this.w.m.variables.add(s.trim().toLowerCase());
          }
        }
        catch (Exception e)
        {
          bf.close();
          return;
        }
        line = bf.readLine();
      }
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("minlevel:"))
      {
        String[] minlevelS = line.split(":");
        if (minlevelS.length == 2) {
          try
          {
            this.w.m.minlevel = Integer.parseInt(minlevelS[1].trim());
          }
          catch (Exception ignored) {}
        }
        line = bf.readLine();
        lineNumber++;
      }
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("permission:"))
      {
        String[] perms = line.split(":");
        if (perms.length == 2) {
          this.w.m.permission = perms[1].trim();
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
      }
      startCodeParse(bf, line, lineNumber);
      bf.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
  }
  
  public void startCodeParse(BufferedReader bf, String curline, int linenumber)
    throws IOException
  {
    while ((curline != null) && (!curline.equalsIgnoreCase("")))
    {
      linenumber++;
      if (curline.startsWith("//"))
      {
        try
        {
          curline = bf.readLine();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
      else
      {
        if (curline.toLowerCase().trim().startsWith("repeat"))
        {
          RepeatStatement rs = new RepeatStatement(this, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("if"))
        {
          IfStatement rs = new IfStatement(this, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("elseif"))
        {
          ElseIfStatement rs = new ElseIfStatement(this, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("else"))
        {
          ElseStatement rs = new ElseStatement(this, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("while"))
        {
          WhileStatement rs = new WhileStatement(this, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("foreach"))
        {
          ForeachStatement rs = new ForeachStatement(this.w, this);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("try"))
        {
          TryStatement rs = new TryStatement(this, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          if (this.newspell) {
            addSpellItem(spi);
          } else {
            addSpellItem(spi, this.p);
          }
        }
        else if (curline.toLowerCase().trim().startsWith("var"))
        {
          VariableParser vp = new VariableParser(this.w);
          if (this.newspell) {
            addSpellItem(vp.getVarPanel(curline));
          } else {
            addSpellItem(vp.getVarPanel(curline), this.p);
          }
        }
        else if (this.newspell)
        {
          addSpellItem(parseSpellItem(curline));
        }
        else
        {
          addSpellItem(parseSpellItem(curline), this.p);
        }
        try
        {
          curline = bf.readLine();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
    if (this.startitem != null) {
      this.startitem.setNextLocation();
    }
    this.w.mp.setScrollBars();
    this.w.setTitle("Spellmaker - " + this.w.m.spellname);
  }
  
  public SpellItem parseSpellItem(String line)
  {
    line = line.trim();
    LinkedList<String> args = new LinkedList();
    int curpos = 0;
    int openbrackets = 0;
    boolean openqmark = false;
    int lastindex = 0;
    if ((line.startsWith("(")) && (line.endsWith(")"))) {
      line = line.substring(1, line.length() - 1);
    }
    while (curpos < line.length())
    {
      if ((line.charAt(curpos) == ',') && (openbrackets == 0) && (!openqmark))
      {
        args.add(line.substring(lastindex, curpos).trim());
        lastindex = curpos + 1;
      }
      if ((line.charAt(curpos) == '(') && (!openqmark)) {
        openbrackets++;
      }
      if ((line.charAt(curpos) == ')') && (!openqmark)) {
        openbrackets--;
      }
      if (line.charAt(curpos) == '"') {
        openqmark = !openqmark;
      }
      curpos++;
    }
    args.add(line.substring(lastindex, curpos));
    for (Command c : SpellPreview.commands) {
      if (((String)args.get(0)).equalsIgnoreCase(c.name.toLowerCase()))
      {
        SpellItem si = new SpellItem(c.name, c.parameters, c.paramdesc, this.w);
        for (int i = 0; i < si.parameters.length; i++) {
          if (args.size() > i + 1)
          {
            ArgumentParser ap = new ArgumentParser(this.w);
            ap.parse((String)args.get(i + 1));
            IParameter ip = ap.getArgumentPanel(this.w);
            if (ip != null)
            {
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
  
  void addSpellItem(ISpellItem si)
  {
    if (si == null) {
      return;
    }
    this.w.mp.add(si);
    this.w.mp.registeredItems.add(si);
    si.setSize(si.getPreferredSize());
    if (this.lastItem == null)
    {
      si.setPrevious(this.startitem);
      si.setLocation(si.getPrevious().getX(), si.getPrevious().getY() + si.getPrevious().getHeight());
      this.startitem.setNext(si);
      this.startitem.setNextLocation();
      this.lastItem = si;
    }
    else
    {
      si.setPrevious(this.lastItem);
      si.setLocation(si.getPrevious().getX(), si.getPrevious().getY() + si.getPrevious().getHeight());
      this.lastItem.setNext(si);
      this.lastItem = si;
    }
  }
  
  void addSpellItem(ISpellItem si, Point p)
  {
    if (si == null) {
      return;
    }
    this.w.mp.add(si);
    this.w.mp.registeredItems.add(si);
    si.setSize(si.getPreferredSize());
    if (this.lastItem == null)
    {
      si.setLocation(p);
      this.lastItem = si;
    }
    else
    {
      si.setPrevious(this.lastItem);
      si.setLocation(si.getPrevious().getX(), si.getPrevious().getY() + si.getPrevious().getHeight());
      this.lastItem.setNext(si);
      this.lastItem = si;
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\SpellParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */