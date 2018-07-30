package org.mendybot.common.role.console.glyph.time;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.widget.time.ClockListener;
import org.mendybot.common.application.model.widget.time.TimekeeperWidget;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.glyph.Glyph;

public class ClockGlyph2 extends Glyph implements ClockListener
{
  private static final Logger LOG = Logger.getInstance(ClockGlyph2.class);
  private static final int W_RATIO = 580/1920;
  private static final int H_RATIO = 180/1080;
  private SimpleDateFormat sdf0 = new SimpleDateFormat("zzzz");
  private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
  private SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
  private Font font0;
  private Font font1;
  private int x;
  private int y;
  private Date date;
  private int w;
  private int h;
  private boolean initialized;

  public ClockGlyph2(ConsoleRole console, String name, String widgetName)
  {
    super(console, name, widgetName);
    TimekeeperWidget widget = (TimekeeperWidget) console.getModel().lookupWidget(widgetName);
    widget.addClockListener(this);
    
    String tzId = console.getModel().getProperty("Glyph."+name+".TZ");
    TimeZone tz;
    if (tzId == null) {
      tz = TimeZone.getDefault();      
    } else {
      tz = TimeZone.getTimeZone(tzId);
    }
    
    sdf0.setTimeZone(tz);
    sdf1.setTimeZone(tz);
    sdf2.setTimeZone(tz);
    

    x = Integer.parseInt(console.getModel().getProperty("Glyph."+name+".X", "10"));
    y = Integer.parseInt(console.getModel().getProperty("Glyph."+name+".Y", "10"));
    date = new Date();
  }
  
  @Override
  public void draw(Graphics g)
  {
    if (!initialized)
    {
      w = Math.round(getConsole().getW()*W_RATIO);
      h = Math.round(getConsole().getH()*H_RATIO);
      w = 580;
      h=180;
      System.out.println(w+":"+h);
      font0 = new Font("SansSerif", Font.PLAIN, Math.round(12*W_RATIO));
      font1 = new Font("SansSerif", Font.PLAIN, Math.round(110*W_RATIO));
//      initialized = true;
    }
    String text0 = getName()+" - "+sdf2.format(date)+" - "+sdf0.format(date);    
    String text1 = sdf1.format(date);
    int textHeight1 = g.getFontMetrics(font1).getHeight();
    int textWidth1 = g.getFontMetrics(font1).stringWidth(text1);
    
    g.setColor(Color.GREEN);
    g.fillRect(x, y, w, h);
    g.setColor(Color.WHITE);
    g.drawRect(x+2, y+2, w-4, h-4);
    g.setFont(font0);
    g.drawString(text0, x+8, y+g.getFontMetrics(font0).getHeight());

    g.setFont(font1);
    g.drawString(text1, x+((w-textWidth1)/2), y+textHeight1);
  }

  @Override
  public void timeChange(Date date)
  {
    LOG.logInfo("timeChange", ""+date);
    this.date = date;
    getConsole().repaint();
  }

}
