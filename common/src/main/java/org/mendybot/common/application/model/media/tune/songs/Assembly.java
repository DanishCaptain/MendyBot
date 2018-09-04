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

public class Assembly
{
  public static final String VOICE = "B";
  private static Song s;

  static {
    s = new Song();
    s.setTitle("Assembly");
    s.setTimeSignature(TimeSignature.COMMON);
    s.setTempo(200);
    s.setInstrument(MusicPlayerService.TRUMPET);
    Measure m = s.lookupPickup(VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.DOTTED_EIGHTH));
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.SIXTEENTH));
   
    int measureCount = 1;
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.SIXTEENTH)); 
        
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.SIXTEENTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER, true));
    m.add(new Rest(NoteType.QUARTER));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Rest(NoteType.WHOLE)); 
  }
  
  public static Song getSong()
  {
    return s;
  }

}
