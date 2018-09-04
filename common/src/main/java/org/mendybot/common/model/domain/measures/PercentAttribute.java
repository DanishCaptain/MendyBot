package org.mendybot.common.model.domain.measures;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.model.domain.DomainAttribute;
import org.mendybot.common.model.domain.attribute.DoubleAttribute;

public abstract class PercentAttribute extends DomainAttribute
{
  private static final long serialVersionUID = -7980774004545861385L;
  private static final Logger LOG = Logger.getInstance(DoubleAttribute.class);
  private Double defaultValue;

  protected PercentAttribute(String name)
  {
    this(name, 0);
  }

  protected PercentAttribute(String name, double defaultValue)
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
	      if (value instanceof Float) {
            return ((Float)value).doubleValue();
	      }
          else if (value instanceof Long)
          {
            return ((Long)value).doubleValue();
	      } 
          else if (value instanceof Integer)
          {
            return ((Integer)value).doubleValue();
          } 
	      else if (value instanceof String)
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
	          LOG.logSevere("getSafeType", "Attribute " + getName()
	              + " with invalid assignment try: " + value);
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
