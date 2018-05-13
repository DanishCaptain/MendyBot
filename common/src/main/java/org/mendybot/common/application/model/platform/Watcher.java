package org.mendybot.common.application.model.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.role.cm.MasterManager;

public class Watcher implements Runnable
{
  private static final Logger LOG = Logger.getInstance(Watcher.class);
  private Thread t = new Thread(this);
  private List<String> list;
  private BufferedReader br;

  public Watcher(List<String> list, InputStream is)
  {
    this.list = list;
    this.br = new BufferedReader(new InputStreamReader(is));
    t.start();
  }

  @Override
  public void run()
  {
    String line;
    try
    {
      while((line = br.readLine()) != null)
      {
        list.add(line);
      }
    }
    catch (IOException e)
    {
      LOG.logDebug("run", e);
    }
  }

}
