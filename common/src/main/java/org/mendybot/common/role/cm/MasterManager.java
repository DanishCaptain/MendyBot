
package org.mendybot.common.role.cm;

import java.util.List;
import java.util.Map;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.archive.Manifest;

public abstract class MasterManager
{
  private static final Logger LOG = Logger.getInstance(MasterManager.class);
  private ApplicationModel model;

  public MasterManager(ApplicationModel model)
  {
    LOG.logDebug("()", "call");
    this.model = model;
  }
  
  protected ApplicationModel getModel()
  {
    return model;
  }

  public abstract void init() throws ExecuteException;

  public abstract void start() throws ExecuteException;

  public abstract void stop();

  public abstract Map<String, Manifest> getSets() throws ExecuteException;

  public abstract Map<String, Manifest> getSets(List<String> namesAllowed) throws ExecuteException;

  public abstract void requestAdd(String id, FileManager fm, Map<String, Manifest> delta) throws ExecuteException;

  public abstract void requestRemove(String id, FileManager fm, Map<String, Manifest> delta) throws ExecuteException;

}
