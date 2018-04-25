package org.mendybot.common.role.archive;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Manifest implements Serializable
{
  private static final long serialVersionUID = 2385571198616776882L;
  private ArrayList<ManifestEntry> entries = new ArrayList<>();
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
      entries.add(me);
    }
  }

  public List<ManifestEntry> getEntries()
  {
    ArrayList<ManifestEntry> list = new ArrayList<>();
    synchronized(entries)
    {
      list.addAll(entries);
    }
    return list;
  }
  
  @Override
  public String toString()
  {
    return name+" "+entries;
  }
}
