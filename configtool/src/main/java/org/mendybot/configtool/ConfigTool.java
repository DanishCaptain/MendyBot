package org.mendybot.configtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.mendybot.configtool.installer.Installer;
import org.mendybot.configtool.installer.UbuntuInstaller;

public class ConfigTool
{
  public ConfigTool()
  {
    File etc = new File("/etc");
    File osFile = new File(etc, "os-release");
    Properties p = new Properties();
    try
    {
      InputStream is = new FileInputStream(osFile);
      p.load(is);
      is.close();
      
      Installer installer;
      String osName = p.getProperty("PRETTY_NAME").replaceAll("\"", "");
      if (osName.startsWith("Ubuntu ")) {
        installer = new UbuntuInstaller();
      } else {
        throw new RuntimeException("missing installer for os");
      }
      installer.init();
      installer.start();
    }
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.setProperty("TEST-MODE", "");
    new ConfigTool();
  }
}
