package org.mendybot.common.role.console.heavyweight;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class OnscreenImagePanel extends JPanel
{
  private static final long serialVersionUID = -3585330065992811147L;
  private BufferedImage image;

  public OnscreenImagePanel(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    setSize(width, height);
    setPreferredSize(getSize());

  }

  public BufferedImage getImage()
  {
    return image;
  }

  @Override
  public void paint(Graphics g) {
    //super.paint(g);
    g.drawImage(image, 0, 0, this);
  }
}
