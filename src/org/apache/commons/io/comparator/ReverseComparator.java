package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

class ReverseComparator
  extends AbstractFileComparator
  implements Serializable
{
  private final Comparator<File> delegate;
  
  public ReverseComparator(Comparator<File> delegate)
  {
    if (delegate == null) {
      throw new IllegalArgumentException("Delegate comparator is missing");
    }
    this.delegate = delegate;
  }
  
  public int compare(File file1, File file2)
  {
    return this.delegate.compare(file2, file1);
  }
  
  public String toString()
  {
    return super.toString() + "[" + this.delegate.toString() + "]";
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\comparator\ReverseComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */