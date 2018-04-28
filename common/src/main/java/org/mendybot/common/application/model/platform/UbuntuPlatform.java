package org.mendybot.common.application.model.platform;

import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;

public class UbuntuPlatform extends ApplicationPlatform
{

  public UbuntuPlatform(ApplicationModel model)
  {
    super(model);
  }

  @Override
  public void enableNetworkInterface(String ipAddress, int netmaskBits, String device) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address add 10.1.2.1/24 dev enp0s9");
    int rc = tool.call();
    if (rc != 0) {
      System.out.println(rc);
      System.out.println(tool.getOutput());
      System.out.println(tool.getErrors());
    }
  }

  @Override
  public void disableNetworkInterface(String ipAddress, int netmaskBits, String device) throws ExecuteException
  {
    CommandTool tool = new CommandTool("sudo ip address delete 10.1.2.1/24 dev enp0s9");
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
    CommandTool tool = new CommandTool("sudo ip address show dev enp0s9");
    int rc = tool.call();
    if (rc != 0) {
      System.out.println(rc);
      System.out.println(tool.getOutput());
      System.out.println(tool.getErrors());
    }
  }

}
