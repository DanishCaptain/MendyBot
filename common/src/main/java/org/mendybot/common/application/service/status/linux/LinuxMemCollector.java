package org.mendybot.common.application.service.status.linux;

import java.util.StringTokenizer;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.domain.AttributeNotFoundException;
import org.mendybot.common.domain.DomainModel;
import org.mendybot.common.domain.DomainObject;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class is used to process power supply information obtained from the OpenManage service on a Linux box
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public class LinuxMemCollector extends LinuxCollector
{
  private static final Logger LOG               = Logger
                                                    .getInstance(LinuxMemCollector.class);
  private static final String LOG_PROCESS_VALUE = "processValue";

//  public String               MsctsDisplay      = "Main System Chassis Power Supply State";
  public static final String MEM_FAULT            = "memFault";
  public static final String STATUS                = "Status";
  public static final String STATUS_OK             = "Ok";
  public static final String STATUS_CRITICAL       = "Critical";
  public static final String INSTALLED_CAPACITY    = "Installed Capacity";
  public static final String OS_AVAILABLE_CAPACITY = "Available to OS";
  public static final String MAX_CAPACITY          = "Maximum Capacity";

  /**
   * Constructor
   * @param model
   * @param baseObj
   * @param name
   * @throws ExecuteException
   */
  public LinuxMemCollector(DomainModel model, DomainObject baseObj,
      String oidName, String parentName, String name) throws ExecuteException
  {
    super(model, baseObj, oidName, parentName, name);
  }

  @Override
  public String getCommand()
  {
    return "omreport chassis memory -fmt cdv";
  }

  @Override
  public void processValue(String vString)
  {
    /*
     * Memory Information
     * 
     * Attributes of Memory Array(s)
     * 
     * Attributes;Memory Array 1
     * Location;System Board or Motherboard
     * Use;System Memory
     * Installed Capacity;4096 MB
     * Maximum Capacity;65280 MB
     * Slots Available;8
     * Slots Used;4
     * ECC Type;Multibit ECC
     * 
     * 
     * Total of Memory Array(s)
     * 
     * Attributes;Value
     * Total Installed Capacity;4096 MB
     * Total Installed Capacity Available to the OS;3950 MB
     * Total Maximum Capacity;65280 MB
     * 
     * 
     * Details of Memory Array 1
     * 
     * Index;Status;Connector Name;Type;Size
     * 0;Ok;DIMM1 ;DDR2 FB-DIMM-SYNCHRONOUS;1024 MB
     * 1;Ok;DIMM2 ;DDR2 FB-DIMM-SYNCHRONOUS;1024 MB
     * 2;Ok;DIMM3 ;DDR2 FB-DIMM-SYNCHRONOUS;1024 MB
     * 3;Ok;DIMM4 ;DDR2 FB-DIMM-SYNCHRONOUS;1024 MB
     * ;Unknown;DIMM5 ;[Not Occupied];
     * ;Unknown;DIMM6 ;[Not Occupied];
     * ;Unknown;DIMM7 ;[Not Occupied];
     * ;Unknown;DIMM8 ;[Not Occupied];
     */
    DomainObject dj0 = getDomain();
    DomainObject dj1 = dj0.getChild(dj0.getName() + "_1");
    DomainObject dj2 = dj0.getChild(dj0.getName() + "_2");
    DomainObject dj3 = dj0.getChild(dj0.getName() + "_3");
    DomainObject dj4 = dj0.getChild(dj0.getName() + "_4");

    StringTokenizer st = new StringTokenizer(vString, "\n");

    String temp;
    if ("Memory Information".equals(st.nextToken()))
    {
      int count = 0;
      while (true)
      {
        count++;
        temp = st.nextToken();
        if ("Total of Memory Array(s)".equals(temp) || count > 11)
        {
          break;
        }
      }
      st.nextToken(); // LabelBlock
      try
      {
        StringTokenizer stValue = new StringTokenizer(st.nextToken(), ";");
        stValue.nextToken(); // eat label
        dj0.setValue(INSTALLED_CAPACITY, stValue.nextToken());
        stValue = new StringTokenizer(st.nextToken(), ";");
        stValue.nextToken(); // eat label
        dj0.setValue(OS_AVAILABLE_CAPACITY, stValue.nextToken());
        stValue = new StringTokenizer(st.nextToken(), ";");
        stValue.nextToken(); // eat label
        dj0.setValue(MAX_CAPACITY, stValue.nextToken());
      }
      catch (AttributeNotFoundException e)
      {
        LOG.logSevere(LOG_PROCESS_VALUE, e);
      }

      while (true)
      {
        count++;
        temp = st.nextToken();
        if ("Details of Memory Array 1".equals(temp) || count > 5)
        {
          break;
        }
      }
      StringTokenizer stLabel = new StringTokenizer(st.nextToken(), ";");
      StringTokenizer stValue1 = new StringTokenizer(st.nextToken(), ";");
      StringTokenizer stValue2 = new StringTokenizer(st.nextToken(), ";");
      StringTokenizer stValue3 = new StringTokenizer(st.nextToken(), ";");
      StringTokenizer stValue4 = new StringTokenizer(st.nextToken(), ";");
      while (stLabel.hasMoreTokens())
      {
        try
        {
          String lbl = clean(stLabel.nextToken());
          dj1.setValue(lbl, stValue1.nextToken());
          dj2.setValue(lbl, stValue2.nextToken());
          dj3.setValue(lbl, stValue3.nextToken());
          dj4.setValue(lbl, stValue4.nextToken());
        }
        catch (AttributeNotFoundException e)
        {
          LOG.logSevere(LOG_PROCESS_VALUE, e);
        }
      }
      try
      {
        if (STATUS_OK.equals(dj1.getValue(STATUS)) &&
            STATUS_OK.equals(dj2.getValue(STATUS)) &&
            STATUS_OK.equals(dj3.getValue(STATUS)) &&
            STATUS_OK.equals(dj4.getValue(STATUS)) )
        {
          dj0.setValue(MEM_FAULT, LinuxDiskCollector.NO_FAULT);
        }
        else if (LinuxDiskCollector.STATUS_OK.equals(dj1.getValue(LinuxDiskCollector.STATUS)) ||
            LinuxDiskCollector.STATUS_OK.equals(dj2.getValue(LinuxDiskCollector.STATUS)) ||
            LinuxDiskCollector.STATUS_OK.equals(dj3.getValue(LinuxDiskCollector.STATUS)) ||
            LinuxDiskCollector.STATUS_OK.equals(dj4.getValue(LinuxDiskCollector.STATUS)) )
        {
          dj0.setValue(MEM_FAULT, LinuxDiskCollector.WARNING);
        }
        else
        {
          dj0.setValue(MEM_FAULT, LinuxDiskCollector.FAILED);
        }
      }
      catch (AttributeNotFoundException e1)
      {
        LOG.logSevere(LOG_PROCESS_VALUE, e1);
      }
    }
    else
    {
      LOG.logInfo(LOG_PROCESS_VALUE, "OmPws-Bad data");
    }
  }
}
