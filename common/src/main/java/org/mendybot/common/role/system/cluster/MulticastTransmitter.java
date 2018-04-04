package org.mendybot.common.role.system.cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

public class MulticastTransmitter implements Runnable
{
  private static final Logger LOG = Logger.getInstance(MulticastTransmitter.class);
  private Thread t = new Thread(this);
  private boolean running;
  private MulticastSocket socket;
  private MulticastManager manager;
  private int port;
  private InetAddress group;
  private int keepalive;

  public MulticastTransmitter(MulticastManager manager)
  {
    this.manager = manager;
    t.setDaemon(true);
    t.setName(getClass().getSimpleName());
  }

  public void init() throws ExecuteException
  {
    try
    {
      keepalive = 1000 * Integer.parseInt(manager.getCluster().getModel().getProperty("keepalive", "1"));
      
      port = Integer.parseInt(manager.getCluster().getModel().getProperty("mcast-port", "4446"));
      socket = new MulticastSocket(port);
      group = InetAddress.getByName(manager.getCluster().getModel().getProperty("mcast-group", "224.0.0.0"));
      socket = new MulticastSocket(port);
      socket.joinGroup(group);
    }
    catch (IOException e)
    {
      throw new ExecuteException(e);
    }
  }

  public void start() throws ExecuteException
  {
    if (!running)
    {
      t.start();
    }
  }

  public void stop()
  {
    try
    {
      socket.leaveGroup(group);
      socket.close();
    }
    catch (IOException e)
    {
      LOG.logWarning("stop", e);
    }
  }

  @Override
  public void run()
  {
    running = true;
    while (running)
    {
      try
      {
        Heartbeat hb = manager.getCluster().createHeartbeat();
        byte[] buf = hb.toString().getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
        socket.send(packet);
      }
      catch (IOException e)
      {
        // TODO what to do
        
        
        LOG.logWarning("run", e);
      }
      try
      {
        Thread.sleep(keepalive);
      }
      catch (InterruptedException e)
      {
        running = false;
        break;
      }
    }
  }

}
