package org.mendybot.common.role.console.headless;

import java.awt.image.BufferedImage;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;

public class HeadlessConsole extends ConsoleRole
{
  private static final Logger LOG = Logger.getInstance(HeadlessConsole.class);

  public HeadlessConsole(ApplicationModel model)
  {
    super(model);
  }


  @Override
  protected void initConsole() throws ExecuteException
  {
    LOG.logDebug("initConsole", "call");
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


  @Override
  protected void repaintLocal()
  {
    // TODO Auto-generated method stub
    
  }


  @Override
  protected BufferedImage getImage()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
