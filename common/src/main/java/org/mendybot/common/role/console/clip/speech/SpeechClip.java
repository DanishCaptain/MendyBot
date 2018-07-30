package org.mendybot.common.role.console.clip.speech;

import org.mendybot.common.application.model.widget.speech.SpeechListener;
import org.mendybot.common.application.model.widget.speech.SpeechWidget;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.clip.Clip;

public class SpeechClip extends Clip implements SpeechListener
{

  public SpeechClip(ConsoleRole console, String name, String widgetName)
  {
    super(console, name, widgetName);
    SpeechWidget widget = (SpeechWidget) console.getModel().lookupWidget(widgetName);
    widget.addSpeechListener(this);
  }
  
  @Override
  public void play()
  {
  }

  @Override
  public void say(String phrase)
  {
    System.out.println("say: "+phrase);
  }

}
