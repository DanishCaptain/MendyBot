package org.mendybot.common.domain;

/**
 * This Interface is used to define objects that are interested in changes in a
 * DomainProperty
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public interface DomainPropertyListener
{

  /**
   * This method is used to notify the listener of changes to a DomainProperty
   * @param event
   * The information describing the change
   */
  public void changed(DomainPropertyEvent event);

}
