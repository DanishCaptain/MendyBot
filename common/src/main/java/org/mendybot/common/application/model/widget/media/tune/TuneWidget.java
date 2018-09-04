package org.mendybot.common.application.model.widget.media.tune;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;

public class TuneWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(TuneWidget.class);
  private TuneQueue queue = new TuneQueue();

    
public TuneWidget(ApplicationModel model, String name)
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
    queue.start();
}

@Override
public void stop()
{
  LOG.logDebug("stop", "call");
  queue.stop();
}

public void addTuneListener(TuneListener lis)
{
  queue.addTuneListener(lis);
}

public void removeTuneListener(TuneListener lis)
{
  queue.removeTuneListener(lis);
}

}
