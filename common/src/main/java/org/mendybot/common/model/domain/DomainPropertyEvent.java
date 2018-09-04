package org.mendybot.common.model.domain;

/**
 * This class is used to hold the necessary information for handling a change
 * to a DomainProperty
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class DomainPropertyEvent
{
  private DomainProperty property;
  private Object         oldValue;
  private Object         newValue;

  /**
   * Constructor
   * @param property
   * The property that was changed
   * @param oldValue
   * The previous value
   * @param newValue
   * The new value
   */
  public DomainPropertyEvent(
  		DomainProperty property, 
  		Object oldValue,
      Object newValue)
  {
    this.property = property;
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  /**
   * This method returns the changed property
   * @return changed object
   */
  public DomainProperty getSource()
  {
    return property;
  }

  /**
   * This method returns the previous value
   * @return Object
   */
  public Object getOldValue()
  {
    return oldValue;
  }

  /**
   * This method returns the new value
   * @return Object
   */
  public Object getNewValue()
  {
    return newValue;
  }

}
