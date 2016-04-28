package org.apache.commons.io.filefilter;

import java.io.File;

public abstract class AbstractFileFilter
  implements IOFileFilter
{
  public boolean accept(File file)
  {
    return accept(file.getParentFile(), file.getName());
  }
  
  public boolean accept(File dir, String name)
  {
    return accept(new File(dir, name));
  }
  
  public String toString()
  {
    return getClass().getSimpleName();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\filefilter\AbstractFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */