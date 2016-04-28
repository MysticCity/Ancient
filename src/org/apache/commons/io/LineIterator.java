package org.apache.commons.io;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator
  implements Iterator<String>
{
  private final BufferedReader bufferedReader;
  private String cachedLine;
  private boolean finished = false;
  
  public LineIterator(Reader reader)
    throws IllegalArgumentException
  {
    if (reader == null) {
      throw new IllegalArgumentException("Reader must not be null");
    }
    if ((reader instanceof BufferedReader)) {
      this.bufferedReader = ((BufferedReader)reader);
    } else {
      this.bufferedReader = new BufferedReader(reader);
    }
  }
  
  /* Error */
  public boolean hasNext()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 46	org/apache/commons/io/LineIterator:cachedLine	Ljava/lang/String;
    //   4: ifnull +5 -> 9
    //   7: iconst_1
    //   8: ireturn
    //   9: aload_0
    //   10: getfield 23	org/apache/commons/io/LineIterator:finished	Z
    //   13: ifeq +5 -> 18
    //   16: iconst_0
    //   17: ireturn
    //   18: aload_0
    //   19: getfield 34	org/apache/commons/io/LineIterator:bufferedReader	Ljava/io/BufferedReader;
    //   22: invokevirtual 50	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   25: astore_1
    //   26: aload_1
    //   27: ifnonnull +10 -> 37
    //   30: aload_0
    //   31: iconst_1
    //   32: putfield 23	org/apache/commons/io/LineIterator:finished	Z
    //   35: iconst_0
    //   36: ireturn
    //   37: aload_0
    //   38: aload_1
    //   39: invokevirtual 56	org/apache/commons/io/LineIterator:isValidLine	(Ljava/lang/String;)Z
    //   42: ifeq +10 -> 52
    //   45: aload_0
    //   46: aload_1
    //   47: putfield 46	org/apache/commons/io/LineIterator:cachedLine	Ljava/lang/String;
    //   50: iconst_1
    //   51: ireturn
    //   52: goto -34 -> 18
    //   55: astore_1
    //   56: aload_0
    //   57: invokevirtual 59	org/apache/commons/io/LineIterator:close	()V
    //   60: new 61	java/lang/IllegalStateException
    //   63: dup
    //   64: aload_1
    //   65: invokespecial 64	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   68: athrow
    // Line number table:
    //   Java source line #88	-> byte code offset #0
    //   Java source line #89	-> byte code offset #7
    //   Java source line #90	-> byte code offset #9
    //   Java source line #91	-> byte code offset #16
    //   Java source line #95	-> byte code offset #18
    //   Java source line #96	-> byte code offset #26
    //   Java source line #97	-> byte code offset #30
    //   Java source line #98	-> byte code offset #35
    //   Java source line #99	-> byte code offset #37
    //   Java source line #100	-> byte code offset #45
    //   Java source line #101	-> byte code offset #50
    //   Java source line #103	-> byte code offset #52
    //   Java source line #104	-> byte code offset #55
    //   Java source line #105	-> byte code offset #56
    //   Java source line #106	-> byte code offset #60
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	69	0	this	LineIterator
    //   25	22	1	line	String
    //   55	10	1	ioe	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   18	36	55	java/io/IOException
    //   37	51	55	java/io/IOException
    //   52	55	55	java/io/IOException
  }
  
  protected boolean isValidLine(String line)
  {
    return true;
  }
  
  public String next()
  {
    return nextLine();
  }
  
  public String nextLine()
  {
    if (!hasNext()) {
      throw new NoSuchElementException("No more lines");
    }
    String currentLine = this.cachedLine;
    this.cachedLine = null;
    return currentLine;
  }
  
  public void close()
  {
    this.finished = true;
    IOUtils.closeQuietly(this.bufferedReader);
    this.cachedLine = null;
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("Remove unsupported on LineIterator");
  }
  
  public static void closeQuietly(LineIterator iterator)
  {
    if (iterator != null) {
      iterator.close();
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\LineIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */