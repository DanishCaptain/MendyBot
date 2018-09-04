package org.mendybot.common.role.console.clip.tune;

import org.mendybot.common.application.model.media.tune.domain.ChannelPlayer;
import org.mendybot.common.application.model.media.tune.domain.MusicPlayerService;
import org.mendybot.common.application.model.media.tune.domain.Playable;
import org.mendybot.common.application.model.widget.media.tune.Tune;
import org.mendybot.common.application.model.widget.media.tune.TuneListener;
import org.mendybot.common.application.model.widget.media.tune.TuneWidget;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.clip.Clip;

public class TuneClip extends Clip implements TuneListener
{
  private MusicPlayerService player;
  private ChannelPlayer channelPlayerChime;

  public TuneClip(ConsoleRole console, String name, String widgetName) throws ExecuteException
  {
	  super(console, name, widgetName);
      player = new MusicPlayerService();
      player.setDefaultBPQ(750);
      channelPlayerChime = player.lookupChannel("Chime");
      TuneWidget widget = (TuneWidget) console.getModel().lookupWidget(widgetName);
      widget.addTuneListener(this);
      
  }
  
  @Override
  public void play(Tune tune)
  {
    System.out.println("playing: "+tune.getSong().getTitle());
//  player.setInstrument(s.getInstrument());
    for (Playable p : tune.getSong().getTune(tune.getVoice())) {
      channelPlayerChime.enqueue(p);
    }
  }

}
