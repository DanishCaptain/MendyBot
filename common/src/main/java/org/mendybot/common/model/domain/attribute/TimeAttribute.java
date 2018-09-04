package org.mendybot.common.model.domain.attribute;

import org.mendybot.common.application.log.Logger;
import java.time.LocalTime;

import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.model.domain.DomainAttribute;

public final class TimeAttribute extends DomainAttribute
{
  private static final long serialVersionUID = -5153567643790334389L;
  private static final Logger LOG = Logger.getInstance(TimeAttribute.class);
  private LocalTime defaultValue;

  public TimeAttribute(String name)
  {
    this(name, LocalTime.now());
  }

  public TimeAttribute(String name, LocalTime defaultValue)
  {
    super(name);
    this.defaultValue = defaultValue;
  }

  @Override
  protected Object objFromString(String sValue)
  {
    return LocalTime.parse(sValue);
  }

  @Override
  public String toString(Object value)
  {
    if (value == null)
    {
      return defaultValue.toString();
    }
    else
    {
      return value.toString();
    }
  }

  @Override
  public Object getDefaultValue()
  {
    return defaultValue;
  }

  @Override
  public Object getSafeType(Object value) throws ExecuteException
  {
    Object r = value;
    if (!(value instanceof LocalTime))
    {
      if (value instanceof String)
      {
        try
        {
          if ("".equals(value))
          {
            r = 0;
          }
          else
          {
            r = LocalTime.parse((String) value);
          }
        }
        catch (Exception e)
        {
          LOG.logSevere("getSafeType", "Attribute " + getName() + " with invalid assignment try: " + value);
        }
      }
      else
      {
        r = 0;
      }
    }
    return r;
  }
}
