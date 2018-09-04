package org.mendybot.common.model.domain;

import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to indicate a DomainObject was not found from the lookup attempt.
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class DomainObjectNotFoundException extends ExecuteException
{
  private static final long serialVersionUID = -8604756371555378672L;

  /**
   * constructor
   * @param address
   * the string representation of the address
   */
  public DomainObjectNotFoundException(String address)
  {
    super(address);
  }

  /**
   * constructor
   * @param address
   * value
   */
  public DomainObjectNotFoundException(HierarchyAddress address)
  {
    super(address.toString());
  }

}
