package org.mendybot.common.application.model.widget.media.tune;

import org.mendybot.common.application.model.media.tune.domain.Song;

public class Tune
{
  private String voice;
  private Song song;

  public Tune(String voice, Song song)
  {
    this.voice = voice;
    this.song = song;
  }
  
  public String getVoice()
  {
    return voice;
  }
  
  public Song getSong()
  {
    return song;
  }

}
