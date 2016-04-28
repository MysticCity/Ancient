package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

public class LastModifiedFileComparator
  extends AbstractFileComparator
  implements Serializable
{
  public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
  public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);
  
  public int compare(File file1, File file2)
  {
    long result = file1.lastModified() - file2.lastModified();
    if (result < 0L) {
      return -1;
    }
    if (result > 0L) {
      return 1;
    }
    return 0;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\comparator\LastModifiedFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */