package org.apache.commons.io.output;

import java.io.Writer;

public class NullWriter
  extends Writer
{
  public static final NullWriter NULL_WRITER = new NullWriter();
  
  public Writer append(char c)
  {
    return this;
  }
  
  public Writer append(CharSequence csq, int start, int end)
  {
    return this;
  }
  
  public Writer append(CharSequence csq)
  {
    return this;
  }
  
  public void write(int idx) {}
  
  public void write(char[] chr) {}
  
  public void write(char[] chr, int st, int end) {}
  
  public void write(String str) {}
  
  public void write(String str, int st, int end) {}
  
  public void flush() {}
  
  public void close() {}
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\output\NullWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */