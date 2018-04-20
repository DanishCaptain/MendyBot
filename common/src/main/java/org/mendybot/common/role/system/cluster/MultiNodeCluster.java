package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public class MultiNodeCluster extends ClusterRole
{
  private static final Logger LOG = Logger.getInstance(MultiNodeCluster.class);
  private int sequence=1;
  private ClusterRoleStatus crs = ClusterRoleStatus.UNKNOWN;
  private int clusterPriority;

  public MultiNodeCluster(ApplicationModel model)
  {
    super(model);
    clusterPriority = Integer.parseInt(model.getProperty("cluster-priority", Integer.toString(Integer.MAX_VALUE)));
  }

  @Override
  protected ClusterManager createClusterManager()
  {
    return new MulticastManager(this);
  }

  @Override
  public Heartbeat createHeartbeat()
  {
    if (sequence > 100) {
      sequence = 0;
    }
    NodeStatus status = NodeStatus.GOOD;
    Heartbeat hb = new Heartbeat(clusterPriority, getModel().getName());
    hb.setSequence(sequence++);
    hb.setTimestamp(System.currentTimeMillis());
    hb.setStatus(status);
    hb.setClusterStatus(crs);
    return hb;
  }

  @Override
  public void setRoleStatus(ClusterRoleStatus crs)
  {
    this.crs = crs;
  }

  @Override
  public ClusterRoleStatus getRoleStatus()
  {
    return crs;
  }

}
