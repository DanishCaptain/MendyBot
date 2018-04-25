package org.mendybot.common.role;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;

public abstract class ApplicationRole implements MendyBotRole
{
	  private static final Logger LOG = Logger.getInstance(ApplicationRole.class);
	  private ApplicationModel model;
    private String id;

	  public ApplicationRole(ApplicationModel model, String id) {
	    this.model = model;
	    this.id = id;
	    LOG.logInfo("()", "loaded: "+getClass().getName());
	  }
	  
	  public ApplicationModel getModel()
	  {
	    return model;
	  }

	  public String getId()
	  {
	    return id;
	  }

}
