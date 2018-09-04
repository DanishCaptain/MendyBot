package org.mendybot.common.role.console.clip.media.audio;

import java.io.File;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.platform.CommandTool;
import org.mendybot.common.application.model.widget.media.audio.AudioWidget;
import org.mendybot.common.application.model.widget.media.audio.Audio;
import org.mendybot.common.application.model.widget.media.audio.AudioListener;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.clip.Clip;

public class AudioClip extends Clip implements AudioListener
{
  private static final Logger LOG = Logger.getInstance(AudioClip.class);
  private FileManager fileManager;

  public AudioClip(ConsoleRole console, String name, String widgetName)
  {
    super(console, name, widgetName);
    fileManager = new FileManager(console.getModel());
    AudioWidget widget = (AudioWidget) console.getModel().lookupWidget(widgetName);
    widget.addAudioListener(this);
  }
  
  @Override
  public void play(Audio audio)
  {
    LOG.logDebug("play", audio.getName());
    
    File baseDir = fileManager.getWorkDirectory();
    File mediaDir = new File(baseDir, "media");
    File audioDir = new File(mediaDir, "audio");
    if (!audioDir.exists()) {
      audioDir.mkdirs();
    }
    File audioFile = new File(audioDir, audio.getFileName());
    if (!audioFile.exists()) {
      String command = "aplay " + audioFile.getAbsolutePath();
      LOG.logDebug("say", command);
      CommandTool tool = new CommandTool(command);
      try
      {
        tool.call();
        
      }
      catch (ExecuteException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      List<String> e = tool.getErrors();
      LOG.logDebug("say", e.toString());
      List<String> o = tool.getOutput();
      LOG.logDebug("say", o.toString());      
    }
  }

}
