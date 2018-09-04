package org.mendybot.common.application;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is the base class for all Applications
 * 
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public final class Application
{
  private static final Logger LOG = Logger.getInstance(Application.class);
  private static final int NORMAL_EXIT = 0;
  private static final int ERROR_EXIT = 1;
  private static final int RESTART_EXIT = 5;
  private final String name;
  private ApplicationModel model;

  public Application(String name) throws ExecuteException
  {
    this.name = name;
    model = new ApplicationModel(this);
  }

  public String getName()
  {
    return name;
  }

  /**
   * This method returns the model
   * 
   * @return DomainModel
   */
  public final ApplicationModel getModel()
  {
    return model;
  }

  protected final void init() throws ExecuteException
  {
    LOG.logDebug("init", "init called");
//    SystemModel[] mList = getSystemModels(model);
//    SystemService[] sList = getSystemServices();
    model.init();
  }

//  protected abstract SystemModel[] getSystemModels(ApplicationModel model) throws ExecuteException;

//  protected abstract SystemService[] getSystemServices() throws ExecuteException;

  protected final void start() throws ExecuteException
  {
    LOG.logDebug("start",  "called");
    model.start();
  }

  protected final void stop()
  {
    LOG.logDebug("stop",  "called");
    model.stop();
  }

  public void applicationStop()
  {
    LOG.logDebug("applicationStop",  "called");
    stop();
    performNormalShutdown();
  }
  
  public void applicationRestart()
  {
    LOG.logDebug("applicationRestart",  "called");
    stop();
    performRestartableShutdown();
  }
  
  public static void performNormalShutdown()
  {
      System.exit(NORMAL_EXIT);
  }

  protected static void performErrorShutdown()
  {
      System.exit(ERROR_EXIT);
  }

  public static void performRestartableShutdown()
  {
      System.exit(RESTART_EXIT);
  }
  
  public static void main(String[] args) {
    if (args.length < 1) {
      LOG.logSevere("main", "need application name");
      try
      {
        System.setProperty("TEST-MODE", "");
        Application a = new Application("AZ-Home-1");
        a.init();
        a.start();
      }
      catch (ExecuteException e)
      {
        LOG.logSevere("main",e);
        System.exit(ERROR_EXIT);
      }
      Thread.yield();
      WeatherFucker f = new WeatherFucker();
    } else {
      try
      {
        Application a = new Application(args[0]);
        a.init();
        a.start();
      }
      catch (ExecuteException e)
      {
        LOG.logSevere("main",e);
        System.exit(ERROR_EXIT);
      }
    }
  }

}
