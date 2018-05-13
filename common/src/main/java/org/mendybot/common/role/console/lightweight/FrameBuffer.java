/*
 *	This file is the JNI Java part of a Raspberry Pi FrameBuffer project.
 *
 *	Created 2013 by Thomas Welsch (ttww@gmx.de).
 *
 *	Do whatever you want to do with it :-)
 *
 **/

package org.mendybot.common.role.console.lightweight;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This class is the Java front end for a simple to use FrameBuffer driver.
 * Simple draw in the BufferedImage and all changes are transfered to the
 * FrameBuffer device.
 * <p>
 * For testing purpose a dummy device is supported (via the devicename
 * "dummy_160x128" instead of "/dev/fb1").<p< The Java process needs write
 * access to the frame buffer device file.
 * <p/>
 * It's used to drive small bit mapped screens connected via SPI, see
 * http://www.sainsmart.com/blog/ada/
 * <p/>
 * <p/>
 * My Linux kernel config for SPI display was:
 * 
 * <pre>
 * CONFIG_FB_ST7735=y
 * CONFIG_FB_ST7735_PANEL_TYPE_RED_TAB=y
 * CONFIG_FB_ST7735_RGB_ORDER_REVERSED=y
 * CONFIG_FB_ST7735_MAP=y
 * CONFIG_FB_ST7735_MAP_RST_GPIO=25
 * CONFIG_FB_ST7735_MAP_DC_GPIO=24
 * CONFIG_FB_ST7735_MAP_SPI_BUS_NUM=0
 * CONFIG_FB_ST7735_MAP_SPI_BUS_CS=0
 * CONFIG_FB_ST7735_MAP_SPI_BUS_SPEED=16000000
 * CONFIG_FB_ST7735_MAP_SPI_BUS_MODE=0
 * </pre>
 * 
 * CONFIG_FB_ST7735_MAP_SPI_BUS_SPEED gives faster updates :-)
 * <p/>
 * If you get the wrong colors, try the CONFIG_FB_ST7735_RGB_ORDER_REVERSED
 * option !
 */
public class FrameBuffer
{


  private String deviceName;

  private long deviceInfo; // Private data from JNI C

  private int width;
  private int height;
  private int bits;

  private BufferedImage img;
  private int[] imgBuffer;

  private ManualRepaintThread mrt;

  // -----------------------------------------------------------------------------------------------------------------

  private native long openDevice(String device);

  private native void closeDevice(long di);

  private native int getDeviceWidth(long di);

  private native int getDeviceHeight(long di);

  private native int getDeviceBitsPerPixel(long di);

  private native boolean updateDeviceBuffer(long di, int[] buffer);

  static
  {
    System.loadLibrary("MB_FrameBufferJNI"); // MB_FrameBufferJNI.dll (Windows) or
                                             // MB_FrameBufferJNI.so (Unixes)
  }

  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Open the named frame buffer device and starts the automatic update thread
   * between the internal BufferedImage and the device.
   *
   * @param deviceName
   *          e.g. /dev/fb1 or dummy_320x200
   */
  public FrameBuffer(String deviceName)
  {
    this(deviceName, true);
  }

  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Open the named frame buffer device.
   *
   * @param deviceName
   *          e.g. /dev/fb1 or dummy_320x200
   * @param autoUpdate
   *          if true, starts the automatic update thread between the internal
   *          BufferedImage and the device. If false, you have to call
   *          repaint();
   */
  public FrameBuffer(String deviceName, boolean autoUpdate)
  {

    this.deviceName = deviceName;

    deviceInfo = openDevice(deviceName);

    if (Math.abs(deviceInfo) < 10)
    {
      throw new IllegalArgumentException(
          "Init. for frame buffer " + deviceName + " failed with error code " + deviceInfo);
    }

    this.width = getDeviceWidth(deviceInfo);
    this.height = getDeviceHeight(deviceInfo);

    System.err.println("Open with " + deviceName + " (" + deviceInfo + ")");
    System.err.println("  width   " + getDeviceWidth(deviceInfo));
    System.err.println("  height  " + getDeviceHeight(deviceInfo));
    System.err.println("  bpp     " + getDeviceBitsPerPixel(deviceInfo));

    // We always use ARGB image type.
    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    imgBuffer = ((DataBufferInt) img.getRaster().getDataBuffer()).getBankData()[0];

    if (autoUpdate)
      new AutoUpdateThread(this, deviceName, deviceInfo).start();
    else
    {
      mrt = new ManualRepaintThread(this, deviceName, deviceInfo);
      mrt.start();
    }
  }

  // -----------------------------------------------------------------------------------------------------------------

  private ScreenPanel screenPanel;

  /**
   * Returns a ScreenPanel (JPanel) which represents the actual frame buffer
   * device.
   *
   * @return ScreenPanel...
   */
  public ScreenPanel getScreenPanel()
  {
    synchronized (deviceName)
    {
      if (screenPanel != null)
        throw new IllegalStateException("Only one screen panel supported");

      screenPanel = new ScreenPanel(this);

      return screenPanel;
    }
  }

  // -----------------------------------------------------------------------------------------------------------------

  // -----------------------------------------------------------------------------------------------------------------

  private ArrayBlockingQueue<Boolean> repaintQueue = new ArrayBlockingQueue<Boolean>(1);
  
  ArrayBlockingQueue<Boolean> getRepaintQueue()
  {
    return repaintQueue;
  }

  /**
   * Request an repaint manually. This method can called at high frequencies. An
   * internal repaint tread is used to avoid exceeding the FPS value.
   */
  public void repaint()
  {
    if (mrt == null)
      throw new IllegalStateException("automatic repaint is active, no need to call this");
    repaintQueue.offer(Boolean.TRUE);
  }

  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Returns the BufferedImage for drawing. Anything your draw here is
   * synchronized to the frame buffer.
   *
   * @return BufferedImage of type ARGB.
   */
  public BufferedImage getScreen()
  {
    return img;
  }

  // -----------------------------------------------------------------------------------------------------------------

  /**
   * Close the device.
   */
  public void close()
  {
    synchronized (deviceName)
    {
      closeDevice(deviceInfo);
      deviceInfo = 0;
      img = null;
      imgBuffer = null;
    }
  }

  // -----------------------------------------------------------------------------------------------------------------

  private long lastUpdate;
  private int updateCount;

  /**
   * Update the screen if no automatic sync is used (see constructor autoUpdate
   * flag). This method is normally called by the autoUpdate thread and is not
   * limited about any frame rate.
   *
   * @return true if the BufferedImage was changed since the last call.
   */
  public boolean updateScreen()
  {

    synchronized (deviceName)
    {
      if (deviceInfo == 0)
        return false;

      boolean ret;
      synchronized (updateLock)
      {

        ret = updateDeviceBuffer(deviceInfo, imgBuffer);

        updateCount++;
        if (lastUpdate == 0)
          lastUpdate = System.currentTimeMillis();
        long now = System.currentTimeMillis();

        long diff = now - lastUpdate;

        if (diff >= 1000)
        {
          //float fps = (1000f / diff) * updateCount;
          // System.err.println("FPS = "+fps);
          updateCount = 0;
          lastUpdate = now;
        }

      }

      if (ret && screenPanel != null)
      {
        screenPanel.repaint();
      }
      return ret;
    } // sync
  }

  // -----------------------------------------------------------------------------------------------------------------

  private Object updateLock = new Object();

  public Object getUpdateLockForSync()
  {
    return updateLock;
  }

  // -----------------------------------------------------------------------------------------------------------------

  public int getWidth()
  {
    return width;
  }

  // -----------------------------------------------------------------------------------------------------------------

  public int getHeight()
  {
    return height;
  }

  public BufferedImage getImage()
  {
    return img;
  }

  // -----------------------------------------------------------------------------------------------------------------

} // of class
