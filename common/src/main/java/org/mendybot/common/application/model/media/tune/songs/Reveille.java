package org.mendybot.common.application.model.media.tune.songs;

import org.mendybot.common.application.model.media.tune.domain.Dynamic;
import org.mendybot.common.application.model.media.tune.domain.Measure;
import org.mendybot.common.application.model.media.tune.domain.Midi;
import org.mendybot.common.application.model.media.tune.domain.MusicPlayerService;
import org.mendybot.common.application.model.media.tune.domain.Note;
import org.mendybot.common.application.model.media.tune.domain.NoteType;
import org.mendybot.common.application.model.media.tune.domain.Rest;
import org.mendybot.common.application.model.media.tune.domain.Song;
import org.mendybot.common.application.model.media.tune.domain.TimeSignature;

public class Reveille
{
  public static final String VOICE = "T";
  private static Song s;

  static {
    s = new Song();
    s.setTitle("Reveille");
    s.setTimeSignature(TimeSignature.TWO_FOUR_TIME);
    s.setTempo(152);
    s.setInstrument(MusicPlayerService.TRUMPET);
    Measure m = s.lookupPickup(VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.SIXTEENTH));
    
    int measureCount = 1;
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.DOTTED_QUARTER));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Rest(NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.DOTTED_QUARTER));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.SIXTEENTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.SIXTEENTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.DOTTED_QUARTER));
    m.add(new Rest(NoteType.EIGHTH));

  }
  
  public static Song getSong()
  {
    return s;
  }

}
