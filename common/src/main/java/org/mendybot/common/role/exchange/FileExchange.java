package org.mendybot.common.role.exchange;

import java.io.File;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.ApplicationRole;

public class FileExchange extends ApplicationRole
{
  private static final Logger LOG = Logger.getInstance(FileExchange.class);
  public static final String ID = "FX";
  private File workDir;

  public FileExchange(ApplicationModel model) {
    super(model, ID);
    workDir = new File(model.getWorkingOptDir(), "archive");
    if (!workDir.exists()) {
      workDir.mkdirs();
    }
  }

  @Override
  public void init() throws ExecuteException
  {
    LOG.logInfo("init", "called");
  }

  @Override
  public void start() throws ExecuteException
  {
    LOG.logInfo("start", "called");
  }

  @Override
  public void stop()
  {
    LOG.logInfo("stop", "called");
  }
  
}
