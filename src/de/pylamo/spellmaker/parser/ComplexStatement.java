package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

abstract class ComplexStatement
{
  private final String end;
  ISpellItem middlestartitem;
  ISpellItem middlelastitem;
  final Window w;
  private final SpellParser sp;
  
  ComplexStatement(SpellParser sp, String end, Window w)
  {
    this.sp = sp;
    this.end = end;
    this.w = w;
  }
  
  protected abstract void parseStart(String paramString);
  
  public void parse(BufferedReader bf, String curline)
    throws IOException
  {
    parseStart(curline);
    curline = bf.readLine();
    while ((!curline.equalsIgnoreCase(this.end)) && (curline != null))
    {
      curline = curline.trim();
      if ((curline.equals("")) || (curline.equals("")))
      {
        curline = bf.readLine();
      }
      else
      {
        if (curline.toLowerCase().trim().startsWith("repeat"))
        {
          RepeatStatement rs = new RepeatStatement(this.sp, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          addSpellItem(spi);
        }
        else if (curline.toLowerCase().trim().startsWith("if"))
        {
          IfStatement rs = new IfStatement(this.sp, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          addSpellItem(spi);
        }
        else if (curline.toLowerCase().trim().startsWith("while"))
        {
          WhileStatement rs = new WhileStatement(this.sp, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          addSpellItem(spi);
        }
        else if (curline.toLowerCase().trim().startsWith("foreach"))
        {
          ForeachStatement rs = new ForeachStatement(this.w, this.sp);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          addSpellItem(spi);
        }
        else if (curline.toLowerCase().trim().startsWith("try"))
        {
          TryStatement rs = new TryStatement(this.sp, this.w);
          rs.parse(bf, curline);
          ISpellItem spi = rs.getSpellItem();
          addSpellItem(spi);
        }
        else if (curline.toLowerCase().trim().startsWith("var"))
        {
          VariableParser vp = new VariableParser(this.w);
          addSpellItem(vp.getVarPanel(curline));
        }
        else
        {
          addSpellItem(this.sp.parseSpellItem(curline));
        }
        curline = bf.readLine();
      }
    }
  }
  
  void addSpellItem(ISpellItem si)
  {
    if (si != null)
    {
      this.w.mp.add(si);
      this.w.mp.registeredItems.add(si);
      si.setSize(si.getPreferredSize());
      if (this.middlestartitem == null)
      {
        this.middlestartitem = si;
        this.middlelastitem = si;
      }
      else
      {
        si.setPrevious(this.middlelastitem);
        si.setLocation(si.getPrevious().getX(), si.getPrevious().getY() + si.getPrevious().getHeight());
        this.middlelastitem.setNext(si);
        this.middlelastitem = si;
      }
    }
  }
  
  public abstract ISpellItem getSpellItem();
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\ComplexStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */