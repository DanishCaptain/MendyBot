package org.mendybot.common.application.model.platform;

import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

public abstract class ApplicationPlatform
{
  private ApplicationModel model;

  public ApplicationPlatform(ApplicationModel model)
  {
    this.model = model;
  }
  
  public ApplicationModel getModel()
  {
    return model;
  }

  public abstract void enableNetworkInterface(String ipAddress, int netmaskBits, String device) throws ExecuteException;

  public abstract void disableNetworkInterface(String ipAddress, int netmaskBits, String device) throws ExecuteException;

  public abstract void checkNetworkInterface(String device) throws ExecuteException;

}
