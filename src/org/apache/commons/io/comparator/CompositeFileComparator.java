package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompositeFileComparator
  extends AbstractFileComparator
  implements Serializable
{
  private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
  private final Comparator<File>[] delegates;
  
  public CompositeFileComparator(Comparator<File>... delegates)
  {
    if (delegates == null)
    {
      this.delegates = ((Comparator[])NO_COMPARATORS);
    }
    else
    {
      this.delegates = ((Comparator[])new Comparator[delegates.length]);
      System.arraycopy(delegates, 0, this.delegates, 0, delegates.length);
    }
  }
  
  public CompositeFileComparator(Iterable<Comparator<File>> delegates)
  {
    if (delegates == null)
    {
      this.delegates = ((Comparator[])NO_COMPARATORS);
    }
    else
    {
      List<Comparator<File>> list = new ArrayList();
      for (Comparator<File> comparator : delegates) {
        list.add(comparator);
      }
      this.delegates = ((Comparator[])list.toArray(new Comparator[list.size()]));
    }
  }
  
  public int compare(File file1, File file2)
  {
    int result = 0;
    for (Comparator<File> delegate : this.delegates)
    {
      result = delegate.compare(file1, file2);
      if (result != 0) {
        break;
      }
    }
    return result;
  }
  
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append(super.toString());
    builder.append('{');
    for (int i = 0; i < this.delegates.length; i++)
    {
      if (i > 0) {
        builder.append(',');
      }
      builder.append(this.delegates[i]);
    }
    builder.append('}');
    return builder.toString();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\comparator\CompositeFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */