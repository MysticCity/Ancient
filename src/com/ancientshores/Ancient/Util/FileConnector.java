package com.ancientshores.Ancient.Util;

import java.io.File;

public abstract interface FileConnector
{
  public abstract String getElementOfFile(String paramString1, String paramString2);
  
  public abstract double getDoubleOfFile(String paramString1, String paramString2, File paramFile);
  
  public abstract int getIntOfFile(String paramString1, String paramString2, File paramFile);
  
  public abstract boolean getBooleanOfFile(String paramString1, String paramString2, File paramFile);
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\FileConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */