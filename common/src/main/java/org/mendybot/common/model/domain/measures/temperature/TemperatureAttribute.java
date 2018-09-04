package org.mendybot.common.model.domain.measures.temperature;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.model.domain.DomainAttribute;
import org.mendybot.common.model.domain.measures.Precision;

/**
 * This attribute is used to define integer type values
 * 
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public final class TemperatureAttribute extends DomainAttribute
{
  private static final long serialVersionUID = 6049050523250618890L;
  private static final Logger LOG = Logger.getInstance(TemperatureAttribute.class);
  private Temperature defaultValue;

  /**
   * This class is used for boolean typed data.
   * 
   * @param name
   */
  public TemperatureAttribute(String name)
  {
    this(name, null);
  }

  /**
   * Constructor
   * 
   * @param name
   * @param defaultValue
   */
  public TemperatureAttribute(String name, Temperature defaultValue)
  {
    super(name);
    this.defaultValue = defaultValue;
  }

  @Override
  protected Object objFromString(String sValue)
  {
    return Integer.parseInt(sValue);
  }

  @Override
  public String toString(Object value)
  {
    if (value == null)
    {
      if (defaultValue == null)
      {
        return "[undefined]";
      }
      else
      {
        return defaultValue.toString();
      }
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
    if (!(value instanceof Temperature))
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
            int index = ((String) value).indexOf('\u00b0');
            if (index >= 0) {
              TemperatureType t = TemperatureType.lookup(((String)value).substring(index+1));
              if (t == null) {
                t = TemperatureType.FAHRENHEIT;
              }
              r = new Temperature(t, Precision.TWO, Double.parseDouble(((String) value).substring(0,  index)));
            } else {
              r = new Temperature(TemperatureType.FAHRENHEIT, Precision.TWO, Double.parseDouble((String) value));
            }
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
