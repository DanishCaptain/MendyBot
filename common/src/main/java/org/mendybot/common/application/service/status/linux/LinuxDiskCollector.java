package org.mendybot.common.application.service.status.linux;

import java.util.StringTokenizer;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.domain.AttributeNotFoundException;
import org.mendybot.common.domain.DomainModel;
import org.mendybot.common.domain.DomainObject;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to process temperature information obtained from the OpenManage service on a Linux box
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class LinuxDiskCollector extends LinuxCollector
{
  private static final Logger LOG               = Logger.getInstance(LinuxDiskCollector.class);
  private static final String LOG_PROCESS_VALUE = "processValue";
  public static final String  STATUS                        = "Status";
  public static final String  STATUS_OK                     = "Ok";
  public static final String  STATUS_NON_CRITICAL           = "Non-Critical";
  public static final String  STATUS_CRITICAL               = "Critical";
  
  public static final String  DISK_FAULT                    = "diskFault";

  public String               MsctsDisplay      = "Main System Storage State";

  /**
   * Constructor
   * @param model
   * @param baseObj
   * @param name
   * @throws ExecuteException
   */
  public LinuxDiskCollector(DomainModel model, DomainObject baseObj,
      String oidName, String parentName, String name) throws ExecuteException
  {
    super(model, baseObj, oidName, parentName, name);
  }

  @Override
  public String getCommand()
  {
    return "omreport storage pdisk controller=0 -fmt cdv";
  }

  @Override
  public void processValue(String vString)
  {
    /*
     * StringBuffer sb = new StringBuffer();
     * sb.append("List of Physical Disks on Controller PERC 6/i Integrated (Embedded)\n");
     * sb.append("\n");
     * sb.append("Controller PERC 6/i Integrated (Embedded)\n");
     * sb.append("\n");
     * sb.append(
     * "ID;Status;Name;State;Failure Predicted;Progress;Type;Capacity;Used RAID Disk Space;Available RAID Disk Space;Hot Spare;Vendor ID;Product ID;Revision;Serial No.;Negotiated Speed;Capable Speed;Manufacture Day;Manufacture Week;Manufacture Year;SAS Address\n"
     * );
     * sb.append(
     * "0:0:0;Ok;Physical Disk 0:0:0;Online;No;Not Applicable;SAS;67.75 GB (72746008576 bytes);67.75 GB (72746008576 bytes);0.00 GB (0 bytes);No;DELL    ;MBA3073RC       ;D306;BLL3P87027D5        ;Not Available;Not Available;04;31;2008;500000E01C856082\n"
     * );
     * sb.append(
     * "0:0:1;Ok;Physical Disk 0:0:1;Online;No;Not Applicable;SAS;67.75 GB (72746008576 bytes);67.75 GB (72746008576 bytes);0.00 GB (0 bytes);No;DELL    ;MBA3073RC       ;D306;BLL3P87027D2        ;Not Available;Not Available;04;31;2008;500000E01C855C42\n"
     * );
     * sb.append("\n");
     */
    DomainObject dj = getDomain();
    DomainObject dj1 = dj.getChild(dj.getName() + "_1");
    DomainObject dj2 = dj.getChild(dj.getName() + "_2");
    StringTokenizer st = new StringTokenizer(vString, "\n");
    if ("List of Physical Disks on Controller PERC 6/i Integrated (Embedded)"
        .equals(st.nextToken()))
    {
      st.nextToken();

      StringTokenizer stLabel = new StringTokenizer(st.nextToken(), ";");
      StringTokenizer stValue1 = new StringTokenizer(st.nextToken(), ";");
      StringTokenizer stValue2 = new StringTokenizer(st.nextToken(), ";");
      while (stLabel.hasMoreTokens())
      {
        try
        {
          String token = clean(stLabel.nextToken());
          dj1.setValue(token, stValue1.nextToken());
          dj2.setValue(token, stValue2.nextToken());
        }
        catch (Exception e)
        {
          LOG.logSevere(LOG_PROCESS_VALUE, e);
        }
      }
      try
      {
        if (STATUS_OK.equals(dj1.getValue(STATUS)) &&
            STATUS_OK.equals(dj2.getValue(STATUS)))
        {
          dj.setValue(DISK_FAULT, NO_FAULT);
        }
        else if (STATUS_OK.equals(dj1.getValue(STATUS)) ||
            STATUS_OK.equals(dj2.getValue(STATUS)))
        {
          dj.setValue(DISK_FAULT, WARNING);
        }
        else
        {
          dj.setValue(DISK_FAULT, FAILED);
        }
      }
      catch (AttributeNotFoundException e)
      {
        LOG.logSevere(LOG_PROCESS_VALUE, e);
      }
    }
    else
    {
      try
      {
        dj.setValue(DISK_FAULT, NO_FAULT);
        LOG.logDebug(LOG_PROCESS_VALUE, "OmDisk-Bad data");
      }
      catch (AttributeNotFoundException e)
      {
        LOG.logSevere(LOG_PROCESS_VALUE, e);
      }
    }
  }
}
