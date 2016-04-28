package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

public class SizeFileFilter
  extends AbstractFileFilter
  implements Serializable
{
  private final long size;
  private final boolean acceptLarger;
  
  public SizeFileFilter(long size)
  {
    this(size, true);
  }
  
  public SizeFileFilter(long size, boolean acceptLarger)
  {
    if (size < 0L) {
      throw new IllegalArgumentException("The size must be non-negative");
    }
    this.size = size;
    this.acceptLarger = acceptLarger;
  }
  
  public boolean accept(File file)
  {
    boolean smaller = file.length() < this.size;
    return this.acceptLarger ? false : !smaller ? true : smaller;
  }
  
  public String toString()
  {
    String condition = this.acceptLarger ? ">=" : "<";
    return super.toString() + "(" + condition + this.size + ")";
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\filefilter\SizeFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */