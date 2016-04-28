package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class ClosedOutputStream
  extends OutputStream
{
  public static final ClosedOutputStream CLOSED_OUTPUT_STREAM = new ClosedOutputStream();
  
  public void write(int b)
    throws IOException
  {
    throw new IOException("write(" + b + ") failed: stream is closed");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\output\ClosedOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */