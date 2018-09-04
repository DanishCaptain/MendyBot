package org.mendybot.common.model.domain;

import java.io.Serializable;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to define the type of data used in data collection.
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * 08/11/10 Brian Sorensen IDR GDISR30
 * </pre>
 */
public abstract class DomainAttribute implements Serializable
{
  private Logger              LOG              = Logger
                                                   .getInstance(DomainAttribute.class);
  private static final long   serialVersionUID = 2067595085022654840L;
  public static final String  TEXT             = "TEXT";
  public static final String  BOOLEAN          = "BOOLEAN";
  private static final String LOG_INIT         = "init";
  private String              name;

  /**
   * Constructor.
   * @param name
   * Name used to lookup up attribute
   */
  protected DomainAttribute(String name)
  {
    this.name = name;
  }

  /**
   * This method returns the name of the DomainAttribute. It is used in indexing, etc.
   * @return String
   */
  public String getName()
  {
    return name;
  }

  /**
   * This method is used to initialize the given DomainObject by populating the
   * appropriate DomainProperty object.
   * @param dj
   * the Domain Object to initialize
   * @see DomainProperty
   */
  final void populate(DomainObject domainObject)
  {
    DomainProperty prop = new DomainProperty(this);
    domainObject.add(prop);
  }

  /**
   * This method is used to initialize the given DomainObject.
   * @param dj
   */
  final void init(DomainObject dj)
  {
    try
    {
      dj.setValue(name, getDefaultValue());
    }
    catch (AttributeNotFoundException e)
    {
      LOG.logSevere(LOG_INIT, e);
    }
  }

  /**
   * This method returns the default value for this attribute
   * @return Object
   */
  public abstract Object getDefaultValue();

  /**
   * This method translates and returns an appropriate categorized object
   * from the given string representation
   * @param sValue
   * The value to translate
   * @return The translated object
   * @see #objFromString(String)
   */
  public final Object fromString(String sValue)
  {
    return objFromString(sValue);
  }

  /**
   * This method translates and returns an appropriate categorized object
   * from the given string representation
   * @param sValue
   * The value to translate
   * @return the translated object
   */
  protected abstract Object objFromString(String sValue);

  /**
   * This method returns the appropriate String representation from the given Object value
   * @param value
   * The object to translate to String
   * @return The string representation.
   */
  public abstract String toString(Object value);

  /**
   * This method returns an adjusted value that is safe for storage in this attribute
   * @param value
   * @return Object
   * @throws ExecuteException if the given value is not valid or safe
   */
  public abstract Object getSafeType(Object value) throws ExecuteException;

  @Override
  public String toString()
  {
    return name;
  }
}
