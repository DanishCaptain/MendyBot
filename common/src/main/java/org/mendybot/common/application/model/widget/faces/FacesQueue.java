package org.mendybot.common.application.model.widget.faces;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FacesQueue extends TimerTask
{
  private ArrayList<FacesListener> facesListeners = new ArrayList<FacesListener>();
  private static final long DELAY = 0;
  private static final long PERIOD = 1000;
  private Calendar calendar;
  private Timer timer;
  private int currentDay=-1;

  public FacesQueue() {
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
    calendar.setTime(d);
    if (currentDay != calendar.get(Calendar.DAY_OF_YEAR))
    {
      currentDay = calendar.get(Calendar.DAY_OF_YEAR);
      notifyFacesListeners(d);
    }
  }

  public void addFacesListener(FacesListener lis)
  {
    facesListeners.add(lis);
  }

  public void removeFacesListener(FacesListener lis)
  {
    facesListeners.remove(lis);
  }

  private void notifyFacesListeners(Date date)
  {
    for (FacesListener lis : facesListeners) {
      lis.dayChange(date);
    }
  }

}
