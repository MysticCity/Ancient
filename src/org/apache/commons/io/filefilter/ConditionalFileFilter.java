package org.apache.commons.io.filefilter;

import java.util.List;

public abstract interface ConditionalFileFilter
{
  public abstract void addFileFilter(IOFileFilter paramIOFileFilter);
  
  public abstract List<IOFileFilter> getFileFilters();
  
  public abstract boolean removeFileFilter(IOFileFilter paramIOFileFilter);
  
  public abstract void setFileFilters(List<IOFileFilter> paramList);
}
