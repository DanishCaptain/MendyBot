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

public class MessCall
{
  public static final String VOICE = "B";
  private static Song s;

  static {
    s = new Song();
    s.setTitle("MessCall");
    s.setTimeSignature(TimeSignature.TWO_FOUR_TIME);
    s.setTempo(152);
    s.setInstrument(MusicPlayerService.TRUMPET);

    int measureCount = 1;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Rest(NoteType.QUARTER)); 
    

    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Rest(NoteType.QUARTER)); 
    
    
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH));
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Rest(NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Rest(NoteType.HALF)); 
  }
  
  public static Song getSong()
  {
    return s;
  }

}
