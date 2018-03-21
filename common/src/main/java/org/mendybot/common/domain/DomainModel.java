package org.mendybot.common.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mendybot.common.application.log.Logger;

/**
 * This class defines a domain model that will hold DomainObjects
 * 
 * <pre>
 * <B>History
 * Date        Author         Description/Justification</B>
 * 11 Mar 2018 Brian Sorensen ENG-100 - Initial code.
 * </pre>
 */
public class DomainModel
{
  private static final Logger LOG = Logger.getInstance(DomainModel.class);
  private static final HashMap<String, DomainModel> singleton = new HashMap<String, DomainModel>();
  private ArrayList<String> ddNames = new ArrayList<String>();
  private ArrayList<DomainDefinition> ddList = new ArrayList<DomainDefinition>();
  private HashMap<String, DomainDefinition> ddMap = new HashMap<String, DomainDefinition>();
  private String name;

  /**
   * Constructor
   * 
   * @param name
   *          initialized name of domain model
   */
  private DomainModel(String name)
  {
    this.name = name;
  }

  /**
   * This method returns the model base name
   * 
   * @return String
   */
  public String getName()
  {
    return name;
  }

  
  /**
   * This is a factory method is used to create and define a DomainDefintion
   * for this model
   * @param name
   * @param list
   * @return DomainDefinition
   */
  public DomainDefinition createDefinition(String name, List<DomainAttribute> list)
  {
    LOG.logDebug("createDefinition", name+":"+list);
    DomainDefinition result = ddMap.get(name);
    if (result == null)
    {
      result = new DomainDefinition(name, list);
      ddList.add(result);
      ddMap.put(result.getName(), result);
      ddNames.add(result.getName());
    }
    return result;
  }


  /**
   * This method is used to lookup/create an instance of a DomainModel (factory
   * - names singleton)
   * 
   * @param name
   *          to use to lookup model
   * @return DomainModel
   */
  public synchronized static DomainModel lookup(String name)
  {
    DomainModel result = singleton.get(name);
    if (result == null)
    {
      result = new DomainModel(name);
      singleton.put(name, result);
    }
    return result;
  }

}
