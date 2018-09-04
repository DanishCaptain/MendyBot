package org.mendybot.common.role.console.glyph.time;

import java.awt.image.BufferedImage;

import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;

public class UnitTestConsoleRole extends ConsoleRole
{
  private BufferedImage img;

  public UnitTestConsoleRole(ApplicationModel model)
  {
    super(model);
    img = new BufferedImage(400,400, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  protected void initConsole() throws ExecuteException
  {
    System.out.println();
    // TODO Auto-generated method stub

  }

  @Override
  public void start() throws ExecuteException
  {
    System.out.println();
    // TODO Auto-generated method stub

  }

  @Override
  public void stop()
  {
    System.out.println();
    // TODO Auto-generated method stub

  }

  @Override
  protected void repaintLocal()
  {
    System.out.println();
    // TODO Auto-generated method stub

  }

  @Override
  protected BufferedImage getImage()
  {
    return img;
  }

}
