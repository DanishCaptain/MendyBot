package org.mendybot.common.role.console.heavyweight;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;

public class HeavyWeightConsole extends ConsoleRole implements WindowListener
{
  private static final Logger LOG = Logger.getInstance(HeavyWeightConsole.class);
  private JFrame frame;
  private OnscreenImagePanel main;
  private BufferedImage image;

  public HeavyWeightConsole(ApplicationModel model)
  {
    super(model);
    frame = new JFrame(model.getName());
    main = new OnscreenImagePanel();
    frame.setContentPane(main);
    
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(d.width, d.height);
//    frame.setAlwaysOnTop(true);
    JFrame.setDefaultLookAndFeelDecorated(false);

    image = main.getImage();
  }


  @Override
  protected void initConsole() throws ExecuteException
  {
    LOG.logDebug("initConsole", "call");
	    frame.addWindowListener(this);
  }

  @Override
  public void start() throws ExecuteException
  {
	    frame.setVisible(true);
	    repaint();
  }

  @Override
  public void stop()
  {
	    frame.setVisible(false);
	    frame.dispose();
  }

  @Override
  public void windowOpened(WindowEvent e)
  {
  }

  @Override
  public void windowClosing(WindowEvent e)
  {
    System.out.println("window closing");
    getModel().stopApplication();
  }

  @Override
  public void windowClosed(WindowEvent e)
  {
  }

  @Override
  public void windowIconified(WindowEvent e)
  {
  }

  @Override
  public void windowDeiconified(WindowEvent e)
  {
  }

  @Override
  public void windowActivated(WindowEvent e)
  {
  }

  @Override
  public void windowDeactivated(WindowEvent e)
  {
  }


  @Override
  protected void repaintLocal()
  {
    main.repaint();
  }


  @Override
  protected BufferedImage getImage()
  {
    return image;
  }

}
