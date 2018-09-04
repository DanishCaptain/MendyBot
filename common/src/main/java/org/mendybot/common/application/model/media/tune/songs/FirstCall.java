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

public class FirstCall
{
  public static final String VOICE = "B";
  private static Song s;

  static {
    s = new Song();
    s.setTitle("First Call");
    s.setTimeSignature(TimeSignature.SIX_EIGHT_TIME);
    s.setTempo(96);
    s.setInstrument(MusicPlayerService.TRUMPET);
    Measure m = s.lookupPickup(VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH));
   
    int measureCount = 1;
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
        
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH)); 

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Rest(NoteType.EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH));
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH));

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH)); 

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.TRIPLET_SIXTEENTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.EIGHTH)); 

    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.A4S, Dynamic.MF, NoteType.EIGHTH, true)); 
    m.add(new Rest(NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Rest(NoteType.DOTTED_QUARTER)); 
  }
  
  public static Song getSong()
  {
    return s;
  }

}
