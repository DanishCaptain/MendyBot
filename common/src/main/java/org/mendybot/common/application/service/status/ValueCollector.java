package org.mendybot.common.application.service.status;

import org.mendybot.common.domain.DomainModel;
import org.mendybot.common.domain.DomainObject;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to collect or monitor values from a data source.
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * 07/25/11 Brian Sorensen DR#59958 CR#WSC001249 - prune can not handle large data set deletes
 * </pre>
 */
public abstract class ValueCollector
{
  private DomainObject       dj;
  private String             name;
  private DomainModel        model;
  private String frugalValue = "";

  /**
   * Constructor. In addition to the constructor, the init method must be called as part of the
   * execution context prior considering objects as viable.
   * @param baseObj
   * @param name collector name
   * The name for the data value collection monitor
   * @throws ExecuteException
   */
  public ValueCollector(DomainModel model, DomainObject baseObj,
      String oidName, String parentName, String name) throws ExecuteException
  {
    this.model = model;
    this.name = name;
    dj = baseObj.getChild(name);
  }

  /**
   * This method returns the name of this data value collection monitor
   * @return String
   */
  public String getName()
  {
    return name;
  }

  /**
   * This method returns the DomainModel this ValueMonitor is using.
   * @return DomainModel
   */
  protected DomainModel getModel()
  {
    return model;
  }

  /**
   * This method returns the command the ValueMonitor uses to request the scoped data from
   * the data source.
   * @return Command message
   */
  public abstract String getCommand();

  /**
   * This method processes the given return message from the ValueMonitor and populates
   * the underlying DomainObject
   * @param vString The data source return value
   * @throws ExecuteException Indicating processing problem.
   */
  public abstract void processValue(String vString) throws ExecuteException;

  protected DomainObject getDomain()
  {
    return dj;
  }

  /**
   * This method is used to remove spaces in a given string.
   * @param s value
   * @return String
   */
  protected static String clean(String s)
  {
    return s.replaceAll(" ", "");
  }

  /**
   * This method is used to store the value used in computing the need for logging under the frugal strategy. 
   * @return log entry
   */
  public String getFrugalValue() 
  {
    return frugalValue;
  }

  /**
   * This method is used to return the value used in computing the need for logging under the frugal strategy. 
   * @return log entry
   */
  public void setFrugalValue(String v) 
  {
    frugalValue = v;
  }

}
