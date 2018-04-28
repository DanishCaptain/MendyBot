package org.mendybot.common.application.model.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Watcher implements Runnable
{
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
