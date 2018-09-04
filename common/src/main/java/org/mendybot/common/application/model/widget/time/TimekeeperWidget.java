package org.mendybot.common.application.model.widget.time;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;

public class TimekeeperWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(TimekeeperWidget.class);
	private Ticker ticker = new Ticker();

	  
  public TimekeeperWidget(ApplicationModel model, String name)
  {
    super(model, name);
  }

  @Override
  public void init() throws ExecuteException
  {
    LOG.logDebug("init", "call");
  }

  @Override
  public void start() throws ExecuteException
  {
    LOG.logDebug("start", "call");
	  ticker.start();
  }

  @Override
  public void stop()
  {
    LOG.logDebug("stop", "call");
    ticker.stop();
  }

  public void addCalendarListener(CalendarListener lis)
  {
    ticker.addCalendarListener(lis);
  }

  public void removeCalendarListener(CalendarListener lis)
  {
    ticker.removeCalendarListener(lis);
  }

  public void addClockListener(ClockListener lis)
  {
    ticker.addClockListener(lis);
  }

  public void removeClockListener(ClockListener lis)
  {
    ticker.removeClockListener(lis);
  }

}
