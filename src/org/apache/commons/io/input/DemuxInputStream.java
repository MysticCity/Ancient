package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class DemuxInputStream
  extends InputStream
{
  private final InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal();
  
  public InputStream bindStream(InputStream input)
  {
    InputStream oldValue = (InputStream)this.m_streams.get();
    this.m_streams.set(input);
    return oldValue;
  }
  
  public void close()
    throws IOException
  {
    InputStream input = (InputStream)this.m_streams.get();
    if (null != input) {
      input.close();
    }
  }
  
  public int read()
    throws IOException
  {
    InputStream input = (InputStream)this.m_streams.get();
    if (null != input) {
      return input.read();
    }
    return -1;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\input\DemuxInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */