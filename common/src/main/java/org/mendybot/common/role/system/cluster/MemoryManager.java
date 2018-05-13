package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;

public class MemoryManager extends ClusterManager
{
  private static final Logger LOG = Logger.getInstance(MemoryManager.class);

  public MemoryManager(SingleNodeCluster cluster)
  {
    super(cluster);
  }

  @Override
  protected void initManager()
  {
    LOG.logDebug("initManager", "call");
  }

  @Override
  protected void startManager()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void stopManager()
  {
    // TODO Auto-generated method stub
    
  }

}
