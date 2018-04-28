package org.mendybot.configtool.installer;

public class NetworkNode
{
  private int o1;
  private int o2;
  private int o3;
  private byte o4;
  
  public NetworkNode(int o1, int o2, int o3, byte o4)
  {
    this.o1 = o1;
    this.o2 = o2;
    this.o3 = o3;
    this.o4 = o4;
  }

  public String getAddressString()
  {
    return o1+"."+o2+"."+o3+"."+o4;
  }

  public String getNetmaskString()
  {
    return "255.255.255.0";
  }
  
  public String getGateway()
  {
    return o1+"."+o2+"."+o3+".0";
  }
  
}
