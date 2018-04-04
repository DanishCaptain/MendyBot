package org.mendybot.common.role.system.cluster;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.StringTokenizer;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

public class MulticastReceiver implements Runnable
{
  private static final Logger LOG = Logger.getInstance(MulticastReceiver.class);
  private Thread t = new Thread(this);
  private boolean running;
  private MulticastSocket socket;
  private MulticastManager manager;
  private int port;
  private InetAddress group;

  public MulticastReceiver(MulticastManager manager)
  {
    this.manager = manager;
    t.setDaemon(true);
    t.setName(getClass().getSimpleName());
  }

  public void init() throws ExecuteException
  {
    try
    {
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
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
        socket.receive(packet);
        StringTokenizer st = new StringTokenizer(new String(packet.getData()), "|");
        Heartbeat hb = new Heartbeat(st.nextToken(), System.currentTimeMillis());
        hb.setSequence(Integer.parseInt(st.nextToken()));
        hb.setTimestamp(Long.parseLong(st.nextToken()));
        hb.setStatus(st.nextToken());
        hb.setClusterStatus(st.nextToken());
        manager.processHeartbeat(hb);
      }
      catch (IOException e)
      {
        // TODO what to do
        
        LOG.logWarning("run", e);
      }
      Thread.yield();
    }
  }

}
