package org.mendybot.common.model.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.exception.ExecuteException;

/**
 * This class defines a domain model that will hold DomainObjects
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class DomainModel
{
  private static final HashMap<String, DomainModel> singleton      = new HashMap<String, DomainModel>();
  private Logger                                    LOG            = Logger.getInstance(DomainModel.class);
  private ArrayList<String>                         ddNames        = new ArrayList<String>();
  private ArrayList<DomainDefinition>               ddList         = new ArrayList<DomainDefinition>();
  private HashMap<String, DomainDefinition>         ddMap          = new HashMap<String, DomainDefinition>();
  private HashMap<String, HierarchyAddress>         addressMap     = new HashMap<String, HierarchyAddress>();
  private ArrayList<DomainObject>                   doRootList     = new ArrayList<DomainObject>();
  private ArrayList<DomainObject>                   doList         = new ArrayList<DomainObject>();
  private HashMap<HierarchyAddress, DomainObject>   doMap          = new HashMap<HierarchyAddress, DomainObject>();
  private String                                    name;
  private DomainObject                              appObject;

  /**
   * Constructor
   * @param name initialized name of domain model
   */
  private DomainModel(String name)
  {
    this.name = name;
  }

  /**
   * This method is a factory method that creates a DomainObject from the given
   * name and DomainDefintion.
   * @param myDef
   * type
   * @param name
   * identifier
   * @return DomainObject
   */
  public DomainObject create(DomainDefinition myDef, String name)
  {
    HierarchyAddress address = lookupAddress(name);
    DomainObject ddo;
    if (address == null)
    {
      address = new HierarchyAddress(name);
      ddo = new DomainObject(myDef, address);
      doRootList.add(ddo);
      doList.add(ddo);
      doMap.put(ddo.getAddress(), ddo);
      addressMap.put(ddo.getAddress().toString(), ddo.getAddress());
    }
    else
    {
      try
      {
        ddo = lookupObject(address);
      }
      catch (DomainObjectNotFoundException e)
      {
        // should never get here
        LOG.logSevere("create", e);
        ddo = new DomainObject(myDef, address);
        doRootList.add(ddo);
        doList.add(ddo);
        doMap.put(ddo.getAddress(), ddo);
        addressMap.put(ddo.getAddress().toString(), ddo.getAddress());
      }
    }
    return ddo;
  }

  /**
   * This method is a factory method that creates a DomainObject from the given
   * name and DomainDefintion.
   * @param myDef type of definition
   * @param parentAddress of DomainObject to associate with
   * @param name identifier
   * @return DomainObject
   * @throws ExecuteException
   * problem of looking up parent DomainObject
   */
  public DomainObject create(DomainDefinition myDef, String parentAddress,
      String name) throws ExecuteException
  {
    DomainObject p;
    try
    {
      p = lookupObject(parentAddress);
    }
    catch (DomainObjectNotFoundException e)
    {
      throw new ExecuteException("Parent Address not found: " + parentAddress);
    }
    return create(myDef, p, name);
  }

  /**
   * This method is a factory method that creates a DomainObject from the given
   * name and DomainDefintion.
   * @param myDef type of definition
   * @param parentObj base DomainObject to associate with
   * @param name identifier
   * @return DomainObject
   */
  public DomainObject create(DomainDefinition myDef, DomainObject parentObj,
      String name)
  {
    DomainObject result = parentObj.getChild(name);
    if (result == null)
    {
      result = new DomainObject(myDef, parentObj, new HierarchyAddress(name));
      doList.add(result);
      doMap.put(result.getAddress(), result);
    }
    addressMap.put(result.getAddress().toString(), result.getAddress());
    return result;
  }

  /**
   * This method is used to look up a HierarchyAddress
   * @param addressString
   * @return HierarchyAddress
   */
  public HierarchyAddress lookupAddress(String addressString)
  {
    synchronized (addressMap)
    {
      HierarchyAddress temp = addressMap.get(addressString);
      return temp;
    }
  }

  /**
   * This is a factory method is used to create and define a DomainDefintion
   * for this model
   * @param name
   * @param list
   * @return DomainDefinition
   */
  public DomainDefinition createDefinition(String name,
      List<DomainAttribute> list)
  {
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
   * This method returns the DomainDefinition from the given name
   * @param name
   * @return DomainDefinition
   */
  public DomainDefinition lookupDefinition(String name)
  {
    return ddMap.get(name);
  }

  /**
   * This method returns a DomainObject from the given address object
   * @param address
   * @return the DomainObject
   * @throws DomainObjectNotFoundException
   * it the address does not match any of the active object addresses
   */
  public DomainObject lookupObject(HierarchyAddress address)
      throws DomainObjectNotFoundException
  {
    if (!doMap.containsKey(address))
    {
      throw new DomainObjectNotFoundException(address);
    }
    else
    {
      return doMap.get(address);
    }
  }

  /**
   * This method returns a DomainObject from the given String representation of an address object
   * @param addressString
   * @return DomainObject
   * @throws DomainObjectNotFoundException
   */
  public DomainObject lookupObject(String addressString)
      throws DomainObjectNotFoundException
  {
    if (addressString == null)
    {
      throw new DomainObjectNotFoundException("null value");
    }
    HierarchyAddress addr = lookupAddress(addressString);
    if (addr == null)
    {
      throw new DomainObjectNotFoundException(addressString);
    }
    return lookupObject(addr);
  }

  /**
   * This method returns the model base name
   * @return String
   */
  public String getName()
  {
    return name;
  }

  /**
   * This method is used to initialize the base application object
   * @param oj domain object to use, if one has not previously been set
   * @return DomainObject
   */
  public synchronized DomainObject initApplicationObject(DomainObject oj)
  {
    if (appObject == null)
    {
      appObject = oj;
    }
    return appObject;
  }

  /**
   * This method is used to return the base application object
   * @return DomainObject
   */
  public synchronized DomainObject lookupApplicationObject()
  {
    return appObject;
  }

  /**
   * This method is used to return all of the root level objects for the model
   * @return List of DomainObject
   */
  public List<DomainObject> getRootObjects()
  {
    return doRootList;
  }

  /**
   * This method is used to lookup/create an instance of a DomainModel (factory - names singleton)
   * @param name to use to lookup model
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
