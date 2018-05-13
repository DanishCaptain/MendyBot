package org.mendybot.common.role.console.lightweight;

// -----------------------------------------------------------------------------------------------------------------

/**
 * Internal helper class for refreshing the frame buffer display and/or JPanel.
 */
public class AutoUpdateThread extends Thread
{
  static final int FPS = 40; // Max. update rate
  private long deviceInfo;
  private FrameBuffer fb;

  public AutoUpdateThread(FrameBuffer fb, String deviceName, long deviceInfo)
  {
    this.fb = fb;
    setDaemon(true);
    setName("FB " + deviceName + " update");
    this.deviceInfo = deviceInfo;
  }

  @Override
  public void run()
  {
    final int SLEEP_TIME = 1000 / FPS;

    // System.err.println("Run Update");
    while (deviceInfo != 0)
    {

      fb.updateScreen();

      try
      {
        sleep(SLEEP_TIME);
      }
      catch (InterruptedException e)
      {
        break;
      }

    } // while
  }

} // class UpdateThread
