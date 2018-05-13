package org.mendybot.common.role.console.lightweight;

import java.awt.image.BufferedImage;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;

public class LightWeightConsole extends ConsoleRole
{
  private static final Logger LOG = Logger.getInstance(LightWeightConsole.class);
  private FrameBuffer fb;

  public LightWeightConsole(ApplicationModel model)
  {
    super(model);
  }


  @Override
  protected void initConsole() throws ExecuteException
  {
    LOG.logDebug("initConsole", "call");
    fb = new FrameBuffer("/dev/fb0");
  }

  @Override
  public void start() throws ExecuteException
  {
  }

  @Override
  public void stop()
  {
  }


  @Override
  protected void repaintLocal()
  {
//    fb.repaint();
  }


  @Override
  protected BufferedImage getImage()
  {
    return fb.getImage();
  }


}
