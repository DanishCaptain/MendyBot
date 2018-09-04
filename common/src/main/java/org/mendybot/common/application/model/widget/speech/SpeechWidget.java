package org.mendybot.common.application.model.widget.speech;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;

public class SpeechWidget extends Widget
{
  private static final Logger LOG = Logger.getInstance(SpeechWidget.class);
  private SpeechQueue ticker = new SpeechQueue();

    
public SpeechWidget(ApplicationModel model, String name)
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
    ticker.addPhrase("Greetings Captain");
}

@Override
public void stop()
{
  LOG.logDebug("stop", "call");
  ticker.stop();
}

public void addSpeechListener(SpeechListener lis)
{
  ticker.addSpeechListener(lis);
}

public void removeSpeechListener(SpeechListener lis)
{
  ticker.removeSpeechListener(lis);
}

}
