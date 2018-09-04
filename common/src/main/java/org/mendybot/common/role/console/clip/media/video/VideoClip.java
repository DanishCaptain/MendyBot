package org.mendybot.common.role.console.clip.media.video;

import java.io.File;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.platform.CommandTool;
import org.mendybot.common.application.model.widget.media.video.VideoWidget;
import org.mendybot.common.application.model.widget.media.video.Video;
import org.mendybot.common.application.model.widget.media.video.VideoListener;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.clip.Clip;

public class VideoClip extends Clip implements VideoListener
{
  private static final Logger LOG = Logger.getInstance(VideoClip.class);
  private FileManager fileManager;

  public VideoClip(ConsoleRole console, String name, String widgetName)
  {
    super(console, name, widgetName);
    fileManager = new FileManager(console.getModel());
    VideoWidget widget = (VideoWidget) console.getModel().lookupWidget(widgetName);
    widget.addVideoListener(this);
  }
  
  @Override
  public void play(Video video)
  {
    LOG.logDebug("play", video.getName());
    
    File baseDir = fileManager.getWorkDirectory();
    File mediaDir = new File(baseDir, "media");
    File videoDir = new File(mediaDir, "video");
    if (!videoDir.exists()) {
      videoDir.mkdirs();
    }
    File videoFile = new File(videoDir, video.getFileName());
    if (videoFile.exists()) {
      String command = "omxplayer " + videoFile.getAbsolutePath();
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
