package org.mendybot.common.application.model.media.tune.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class MeasureBlock
{
  private ArrayList<Measure> lMeasures = new ArrayList<Measure>();
  private LinkedHashMap<Voice, Measure> mMeasures = new LinkedHashMap<Voice, Measure>();
  private Song song;
  private boolean pickupFlag;
  private MeasureBlock previous;
  private MeasureBlock next;
  
  MeasureBlock(Song song)
  {
    this(song, false);
  }

  MeasureBlock(Song song, boolean pickupFlag)
  {
    this.song = song;
    this.pickupFlag = pickupFlag;
  }
  
  public Song getSong()
  {
    return song;
  }
  
  public boolean isPickup()
  {
    return pickupFlag;
  }

  public void link(MeasureBlock m)
  {
    next = m;
    m.linkParent(this);
  }

  private void linkParent(MeasureBlock p)
  {
    previous = p;
  }

  public Measure lookupPrevious(Voice voice)
  {
    if (previous != null) {
      return previous.lookupMeasure(voice);
    } else {
      return null;
    }
  }

  private Measure lookupMeasure(Voice voice)
  {
    return mMeasures.get(voice);
  }

  public void register(Measure measure, Voice voice)
  {
    lMeasures.add(measure);
    mMeasures.put(voice, measure);
  }

  public Measure lookupNext(Voice voice)
  {
    if (next != null) {
      return next.lookupMeasure(voice);
    } else {
      return null;
    }
  }

  public Measure getMeasure(Voice voice)
  {
    return mMeasures.get(voice);
  }

  public List<Measure> getMeasures()
  {
    return lMeasures;
  }

  public boolean isSane(int numberOfVoices)
  {
    boolean sane = true;
    for (Measure m : lMeasures) {
//      System.out.println(m.getVoice());
      sane = sane && m.isSane();
    }
    return sane && (mMeasures.keySet().size() == numberOfVoices);
  }

  public void makeSane(HashMap<String, Voice> vMap)
  {
    if (mMeasures.keySet().size() != vMap.size()) {
     for (Entry<String, Voice> v : vMap.entrySet()) {
       Measure m = lookupMeasure(v.getValue());
       if (m == null) {
         m = new Measure(this, v.getValue());
       }
     }
    }
    for (Measure m : lMeasures) {
      if (!m.isSane()) {
        m.makeSane();
      }
    }
  }

}
