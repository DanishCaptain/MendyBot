package org.mendybot.common.role.console.clip;

import org.mendybot.common.role.console.ConsoleRole;

public abstract class Clip
{
  private ConsoleRole console;
  private String name;
  private String widgetName;

  public Clip(ConsoleRole console, String name, String widgetName)
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

  public abstract void play();
}
