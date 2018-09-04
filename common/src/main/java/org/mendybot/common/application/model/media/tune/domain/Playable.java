package org.mendybot.common.application.model.media.tune.domain;

public abstract class Playable
{
  private NoteType duration;
  private boolean hasFirmada;

  public Playable(NoteType duration)
  {
    this.duration = duration;
  }

  public NoteType getDuration()
  {
    return duration;
  }

  protected void setFirmada(boolean f)
  {
    hasFirmada = f;
  }

  public boolean getFirmada()
  {
    return hasFirmada;
  }

}
