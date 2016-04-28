package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class AutoCloseInputStream
  extends ProxyInputStream
{
  public AutoCloseInputStream(InputStream in)
  {
    super(in);
  }
  
  public void close()
    throws IOException
  {
    this.in.close();
    this.in = new ClosedInputStream();
  }
  
  protected void afterRead(int n)
    throws IOException
  {
    if (n == -1) {
      close();
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    close();
    super.finalize();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\input\AutoCloseInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */