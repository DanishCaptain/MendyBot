package org.mendybot.common.role.console.clip.speech;

import java.io.File;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.platform.CommandTool;
import org.mendybot.common.application.model.widget.speech.SpeechListener;
import org.mendybot.common.application.model.widget.speech.SpeechWidget;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.clip.Clip;

public class SpeechClip extends Clip implements SpeechListener
{
  private static final Logger LOG = Logger.getInstance(SpeechClip.class);
  private FileManager fileManager;
  private int currentVolume = 1400;

  public SpeechClip(ConsoleRole console, String name, String widgetName)
  {
    super(console, name, widgetName);
    fileManager = new FileManager(console.getModel());
    SpeechWidget widget = (SpeechWidget) console.getModel().lookupWidget(widgetName);
    widget.addSpeechListener(this);
    init();
  }
  
  private void init()
  { 
    int soundLevel = 90;
    String command = "amixer sset PCM,0 " + soundLevel + "%";
    LOG.logDebug("init", command);
    CommandTool tool = new CommandTool(command);
      try
      {
        tool.call();
      }
      catch (ExecuteException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      List<String> e = tool.getErrors();
      LOG.logDebug("init", e.toString());
      List<String> o = tool.getOutput();
      LOG.logDebug("init", o.toString());
  }

  @Override
  public void say(String phrase)
  {
    LOG.logDebug("say", phrase);
    
    File baseDir = fileManager.getWorkDirectory();
    File speechDir = new File(baseDir, "speech");
    if (!speechDir.exists()) {
      speechDir.mkdirs();
    }
    File phraseFile = new File(speechDir, phrase.replaceAll(" ", "_")+".wav");
    if (!phraseFile.exists()) {
      String command = "/opt/swift/bin/swift -n Diane -o " + phraseFile.getAbsolutePath() + " \"" + phrase + "\"";
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
    if (phraseFile.exists()) {
//      String command = "aplay " + phraseFile.getAbsolutePath();
      String command = "omxplayer --vol "+currentVolume+" " + phraseFile.getAbsolutePath();
      LOG.logDebug("say", command);
     CommandTool tool = new CommandTool(command);
      try
      {
        tool.call();
      }
      catch (ExecuteException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      List<String> e = tool.getErrors();
      LOG.logDebug("say", e.toString());
      List<String> o = tool.getOutput();
      LOG.logDebug("say", o.toString());
    }
  }

}
