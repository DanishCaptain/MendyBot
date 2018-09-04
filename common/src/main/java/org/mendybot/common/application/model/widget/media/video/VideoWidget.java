package org.mendybot.common.application.model.widget.media.video;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;

public class VideoWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(VideoWidget.class);
  private VideoQueue queue = new VideoQueue();

    
public VideoWidget(ApplicationModel model, String name)
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

public void addVideoListener(VideoListener lis)
{
  queue.addVideoListener(lis);
}

public void removeVideoListener(VideoListener lis)
{
  queue.removeVideoListener(lis);
}

}
