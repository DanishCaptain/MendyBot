package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public class DualNodeCluster extends ClusterRole
{
  private static final Logger LOG = Logger.getInstance(ClusterRole.class);
  private ClusterRoleStatus crs = ClusterRoleStatus.UNKNOWN;
  private int clusterPriority;

  public DualNodeCluster(ApplicationModel model)
  {
    super(model);
    clusterPriority = 1000 * Integer.parseInt(model.getProperty("cluster-priority", Integer.toString(Integer.MAX_VALUE)));
  }

  @Override
  protected ClusterManager createClusterManager()
  {
    return new SerialManager(this);
  }

  @Override
  public Heartbeat createHeartbeat()
  {
    return new Heartbeat(clusterPriority, "dual node fix me");
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
