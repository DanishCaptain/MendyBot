package org.mendybot.common.application.model.platform;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

public class OsXPlatform extends ApplicationPlatform
{
  private Logger LOG = Logger.getInstance(OsXPlatform.class);
  private static final String DEVICE = "enp2s0f1";
  private static final String IP = "10.1.2.1";
  private static final String MASK = "24";

  public OsXPlatform(ApplicationModel model)
  {
    super(model);
  }

  @Override
  public void enableNetworkInterface(String ipAddress, int netmaskBits) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address add "+IP+"/"+MASK+" dev "+DEVICE);
    int rc = tool.call();
    if (rc != 0) {
      LOG.logDebug("enableNetworkInterface", rc+"");
      LOG.logDebug("enableNetworkInterface", tool.getOutput()+"");
      LOG.logDebug("enableNetworkInterface", tool.getErrors()+"");
    }
  }

  @Override
  public void disableNetworkInterface(String ipAddress, int netmaskBits) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address delete "+IP+"/"+MASK+" dev "+DEVICE);
    int rc = tool.call();
    if (rc != 0) {
      LOG.logDebug("disableNetworkInterface", rc+"");
      LOG.logDebug("disableNetworkInterface", tool.getOutput()+"");
      LOG.logDebug("disableNetworkInterface", tool.getErrors()+"");
    }
  }

  @Override
  public void checkNetworkInterface(String device) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address show dev "+DEVICE);
    int rc = tool.call();
    if (rc != 0) {
      LOG.logDebug("checkNetworkInterface", rc+"");
      LOG.logDebug("checkNetworkInterface", tool.getOutput()+"");
      LOG.logDebug("checkNetworkInterface", tool.getErrors()+"");
    }
  }

}
