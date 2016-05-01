package com.ancientshores.Ancient.Util.spell;

import com.ancientshores.Ancient.Util.spell.data.Data;
import java.util.Iterator;
import java.util.LinkedList;

public class ExecutionReturn
{
  private final int NEXTLINE;
  private final LinkedList<Data> DATA;
  
  public ExecutionReturn(int nextLine)
  {
    this.NEXTLINE = nextLine;
    this.DATA = new LinkedList();
  }
  
  public void addData(Data... data)
  {
    for (Data d : data) {
      this.DATA.add(d);
    }
  }
  
  public int getNextLine()
  {
    return this.NEXTLINE;
  }
  
  public Iterator<Data> getData()
  {
    return this.DATA.iterator();
  }
}
