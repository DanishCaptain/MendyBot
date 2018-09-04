package org.mendybot.common.application.model.media.tune.domain;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Song
{
  private static final String PICKUP = "p";
  private HashMap<String, Voice> vMap = new HashMap<String, Voice>();
  private LinkedHashMap<Object, MeasureBlock> mbMap = new LinkedHashMap<Object, MeasureBlock>();
  private ArrayList<MeasureBlock> mbList = new ArrayList<MeasureBlock>();
  private TimeSignature timeSignature;
  private String title;
  private Tempo tempo = new Tempo(60);
  private int instrument=0;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title=title;
  }

  public int getTempo()
  {
    return tempo.getValue();
  }

  public void setTempo(int t)
  {
    tempo.setValue(t);
  }

  public void setTimeSignature(TimeSignature timeSignature)
  {
    this.timeSignature = timeSignature;
  }

  public Measure lookupPickup(String voiceKey)
  {
    Voice voice = lookupVoice(voiceKey);
    Measure m;
    MeasureBlock pickup = mbMap.get(PICKUP);
    if (pickup == null) {
      pickup = new MeasureBlock(this, true);
      mbMap.put(PICKUP, pickup);
      MeasureBlock first = mbMap.get(1);
      if (first != null) {
        pickup.link(first);
      }
    }
    m = pickup.getMeasure(voice);
    if (m == null) {
      m = new Measure(pickup, voice);
    }
    return m;
  }

  public Measure lookup(int index, String voiceKey)
  {
    Voice voice = lookupVoice(voiceKey);
    Measure m;
    MeasureBlock block = mbMap.get(index);
    if (block == null) {
      if (mbList.size() < index-1) {
        // add missing
        new MeasureBlock(this);
      }
      block = new MeasureBlock(this);
      mbMap.put(index, block);
      mbList.add(index-1, block);

      MeasureBlock parent;
      if (index==1) {
        parent = mbMap.get(PICKUP);
      } else {
        parent = mbMap.get(index-1);
      }
      if (parent != null) {
        parent.link(block);
      }
    }
    m = block.getMeasure(voice);
    if (m == null) {
      m = new Measure(block, voice);
    }
    return m;
  }

  private Voice lookupVoice(String voiceKey)
  {
    Voice voice = vMap.get(voiceKey);
    if (voice == null)
    {
      voice = new Voice(this, voiceKey);
      vMap.put(voiceKey, voice);
    }
    return voice;
  }

  public TimeSignature getTimeSignature()
  {
    return timeSignature;
  }

  /*
  public void writeBulbScript(String voiceKey)
  {
    Voice voice = lookupVoice(voiceKey);
//    File d = new File("/opt/archive/station/bulb_model");
    File d = new File(".");
    File f = new File(d, title+"_"+voiceKey+".mbc");
//    File f = new File(title+"_S.mbc");
    try
    {
      PrintWriter pw = new PrintWriter(new FileWriter(f));
      writeClosedCommand(pw, 1028);
      writeBulbScript(pw, voice, mbMap.get(PICKUP));
      for (MeasureBlock b : mbList) {
        writeBulbScript(pw, voice, b);
      }
      writeClosedCommand(pw, 10);
      pw.close();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  */

  /*
  private void writeBulbScript(PrintWriter pw, Voice voice, MeasureBlock b)
  {
    if (b != null) {
      Measure m = b.getMeasure(voice);
      if (m != null) {
        for (Playable p : m.getPlayables()) {
          int fv = 0;
          if (p.getFirmada()) {
            fv = 20;
          }
          writeMouthCommand(pw, p, getMilliseconds(p)+fv);
        }
      }
    }
  }
  */

  /*
  private int getMilliseconds(Playable p)
  {
    NoteType type = p.getDuration();
    double dd = timeSignature.getDuraction(type);
    System.out.println(tempo+":"+(dd*60000/tempo)+":");
    return (int) Math.round(dd*60000/tempo);
  }
  */

  private void writeClosedCommand(PrintWriter pw, int duration)
  {
    pw.println("|command|1|"+duration+"|");
  }

  private void writeOpenCommand(PrintWriter pw, int duration)
  {
    pw.println("|command|2|"+duration+"|");
  }

  private void writeMouthCommand(PrintWriter pw, Playable p, int duration)
  {
    int close = 50;
    if (p instanceof Note) {
      if (duration < close) {
        writeOpenCommand(pw, duration);
        writeClosedCommand(pw, 0);
      } else {
        writeOpenCommand(pw, duration-close);
        writeClosedCommand(pw, close);
      }
    } else {
      writeClosedCommand(pw, duration);
    }
  }

  /*
  public List<Measure> getMeasures(String voiceKey)
  {
    ArrayList<Measure> list = new ArrayList<Measure>();
    MeasureBlock pu = mbMap.get(PICKUP);
    if (pu != null) {
//      list.add(pu.getMeasure(lookupVoice(voiceKey)));
    }
    list.addAll(lookupVoice(voiceKey).getMeasures());
    return list;
  }
  */

  public List<Playable> getTune(String voiceKey)
  {
    ArrayList<Playable> list = new ArrayList<Playable>();
    list.add(tempo);

    List<Measure> mList = lookupVoice(voiceKey).getMeasures();
    for (Measure m : mList) {
      list.addAll(m.getPlayables());
    }

    return list;
  }

  public boolean isSane()
  {
    boolean sane = true;
    for (MeasureBlock b : mbList) {
      sane = sane && b.isSane(this.vMap.size());
    }
    return sane;
  }

  public void makeSane()
  {
    for (MeasureBlock b : mbList) {
      b.makeSane(vMap);
    }
  }

  public Map<Object, MeasureBlock> getBlocks()
  {
    return mbMap;
  }

  public void setInstrument(int instrument) {
    this.instrument = instrument;
  }
  
  public int getInstrument()
  {
    return instrument;
  }
}
