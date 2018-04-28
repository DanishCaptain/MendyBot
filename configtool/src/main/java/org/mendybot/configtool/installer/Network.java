package org.mendybot.configtool.installer;

import java.util.HashMap;

public class Network
{
  private HashMap<Byte, NetworkNode> nodeMap = new HashMap();
  private String name;
  private byte[] address;
  private int netmask;
  
  public Network(String name, int o1, int o2, int o3, int o4, int netmask)
  {
    this.name = name;
    this.address = new byte[]{(byte)o1, (byte)o2, (byte)o3, (byte)o4};
    this.netmask = netmask;
    for (byte i=1; i<netmask-1; i++)
    {
      nodeMap.put(i, new NetworkNode(o1, o2, o3, i));
    }
  }

  public NetworkNode lookupNode(byte key)
  {
    return nodeMap.get(key);
  }

  /*

  public void assign(int node, Host host)
  {
    NetworkNode n = nodeMap.get((byte)node);
    n.assign(host);
    host.addNetworkNode(n);
  }
  */

}
