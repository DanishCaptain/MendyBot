package org.mendybot.common.model.domain.attribute;

import org.mendybot.common.model.domain.DomainAttribute;

/**
 * 
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public abstract class TextAttribute extends DomainAttribute
{
  private static final long serialVersionUID = -5143751899696545109L;

  /**
   * Constructor
   * @param name
   */
  public TextAttribute(String name)
  {
    super(name);
  }

  @Override
  protected Object objFromString(String sValue)
  {
    return sValue;
  }

  @Override
  public String toString(Object value)
  {
    if (value == null)
    {
      return "";
    }
    else
    {
      return value.toString();
    }
  }

  @Override
  public Object getDefaultValue()
  {
    return "";
  }

  @Override
  public Object getSafeType(Object value)
  {
    Object r;
    if (value == null)
    {
      r = "";
    }
    else if (!(value instanceof String))
    {
      r = value.toString();
    }
    else
    {
      r = value;
    }
    return r;
  }
}
