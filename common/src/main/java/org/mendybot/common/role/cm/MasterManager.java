
package org.mendybot.common.role.cm;

import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.Manifest;

public abstract class MasterManager
{
  private static final Logger LOG = Logger.getInstance(MasterManager.class);
  private ApplicationModel model;

  public MasterManager(ApplicationModel model)
  {
    this.model = model;
  }
  
  protected ApplicationModel getModel()
  {
    return model;
  }

  public abstract void init() throws ExecuteException;

  public abstract void start() throws ExecuteException;

  public abstract void stop();

  public abstract List<Manifest> getSets() throws ExecuteException;

}
