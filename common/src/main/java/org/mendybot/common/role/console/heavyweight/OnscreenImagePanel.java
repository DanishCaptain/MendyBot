package org.mendybot.common.role.console.heavyweight;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class OnscreenImagePanel extends JPanel
{
  private static final long serialVersionUID = -3585330065992811147L;
  private BufferedImage image;

  public OnscreenImagePanel() {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);

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
