package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

public class MulticastManager extends ClusterManager
{
  private static final Logger LOG = Logger.getInstance(MulticastManager.class);
  private MulticastTransmitter transmitter;
  private MulticastReceiver receiver;

  public MulticastManager(MultiNodeCluster cluster)
  {
    super(cluster);
  }

  @Override
  protected void initManager() throws ExecuteException
  {
    LOG.logDebug("initManager", "call");
    try
    {
      receiver = new MulticastReceiver(this);
      receiver.init();
      transmitter = new MulticastTransmitter(this);
      transmitter.init();
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
  }

  @Override
  protected void startManager() throws ExecuteException
  {
    receiver.start();
    transmitter.start();
  }

  @Override
  protected void stopManager()
  {
    transmitter.stop();
    receiver.stop();
  }

}
