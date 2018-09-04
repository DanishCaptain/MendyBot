package org.mendybot.common.application.model.media.tune.domain;

import java.util.ArrayList;

import javax.sound.midi.MidiChannel;

import org.mendybot.common.application.log.Logger;

public class ChannelPlayer implements Runnable
{
  private Logger LOG = Logger.getInstance(ChannelPlayer.class);
  private ArrayList<Playable> queue = new ArrayList<Playable>();
  private Thread t = new Thread(this);
  private String name;
  private MidiChannel c;
  private boolean running;
  private boolean on = true;
  private int bpq;

  public ChannelPlayer(String name, MidiChannel c, int bpq) {
    this.name = name;
    this.c = c;
    this.bpq = bpq;
    t.start();
  }
  
  public String getName()
  {
    return name;
  }

  public void enqueue(Playable p) {
    queue.add(p);
    synchronized (this) {
      notifyAll();
    }
  }
  
  @Override
  public void run()
  {
    
    running = true;
    while(running) {
      while(queue.size() > 0) {
        Playable p = queue.remove(0);
        LOG.logDebug("run", "name "+queue.size());
        if (on) {
          if (p instanceof Note) {
            Note n = (Note) p;
            c.noteOn(n.getPitch().getValue(), n.getDynamic().getValue());
            boolean interrupted = holdForDuration(n.getDuration(), getFirmada(n, bpq));
            if (interrupted) {
              break;
            }
            if (queue.size() == 0) {
              c.noteOff(n.getPitch().getValue());
            }
          } else if (p instanceof Rest) {
            Rest r = (Rest) p;
            boolean interrupted = holdForDuration(r.getDuration(), getFirmada(r, bpq));
            if (interrupted) {
              break;
            }
          } else if (p instanceof Tempo) {
            Tempo t = (Tempo) p;
            bpq = Math.round(60000/t.getValue());
          }
        }
      }
      synchronized (this) {
        try {
          wait();
        } catch (InterruptedException ex) {
        }
      }
      Thread.yield();
    }
  }

  private boolean holdForDuration(NoteType noteType, int firmadaTime)
  {
    try
    {
      if (NoteType.QUARTER == noteType) {
        sleep(bpq+firmadaTime);
      }
      else if (NoteType.EIGHTH == noteType) {
        sleep(bpq/2+firmadaTime);
      } 
      else if (NoteType.SIXTEENTH == noteType) {
          sleep(bpq/4+firmadaTime);
      }
      else if (NoteType.WHOLE == noteType) {
        sleep(bpq*4+firmadaTime);
      }
      else if (NoteType.HALF == noteType) {
        sleep(bpq*2+firmadaTime);
      }
      else if (NoteType.THIRTYSECOND == noteType) {
        sleep(bpq/8+firmadaTime);
      }
      else if (NoteType.SIXTYFOURTH == noteType) {
        sleep(bpq/16+firmadaTime);
      }
      else if (NoteType.ONETWENTYEIGHTH == noteType) {
        sleep(bpq/32+firmadaTime);
      }
      else if (NoteType.DOTTED_QUARTER == noteType) {
        sleep(bpq+bpq/2+firmadaTime);
      }
      else if (NoteType.DOTTED_EIGHTH == noteType) {
        sleep(bpq/2+bpq/4+firmadaTime);
      }
      else if (NoteType.DOTTED_SIXTEENTH == noteType) {
        sleep(bpq/4+bpq/8+firmadaTime);
      }
      else if (NoteType.DOTTED_HALF == noteType) {
        sleep(bpq*2+bpq+firmadaTime);
      }
      else if (NoteType.DOTTED_THIRTYSECOND == noteType) {
        sleep(bpq/8+bpq/16+firmadaTime);
      }
      else if (NoteType.DOTTED_SIXTYFOURTH == noteType) {
        sleep(bpq/16+bpq/32+firmadaTime);
      }
      else if (NoteType.TRIPLET_SIXTEENTH == noteType) {
        sleep(bpq/2/3+firmadaTime);
      }
      else if (NoteType.TRIPLET_EIGHTH == noteType) {
        sleep(bpq/3+firmadaTime);
      }
      else if (NoteType.NONE == noteType) {
      }
      else {
        throw new RuntimeException("missing duration");
      }
    }
    catch (InterruptedException e)
    {
      running = false;
      return true;
    }
    return false;
  }

  private int getFirmada(Playable n, int bpq)
  {
    if (n.getFirmada()) {
      return bpq;
    } else {
      return 0;
    }
  }

  private void sleep(int duration) throws InterruptedException
  {
    Thread.sleep(duration);
  }

  public int size()
  {
    return queue.size();
  }

  public void turnOn()
  {
    on = true;
  }

  public void turnOff()
  {
    on = false;
  }

  public void setInstrument(int i)
  {
    c.programChange(i);
  }

  public void noteOn(int noteNumber, int velocity)
  {
    c.noteOn(noteNumber, velocity);
  }

  public void noteOff(int noteNumber, int velocity)
  {
    c.noteOff(noteNumber, velocity);
  }

  
}
