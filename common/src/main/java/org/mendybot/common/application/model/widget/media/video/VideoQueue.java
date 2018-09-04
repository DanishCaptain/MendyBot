package org.mendybot.common.application.model.widget.media.video;

import java.util.ArrayList;

public class VideoQueue implements Runnable
{
  private ArrayList<Video> videos = new ArrayList<>();
  private ArrayList<VideoListener> videoListeners = new ArrayList<>();
  private Thread t;
  private boolean running;

  public VideoQueue() {
    Video v = new Video();
    v.setName("Movie 1");
    v.setFileName("movie1.mp4");
    videos.add(v);
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
        if (videos.size() > 0) {
          synchronized (videos) {
          Video video = videos.remove(0);
          if (video != null) {
            notifyVideoListeners(video);
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

  public void addVideoListener(VideoListener lis)
  {
    videoListeners.add(lis);
  }

  public void removeVideoListener(VideoListener lis)
  {
    videoListeners.remove(lis);
  }

  private void notifyVideoListeners(Video video)
  {
    for (VideoListener lis : videoListeners) {
      lis.play(video);
    }
  }

  public void addVideo(Video video)
  {
    if (video != null) {
      synchronized (video) {
        videos.add(video);
      }
    }
  }

}
