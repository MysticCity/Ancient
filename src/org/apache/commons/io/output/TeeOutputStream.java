package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class TeeOutputStream
  extends ProxyOutputStream
{
  protected OutputStream branch;
  
  public TeeOutputStream(OutputStream out, OutputStream branch)
  {
    super(out);
    this.branch = branch;
  }
  
  public synchronized void write(byte[] b)
    throws IOException
  {
    super.write(b);
    this.branch.write(b);
  }
  
  public synchronized void write(byte[] b, int off, int len)
    throws IOException
  {
    super.write(b, off, len);
    this.branch.write(b, off, len);
  }
  
  public synchronized void write(int b)
    throws IOException
  {
    super.write(b);
    this.branch.write(b);
  }
  
  public void flush()
    throws IOException
  {
    super.flush();
    this.branch.flush();
  }
  
  public void close()
    throws IOException
  {
    try
    {
      super.close();
    }
    finally
    {
      this.branch.close();
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\output\TeeOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */