package org.mendybot.common.role.archive;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.platform.CommandTool;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.ApplicationRole;

public class FileManager extends ApplicationRole
{
  private static final Logger LOG = Logger.getInstance(FileManager.class);
  private HashMap<String, File> contextM = new HashMap<>();
  public static final String ID = "FM";
  private File workDir;

  public FileManager(ApplicationModel model) {
    super(model, ID);
    workDir = new File(model.getWorkingOptDir(), "archive");
    if (!workDir.exists()) {
      workDir.mkdirs();
    }
  }

  @Override
  public void init() throws ExecuteException
  {
    LOG.logInfo("init", "called");
  }

  @Override
  public void start() throws ExecuteException
  {
    LOG.logInfo("start", "called");
  }

  @Override
  public void stop()
  {
    LOG.logInfo("stop", "called");
  }

  public void initContext(String id)
  {
    LOG.logInfo("initContext", "id: "+id);
    File c = new File(workDir, id);
    LOG.logInfo("initContext", "file: "+c.getPath());
    LOG.logInfo("initContext", "exists: "+c.exists());
    if (!c.exists())
    {
      LOG.logInfo("initContext", "creating: "+c.getPath());
      c.mkdir();
    }
    contextM.put(id, c);
  }

  public Map<String, Manifest> getSets(String id)
  {
    Map<String, Manifest> map = new LinkedHashMap<>();
    File f = contextM.get(id);
    if (f != null && f.exists()) {
      File[] dirs = f.listFiles();
      for (File d : dirs) {
        Manifest m = new Manifest(d.getName());
        File[] entries = d.listFiles();
        for (File e : entries) {
          ManifestEntry me = new ManifestEntry(e.getName());
          me.setLastModified(e.lastModified());
          m.add(me);
        }
        map.put(m.getName(), m);
      }
    }
    return map;
  }
  
  public Map<String, Manifest> getSets(List<String> namesAllowed, String id)
  {
    Map<String, Manifest> map = new LinkedHashMap<>();
    File f = contextM.get(id);
    if (f != null && f.exists()) {
      File[] dirs = f.listFiles();
      for (File d : dirs) {
        Manifest m = new Manifest(d.getName());
        File[] entries = d.listFiles();
        for (File e : entries) {
          if (namesAllowed.contains(e.getName())) {
            ManifestEntry me = new ManifestEntry(e.getName());
            me.setLastModified(e.lastModified());
            m.add(me);
          }
        }
        if (m.getEntries().size() > 0) 
        {
          map.put(m.getName(), m);
        }
      }
    }
    return map;
  }

  public File lookupFile(String id, String manifestName, String manefestEntryName)
  {
    LOG.logDebug("lookupFile", "call");
    File baseDir = contextM.get(id);
    File mDir = new File(baseDir, manifestName);
    File mEntry = new File(mDir, manefestEntryName);
    return mEntry;
  }

  public void copyTo(String id, String manifestName, String manefestEntryName, long lastModified, File f) throws ExecuteException
  {
    File baseDir = contextM.get(id);
    File mDir = new File(baseDir, manifestName);
    if (!mDir.exists()) {
      mDir.mkdirs();
    }
    File mEntry = new File(mDir, manefestEntryName);
    
    LOG.logInfo("copyTo", mEntry.getAbsolutePath() +":"+mEntry.exists());
    
    File temp = new File(mDir, "temp_"+manefestEntryName);
    CommandTool ct = new CommandTool("cp "+f.getAbsolutePath()+" "+temp.getAbsolutePath());
    int result = ct.call();
    temp.renameTo(mEntry);
    mEntry.setLastModified(lastModified);
    LOG.logInfo("copyTo", result+"::"+mEntry.exists());
    
  }
  
  public void remove(String id, String manifestName, String manefestEntryName) throws ExecuteException
  {
    File baseDir = contextM.get(id);
    File mDir = new File(baseDir, manifestName);
    if (!mDir.exists()) {
      mDir.mkdirs();
    }
    File mEntry = new File(mDir, manefestEntryName);
    
    LOG.logInfo("remove", mEntry.getAbsolutePath() +":"+mEntry.exists());
    
    CommandTool ct = new CommandTool("rm "+mEntry.getAbsolutePath());
    int result = ct.call();
    if (mDir.listFiles().length == 0) {
      mDir.delete();
    }
    LOG.logInfo("fileRemoved", result+"::"+mEntry.exists());
    
  }
  
}
