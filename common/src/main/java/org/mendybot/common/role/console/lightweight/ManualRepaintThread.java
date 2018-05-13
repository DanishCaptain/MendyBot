package org.mendybot.common.role.console.lightweight;

/**
 * Internal helper class for refreshing the frame buffer display and/or JPanel.
 */
public class ManualRepaintThread extends Thread
{
  private long deviceInfo;
  private FrameBuffer fb;

  ManualRepaintThread(FrameBuffer fb, String deviceName, long deviceInfo)
  {
    this.fb = fb;
    setDaemon(true);
    setName("FB " + deviceName + " repaint");
    this.deviceInfo = deviceInfo;
  }

  @Override
  public void run()
  {
    final int SLEEP_TIME = 1000 / AutoUpdateThread.FPS;

    try
    {
      System.err.println("Run Repaint");
      while (deviceInfo != 0)
      {

        fb.getRepaintQueue().take();
        fb.updateScreen();

        sleep(SLEEP_TIME);

      } // while
    }
    catch (InterruptedException e)
    {
    }

  } // class UpdateThread
}
