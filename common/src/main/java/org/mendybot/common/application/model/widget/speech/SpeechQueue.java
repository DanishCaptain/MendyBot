package org.mendybot.common.application.model.widget.speech;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SpeechQueue extends TimerTask
{
  private ArrayList<String> phrases = new ArrayList<>();
  private ArrayList<SpeechListener> speechListeners = new ArrayList<SpeechListener>();
  private static final long DELAY = 0;
  private static final long PERIOD = 1000;
  private Timer timer;

  public SpeechQueue() {
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
    synchronized (phrases) {
//      System.out.print(".");
      String phrase = null;
      if (phrases.size() > 0) {
        phrase = phrases.remove(0);
      }
      if (phrase != null) {
        notifySpeechListeners(phrase);
      }
    }
    Thread.yield();
  }

  public void addSpeechListener(SpeechListener lis)
  {
    speechListeners.add(lis);
  }

  public void removeSpeechListener(SpeechListener lis)
  {
    speechListeners.remove(lis);
  }

  private void notifySpeechListeners(String phrase)
  {
    for (SpeechListener lis : speechListeners) {
      lis.say(phrase);
    }
  }

  public void addPhrase(String phrase)
  {
    if (phrase != null) {
      synchronized (phrases) {
        phrases.add(phrase);
      }
    }
  }

}
