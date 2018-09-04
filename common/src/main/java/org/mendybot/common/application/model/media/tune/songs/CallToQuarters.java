package org.mendybot.common.application.model.media.tune.songs;

import org.mendybot.common.application.model.media.tune.domain.Dynamic;
import org.mendybot.common.application.model.media.tune.domain.Measure;
import org.mendybot.common.application.model.media.tune.domain.Midi;
import org.mendybot.common.application.model.media.tune.domain.MusicPlayerService;
import org.mendybot.common.application.model.media.tune.domain.Note;
import org.mendybot.common.application.model.media.tune.domain.NoteType;
import org.mendybot.common.application.model.media.tune.domain.Rest;
import org.mendybot.common.application.model.media.tune.domain.Song;
import org.mendybot.common.application.model.media.tune.domain.Tempo;
import org.mendybot.common.application.model.media.tune.domain.TimeSignature;

public class CallToQuarters
{
  public static final String VOICE = "B";
  private static Song s;

  static {
    s = new Song();
    s.setTitle("Call to Quarters");
    s.setTimeSignature(TimeSignature.COMMON);
    //s.setTempo(100); // 80/100 after first measure
    s.setTempo(80); // 80/100 after first measure
    s.setInstrument(MusicPlayerService.TRUMPET);

    int measureCount = 1;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER));
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.QUARTER, true)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER));
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Tempo(100));
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.SIXTEENTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.HALF));
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.DOTTED_EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.SIXTEENTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.TRIPLET_EIGHTH)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.HALF)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.F5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER)); 
    m.add(new Note(Midi.D5, Dynamic.MF, NoteType.QUARTER)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B3F, Dynamic.MF, NoteType.QUARTER, true)); 
    m.add(new Note(Midi.F4, Dynamic.MF, NoteType.QUARTER, true)); 
    m.add(new Note(Midi.B4F, Dynamic.MF, NoteType.HALF, true)); 
    
    m = s.lookup(measureCount++, VOICE);
    m.add(new Rest(NoteType.WHOLE)); 
  }
  
  public static Song getSong()
  {
    return s;
  }

}
