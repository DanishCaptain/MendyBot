package org.mendybot.common.role.system.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

public abstract class ClusterManager implements Runnable
{
  private static final Logger LOG = Logger.getInstance(ClusterManager.class);
  private ArrayBlockingQueue<Heartbeat> queue = new ArrayBlockingQueue<Heartbeat>(10);
  private HashMap<String, Heartbeat> hbMap = new HashMap<String, Heartbeat>();
  private Thread t = new Thread(this);
  private int deadtime;
  private int warntime;

  private ClusterRole cluster;

  private boolean running;

  public ClusterManager(ClusterRole cluster)
  {
    t.setName(getClass().getSimpleName());
    this.cluster = cluster;
  }
  
  protected ClusterRole getCluster()
  {
    return cluster;
  }

  public final void init() throws ExecuteException {
    deadtime = 1000 * Integer.parseInt(cluster.getModel().getProperty("deadtime", "10"));
    warntime = 1000 * Integer.parseInt(cluster.getModel().getProperty("warntime", "5"));
    initManager();
  }

  protected abstract void initManager() throws ExecuteException;

  public final void start() throws ExecuteException {
    LOG.logDebug("start", "called");
    startManager();
    if (!running) {
      t.start();
    }
  }

  protected abstract void startManager() throws ExecuteException;
  
  public final void stop() {
    LOG.logDebug("stop", "called");
    stopManager();
    if (!running) {
      t.start();
    }
  }

  protected abstract void stopManager();

  
  @Override
  public final void run()
  {
    running = true;
    while(running) {
      try
      {
        Heartbeat hb = queue.take();
        Heartbeat previous = hbMap.get(hb.getName());
        hbMap.put(hb.getName(), hb);
        if (previous != null) {
          int delta = hb.getSequence() - previous.getSequence();
          if (hb.getSequence() > 0 && delta != 0) {
            LOG.logDebug("run", "<--(Q)"+hb+" missing: "+delta+"  "+hb.getSequence()+":"+previous.getSequence());
          } else {
            LOG.logDebug("run", "<--(Q)"+hb);
            LOG.logDebug("run", "sinceLast: "+(hb.getTimestamp()-previous.getTimestamp()));
            LOG.logDebug("run", "inTransit: "+(hb.getReceivedTimestamp()-hb.getTimestamp()));
          }
        } else {
          LOG.logInfo("run", "initial contact with: "+hb.getName());
        }
        long now = System.currentTimeMillis();
        ArrayList<Heartbeat> expired = new ArrayList<Heartbeat>();
        boolean hasPrime = cluster.getRoleStatus() == ClusterRoleStatus.PRIME;
        for(Entry<String, Heartbeat> e : hbMap.entrySet()) {
          if (now - e.getValue().getTimestamp() >= deadtime) {
            expired.add(e.getValue());
          } else if (now - e.getValue().getTimestamp() >= warntime) {
            LOG.logInfo("run", "lost contact with: "+e.getValue().getName());
          } else {
            hasPrime = hasPrime || e.getValue().getClusterStatus() == ClusterRoleStatus.PRIME;
          }
        }
        for (Heartbeat whb : expired) {
          hbMap.remove(whb.getName());
        }
        if (!hasPrime && previous != null) {
          ArrayList<Heartbeat> w = new ArrayList<Heartbeat>(hbMap.values());
          Collections.sort(w);
          Heartbeat newPrime = w.get(0);
          if (cluster.getAppName().equals(newPrime.getName())) {
            cluster.setRoleStatus(ClusterRoleStatus.PRIME);
            LOG.logInfo("run", "PRIME assumed");
            try
            {
              getCluster().getModel().getApplicationPlatform().enableNetworkInterface("10.2.0.1", 24);
            }
            catch (ExecuteException e1)
            {
              LOG.logSevere("run", e1);
            }
          }
        }
        if (cluster.getRoleStatus() == ClusterRoleStatus.UNKNOWN) {
          cluster.setRoleStatus(ClusterRoleStatus.SLAVE);
          LOG.logInfo("run", "SLAVE assumed");
          try
          {
            getCluster().getModel().getApplicationPlatform().disableNetworkInterface("10.2.0.1", 24);
          }
          catch (ExecuteException e1)
          {
            LOG.logSevere("run", e1);
          }
        }
        
//        if (!hasPrime && previous != null) {
//          LOG.logInfo("run", "no prime detected.");
//          cluster.setRoleStatus(ClusterRoleStatus.PRIME);
//          LOG.logInfo("run", "PRIME assumed");
//        } else {
//        }
        Thread.yield();
      }
      catch (InterruptedException e)
      {
        running = false;
        break;
      }
    }
  }

  public void processHeartbeat(Heartbeat hb)
  {
    try
    {
      queue.put(hb);
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
