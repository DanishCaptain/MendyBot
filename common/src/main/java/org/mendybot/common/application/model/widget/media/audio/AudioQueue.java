package org.mendybot.common.application.model.widget.media.audio;

import java.util.ArrayList;

public class AudioQueue implements Runnable
{
  private ArrayList<Audio> clips = new ArrayList<>();
  private ArrayList<AudioListener> audioListeners = new ArrayList<>();
  private Thread t;
  private boolean running;

  public AudioQueue() {
  }
  
  public synchronized void start()
  {
    if (!running)
    {
      if (t == null)
      {
        t = new Thread(this);
        t.setName("AudioManager");
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
        if (clips.size() > 0) {
          synchronized (clips) {
          Audio clip = clips.remove(0);
          if (clip != null) {
            notifyAudioListeners(clip);
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

  public void addAudioListener(AudioListener lis)
  {
    audioListeners.add(lis);
  }

  public void removeAudioListener(AudioListener lis)
  {
    audioListeners.remove(lis);
  }

  private void notifyAudioListeners(Audio audio)
  {
    for (AudioListener lis : audioListeners) {
      lis.play(audio);
    }
  }

  public void addAudio(Audio audio)
  {
    if (audio != null) {
      synchronized (audio) {
        clips.add(audio);
      }
    }
  }

}
