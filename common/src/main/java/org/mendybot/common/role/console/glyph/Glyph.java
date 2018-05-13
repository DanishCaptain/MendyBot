package org.mendybot.common.role.console.glyph;

import java.awt.Graphics;

import org.mendybot.common.role.console.ConsoleRole;

public abstract class Glyph
{
  private ConsoleRole console;
  private String name;
  private String widgetName;

  public Glyph(ConsoleRole console, String name, String widgetName)
  {
    this.console = console;
    this.name = name;
    this.widgetName = widgetName;
  }
  
  protected ConsoleRole getConsole()
  {
    return console;
  }
  
  public String getName()
  {
    return name;
  }

  public String getWIdgetName()
  {
    return widgetName;
  }

  public abstract void draw(Graphics g);
}
