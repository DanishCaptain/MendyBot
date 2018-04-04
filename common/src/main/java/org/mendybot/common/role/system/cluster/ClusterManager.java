package org.mendybot.common.role.system.cluster;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

public abstract class ClusterManager implements Runnable
{
  private static final Logger LOG = Logger.getInstance(ClusterManager.class);
  private ArrayBlockingQueue<Heartbeat> queue = new ArrayBlockingQueue<Heartbeat>(10);
  private HashMap<String, Heartbeat> hbMap = new HashMap<String, Heartbeat>();
  private Thread t = new Thread(this);

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
    initManager();
  }

  protected abstract void initManager() throws ExecuteException;

  public final void start() throws ExecuteException {
    System.out.println(getClass().getSimpleName()+" starting ...");
    startManager();
    if (!running) {
      t.start();
    }
  }

  protected abstract void startManager() throws ExecuteException;
  
  public final void stop() {
    System.out.println(getClass().getSimpleName()+" stopping ...");
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
          System.out.println(delta);
          if (hb.getSequence() > 0 && delta != 0) {
            System.out.println("<--(Q)"+hb+" missing: "+delta+"  "+hb.getSequence()+":"+previous.getSequence());
          } else {
            System.out.println("<--(Q)"+hb);
            System.out.println("sinceLast: "+(hb.getTimestamp()-previous.getTimestamp()));
            System.out.println("inTransit: "+(hb.getReceivedTimestamp()-hb.getTimestamp()));
          }
        } else {
          System.out.println("initial contact with: "+hb.getName());
        }
      }
      catch (InterruptedException e)
      {
        running = false;
        break;
      }
      Thread.yield();
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
