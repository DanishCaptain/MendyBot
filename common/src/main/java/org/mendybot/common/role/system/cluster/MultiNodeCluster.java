package org.mendybot.common.role.system.cluster;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public class MultiNodeCluster extends ClusterRole
{
  private static final Logger LOG = Logger.getInstance(MultiNodeCluster.class);
  private int sequence=1;

  public MultiNodeCluster(ApplicationModel model)
  {
    super(model);
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
    String status = "GOOD";
    String clusterStatus = "UNKN";
    Heartbeat hb = new Heartbeat(getModel().getName());
    hb.setSequence(sequence++);
    hb.setTimestamp(System.currentTimeMillis());
    hb.setStatus(status);
    hb.setClusterStatus(clusterStatus);
    return hb;
  }

}
