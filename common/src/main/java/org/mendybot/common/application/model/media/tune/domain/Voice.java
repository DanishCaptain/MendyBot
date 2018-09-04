package org.mendybot.common.application.model.media.tune.domain;

import java.util.ArrayList;
import java.util.List;

public class Voice
{
  private ArrayList<Measure> measures = new ArrayList<Measure>();
  private Song song;
  private String key;

  public Voice(Song song, String key)
  {
    this.song = song;
    this.key = key;
  }

  public String getKey()
  {
    return key;
  }

  @Override
  public String toString()
  {
    return key;
  }

  public List<Measure> getMeasures()
  {
    return measures;
  }

  public void register(Measure measure)
  {
    if (measure.isPickup())
    {
      measures.add(0, measure);
    }
    else
    {
      measures.add(measure);
    }
  }

  public TimeSignature getTimeSignature()
  {
    return song.getTimeSignature();
  }

}
