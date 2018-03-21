package org.mendybot.common.application.service.status.linux;

import java.util.ArrayList;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.service.SystemService;
import org.mendybot.common.domain.DomainAttribute;
import org.mendybot.common.domain.DomainDefinition;
import org.mendybot.common.domain.DomainModel;
import org.mendybot.common.domain.TextAttribute;

public class UbuntuSystemStatus extends SystemService
{
  private Logger LOG = Logger.getInstance(UbuntuSystemStatus.class);
  private DomainModel domain;

  public UbuntuSystemStatus(DomainModel domain)
  {
    this.domain = domain;
    List<DomainAttribute> ddList = new ArrayList<DomainAttribute>();
    ddList.add(new TextAttribute("id"));
    DomainDefinition d = domain.createDefinition("SystemStatus", ddList);
    //domain.lookup(name);
  }

}
