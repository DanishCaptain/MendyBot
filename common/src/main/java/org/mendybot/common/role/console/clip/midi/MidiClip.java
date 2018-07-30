package org.mendybot.common.role.console.clip.midi;

import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.clip.Clip;

public class MidiClip extends Clip// implements ClockListener
{

  public MidiClip(ConsoleRole console, String name, String widgetName)
  {
	  super(console, name, widgetName);
  }
  
  @Override
  public void play()
  {
  }

}
