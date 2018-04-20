package org.mendybot.common.role.system.cluster;

public class Heartbeat implements Comparable<Heartbeat>
{
  private String name;
  private int sequence;
  private long ts;
  private NodeStatus status;
  private ClusterRoleStatus clusterStatus;
  private long receivedTS;
  private int clusterPriority;

  public Heartbeat(int clusterPriority, String name)
  {
    this.clusterPriority = clusterPriority;
    this.name = name;
  }

  public Heartbeat(int clusterPriority, String name, long receivedTS)
  {
    this(clusterPriority, name);
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

  public void setStatus(NodeStatus status)
  {
    this.status=status;
  }

  public NodeStatus setStatus()
  {
    return status;
  }

  public void setClusterStatus(ClusterRoleStatus clusterStatus)
  {
    this.clusterStatus=clusterStatus;
  }

  public ClusterRoleStatus getClusterStatus()
  {
    return clusterStatus;
  }

  @Override
  public String toString()
  {
    return "|"+clusterPriority+"|"+name+"|"+(sequence++)+"|"+ts+"|"+status+"|"+clusterStatus+"|";
  }

  @Override
  public int compareTo(Heartbeat o)
  {
    return Integer.compare(clusterPriority, o.clusterPriority);
  }

}
