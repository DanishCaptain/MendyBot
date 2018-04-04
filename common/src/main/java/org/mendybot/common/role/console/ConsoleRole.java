package org.mendybot.common.role.console;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.MendyAppRole;

public abstract class ConsoleRole implements MendyAppRole
{
  private static final Logger LOG = Logger.getInstance(ConsoleRole.class);
  private ApplicationModel model;

  public ConsoleRole(ApplicationModel model) {
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
