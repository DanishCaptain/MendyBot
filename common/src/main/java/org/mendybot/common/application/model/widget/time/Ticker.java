package org.mendybot.common.application.model.widget.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Ticker extends TimerTask
{
  private ArrayList<ClockListener> clockListeners = new ArrayList<ClockListener>();
  private ArrayList<CalendarListener> calendarListeners = new ArrayList<CalendarListener>();
  private static final long DELAY = 0;
  private static final long PERIOD = 1000;
  private Calendar calendar;
  private Timer timer;
  private int currentDay=-1;

  public Ticker() {
    calendar = Calendar.getInstance();
    timer = new Timer();
  }
  
  public void start()
  {
    timer.schedule(this, DELAY, PERIOD);
  }
  
  public void stop()
  {
    timer.cancel();
  }

  @Override
  public void run()
  {
    Date d = new Date();
    notifyClockListeners(d);
    calendar.setTime(d);
    if (currentDay != calendar.get(Calendar.DAY_OF_YEAR))
    {
      currentDay = calendar.get(Calendar.DAY_OF_YEAR);
      notifyCalendarListeners(d);
    }
  }

  public void addCalendarListener(CalendarListener lis)
  {
    calendarListeners.add(lis);
  }

  public void removeCalendarListener(CalendarListener lis)
  {
    calendarListeners.remove(lis);
  }

  public void addClockListener(ClockListener lis)
  {
    clockListeners.add(lis);
  }

  public void removeClockListener(ClockListener lis)
  {
    clockListeners.remove(lis);
  }

  private void notifyClockListeners(Date date)
  {
    for (ClockListener lis : clockListeners) {
      lis.timeChange(date);
    }
  }

  private void notifyCalendarListeners(Date date)
  {
    for (CalendarListener lis : calendarListeners) {
      lis.dayChange(date);
    }
  }

}
