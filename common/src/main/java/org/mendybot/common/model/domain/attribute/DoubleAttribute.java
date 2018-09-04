package org.mendybot.common.model.domain.attribute;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.model.domain.DomainAttribute;

/**
 * This attribute is used to define boolean type values
 * 
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public abstract class DoubleAttribute extends DomainAttribute
{
  private static final long serialVersionUID = -6285014389831591167L;
  private static final Logger LOG = Logger.getInstance(DoubleAttribute.class);
  private Double defaultValue;

  /**
   * This class is used for boolean typed data.
   * 
   * @param name
   */
  public DoubleAttribute(String name)
  {
    this(name, 0);
  }

  /**
   * Constructor
   * 
   * @param name
   * @param defaultValue
   */
  public DoubleAttribute(String name, double defaultValue)
  {
    super(name);
    this.defaultValue = defaultValue;
  }

  @Override
  protected Object objFromString(String sValue)
  {
    return Double.parseDouble(sValue);
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
    if (!(value instanceof Double))
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
            r = Double.parseDouble((String) value);
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
