package org.apache.commons.io;

import java.io.File;
import java.io.IOException;

public class FileExistsException
  extends IOException
{
  private static final long serialVersionUID = 1L;
  
  public FileExistsException() {}
  
  public FileExistsException(String message)
  {
    super(message);
  }
  
  public FileExistsException(File file)
  {
    super("File " + file + " exists");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\FileExistsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */