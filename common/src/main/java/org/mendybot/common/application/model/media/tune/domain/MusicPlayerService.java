package org.mendybot.common.application.model.media.tune.domain;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

import org.mendybot.common.exception.ExecuteException;

public class MusicPlayerService
{
  public final static int GRAND_PIANO = 0;
  public final static int CHIME = GRAND_PIANO;
  public final static int HARPSICHORD = 6;
  public final static int GLOCKENSPIEL = 9;
  public static final int MUSIC_BOX = 10;
  public final static int TRUMPET = 56;
  int E=425;
  int Q=E*2;
  int H=Q*2;
  int W=H*2;
  
  private HashMap<String, ChannelPlayer> channels = new HashMap<String, ChannelPlayer>();
  private Synthesizer midiSynth;
  private int channelCounter;
  private int defaultBPQ = 1000;

  public MusicPlayerService() throws ExecuteException
  {
    try
    {
      midiSynth = MidiSystem.getSynthesizer();
      midiSynth.open();
      Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
//      for (int i=0; i<instr.length; i++) {
//          System.out.println(i+":"+instr[i].getName());
//      }
    }
    catch (MidiUnavailableException e)
    {
      throw new ExecuteException(e);
    }
  }

  public ChannelPlayer lookupChannel(String id) {
    ChannelPlayer c = channels.get(id);
    if (c == null) {
      c = new ChannelPlayer(id, midiSynth.getChannels()[channelCounter++], defaultBPQ);
      channels.put(id, c);
    }
    return c;
  }

  public void setDefaultBPQ(int i)
  {
    defaultBPQ = i;
  }

  public void close()
  {
    for (Entry<String, ChannelPlayer> e : channels.entrySet()) {
      e.getValue().turnOff();
    }
    channels.clear();
    for (MidiChannel x : midiSynth.getChannels()) {
    }
    for (Transmitter x : midiSynth.getTransmitters()) {
      x.close();
    }
    midiSynth.unloadAllInstruments(midiSynth.getDefaultSoundbank());
    midiSynth.close();
    midiSynth = null;
    try
    {
      MidiSystem.getSequencer().close();
      MidiSystem.getReceiver().close();
//      MidiSystem.getTransmitter().close();
    }
    catch (MidiUnavailableException e1)
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }
  

}

/*
0:Acoustic Grand Piano
1:Bright Acoustic Pian
2:Electric Grand Piano
3:Honky-tonk Piano
4:Electric Piano 1
5:Electric Piano 2
6:Harpsichord
7:Clavi
8:Celesta
9:Glockenspiel
10:Music Box
11:Vibraphone
12:Marimba
13:Xylophone
14:Tubular Bells
15:Dulcimer
16:Drawbar Organ
17:Percussive Organ
18:Rock Organ
19:Church Organ
20:Reed Organ
21:Accordion
22:Harmonica
23:Tango Accordion
24:Acoustic Guitar (nyl
25:Acoustic Guitar (ste
26:Electric Guitar (jaz
27:Electric Guitar (cle
28:Electric Guitar (mut
29:Overdriven Guitar
30:Distortion Guitar
31:Guitar harmonics
32:Acoustic Bass
33:Electric Bass (finge
34:Electric Bass (pick)
35:Fretless Bass
36:Slap Bass 1
37:Slap Bass 2
38:Synth Bass 1
39:Synth Bass 2
40:Violin
41:Viola
42:Cello
43:Contrabass
44:Tremolo Strings
45:Pizzicato Strings
46:Orchestral Harp
47:Timpani
48:String Ensemble 1
49:String Ensemble 2
50:SynthStrings 1
51:SynthStrings 2
52:Choir Aahs
53:Voice Oohs
54:Synth Voice
55:Orchestra Hit
56:Trumpet
57:Trombone
58:Tuba
59:Muted Trumpet
60:French Horn
61:Brass Section
62:SynthBrass 1
63:SynthBrass 2
64:Soprano Sax
65:Alto Sax
66:Tenor Sax
67:Baritone Sax
68:Oboe
69:English Horn
70:Bassoon
71:Clarinet
72:Piccolo
73:Flute
74:Recorder
75:Pan Flute
76:Blown Bottle
77:Shakuhachi
78:Whistle
79:Ocarina
80:Lead 1 (square)
81:Lead 2 (sawtooth)
82:Lead 3 (calliope)
83:Lead 4 (chiff)
84:Lead 5 (charang)
85:Lead 6 (voice)
86:Lead 7 (fifths)
87:Lead 8 (bass + lead)
88:Pad 1 (new age)
89:Pad 2 (warm)
90:Pad 3 (polysynth)
91:Pad 4 (choir)
92:Pad 5 (bowed)
93:Pad 6 (metallic)
94:Pad 7 (halo)
95:Pad 8 (sweep)
96:FX 1 (rain)
97:FX 2 (soundtrack)
98:FX 3 (crystal)
99:FX 4 (atmosphere)
100:FX 5 (brightness)
101:FX 6 (goblins)
102:FX 7 (echoes)
103:FX 8 (sci-fi)
104:Sitar
105:Banjo
106:Shamisen
107:Koto
108:Kalimba
109:Bag pipe
110:Fiddle
111:Shanai
112:Tinkle Bell
113:Agogo
114:Steel Drums
115:Woodblock
116:Taiko Drum
117:Melodic Tom
118:Synth Drum
119:Reverse Cymbal
120:Guitar Fret Noise
121:Breath Noise
122:Seashore
123:Bird Tweet
124:Telephone Ring
125:Helicopter
126:Applause
127:Gunshot
128:Standard Kit
*/
