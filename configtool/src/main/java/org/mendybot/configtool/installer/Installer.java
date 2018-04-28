package org.mendybot.configtool.installer;

public abstract class Installer
{
  private boolean testMode;
  public Installer()
  {
    testMode = System.getProperty("TEST-MODE") != null;
  }

  public final void init() {
    initInstaller(testMode);
  }
  
  public abstract void initInstaller(boolean testMode);
  
  public final void start() {
    startInstaller();
  }
  
  public abstract void startInstaller();

}
