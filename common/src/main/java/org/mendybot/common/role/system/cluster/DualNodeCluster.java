package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public class DualNodeCluster extends ClusterRole
{
  private static final Logger LOG = Logger.getInstance(ClusterRole.class);

  public DualNodeCluster(ApplicationModel model)
  {
    super(model);
  }

  @Override
  protected ClusterManager createClusterManager()
  {
    return new SerialManager(this);
  }

  @Override
  public Heartbeat createHeartbeat()
  {
    return new Heartbeat("dual node fix me");
  }

}
