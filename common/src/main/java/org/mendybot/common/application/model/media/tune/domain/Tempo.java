package org.mendybot.common.application.model.media.tune.domain;

public class Tempo extends Playable
{
  private int value;

  public Tempo(int value)
  {
    super(NoteType.NONE);
    this.value = value;
  }

  @Override
  public String toString()
  {
    return "T-"+value;
  }

  public int getValue()
  {
    return value;
  }

  public void setValue(int t)
  {
    value = t;
  }
}
