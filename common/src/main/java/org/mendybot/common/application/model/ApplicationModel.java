package org.mendybot.common.application.model;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.properties.DomainPropertySourceNotFoundException;
import org.mendybot.common.application.model.properties.PropertyManager;
import org.mendybot.common.domain.DomainModel;
import org.mendybot.common.exception.ExecuteException;

public class ApplicationModel
{
  private Logger LOG = Logger.getInstance(ApplicationModel.class);
  public static final String APPLICATION_PROPERTES = "APP";
  private PropertyManager properties;
  private DomainModel domain;

  public ApplicationModel(String name) throws ExecuteException
  {
    properties = new PropertyManager();
    try
    {
      properties.init(APPLICATION_PROPERTES, name + ".properties");
    }
    catch (DomainPropertySourceNotFoundException e)
    {
      LOG.logWarning("()", e);
    }
    
    domain = DomainModel.lookup(name);
  }

  /**
   * This method returns the model
   * 
   * @return DomainModel
   */
  public final DomainModel getDomain()
  {
    return domain;
  }

}
