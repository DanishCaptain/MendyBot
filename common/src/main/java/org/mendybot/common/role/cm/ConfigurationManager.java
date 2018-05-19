package org.mendybot.common.role.cm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.ApplicationRole;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.archive.Manifest;
import org.mendybot.common.role.archive.ManifestEntry;

public class ConfigurationManager extends ApplicationRole implements Runnable
{
  private static final Logger LOG = Logger.getInstance(ConfigurationManager.class);
  public static final String ID = "CM";
  private Thread t = new Thread(this);
  //private File workDir;
  private MasterManager master;
  private FileManager fileManager;
  private boolean running;
  private List<String> namesAllowed;

  public ConfigurationManager(ApplicationModel model) {
    super(model, ID);
    boolean isCMDisable = Boolean.parseBoolean(model.getProperty(ID+".Disable", "false"));
    if (!isCMDisable) {
      boolean isMaster = Boolean.parseBoolean(model.getProperty(ID+".IsMaster", "false"));
      if (isMaster) {
        master = new LocalMasterManager(model);
      } else {
        master = new RemoteMasterManager(model);
      }
    }
//    File myDir = new File(model.getWorkingOptDir(), model.getName());
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
    if (master != null) 
    {
      master.init();
    }
    fileManager = (FileManager) getModel().lookupApplicationModel(FileManager.ID);
    if (fileManager != null)
    {
      fileManager.initContext(ConfigurationManager.ID);
    }
    namesAllowed = getModel().getProperties(ID+".jars-allowed");
 }

  @Override
  public void start() throws ExecuteException
  {
    LOG.logInfo("start", "called");
    if (master != null) 
    {
      master.start();
    }
    t.start();
  }

  @Override
  public void stop()
  {
    LOG.logInfo("stop", "called");
    if (master != null) 
    {
      master.stop();
    }
  }

  @Override
  public void run()
  {
    running = master != null;
    while(running) {
      Map<String, Manifest> vSet = fileManager.getSets(ConfigurationManager.ID);
      LOG.logInfo("run", "versions: " + vSet);
      try
      {
        System.out.println("allowed: "+namesAllowed);
        Map<String, Manifest> masterSet = master.getSets(namesAllowed);
        
        
        boolean areSetsConsistent = vSet.toString().equals(masterSet.toString());
        if (!areSetsConsistent)
        {
          LOG.logInfo("run", "master versions: " + masterSet);
          LOG.logInfo("run", "consistent: " + areSetsConsistent);
          Map<String, Manifest> deltaNew = filterMissing(masterSet, vSet);
          LOG.logInfo("run", "need to grab: "+ConfigurationManager.ID+"<-"+deltaNew);
          master.requestAdd(ConfigurationManager.ID, fileManager, deltaNew);
          Map<String, Manifest> deltaOld = filterExtra(masterSet, vSet);
          LOG.logInfo("run", "need to remove: "+ConfigurationManager.ID+"<-"+deltaOld);
          master.requestRemove(ConfigurationManager.ID, fileManager, deltaOld);
        }
      }
      catch (ExecuteException e)
      {
        LOG.logSevere("run", e);
      }
      try
      {
//        Thread.sleep(5 * 60 * 1000);
        Thread.sleep(60 * 1000);
      }
      catch (InterruptedException e)
      {
        running = false;
      }
    }
  }

  public static Map<String, Manifest> filterMissing(Map<String, Manifest> master, Map<String, Manifest> local)
  {
    Map<String, Manifest> map = new LinkedHashMap<>();
    if (master.size() == 0) {
      return map;
    }
    if (local.size() == 0) {
      map.putAll(master);
      return map;
    }

    for (Entry<String, Manifest> mSet : master.entrySet()) {
      Manifest mMaster = mSet.getValue();
      Manifest mLocal = local.get(mMaster.getName());
      if (mLocal == null) {
        map.put(mMaster.getName(), mMaster);
      } else {
        Manifest mNew = new Manifest(mMaster.getName());
        for (Entry<String, ManifestEntry> meSet : mMaster.getEntries().entrySet()) {
          ManifestEntry meMaster = meSet.getValue();
          ManifestEntry meLocal = mLocal.getEntries().get(meMaster.getName());
          if (meLocal == null) {
            mNew.add(meMaster);
          } else {
            if (meMaster.getLastModified() != meLocal.getLastModified()) {
              mNew.add(meMaster);
            }
          }
        }
        if (mNew.getEntries().size() > 0) {
          map.put(mNew.getName(), mNew);
        }
      }
    }
    return map;
  }
  
  public static Map<String, Manifest> filterExtra(Map<String, Manifest> master, Map<String, Manifest> local)
  {
    Map<String, Manifest> map = new LinkedHashMap<>();
    if (local.size() == 0) {
      return map;
    }
    if (master.size() == 0) {
      map.putAll(local);
      return map;
    }

    for (Entry<String, Manifest> mSet : local.entrySet()) {
      Manifest mMaster = mSet.getValue();
      Manifest mLocal = master.get(mMaster.getName());
      if (mLocal == null) {
        map.put(mMaster.getName(), mMaster);
      } else {
        Manifest mNew = new Manifest(mMaster.getName());
        for (Entry<String, ManifestEntry> meSet : mMaster.getEntries().entrySet()) {
          ManifestEntry meMaster = meSet.getValue();
          ManifestEntry meLocal = mLocal.getEntries().get(meMaster.getName());
          if (meLocal == null) {
            mNew.add(meMaster);
          } else {
            if (meMaster.getLastModified() != meLocal.getLastModified()) {
              mNew.add(meMaster);
            }
          }
        }
        if (mNew.getEntries().size() > 0) {
          map.put(mNew.getName(), mNew);
        }
      }
    }
    return map;
  }
  
}
