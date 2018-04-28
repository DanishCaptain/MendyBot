package org.mendybot.common.role.console.lightweight;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;

public class LightWeightConsole extends ConsoleRole implements Runnable
{
  private static final Logger LOG = Logger.getInstance(LightWeightConsole.class);
  private Thread t = new Thread(this);
  private boolean running;

  public LightWeightConsole(ApplicationModel model)
  {
    super(model);
  }


  @Override
  public void init() throws ExecuteException
  {
    t.setName(getClass().getSimpleName());
  }

  @Override
  public void start() throws ExecuteException
  {
    t.start();
  }

  @Override
  public void stop()
  {
    running = false;
    t.interrupt();
  }


  @Override
  public void run()
  {
    running = true;
    while(running) {
      try
      {
        Thread.sleep(10000);
      }
      catch (InterruptedException e)
      {
        running = false;
      }
    }
  }

}
