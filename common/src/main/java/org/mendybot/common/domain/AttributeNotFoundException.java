package org.mendybot.common.domain;

import org.mendybot.common.exception.ExecuteException;

/**
 * This Exception is used to indicate an attribute was not found upon lookup
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public class AttributeNotFoundException extends ExecuteException
{
  private static final long serialVersionUID = -2994127838803000269L;

  /**
   * constructor
   * @param attributeName
   */
  public AttributeNotFoundException(String attributeName)
  {
    super(attributeName);
  }

}
