package org.mendybot.common.role.cm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.archive.Manifest;
import org.mendybot.common.role.archive.ManifestEntry;

public class LocalMasterManager extends MasterManager implements Runnable
{
  private static final Logger LOG = Logger.getInstance(LocalMasterManager.class);
  public static final int PORT = 3000;
  public static final String ID = "CMMM";
  public static final byte COMMAND_GET_SET_LIST = 10;
  public static final byte COMMAND_GET_SET_LIST_ALLOWED_RESTRICTED = 11;
  public static final byte COMMAND_FILE_REQUEST = 20;
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
    LOG.logDebug("init", "called");
    fileManager = (FileManager) getModel().lookupApplicationModel(FileManager.ID);
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
    LOG.logDebug("start", "called");
    t.start();
  }

  public void stop()
  {
    LOG.logDebug("stop", "called");
    running = false;
    t.interrupt();
  }

  @Override
  public Map<String, Manifest> getSets()
  {
    Map<String, Manifest> vSet = fileManager.getSets(ID);
    LOG.logDebug("getSets-"+ID, "versions: " + vSet);
    return vSet;
  }

  @Override
  public Map<String, Manifest> getSets(List<String> namesAllowed) throws ExecuteException
  {
    Map<String, Manifest> vSet = fileManager.getSets(namesAllowed, ID);
    LOG.logDebug("getSets-"+ID, "versions: " + vSet);
    return vSet;
  }

  @Override
  public void requestAdd(String id, FileManager fm, Map<String, Manifest> delta) throws ExecuteException
  {
    for (Entry<String, Manifest> mSet : delta.entrySet())
    {
      for (Entry<String, ManifestEntry> eSet : mSet.getValue().getEntries().entrySet())
      {
        ManifestEntry e = eSet.getValue();
        File f = fileManager.lookupFile(ID, mSet.getValue().getName(), e.getName());
        fm.copyTo(id, e.getName(), eSet.getValue().getName(), e.getLastModified(), f);
      }
    }
  }

  @Override
  public void requestRemove(String id, FileManager fm, Map<String, Manifest> delta) throws ExecuteException
  {
    for (Entry<String, Manifest> mSet : delta.entrySet())
    {
      for (Entry<String, ManifestEntry> eSet : mSet.getValue().getEntries().entrySet())
      {
        fm.remove(id, mSet.getValue().getName(), eSet.getValue().getName());
      }
    }
  }

  @Override
  public void run()
  {
    running = true;
    while(running) {
      try
      {
        Socket socket = ss.accept();
        System.out.println("got LocalMasterManager.cm.call");
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        byte command = (byte) is.read();
        if (command == COMMAND_GET_SET_LIST)
        {
          LOG.logDebug("run", "got: Command - get set list");
          os.writeObject(getSets());
          os.flush();
        }
        else if (command == COMMAND_GET_SET_LIST_ALLOWED_RESTRICTED)
        {
          LOG.logDebug("run", "got: Command - get set list - allowed restricted");
          @SuppressWarnings("unchecked")
          List<String> namesAllowed = (List<String>) is.readObject();
          os.writeObject(getSets(namesAllowed));
          os.flush();
        }
        else if (command == COMMAND_FILE_REQUEST)
        {
          LOG.logDebug("run", "got: Command - file request");
          String id = is.readUTF();
          String manifestName = is.readUTF();
          String entryName = is.readUTF();

          File f = fileManager.lookupFile(ID, manifestName, entryName);
          FileInputStream fis = new FileInputStream(f);
          os.writeLong(f.length());
          os.flush();
          while(fis.available() > 0) {
            os.write(fis.read());
          }
          fis.close();
          os.flush();
        }
        os.close();
        is.close();
        socket.close();
      }
      catch (Exception e)
      {
        LOG.logSevere("run", e);
      }
    }
  }

}
