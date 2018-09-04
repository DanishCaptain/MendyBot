package org.mendybot.common.application.model.widget.weather;

import org.mendybot.common.model.domain.DomainObject;

public interface WeatherListener
{

  void change(DomainObject current);

}
