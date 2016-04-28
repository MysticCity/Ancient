package org.apache.commons.io;

import java.io.IOException;

public class IOExceptionWithCause
  extends IOException
{
  private static final long serialVersionUID = 1L;
  
  public IOExceptionWithCause(String message, Throwable cause)
  {
    super(message);
    initCause(cause);
  }
  
  public IOExceptionWithCause(Throwable cause)
  {
    super(cause == null ? null : cause.toString());
    initCause(cause);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\IOExceptionWithCause.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */