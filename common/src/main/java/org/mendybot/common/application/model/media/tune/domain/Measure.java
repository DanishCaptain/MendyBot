package org.mendybot.common.application.model.media.tune.domain;

import java.util.ArrayList;
import java.util.List;

public class Measure
{
  private ArrayList<Playable> playables = new ArrayList<Playable>();
  private MeasureBlock block;
  private Voice voice;

  Measure(MeasureBlock block, Voice voice)
  {
    this.block = block;
    this.voice = voice;
    block.register(this, voice);
    voice.register(this);
  }

  public Measure getPrevious()
  {
    return block.lookupPrevious(voice);
  }
  
  public Measure getNext()
  {
    return block.lookupNext(voice);
  }

  public void add(Playable p)
  {
    playables.add(p);
  }

  public boolean isSane()
  {
    boolean state = false;
    if (block.isPickup() && playables.size() > 0) {
      state = true;
    } else {
      TimeSignature ts = voice.getTimeSignature();
      if (ts != null) {
        state = ts.isSane(playables);
      }
    }
    return state;
  }

  public boolean isPickup()
  {
    return block.isPickup();
  }

  public List<Playable> getPlayables()
  {
    return playables;
  }

  public void makeSane()
  {
    TimeSignature ts = block.getSong().getTimeSignature();
    for (int i=0; i<ts.getBeats(); i++) {
      add(new Rest(ts.getD()));
    }
  }

  public Voice getVoice()
  {
    return voice;
  }

}
