package org.apache.commons.io.monitor;

import java.io.File;

public class FileAlterationListenerAdaptor
  implements FileAlterationListener
{
  public void onStart(FileAlterationObserver observer) {}
  
  public void onDirectoryCreate(File directory) {}
  
  public void onDirectoryChange(File directory) {}
  
  public void onDirectoryDelete(File directory) {}
  
  public void onFileCreate(File file) {}
  
  public void onFileChange(File file) {}
  
  public void onFileDelete(File file) {}
  
  public void onStop(FileAlterationObserver observer) {}
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\org\apache\commons\io\monitor\FileAlterationListenerAdaptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */