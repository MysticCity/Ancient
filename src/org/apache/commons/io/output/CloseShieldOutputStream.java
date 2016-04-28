package org.apache.commons.io.output;

import java.io.OutputStream;

public class CloseShieldOutputStream
  extends ProxyOutputStream
{
  public CloseShieldOutputStream(OutputStream out)
  {
    super(out);
  }
  
  public void close()
  {
    this.out = new ClosedOutputStream();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\output\CloseShieldOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */