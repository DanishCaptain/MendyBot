package org.mendybot.common.application.model.media.tune.domain;

import java.util.ArrayList;

import org.mendybot.common.application.log.Logger;

public class TimeSignature
{
  private Logger LOG = Logger.getInstance(TimeSignature.class);
  public static final TimeSignature TWO_TWO_TIME = new TimeSignature(2,NoteType.HALF);
  public static final TimeSignature TWO_FOUR_TIME = new TimeSignature(2,NoteType.QUARTER);
  public static final TimeSignature THREE_FOUR_TIME = new TimeSignature(3,NoteType.QUARTER);
  public static final TimeSignature FOUR_FOUR_TIME = new TimeSignature(4,NoteType.QUARTER);
  public static final TimeSignature FIVE_FOUR_TIME = new TimeSignature(5,NoteType.QUARTER);
  public static final TimeSignature SIX_EIGHT_TIME = new TimeSignature(6,NoteType.EIGHTH);
  public static final TimeSignature COMMON = FOUR_FOUR_TIME;
  private double beats;
  private double quarterBeat;
  private NoteType d;

  private TimeSignature(int n, NoteType d)
  {
    this.beats = n;
    this.d = d;
    if (NoteType.HALF == d) {
      quarterBeat = 0.5F;
    } else if (NoteType.QUARTER == d) {
      quarterBeat = 1;
    } else if (NoteType.EIGHTH == d) {
      quarterBeat = 4;
    } else {
      throw new RuntimeException("missing note type "+d);
    }
  }
  
  @Override
  public String toString()
  {
    if (NoteType.HALF == d) {
      return ((int)beats)+"/2";
    } else if (NoteType.QUARTER == d) {
      return ((int)beats)+"/4";
    } else if (NoteType.EIGHTH == d) {
      return ((int)beats)+"/8";
    } else {
      throw new RuntimeException("missing note type "+d);
    }
  }
  
  public NoteType getD()
  {
    return d;
  }

  public boolean isSane(ArrayList<Playable> playables)
  {
    double bCount = 0;
    for (Playable p : playables) {
      NoteType n = p.getDuration();
      bCount += getDuraction(n);
    }
    /*
an indication of rhythm following a clef, 
generally expressed as a fraction with the 
denominator defining the beat as a division of a whole note and the 
numerator giving the number of beats in each bar.
     */
    LOG.logDebug("isSane", bCount+":"+beats+":"+this.quarterBeat+":"+((int)bCount==(int)beats));
    return (int)bCount == (int)beats;
  }

public double getDuraction(NoteType n)
{
  if (n == NoteType.QUARTER) {
    return quarterBeat;
  } else if (n == NoteType.WHOLE) {
    return quarterBeat * 4;
  } else if (n == NoteType.HALF) {
    return quarterBeat * 2;
  } else if (n == NoteType.EIGHTH) {
    return quarterBeat / 2;
  } else if (n == NoteType.SIXTEENTH) {
    return quarterBeat / 4;
  } else if (n == NoteType.THIRTYSECOND) {
    return quarterBeat / 8;
  } else if (n == NoteType.SIXTYFOURTH) {
    return quarterBeat / 16;
  } else if (n == NoteType.ONETWENTYEIGHTH) {
    return quarterBeat / 32;
    
  } else if (n == NoteType.DOTTED_HALF) {
    return quarterBeat * 2 + quarterBeat;
  } else if (n == NoteType.DOTTED_QUARTER) {
    return quarterBeat + quarterBeat/2;
  } else if (n == NoteType.DOTTED_EIGHTH) {
    return quarterBeat / 2 + quarterBeat / 4;
  } else if (n == NoteType.DOTTED_SIXTEENTH) {
    return quarterBeat / 4 + quarterBeat / 8;
  } else if (n == NoteType.DOTTED_THIRTYSECOND) {
    return quarterBeat / 8 + quarterBeat / 16;
  } else if (n == NoteType.DOTTED_SIXTYFOURTH) {
    return quarterBeat / 16 + quarterBeat / 32;
  } else if (n == NoteType.TRIPLET_EIGHTH) {
    return quarterBeat / 3;
  } else if (n == NoteType.TRIPLET_SIXTEENTH) {
    return quarterBeat / 2 / 3;
  } else if (n == NoteType.NONE) {
    return 0;
  } else {
    throw new RuntimeException("missing note type "+n);
  }
}

public double getBeats()
{
  return beats;
}
}
