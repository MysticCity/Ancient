package de.pylamo.spellmaker.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

public class SimpleDragObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public final String s;
  public static DataFlavor dataFlavor;
  
  static
  {
    try
    {
      dataFlavor = new DataFlavor("text/uri-list;class=de.pylamo.spellmaker.gui.SimpleDragObject");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }
  
  public SimpleDragObject(String s)
  {
    this.s = s;
  }
  
  public static class TransferableSimpleDragObject
    implements Transferable
  {
    private final SimpleDragObject s;
    
    public TransferableSimpleDragObject(SimpleDragObject pp)
    {
      this.s = pp;
    }
    
    public DataFlavor[] getTransferDataFlavors()
    {
      return new DataFlavor[] { SimpleDragObject.dataFlavor };
    }
    
    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
      return flavor.equals(SimpleDragObject.dataFlavor);
    }
    
    public Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException, IOException
    {
      if (flavor.equals(SimpleDragObject.dataFlavor)) {
        return this.s;
      }
      throw new UnsupportedFlavorException(flavor);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SimpleDragObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */