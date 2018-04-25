package org.mendybot.common.role.cm;

import java.io.File;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.ApplicationRole;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.archive.Manifest;

public class ConfigurationManager extends ApplicationRole implements Runnable
{
  private static final Logger LOG = Logger.getInstance(ConfigurationManager.class);
  public static final String ID = "CM";
  private Thread t = new Thread(this);
  //private File workDir;
  private MasterManager master;
  private FileManager fileManager;
  private boolean running;

  public ConfigurationManager(ApplicationModel model) {
    super(model, ID);
    boolean isMaster = Boolean.parseBoolean(model.getProperty(ID+".IsMaster", "false"));
    if (isMaster) {
      master = new LocalMasterManager(model);
    } else {
      master = new RemoteMasterManager(model);
    }
    File myDir = new File(model.getWorkingOptDir(), model.getName());
//    workDir = new File(myDir, "release");
//    if (!workDir.exists()) {
//      workDir.mkdirs();
//    }
  }

  @Override
  public void init() throws ExecuteException
  {
    LOG.logInfo("init", "called");
    t.setName(getClass().getSimpleName());
    t.setDaemon(true);
    master.init();
    fileManager = getModel().lookupApplicationModel(FileManager.ID);
    if (fileManager != null)
    {
      fileManager.initContext(ConfigurationManager.ID);
    }
 }

  @Override
  public void start() throws ExecuteException
  {
    LOG.logInfo("start", "called");
    master.start();
    t.start();
  }

  @Override
  public void stop()
  {
    LOG.logInfo("stop", "called");
    master.stop();
  }

  @Override
  public void run()
  {
    running = true;
    while(running) {
      List<Manifest> vSet = fileManager.getSets(ConfigurationManager.ID);
      LOG.logInfo("run", "versions: " + vSet);
      try
      {
        List<Manifest> masterSet = master.getSets();
        LOG.logInfo("run", "master versions: " + masterSet);
        boolean areSetsConsistent = vSet.toString().equals(masterSet.toString());
        LOG.logInfo("run", "consistent: " + areSetsConsistent);
        if (!areSetsConsistent)
        {
          //x
        }
      }
      catch (ExecuteException e)
      {
        LOG.logSevere("run", e);
      }
      try
      {
        Thread.sleep(5 * 60 * 1000);
//        Thread.sleep(60 * 1000);
      }
      catch (InterruptedException e)
      {
        running = false;
      }
    }
  }
  
}
