package org.mendybot.common.application.model.media.tune.domain;

public class Note extends Playable
{
  private Midi pitch;
  private Dynamic dynamic;

  public Note(Midi pitch, Dynamic dynamic, NoteType duration)
  {
    super(duration);
    this.pitch = pitch;
    this.dynamic = dynamic;
  }

  public Note(Midi pitch, Dynamic dynamic, NoteType duration, boolean hasFirmada)
  {
    this(pitch, dynamic, duration);
    setFirmada(hasFirmada);
  }

  public Midi getPitch()
  {
    return pitch;
  }

  public Dynamic getDynamic()
  {
    return dynamic;
  }

  @Override
  public String toString()
  {
    return "N-"+pitch+":"+dynamic+":"+getDuration();
  }
}
