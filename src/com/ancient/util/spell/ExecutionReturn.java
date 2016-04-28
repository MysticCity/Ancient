package com.ancient.util.spell;

import com.ancient.util.spell.data.Data;
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\spell\ExecutionReturn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */