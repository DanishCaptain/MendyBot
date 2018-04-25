package org.mendybot.common.role.cm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.archive.Manifest;

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
    LOG.logInfo("init", "called");
    host = getModel().getProperty(ConfigurationManager.ID+".MasterHost");
    if (host == null)
    {
      throw new ExecuteException("property for CM master host is missing");
    }
  }

  public void start() throws ExecuteException
  {
    LOG.logInfo("start", "called");
  }

  public void stop()
  {
    LOG.logInfo("stop", "called");
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Manifest> getSets() throws ExecuteException
  {
    List<Manifest> list;
    Socket socket = null;
    try
    {
      socket = new Socket(host, LocalMasterManager.PORT);
      OutputStream os = socket.getOutputStream();
      ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
      os.write(LocalMasterManager.COMMAND_GET_SET_LIST);
      list =  (List<Manifest>) is.readObject();
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
    return list;
  }

}
