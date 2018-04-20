package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public class SingleNodeCluster extends ClusterRole
{
  private static final Logger LOG = Logger.getInstance(SingleNodeCluster.class);
  private ClusterRoleStatus crs = ClusterRoleStatus.UNKNOWN;

  public SingleNodeCluster(ApplicationModel model)
  {
    super(model);
  }

  @Override
  protected ClusterManager createClusterManager()
  {
    return new MemoryManager(this);
  }

  @Override
  public Heartbeat createHeartbeat()
  {
    return new Heartbeat(1, "single node - fix me");
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
