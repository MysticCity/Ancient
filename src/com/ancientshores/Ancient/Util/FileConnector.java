package com.ancientshores.Ancient.Util;

import java.io.File;

public abstract interface FileConnector
{
  public abstract String getElementOfFile(String paramString1, String paramString2);
  
  public abstract double getDoubleOfFile(String paramString1, String paramString2, File paramFile);
  
  public abstract int getIntOfFile(String paramString1, String paramString2, File paramFile);
  
  public abstract boolean getBooleanOfFile(String paramString1, String paramString2, File paramFile);
}
