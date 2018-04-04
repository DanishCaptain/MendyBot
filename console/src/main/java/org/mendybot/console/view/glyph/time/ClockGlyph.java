package org.mendybot.console.view.glyph.time;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import org.mendybot.common.application.model.widget.time.ClockListener;
import org.mendybot.common.application.model.widget.time.TimekeeperWidget;
import org.mendybot.console.view.glyph.PanelPosition;
import org.mendybot.console.view.glyph.Glyph;

public class ClockGlyph extends Glyph implements ClockListener
{
  private static final long serialVersionUID = -6998275195294301347L;
  private static final int W_WIDTH = 580;
  private static final int W_HEIGHT = 180;
  private SimpleDateFormat sdf0 = new SimpleDateFormat("zzzz");
  private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
  private SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");

  private TitledBorder border;
  private JLabel label1;
  private TimeZone tz;

  public ClockGlyph(TimekeeperWidget widget, TimeZone tz, PanelPosition pos)
  {
    super(pos, new Dimension(W_WIDTH, W_HEIGHT));
    this.tz=tz;
    Font font1 = new Font("SansSerif", Font.PLAIN, 110);

    sdf0.setTimeZone(tz);
    sdf1.setTimeZone(tz);
    sdf2.setTimeZone(tz);
    
    border = new TitledBorder("");
    border.setTitleColor(Color.WHITE);
    setBorder(border);
    setBackground(Color.BLACK);
    setForeground(Color.WHITE);
    
    setLayout(new GridLayout(0,1));

    label1 = new JLabel();
    label1.setHorizontalAlignment(JLabel.CENTER);
    label1.setFont(font1);
    label1.setForeground(Color.WHITE);
    add(label1);
    
    widget.addClockListener(this);
  }

  @Override
  public void timeChange(Date date)
  {
    border.setTitle("Clock - "+sdf2.format(date)+" - "+sdf0.format(date));
    label1.setText(sdf1.format(date));
  }
  
  @Override
  public String toString()
  {
    return tz.getID()+" "+super.toString();
  }
}
