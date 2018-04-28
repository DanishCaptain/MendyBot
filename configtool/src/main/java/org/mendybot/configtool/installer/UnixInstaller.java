package org.mendybot.configtool.installer;

public abstract class UnixInstaller extends Installer
{

  @Override
  public final void initInstaller(boolean testMode)
  {
    initUnixInstaller(testMode);
  }

  @Override
  public final void startInstaller()
  {
    startUnixInstaller();
  }

  public abstract void initUnixInstaller(boolean testMode);
  public abstract void startUnixInstaller();
}
