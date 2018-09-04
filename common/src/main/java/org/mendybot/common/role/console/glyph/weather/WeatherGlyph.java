package org.mendybot.common.role.console.glyph.weather;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.mendybot.common.application.model.widget.weather.WeatherListener;
import org.mendybot.common.application.model.widget.weather.WeatherWidget;
import org.mendybot.common.model.domain.DomainObject;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.glyph.Glyph;

public class WeatherGlyph extends Glyph implements WeatherListener
{
  private static final int W_WIDTH = 580;
  private static final int W_HEIGHT = 680;
  private SimpleDateFormat sdf0 = new SimpleDateFormat("zzzz");
  private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
  private SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
  private Font font0;
  private Font font1;
  private int x;
  private int y;
  private DomainObject current;
  private int ww;
  private int hh;
  private boolean initialized;
  private DomainObject archiveCurrent;

  public WeatherGlyph(ConsoleRole console, String name, String widgetName)
  {
    super(console, name, widgetName);
    WeatherWidget widget = (WeatherWidget) console.getModel().lookupWidget(widgetName);
    widget.addWeatherListener(this);
    
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
    
  }
  
  @Override
  public void draw(Graphics g)
  {
    if (!initialized)
    {
      x = (int) Math.round((double)x/(double)1920*(double)getConsole().getW());
      y = (int) Math.round((double)y/(double)1920*(double)getConsole().getW());
      
//      int w1 = 1920;
//      int h1 = 1080;
      int w1 = 1400;
      int h1 = 1180;
      ww = (int) Math.round((double)W_WIDTH/(double)w1*(double)getConsole().getW());
      hh = (int) Math.round((double)W_HEIGHT/(double)h1*(double)getConsole().getH());
      System.out.println("1 "+getConsole().getW()+":"+getConsole().getH());
      System.out.println("2 "+ww+":"+hh);
      
      System.out.println((int) Math.round((double)W_WIDTH/(double)1920*(double)12)+":"+(int) Math.round((double)W_WIDTH/(double)1920*(double)110));
      
      font0 = new Font("SansSerif", Font.PLAIN, (int) Math.round((double)56/(double)1920*(double)getConsole().getW()));
      font1 = new Font("SansSerif", Font.PLAIN, (int) Math.round((double)55/(double)1920*(double)getConsole().getW()));
      
      initialized = true;
    }
//    if (archiveCurrent == current) {
//      return;
//    }
    archiveCurrent = current;

    
    /*
    private Date created = new Date();
    private String iconImage;
    private String description;
    private Location location;
    private String sunrise;
    private String sunset;
    private double tF;
    private double tMaxF;
    private double tMinF;
    private int humidity;
    private int pressure;
    private String lastUpdate;
    */

    int textHeight0 = g.getFontMetrics(font0).getHeight();
    int textHeight1 = g.getFontMetrics(font1).getHeight();
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x, y, ww, hh);
    g.setColor(Color.BLACK);
    g.drawRect(x+2, y+2, ww-4, hh-4);
    g.setFont(font0);
    if (current != null) {
      DomainObject location = (DomainObject) current.getChild(WeatherWidget.WEATHER_REPORT_LOCATION);
      g.drawString(getName()+" - "+location.getValue(WeatherWidget.LOCATION_CITY)+", "+location.getValue(WeatherWidget.LOCATION_COUNTRY), x+8, y+(textHeight0));
      g.setFont(font1);
      int yW = y + textHeight0;
      int rowCount = 1;
      g.drawString((String) current.getValue(WeatherWidget.WEATHER_REPORT_DESCRIPTION), x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("temperature: "+current.getValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE), x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("low: "+current.getValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE_LOW), x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("high: "+current.getValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE_HIGH), x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("humidity: "+current.getValue(WeatherWidget.WEATHER_REPORT_HUMIDITY)+"%", x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("pressure: "+current.getValue(WeatherWidget.WEATHER_REPORT_ATMOSPHERIC_PRESSURE)+" hPa", x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("wind: "+current.getValue(WeatherWidget.WEATHER_REPORT_WIND_DIRECTION)+"\u00b0 at "+current.getValue(WeatherWidget.WEATHER_REPORT_WIND_SPEED)+" mph", x+8, yW+(textHeight1*(rowCount++)));
      g.drawString("last update: "+current.getValue(WeatherWidget.WEATHER_REPORT_LAST_UPDATE), x+8, yW+(textHeight1*(rowCount++)));
    } else {
      g.drawString(getName()+" - ***", x+8, y+(textHeight0));
    }
  }

  @Override
  public void change(DomainObject current)
  {
    this.current = current;
    getConsole().repaint();
  }

}
