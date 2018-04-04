package org.mendybot.common.application.model.widget;

import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

public abstract class Widget
{
  private ApplicationModel model;
  private String name;

  public Widget(ApplicationModel model, String name)
  {
    this.model = model;
    this.name = name;
  }
  
  public ApplicationModel getModel()
  {
    return model;
  }
  
  public String getName()
  {
    return name;
  }

  public abstract void init() throws ExecuteException;

  public abstract void start() throws ExecuteException;

  public abstract void stop();

}
