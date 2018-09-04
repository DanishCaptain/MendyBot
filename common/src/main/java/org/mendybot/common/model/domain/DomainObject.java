package org.mendybot.common.model.domain;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mendybot.common.application.log.Logger;

/**
 * This class is used to define a collection of typed key and value pairs. The type is
 * defined by a DomainDefinition and its DomainAttributes
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * 05/31/10 Brian Sorensen TO-104 GDIS-R - Fixed restore to defaults
 * 08/11/10 Brian Sorensen IDR GDISR30
 * </pre>
 * @see gov.nasa.wsc.ssc.domain.DomainDefinition
 * @see gov.nasa.wsc.ssc.domain.DomainAttribute
 */
public class DomainObject
{
  private static final Logger                    LOG          = Logger.getInstance(DomainObject.class);
  private DomainDefinition                       type;
  private HashMap<String, DomainProperty>        values       = new HashMap<String, DomainProperty>();
  private String                                 name;
  private HierarchyAddress                       address;
  private ArrayList<DomainObject>                children     = new ArrayList<DomainObject>();
  private HashMap<String, DomainObject>          childMap     = new HashMap<String, DomainObject>();
  private ArrayList<WeakReference<DeltaHistory>> histories    = new ArrayList<WeakReference<DeltaHistory>>();
  private ArrayList<WeakReference<DeltaHistory>> historyTrash = new ArrayList<WeakReference<DeltaHistory>>();

  /**
   * This constructor creates a named DomainObject of type DomainDefinition
   * @param type
   * The type definition
   * @param address
   */
  DomainObject(DomainDefinition type, HierarchyAddress address)
  {
    this.type = type;
    this.name = address.getName();
    this.address = address;
    type.populateAndInit(this);
  }

  /**
   * This constructor creates a named DomainObject of type DomainDefinition and
   * linking it to the given parent DomainObject. The parent them becomes the
   * container for the child's data in addition to its own attributes. The
   * HierarchyAddress of the child will reflect this relationship.
   * 
   * @param type
   * The type definition
   * @param parent
   * The parent to associate with
   * @param address
   */
  DomainObject(
      DomainDefinition type, 
      DomainObject parent,
      HierarchyAddress address)
  {
    this(type, address);
    parent.addChild(this);
  }

  /**
   * This method returns the name of this DomainObject. The name is used to
   * index this entity.
   * 
   * @return name value
   */
  public String getName()
  {
    return name;
  }

  /**
   * This method returns the declared type of this DomainObject
   * 
   * @return data type
   */
  public DomainDefinition getType()
  {
    return type;
  }

  /**
   * This method returns the HierarchyAddress for this DomainObject so it may be
   * retrieved in relationship to it chain of parents if any.
   * 
   * @return HierarchyAddress
   */
  public HierarchyAddress getAddress()
  {
    return address;
  }

  /**
   * This method returns a list of the contained child DomainObjects
   * 
   * @return List
   */
  public List<DomainObject> getChildren()
  {
    return children;
  }

  /**
   * This method returns a subset of children DomainObjects
   * that match the type named by the input String.
   * @return List
   */
  public List<DomainObject> getTypeChildren(String type)
  {
    List<DomainObject> tempList = new ArrayList<DomainObject>();

    for (int i = 0; i < children.size(); i++)
    {
      DomainDefinition currChildType = children.get(i).getType();
      if (currChildType.getName() == type)
      {
        tempList.add(children.get(i));
      }
    }
    return tempList;

  }

  /**
   * This method allows the lookup of the contained child by name.
   * 
   * @param name
   * @return DomainObject
   */
  public DomainObject getChild(String name)
  {
    return childMap.get(name);
  }

  /**
   * This method adds a child to its local container, completes the similar
   * history chain, and registers the parental association with the child.
   * 
   * @param child
   */
  private void addChild(DomainObject child)
  {
    children.add(child);
    child.address.setParent(address);
    childMap.put(child.getName(), child);
  }

  /**
   * This method allows a DomainPropertyListener object to request notification interest
   * in changes of this DomainObject's specified attribute
   * @param attributeName
   * @param listener
   * @throws AttributeNotFoundException
   */
  public void addInterest(String attributeName, DomainPropertyListener listener)
      throws AttributeNotFoundException
  {
    DomainProperty p = values.get(attributeName);
    if (p == null)
    {
      throw new AttributeNotFoundException(attributeName);
    }
    values.get(attributeName).addDomainPropertyListener(listener);
  }

  /**
   * This method removes the notification interest for the specified attribute
   * @param attributeName
   * @param listener
   * @throws AttributeNotFoundException
   */
  public void removeInterest(String attributeName,
      DomainPropertyListener listener) throws AttributeNotFoundException
  {
    DomainProperty p = values.get(attributeName);
    if (p == null)
    {
      throw new AttributeNotFoundException(attributeName);
    }
    values.get(attributeName).removeDomainPropertyListener(listener);
  }

  /**
   * This method returns the current value from the given attribute name.
   * 
   * @param attributeName
   * The name of the attribute
   * @return The current value
   * @throws AttributeNotFoundException
   * Attribute could not be found in the DomainDefinition for this
   * DomainObject
   * @see DomainAttribute
   */
  public final Object getValue(String attributeName)
      throws AttributeNotFoundException
  {
    DomainProperty p = values.get(attributeName);
    if (p == null)
    {
      throw new AttributeNotFoundException(attributeName);
    }
    return p.getValue();
  }

  /**
   * This method returns the current value from the given attribute name, but as
   * a String value
   * 
   * @param attributeName
   * The name of the attribute
   * @return The current value
   * @throws AttributeNotFoundException
   * Attribute could not be found in the DomainDefinition for this
   * DomainObject
   * @see DomainAttribute
   */
  public final String getStringValue(String attributeName)
      throws AttributeNotFoundException
  {
    DomainProperty p = values.get(attributeName);
    if (p == null)
    {
      throw new AttributeNotFoundException(attributeName);
    }
    return p.getStringValue();
  }

  /**
   * This method sets the value associated with the given attribute name.
   * 
   * @param attributeName
   * The name of the attribute
   * @param value
   * the new value
   * @throws AttributeNotFoundException
   * Attribute could not be found in the DomainDefinition for this
   * DomainObject
   * @see DomainAttribute
   */
  public synchronized void setValue(String attributeName, Object value)
      throws AttributeNotFoundException
  {
    DomainProperty p = values.get(attributeName);
    if (p == null)
    {
      throw new AttributeNotFoundException(name + "." + attributeName);
    }
    Object ov = p.getValue();
    if (ov == null)
    {
      if (value != null)
      {
        p.setValue(value);
        populateHistory(attributeName, value);
      }
    }
    else if (!ov.equals(value))
    {
      p.setValue(value);
      populateHistory(attributeName, value);
    }
  }

  /**
   * This method updates all of the interested DeltaHistory objects with the new attribute
   * value.
   * @param attributeName
   * @param value
   */
  private void populateHistory(String attributeName, Object value)
  {
    synchronized (historyTrash)
    {
      for (int i = 0; i < histories.size(); i++)
      {
        WeakReference<DeltaHistory> ref = histories.get(i);
        DeltaHistory history = ref.get();
        if (history != null)
        {
          history.setValue(address, attributeName, value);
        }
        else
        {
          historyTrash.add(ref);
        }
      }
      histories.removeAll(historyTrash);
      historyTrash.clear();
    }
  }

  /**
   * This method adds the given DomainProperty which represents the instance
   * representation of the static value defined by a DomainAttribute
   * 
   * @param prop
   */
  void add(DomainProperty prop)
  {
    values.put(prop.getName(), prop);
  }

  /**
   * This method adds a DeltaHistory object to the interest list.
   */
  public void addHistory(DeltaHistory dh)
  {
    Set<String> set = values.keySet();
    Iterator<String> it = set.iterator();
    while (it.hasNext())
    {
      String attr = it.next();
      dh.setValue(address, attr, values.get(attr).getValue());
    }
    histories.add(new WeakReference<DeltaHistory>(dh));
    for (int i = 0; i < children.size(); i++)
    {
      children.get(i).addHistory(dh);
    }
  }

  /**
   * This method removes the DeltaHistory from the interest list.
   * @param dh
   */
  public void removeHistory(DeltaHistory dh)
  {
    histories.remove(dh);
  }

  /**
   * This method is provided mainly for testing.
   * @return
   */
  List<WeakReference<DeltaHistory>> getHistoryList()
  {
    return histories;
  }

  @Override
  public String toString()
  {
    return type + ": " + name;
  }

  /**
   * This method populates the DomainObjectDelta object with appropriate values
   * @param ddr
   * @throws AttributeNotFoundException
   */
  public void populate(DomainObjectDelta ddr) throws AttributeNotFoundException
  {
    HashMap<HierarchyAddress, HashMap<String, Object>> map = ddr.getMap();
    HashMap<String, Object> myMap = map.get(address);
    if (myMap != null)
    {
      Set<String> s = myMap.keySet();
      Iterator<String> it = s.iterator();
      while (it.hasNext())
      {
        String key = it.next();
        setValue(key, myMap.get(key));
      }
    }
    for (int i = 0; i < children.size(); i++)
    {
      DomainObject child = children.get(i);
      child.populate(ddr);
    }
  }

  /**
   * This method restores the DomainObject to it's default values.
   */
  public void restoreDefault()
  {
    List<DomainAttribute> aList = type.getAttributes();
    for (int i=0; i<aList.size(); i++)
    {
      DomainAttribute a = aList.get(i);
      try
      {
        setValue(a.getName(), a.getDefaultValue());
      }
      catch (AttributeNotFoundException ex)
      {
        LOG.logSevere("restoreDefault", ex);
      }
    }
    for (int i = 0; i < children.size(); i++)
    {
      children.get(i).restoreDefault();
    }
  }

  /**
   * This method returns a clone of the current domain object, but it is not associated with
   * any domain model
   * @param oj domain object to clone
   * @return DomainObject
   */
  public static DomainObject createUnreferencedClone(DomainObject oj)
  {
    DomainObject dj = new DomainObject(oj.getType(), oj.getAddress());
    List<DomainAttribute> list = oj.getType().getAttributes();
    for (int i = 0; i < list.size(); i++)
    {
      try
      {
        dj.setValue(list.get(i).getName(), oj.getValue(list.get(i).getName()));
      }
      catch (AttributeNotFoundException e)
      {
        LOG.logSevere("creaetUnreferencedClone", "Should never get here!");
        LOG.logSevere("creaetUnreferencedClone", e);
      }
    }
    return dj;
  }

  /**
   * This method is used to retrieved the attribute of the given name
   * @param attributeName
   * @return DomainAttribute
   * @throws AttributeNotFoundException 
   */
    public DomainAttribute getAttribute(String attributeName) 
    throws AttributeNotFoundException
  {
      return getType().getAttribute(attributeName);
  }

    /**
     * This method is used to remove an entry from the history mechanism
     * @param key The Attribute name  
     */
  public void flushHistoryEntry(String key)
  {
    for (int i=0; i<histories.size(); i++)
    {
      WeakReference<DeltaHistory> ref = histories.get(i);
      DeltaHistory history = ref.get();
      history.clearValue(address, key);
    }
  }

}
