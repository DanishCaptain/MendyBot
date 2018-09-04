package org.mendybot.common.model.domain.measures.pressure;

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
public final class PressureAttribute extends DomainAttribute
{
  private static final long serialVersionUID = 6049050523250618890L;
  private static final Logger LOG = Logger.getInstance(PressureAttribute.class);
  private Pressure defaultValue;

  /**
   * This class is used for boolean typed data.
   * 
   * @param name
   */
  public PressureAttribute(String name)
  {
    this(name, null);
  }

  /**
   * Constructor
   * 
   * @param name
   * @param defaultValue
   */
  public PressureAttribute(String name, Pressure defaultValue)
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
    if (!(value instanceof Pressure))
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
            int index = ((String) value).indexOf(' ');
            if (index >= 0) {
              r = new Pressure(PressureType.HECTOPASCALS, Precision.TWO, Double.parseDouble(((String) value).substring(0,  index)));
            } else {
              r = new Pressure(PressureType.HECTOPASCALS, Precision.TWO, Double.parseDouble((String) value));
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
