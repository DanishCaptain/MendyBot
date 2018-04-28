package org.mendybot.configtool.installer;

public class NetworkInterface
{
  private String id;
  private NetworkNode node;
  
  public NetworkInterface(String id)
  {    
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void plumb(NetworkNode node)
  {
    this.node = node;
  }

  public String getAddressString()
  {
    if (node == null) {
      return "down";
    } else {
    return node.getAddressString();
    }
  }

  public boolean hasAddressString()
  {
    return node != null;
  }
  
  /*
  private String address;
  private String netmask;
  private String gateway;

  public NetworkInterface(String intf, String address, String netmask, String gateway)
  {    
    this.address = address;
    this.netmask = netmask;
    this.gateway = gateway;
  }

  */

  @Override
  public String toString()
  {
    return "auto "+id+"\n"+
    "iface "+id+" inet static\n"+
    "address "+node.getAddressString()+"\n"+
    "netmask "+node.getNetmaskString()+"\n"+
    "#gateway "+node.getGateway()+"\n";
  }
}
