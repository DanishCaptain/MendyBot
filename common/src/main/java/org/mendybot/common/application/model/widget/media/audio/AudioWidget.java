package org.mendybot.common.application.model.widget.media.audio;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;

public class AudioWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(AudioWidget.class);
  private AudioQueue queue = new AudioQueue();

    
public AudioWidget(ApplicationModel model, String name)
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

public void addAudioListener(AudioListener lis)
{
  queue.addAudioListener(lis);
}

public void removeAudioListener(AudioListener lis)
{
  queue.removeAudioListener(lis);
}

}
