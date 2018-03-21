package org.mendybot.common.application.service.status.linux;

import org.mendybot.common.application.service.status.ValueCollector;
import org.mendybot.common.domain.DomainModel;
import org.mendybot.common.domain.DomainObject;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to process information obtained from the OpenManage service on a Linux box
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public abstract class LinuxCollector extends ValueCollector
{
  public static final String  NO_FAULT                = "NO FAULT";
  public static final String  WARNING                 = "WARNING";
  public static final String  FAILED                  = "FAILED";

  //public static final String  STATUS = "Status";
  //public static final String  NO_FAULT = "NO FAULT";
  //public static final String  WARNING = "WARNING";

  /**
   * Constructor
   * @param model
   * @param baseObj
   * @param name
   * @throws ExecuteException
   */
  public LinuxCollector(DomainModel model, DomainObject baseObj, String oidName,
      String parentName, String name) throws ExecuteException
  {
    super(model, baseObj, oidName, parentName, name);
  }

}
