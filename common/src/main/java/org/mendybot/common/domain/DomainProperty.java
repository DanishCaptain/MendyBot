package org.mendybot.common.domain;

import java.io.Serializable;
import java.util.ArrayList;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class holds the values as defined from a given DomainAttribute
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * 08/11/10 Brian Sorensen IDR GDISR30
 * </pre>
 */
public class DomainProperty implements Serializable
{
  private static final long                 serialVersionUID = -37677051699714633L;
  private Logger LOG = Logger.getInstance(DomainProperty.class);
  private DomainAttribute                   attribute;
  private Object                            value;
  private ArrayList<DomainPropertyListener> listeners        = new ArrayList<DomainPropertyListener>();

  /**
   * constructor
   * @param attribute
   * defining attribute type
   */
  DomainProperty(DomainAttribute attribute)
  {
    this.attribute = attribute;

  }

  /**
   * This method returns the name of this property as defined by the defining attribute
   * @return String
   */
  public String getName()
  {
    return attribute.getName();
  }

  /**
   * This method returns the current value
   * @return Object
   */
  Object getValue()
  {
    return value;
  }

  /**
   * This method returns the current value in its String representation
   * @return String
   */
  String getStringValue()
  {
    return attribute.toString(value);
  }

  /**
   * This method sets the current value to the given value. The appropriate
   * DomainPropertyListeners are notified of this change if it is different than the
   * old value.
   * @param value
   */
  void setValue(Object value)
  {
    Object oldValue = this.value;
    if ((oldValue == null && value == null)
        || (oldValue != null && oldValue.equals(value)))
    {
      return;
    }
    try
    {
      this.value = attribute.getSafeType(value);
    }
    catch (ExecuteException e)
    {
      this.value = oldValue;
      LOG.logSevere("setValue", e);
      return;
    }
    DomainPropertyEvent event = new DomainPropertyEvent(this, oldValue, this.value);
    for (int i = 0; i < listeners.size(); i++)
    {
      DomainPropertyListener listener = listeners.get(i);
      listener.changed(event);
    }
  }

  /**
   * This method sets the value to the attributes's default
   */
  void setToDefault()
  {
    setValue(attribute.getDefaultValue());
  }

  @Override
  public String toString()
  {
    return attribute.toString(value);
  }

  /**
   * This method adds a change listener so it will be notified when changes are made.
   * @param dpl
   * new listener
   */
  public void addDomainPropertyListener(DomainPropertyListener dpl)
  {
    listeners.add(dpl);
    DomainPropertyEvent event = new DomainPropertyEvent(this, value, value);
    dpl.changed(event);
  }

  /**
   * This method removes the given listener from the change notification list.
   * @param dpl
   * listener
   */
  public void removeDomainPropertyListener(DomainPropertyListener dpl)
  {
    listeners.remove(dpl);
  }

}
