package org.mendybot.common.application.model.media.tune.domain;

public class Rest extends Playable
{

  public Rest(NoteType duration)
  {
    super(duration);
  }

  @Override
  public String toString()
  {
    return "R-"+getDuration();
  }
}
