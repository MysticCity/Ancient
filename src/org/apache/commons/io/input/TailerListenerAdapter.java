package org.apache.commons.io.input;

public class TailerListenerAdapter
  implements TailerListener
{
  public void init(Tailer tailer) {}
  
  public void fileNotFound() {}
  
  public void fileRotated() {}
  
  public void handle(String line) {}
  
  public void handle(Exception ex) {}
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\input\TailerListenerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */