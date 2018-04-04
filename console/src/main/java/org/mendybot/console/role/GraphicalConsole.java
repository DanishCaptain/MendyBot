package org.mendybot.console.role;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.time.TimekeeperWidget;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.console.view.GlyphPanel;
import org.mendybot.console.view.glyph.PanelPosition;
import org.mendybot.console.view.glyph.time.ClockGlyph;

public class GraphicalConsole extends ConsoleRole implements WindowListener
{
  private JFrame frame;
  private JPanel main;

  public GraphicalConsole(ApplicationModel model)
  {
    super(model);
    frame = new JFrame(model.getName());
    
    main = new JPanel();
    main.setLayout(new CardLayout());
    main.setBackground(Color.BLACK);
    main.setForeground(Color.WHITE);
    frame.setContentPane(main);
    
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(d.width, d.height);
//    frame.setAlwaysOnTop(true);
    JFrame.setDefaultLookAndFeelDecorated(false);
  }

  @Override
  public void init() throws ExecuteException
  {
    frame.addWindowListener(this);
    TimekeeperWidget widget = (TimekeeperWidget) getModel().lookupWidget("TIMEKEEPER");
    
    GlyphPanel defaultPanel = new GlyphPanel();
    defaultPanel.setLayout(null);
    defaultPanel.setBackground(Color.BLACK);
    defaultPanel.setForeground(Color.WHITE);
    defaultPanel.add(new ClockGlyph(widget, TimeZone.getDefault(), PanelPosition.TOP_RIGHT));
    defaultPanel.add(new ClockGlyph(widget, TimeZone.getTimeZone("GMT"), PanelPosition.BOTTOM_RIGHT));

    main.add(defaultPanel, "DEFAULT");
    
    CardLayout cl = (CardLayout)(main.getLayout());
    cl.show(main, "DEFAULT");
  }

  @Override
  public void start() throws ExecuteException
  {
    frame.setVisible(true);
  }

  @Override
  public void stop()
  {
    System.out.println("TerminalDisplay.stop");
    frame.setVisible(false);
    frame.dispose();
  }

  public void windowOpened(WindowEvent e)
  {
  }

  public void windowClosing(WindowEvent e)
  {
    System.out.println("window closing");
    getModel().stopApplication();
  }

  public void windowClosed(WindowEvent e)
  {
  }

  public void windowIconified(WindowEvent e)
  {
  }

  public void windowDeiconified(WindowEvent e)
  {
  }

  public void windowActivated(WindowEvent e)
  {
  }

  public void windowDeactivated(WindowEvent e)
  {
  }

}
