package org.mendybot.common.role.archive;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
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

  public List<Manifest> getSets(String id)
  {
    ArrayList<Manifest> list = new ArrayList<>();
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
        list.add(m);
      }
    }
    return list;
  }
  
}
