package org.mendybot.common.application.model.widget.media.tune;

import java.util.ArrayList;

import org.mendybot.common.application.model.media.tune.songs.Taps;

public class TuneQueue implements Runnable
{
  private ArrayList<Tune> tunes = new ArrayList<>();
  private ArrayList<TuneListener> tuneListeners = new ArrayList<>();
  private Thread t;
  private boolean running;

  public TuneQueue() {
    tunes.add(new Tune(Taps.VOICE, Taps.getSong()));
  }
  
  public void start()
  {
    if (!running)
    {
      if (t == null)
      {
        t = new Thread(this);
        t.setName("VideoManager");
        t.setDaemon(true);
      }
      t.start();
    }
  }
  
  public void stop()
  {
    running = false;
    t.interrupt();
  }

  @Override
  public void run()
  {
    running = true;
    while (running) {
        if (tunes.size() > 0) {
          synchronized (tunes) {
          Tune tune = tunes.remove(0);
          if (tune != null) {
            notifyTuneListeners(tune);
          }
          }
          Thread.yield();
        } else {
          try
          {
            Thread.sleep(10000);
          }
          catch (InterruptedException e)
          {
            running = false;
          }
      }
    }
  }

  public void addTuneListener(TuneListener lis)
  {
    tuneListeners.add(lis);
  }

  public void removeTuneListener(TuneListener lis)
  {
    tuneListeners.remove(lis);
  }

  private void notifyTuneListeners(Tune tune)
  {
    for (TuneListener lis : tuneListeners) {
      lis.play(tune);
    }
  }

  public void addTune(Tune tune)
  {
    if (tune != null) {
      synchronized (tunes) {
        tunes.add(tune);
      }
    }
  }

}
