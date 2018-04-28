package org.mendybot.configtool.installer;

public class NfsLink
{
  private Host host;
  private String path1;
  private String path2;
  private int rSize;
  private int wSize;
  private int timeo;

  public NfsLink(Host host, String path1, String path2, int rSize, int wSize, int timeo)
  {
    this.host = host;
    this.path1 = path1;
    this.path2 = path2;
    this.rSize = rSize;
    this.wSize = wSize;
    this.timeo = timeo;
  }

  
  @Override
  public String toString()
  {
    return host.getName()+":"+path1+" /"+host.getName()+path2+" nfs rsize="+rSize+",wsize="+wSize+",timeo="+timeo+",intr";
  }
}
