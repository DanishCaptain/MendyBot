package org.mendybot.configtool.installer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Host
{
  private HashMap<String, NetworkInterface> intfcs = new HashMap<String, NetworkInterface>();
  private String name;

  public Host(String name)
  {
    this.name = name;
  }

  public void add(NetworkInterface ni)
  {
    intfcs.put(ni.getId(), ni);
  }

  public NetworkInterface lookupInterface(String key)
  {
    return intfcs.get(key);
  }

  public void writeHostsEntries(PrintWriter pw)
  {
    for (Entry<String, NetworkInterface> e : intfcs.entrySet())
    {
      if (e.getValue().hasAddressString()) {
        pw.println(e.getValue().getAddressString()+" "+name);
      }
    }
  }

  public String getName()
  {
    return name;
  }

  public List<NetworkInterface> getInterfaces()
  {
    ArrayList<NetworkInterface> list = new ArrayList<NetworkInterface>();
    for (Entry<String, NetworkInterface> e : intfcs.entrySet())
    {
      list.add(e.getValue());
    }
    return list;
  }

  /*
  private ArrayList<NetworkNode> nodes = new ArrayList<NetworkNode>();
  private String ip;
  public void addNetworkNode(NetworkNode n)
  {
    nodes.add(n);
  }

  public void writeHostsEntries(PrintWriter pw)
  {
    for (NetworkNode node : nodes)
    {
      pw.println(node.getAddressString()+" "+name);
    }
  }
  */
}
