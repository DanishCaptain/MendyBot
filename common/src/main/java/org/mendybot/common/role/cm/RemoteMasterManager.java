package org.mendybot.common.role.cm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.FileManager;
import org.mendybot.common.role.archive.Manifest;
import org.mendybot.common.role.archive.ManifestEntry;

public class RemoteMasterManager extends MasterManager
{
  private static final Logger LOG = Logger.getInstance(RemoteMasterManager.class);
  private String host;

  public RemoteMasterManager(ApplicationModel model)
  {
    super(model);
  }

  public void init() throws ExecuteException
  {
    LOG.logDebug("init", "called");
    host = getModel().getProperty(ConfigurationManager.ID+".MasterHost");
    if (host == null)
    {
      throw new ExecuteException("property for CM master host is missing");
    }
  }

  public void start() throws ExecuteException
  {
    LOG.logDebug("start", "called");
  }

  public void stop()
  {
    LOG.logDebug("stop", "called");
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, Manifest> getSets() throws ExecuteException
  {
    Map<String, Manifest> map;
    Socket socket = null;
    try
    {
      socket = new Socket(host, LocalMasterManager.PORT);
      OutputStream os = socket.getOutputStream();
      ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
      os.write(LocalMasterManager.COMMAND_GET_SET_LIST);
      os.flush();
      map =  (Map<String, Manifest>) is.readObject();
      os.close();
      is.close();
      socket.close();
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
    finally
    {
      if (socket != null) {
        try
        {
          socket.close();
        }
        catch (IOException e)
        {
          // don't care a lot
          LOG.logWarning("getSets", e);
        }
      }
    }
    return map;
  }

  @Override
  public void requestAdd(String id, FileManager fm, Map<String, Manifest> delta) throws ExecuteException
  {
    LOG.logDebug("request", "called");
    for (Entry<String, Manifest> mSet : delta.entrySet())
    {
      for (Entry<String, ManifestEntry> eSet : mSet.getValue().getEntries().entrySet())
      {
        ManifestEntry e = eSet.getValue();
        File f = grabRemoteFile(LocalMasterManager.ID, mSet.getValue().getName(), e.getName());
        fm.copyTo(id, mSet.getValue().getName(), e.getName(), e.getLastModified(), f);
        f.delete();
      }
    }
  }

  @Override
  public void requestRemove(String id, FileManager fm, Map<String, Manifest> delta) throws ExecuteException
  {
    LOG.logDebug("request", "called");
    for (Entry<String, Manifest> mSet : delta.entrySet())
    {
      for (Entry<String, ManifestEntry> eSet : mSet.getValue().getEntries().entrySet())
      {
        fm.remove(id, mSet.getValue().getName(), eSet.getValue().getName());
      }
    }
  }

  private File grabRemoteFile(String id, String manifestName, String entryName) throws ExecuteException
  {
    LOG.logDebug("lookupRemoteFile", "called");
    Socket socket = null;
    try
    {
      socket = new Socket(host, LocalMasterManager.PORT);
      ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
      os.write(LocalMasterManager.COMMAND_FILE_REQUEST);
      os.flush();
      os.writeUTF("id");
      os.flush();
      os.writeUTF(manifestName);
      os.flush();
      os.writeUTF(entryName);
      os.flush();
      
      File f = File.createTempFile("MB_", "_XFER", new File("/tmp"));
      FileOutputStream fw = new FileOutputStream(f);
      long length = is.readLong();
      for (long i=0; i<length; i++) {
        fw.write(is.read());
      }
      
      fw.close();
      os.close();
      is.close();
      socket.close();
      return f;
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
    finally
    {
      if (socket != null) {
        try
        {
          socket.close();
        }
        catch (IOException e)
        {
          // don't care a lot
          LOG.logWarning("getSets", e);
        }
      }
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, Manifest> getSets(List<String> namesAllowed) throws ExecuteException
  {
    Map<String, Manifest> map;
    Socket socket = null;
    try
    {
      socket = new Socket(host, LocalMasterManager.PORT);
      ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
      os.write(LocalMasterManager.COMMAND_GET_SET_LIST_ALLOWED_RESTRICTED);
      os.flush();
      os.writeObject(namesAllowed);
      os.flush();
      map =  (Map<String, Manifest>) is.readObject();
      os.close();
      is.close();
      socket.close();
    }
    catch (Exception e)
    {
      LOG.logWarning("getSets", host+"::"+e.getMessage());
      return new HashMap<>();
    }
    finally
    {
      if (socket != null) {
        try
        {
          socket.close();
        }
        catch (IOException e)
        {
          // don't care a lot
          LOG.logWarning("getSets", e);
        }
      }
    }
    return map;
  }

}
