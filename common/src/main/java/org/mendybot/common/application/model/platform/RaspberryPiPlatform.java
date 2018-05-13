package org.mendybot.common.application.model.platform;

import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

public class RaspberryPiPlatform extends ApplicationPlatform
{
  private static final String DEVICE = "eth0";
  private static final String IP = "10.1.2.1";
  private static final String MASK = "24";

  public RaspberryPiPlatform(ApplicationModel model)
  {
    super(model);
  }

  @Override
  public void enableNetworkInterface(String ipAddress, int netmaskBits) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address add "+IP+"/"+MASK+" dev "+DEVICE);
    int rc = tool.call();
    if (rc != 0) {
      System.out.println(rc);
      System.out.println(tool.getOutput());
      System.out.println(tool.getErrors());
    }
  }

  @Override
  public void disableNetworkInterface(String ipAddress, int netmaskBits) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address delete "+IP+"/"+MASK+" dev "+DEVICE);
    int rc = tool.call();
    if (rc != 0) {
      System.out.println(rc);
      System.out.println(tool.getOutput());
      System.out.println(tool.getErrors());
    }
  }

  @Override
  public void checkNetworkInterface(String device) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address show dev "+DEVICE);
    int rc = tool.call();
    if (rc != 0) {
      System.out.println(rc);
      System.out.println(tool.getOutput());
      System.out.println(tool.getErrors());
    }
  }

}
