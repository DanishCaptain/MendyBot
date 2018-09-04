package org.mendybot.common.application.model.widget.faces;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;

public class FacesWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(FacesWidget.class);
	private FacesQueue ticker = new FacesQueue();

	  
  public FacesWidget(ApplicationModel model, String name)
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

  public void addFacesListener(FacesListener lis)
  {
    ticker.addFacesListener(lis);
  }

  public void removeFacesListener(FacesListener lis)
  {
    ticker.removeFacesListener(lis);
  }

}
