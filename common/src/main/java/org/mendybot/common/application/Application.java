package org.mendybot.common.application;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.application.model.SystemModel;
import org.mendybot.common.application.service.SystemService;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is the base class for all Applications
 * 
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public abstract class Application
{
  private static final Logger LOG = Logger.getInstance(Application.class);
  private final String name;
  private final ApplicationModel model;

  public Application(String name) throws ExecuteException
  {
    this.name = name;
    model = new ApplicationModel(name);
  }

  protected String getName()
  {
    return name;
  }

  /**
   * This method returns the model
   * 
   * @return DomainModel
   */
  public final ApplicationModel getModel()
  {
    return model;
  }

  protected final void init()
  {
    LOG.logDebug("init", "init called");
    SystemModel[] mList = getSystemModels(model);
    SystemService[] sList = getSystemServices();
  }

  protected abstract SystemModel[] getSystemModels(ApplicationModel model);

  protected abstract SystemService[] getSystemServices();

  protected final void start()
  {
    LOG.logDebug("start",  "start called");
  }

}
