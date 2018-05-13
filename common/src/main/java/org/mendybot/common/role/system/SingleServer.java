package org.mendybot.common.role.system;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

public class SingleServer extends SystemRole
{
  private static final Logger LOG = Logger.getInstance(SingleServer.class);

  public SingleServer(ApplicationModel model)
  {
    super(model);
  }

  @Override
  public void init() throws ExecuteException
  {
    LOG.logDebug("init", "call");
  }

  @Override
  public void start() throws ExecuteException
  {
  }

  @Override
  public void stop()
  {
  }
  
}
