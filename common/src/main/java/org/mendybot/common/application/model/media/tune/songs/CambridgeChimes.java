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

//1
//23
//451
//2345 


public class CambridgeChimes
{
  public static final String VOICE = "S";
  private static Song quarterFirst;
  private static Song quarterSecond;
  private static Song quarterThird;
  private static Song quarterFourth;
  private static Song hourChime;
  private static int TEMPO = 70;
  
  static {
    quarterFirst = new Song();
    quarterFirst.setTitle("CambridgeChimes-1Q");
    quarterFirst.setTimeSignature(TimeSignature.FIVE_FOUR_TIME);
    quarterFirst.setTempo(TEMPO);
    quarterFirst.setInstrument(MusicPlayerService.CHIME);
    populateSegment1(quarterFirst, 1);

    quarterSecond = new Song();
    quarterSecond.setTitle("CambridgeChimes-2Q");
    quarterSecond.setTimeSignature(TimeSignature.FIVE_FOUR_TIME);
    quarterSecond.setTempo(TEMPO);
    quarterSecond.setInstrument(MusicPlayerService.CHIME);
    int count = populateSegment2(quarterSecond, 1);
    populateSegment3(quarterSecond, count);

    quarterThird = new Song();
    quarterThird.setTitle("CambridgeChimes-3Q");
    quarterThird.setTimeSignature(TimeSignature.FIVE_FOUR_TIME);
    quarterThird.setTempo(TEMPO);
    quarterThird.setInstrument(MusicPlayerService.CHIME);
    count = populateSegment4(quarterThird, 1);
    count = populateSegment5(quarterThird, count);
    count = populateSegment1(quarterThird, count);

    quarterFourth = new Song();
    quarterFourth.setTitle("CambridgeChimes-4Q");
    quarterFourth.setTimeSignature(TimeSignature.FIVE_FOUR_TIME);
    quarterFourth.setTempo(TEMPO);
    quarterFourth.setInstrument(MusicPlayerService.CHIME);
    count = populateSegment2(quarterFourth, 1);
    count = populateSegment3(quarterFourth, count);
    count = populateSegment4(quarterFourth, count);
    count = populateSegment5(quarterFourth, count);

    hourChime = new Song();
    hourChime.setTitle("CambridgeChimes-H");
    hourChime.setTimeSignature(TimeSignature.COMMON);
    hourChime.setTempo(TEMPO+16);
    hourChime.setInstrument(MusicPlayerService.CHIME);
    populateSegmentH(hourChime, 1);
  }

  private CambridgeChimes() {
  }
  
  private static int populateSegment1(Song s, int start)
  {
//  G♯4, F♯4, E4, B3
    int measureCount = start;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.G4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.F4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.E4, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.B3, Dynamic.FFF, NoteType.HALF));
    return measureCount++;
  }

  private static int populateSegment2(Song s, int start)
  {
//  E4, G♯4, F♯4, B3
    int measureCount = start;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.E4, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.G4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.F4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.B3, Dynamic.FFF, NoteType.HALF));
    return measureCount++;
  }

  private static int populateSegment3(Song s, int start)
  {
//  E4, F♯4, G♯4, E4
    int measureCount = start;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.E4, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.F4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.G4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.E4, Dynamic.FFF, NoteType.HALF));
    return measureCount++;
  }

  private static int populateSegment4(Song s, int start)
  {
//  G♯4, E4, F♯4, B3
    int measureCount = start;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.G4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.E4, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.F4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.B3, Dynamic.FFF, NoteType.HALF));
    return measureCount++;
  }

  private static int populateSegment5(Song s, int start)
  {
//  B3, F♯4, G♯4, E4
    int measureCount = start;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.B3, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.F4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.G4S, Dynamic.FFF, NoteType.QUARTER));
    m.add(new Note(Midi.E4, Dynamic.FFF, NoteType.HALF));
    return measureCount++;
  }

  private static int populateSegmentH(Song s, int start)
  {
    int measureCount = start;
    Measure m = s.lookup(measureCount++, VOICE);
    m.add(new Note(Midi.E3, Dynamic.FFFF, NoteType.DOTTED_HALF));
    m.add(new Rest(NoteType.QUARTER));
    return measureCount++;
  }


  public static Song getFirstQuarter()
  {
    return quarterFirst;
  }

  public static Song getSecondQuarter()
  {
    return quarterSecond;
  }

  public static Song getThirdQuarter()
  {
    return quarterThird;
  }

  public static Song getFourthQuarter()
  {
    return quarterFourth;
  }

  public static Song getHourChime()
  {
    return hourChime;
  }

}
