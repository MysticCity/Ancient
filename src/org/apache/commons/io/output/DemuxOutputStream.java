package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class DemuxOutputStream
  extends OutputStream
{
  private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal();
  
  public OutputStream bindStream(OutputStream output)
  {
    OutputStream stream = (OutputStream)this.m_streams.get();
    this.m_streams.set(output);
    return stream;
  }
  
  public void close()
    throws IOException
  {
    OutputStream output = (OutputStream)this.m_streams.get();
    if (null != output) {
      output.close();
    }
  }
  
  public void flush()
    throws IOException
  {
    OutputStream output = (OutputStream)this.m_streams.get();
    if (null != output) {
      output.flush();
    }
  }
  
  public void write(int ch)
    throws IOException
  {
    OutputStream output = (OutputStream)this.m_streams.get();
    if (null != output) {
      output.write(ch);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\output\DemuxOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */