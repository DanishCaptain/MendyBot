package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public class SingleNodeCluster extends ClusterRole
{
  private static final Logger LOG = Logger.getInstance(SingleNodeCluster.class);

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
    return new Heartbeat("single node - fix me");
  }

}
