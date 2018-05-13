package org.mendybot.common.role.system;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.MendyBotRole;

public abstract class SystemRole implements MendyBotRole
{
  private static final Logger LOG = Logger.getInstance(SystemRole.class);
  private ApplicationModel model;

  public SystemRole(ApplicationModel model) {
    LOG.logDebug("()", "call");
    this.model = model;
  }
  
  public ApplicationModel getModel()
  {
    return model;
  }

  public abstract void init() throws ExecuteException;

  public abstract void start() throws ExecuteException;

  public abstract void stop();
  
  public String getAppName()
  {
    return model.getName();
  }

}
