package org.mendybot.common.model.domain;

import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to indicate when an address is not found (lookup failed)
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class HierarchyAddressNotFoundException extends ExecuteException
{
  private static final long serialVersionUID = -6929526717555851824L;

  /**
   * Constructor
   * @param address
   */
  public HierarchyAddressNotFoundException(String address)
  {
    super(address);
  }

}
