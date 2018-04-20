package org.mendybot.common.role.console.lightweight;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;

public class LightWeightConsole extends ConsoleRole
{
  private static final Logger LOG = Logger.getInstance(LightWeightConsole.class);

  public LightWeightConsole(ApplicationModel model)
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
