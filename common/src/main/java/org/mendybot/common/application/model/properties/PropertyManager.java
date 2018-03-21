package org.mendybot.common.application.model.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.mendybot.common.exception.ExecuteException;

public class PropertyManager
{
  private HashMap<String, Properties> map = new HashMap<String, Properties>();

  public void init(String domain, String fileName) throws ExecuteException, DomainPropertySourceNotFoundException
  {
    Properties p = lookup(domain);
    File file = new File(fileName);
    if (file.exists())
    {
      try
      {
        FileInputStream is = new FileInputStream(file);
        p.load(is);
        is.close();
      }
      catch (IOException e)
      {
        throw new ExecuteException(e);
      }
    }
    else
    {
      throw new DomainPropertySourceNotFoundException("domain properties ("+domain+") not found: "+file.getAbsolutePath());
    }
  }

  public Properties lookup(String domain)
  {
    Properties p;
    synchronized (map)
    {
      p = map.get(domain);
      if (p == null)
      {
        p = new Properties();
        map.put(domain, p);
      }
    }
    return p;
  }

}
