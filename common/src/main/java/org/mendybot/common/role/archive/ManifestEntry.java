package org.mendybot.common.role.archive;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManifestEntry implements Serializable
{
  private static final long serialVersionUID = 5802927347869727709L;
  private String name;
  private long lastModified;

  public ManifestEntry(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return name;
  }

  public void setLastModified(long lastModified)
  {
    this.lastModified = lastModified;
  }

  public long getLastModified()
  {
    return lastModified;
  }

  @Override
  public String toString()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yy HH:mm");
    return name+" "+sdf.format(new Date(lastModified));
  }
}
