package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.system.SystemRole;

public abstract class ClusterRole extends SystemRole
{
  private static final Logger LOG = Logger.getInstance(ClusterRole.class);
  private ClusterManager clusterManager;

  public ClusterRole(ApplicationModel model)
  {
    super(model);
    clusterManager = createClusterManager();
  }

  protected abstract ClusterManager createClusterManager();

  @Override
  public final void init() throws ExecuteException
  {
    clusterManager.init();
  }

  @Override
  public final void start() throws ExecuteException
  {
    clusterManager.start();
  }

  @Override
  public void stop()
  {
    clusterManager.stop();
  }

  public abstract Heartbeat createHeartbeat();

  public abstract void setRoleStatus(ClusterRoleStatus crs);

  public abstract ClusterRoleStatus getRoleStatus();

}
