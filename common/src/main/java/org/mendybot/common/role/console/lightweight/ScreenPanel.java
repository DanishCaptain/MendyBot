package org.mendybot.common.role.console.lightweight;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ScreenPanel extends JPanel
{
  private static final long serialVersionUID = -1338448756122429165L;
  private int scale = 1;
  private FrameBuffer fb;

  public ScreenPanel(FrameBuffer fb)
  {
    this.fb = fb;
    setPreferredSize(new Dimension(fb.getWidth(), fb.getHeight()));
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    synchronized (fb.getUpdateLockForSync())
    {
      //super.paintComponent(g);
      BufferedImage img = fb.getImage();

      int w = this.getWidth();
      int h = this.getHeight();
      int wi = img.getWidth() * scale;
      int hi = img.getHeight() * scale;

      Graphics2D g2 = (Graphics2D) g;
      // g2.translate(w / 2 - wi / 2, h / 2 - hi / 2);
      g2.translate((w - wi) / 2, (h - hi) / 2);
      g2.scale(scale, scale);

      g.setColor(Color.BLACK);
      g.fillRect(0, 0, img.getWidth(), img.getHeight());
      g.drawImage(img, 0, 0, null);
    }
  }

  public void setScale(int scale)
  {
    this.scale = scale;
    repaint();
  }

  public int componentToScreenX(int x)
  {
    BufferedImage img = fb.getImage();
    int w = this.getWidth();
    int wi = img.getWidth();
    int d = (int) (((w - wi * scale) / 2f));
    x = (x - d) / scale;
    if (x < 0)
      x = 0;
    if (x >= wi)
      x = wi - 1;
    return x;
  }

  public int componentToScreenY(int y)
  {
    BufferedImage img = fb.getImage();
    int h = this.getHeight();
    int hi = img.getHeight();
    int d = (int) (((h - hi * scale) / 2f));
    y = (y - d) / scale;
    if (y < 0)
      y = 0;
    if (y >= hi)
      y = hi - 1;
    return y;
  }
}
