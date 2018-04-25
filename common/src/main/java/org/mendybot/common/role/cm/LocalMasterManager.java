package org.mendybot.common.role.cm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.archive.Manifest;

public class LocalMasterManager extends MasterManager implements Runnable
{
  private static final Logger LOG = Logger.getInstance(LocalMasterManager.class);
  public static final int PORT = 3000;
  public static final String ID = "CMMM";
  public static final byte COMMAND_GET_SET_LIST = 10;
  private Thread t = new Thread(this);
  private File workDir;
  // private File masterDir;
  private FileManager fileManager;
  private boolean running;
  private ServerSocket ss;

  public LocalMasterManager(ApplicationModel model)
  {
    super(model);
    workDir = new File(model.getWorkingOptDir(), model.getName());
    if (!workDir.exists())
    {
      workDir.mkdirs();
    }
  }

  public void init() throws ExecuteException
  {
    LOG.logInfo("init", "called");
    fileManager = getModel().lookupApplicationModel(FileManager.ID);
    if (fileManager != null)
    {
      fileManager.initContext(ID);
    }
    try
    {
      ss = new ServerSocket(PORT);
    }
    catch (IOException e)
    {
      throw new ExecuteException(e);
    }
    t.setName(getClass().getSimpleName());
    t.setDaemon(true);
  }

  public void start() throws ExecuteException
  {
    LOG.logInfo("start", "called");
    t.start();
  }

  public void stop()
  {
    LOG.logInfo("stop", "called");
    running = false;
    t.interrupt();
  }

  @Override
  public List<Manifest> getSets()
  {
    List<Manifest> vSet = fileManager.getSets(ID);
    LOG.logInfo("getSets", "versions: " + vSet);
    return vSet;
  }

  @Override
  public void run()
  {
    running = true;
    while(running) {
      try
      {
        Socket socket = ss.accept();
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        InputStream is = socket.getInputStream();
        byte command = (byte) is.read();
        if (command == COMMAND_GET_SET_LIST)
        {
          os.writeObject(getSets());
        }
        os.close();
        is.close();
        socket.close();
      }
      catch (IOException e)
      {
        LOG.logSevere("run", e);
      }
    }
  }

}
