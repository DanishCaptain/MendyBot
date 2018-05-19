package org.mendybot.common.role.console.glyph.time;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.mendybot.common.application.model.widget.time.ClockListener;
import org.mendybot.common.application.model.widget.time.TimekeeperWidget;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.glyph.Glyph;

public class ClockGlyph extends Glyph implements ClockListener
{
  private static final int W_WIDTH = 580;
  private static final int W_HEIGHT = 180;
  private SimpleDateFormat sdf0 = new SimpleDateFormat("zzzz");
  private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
  private SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
  private Font font0;
  private Font font1;
  private int x;
  private int y;
  private Date date;
  private int ww;
  private int hh;
  private boolean initialized;

  public ClockGlyph(ConsoleRole console, String name, String widgetName)
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
      x = (int) Math.round((double)x/(double)1920*(double)getConsole().getW());
      y = (int) Math.round((double)y/(double)1920*(double)getConsole().getW());
      
      ww = (int) Math.round((double)W_WIDTH/(double)1920*(double)getConsole().getW());
      hh = (int) Math.round((double)W_HEIGHT/(double)1080*(double)getConsole().getH());
      System.out.println("1 "+getConsole().getW()+":"+getConsole().getH());
      System.out.println("2 "+ww+":"+hh);
      
      System.out.println((int) Math.round((double)W_WIDTH/(double)1920*(double)12)+":"+(int) Math.round((double)W_WIDTH/(double)1920*(double)110));
      
      font0 = new Font("SansSerif", Font.PLAIN, (int) Math.round((double)12/(double)1920*(double)getConsole().getW()));
      font1 = new Font("SansSerif", Font.PLAIN, (int) Math.round((double)110/(double)1920*(double)getConsole().getW()));
      
      initialized = true;
    }
    String text0 = getName()+" - "+sdf2.format(date)+" - "+sdf0.format(date);    
    String text1 = sdf1.format(date);
    int textHeight1 = g.getFontMetrics(font1).getHeight();
    int textWidth1 = g.getFontMetrics(font1).stringWidth(text1);
    
    g.setColor(Color.RED);
    g.fillRect(x, y, ww, hh);
    g.setColor(Color.WHITE);
    g.drawRect(x+2, y+2, ww-4, hh-4);
    g.setFont(font0);
    g.drawString(text0, x+8, y+g.getFontMetrics(font0).getHeight());

    g.setFont(font1);
    g.drawString(text1, x+((ww-textWidth1)/2), y+textHeight1);
  }

  @Override
  public void timeChange(Date date)
  {
    this.date = date;
    getConsole().repaint();
  }

}
