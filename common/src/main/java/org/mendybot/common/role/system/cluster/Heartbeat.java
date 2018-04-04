package org.mendybot.common.role.system.cluster;

public class Heartbeat
{
  private String name;
  private int sequence;
  private long ts;
  private String status;
  private String clusterStatus;
  private long receivedTS;

  public Heartbeat(String name)
  {
    this.name = name;
  }

  public Heartbeat(String name, long receivedTS)
  {
    this(name);
    this.receivedTS = receivedTS;
  }

  public String getName()
  {
    return name;
  }
  
  public void setSequence(int s)
  {
    this.sequence = s;
  }

  public int getSequence()
  {
    return sequence;
  }

  public void setTimestamp(long ts)
  {
    this.ts = ts;
  }

  public long getTimestamp()
  {
    return ts;
  }

  public long getReceivedTimestamp()
  {
    return receivedTS;
  }

  public void setStatus(String status)
  {
    this.status=status;
  }

  public void setClusterStatus(String clusterStatus)
  {
    this.clusterStatus=clusterStatus;
  }

  @Override
  public String toString()
  {
    return "|"+name+"|"+(sequence++)+"|"+ts+"|"+status+"|"+clusterStatus+"|";
  }

}
