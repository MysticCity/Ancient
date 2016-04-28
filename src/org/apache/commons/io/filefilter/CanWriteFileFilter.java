package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

public class CanWriteFileFilter
  extends AbstractFileFilter
  implements Serializable
{
  public static final IOFileFilter CAN_WRITE = new CanWriteFileFilter();
  public static final IOFileFilter CANNOT_WRITE = new NotFileFilter(CAN_WRITE);
  
  public boolean accept(File file)
  {
    return file.canWrite();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\filefilter\CanWriteFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */