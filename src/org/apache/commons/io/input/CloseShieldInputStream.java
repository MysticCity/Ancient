package org.apache.commons.io.input;

import java.io.InputStream;

public class CloseShieldInputStream
  extends ProxyInputStream
{
  public CloseShieldInputStream(InputStream in)
  {
    super(in);
  }
  
  public void close()
  {
    this.in = new ClosedInputStream();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\input\CloseShieldInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */