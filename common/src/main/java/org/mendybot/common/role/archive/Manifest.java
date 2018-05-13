package org.mendybot.common.role.archive;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Manifest implements Cloneable, Serializable
{
  private static final long serialVersionUID = 2385571198616776882L;
  private LinkedHashMap<String, ManifestEntry> entries = new LinkedHashMap<>();
  private String name;

  public Manifest(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void add(ManifestEntry me)
  {
    synchronized(entries)
    {
      entries.put(me.getName(), me);
    }
  }

  public void remove(ManifestEntry me)
  {
    synchronized(entries)
    {
      entries.remove(me.getName());
    }
  }
  
  public Map<String, ManifestEntry> getEntries()
  {
    LinkedHashMap<String, ManifestEntry> map = new LinkedHashMap<>();
    synchronized(entries)
    {
      map.putAll(entries);
    }
    return map;
  }
  
  @Override
  public String toString()
  {
    return name+" "+entries;
  }

  public Manifest clone()
  {
    Manifest m = new Manifest(name);
    for (Entry<String, ManifestEntry> meSet : entries.entrySet())
    {
      m.add(meSet.getValue().clone());
    }
    return m;    
  }
}
