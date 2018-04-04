package org.mendybot.common.role.console;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.system.SingleServer;

public class HeadlessConsole extends ConsoleRole
{
  private static final Logger LOG = Logger.getInstance(SingleServer.class);

  public HeadlessConsole(ApplicationModel model)
  {
    super(model);
  }


  @Override
  public void init() throws ExecuteException
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void start() throws ExecuteException
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void stop()
  {
    // TODO Auto-generated method stub
    
  }

}
