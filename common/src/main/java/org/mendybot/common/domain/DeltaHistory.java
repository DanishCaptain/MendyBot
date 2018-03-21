package org.mendybot.common.domain;

import java.util.HashMap;

/**
 * This class is used to define the delta change recording values.
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * 08/11/10 Brian Sorensen IDR GDISR30
 * </pre>
 */
public class DeltaHistory
{
  HashMap<HierarchyAddress, HashMap<String, Object>> addressMap = new HashMap<HierarchyAddress, HashMap<String, Object>>();

  /**
   * constructor
   */
  public DeltaHistory()
  {
  }

  /**
   * This method sets a key value pair within the history store
   * @param key
   * @param value
   */
  public synchronized void setValue(HierarchyAddress address, String key,
      Object value)
  {
    HashMap<String, Object> v = addressMap.get(address);
    if (v == null)
    {
      v = new HashMap<String, Object>();
      addressMap.put(address, v);
    }
    v.put(key, value);
  }

  /**
   * This method clears the history store
   */
  public synchronized void clear()
  {
    addressMap.clear();
  }

  /**
   * This method creates a delta representation of all the changes within the history
   * @return DomainObjectDelta
   */
  public synchronized DomainObjectDelta getDelta()
  {
    DomainObjectDelta result = new DomainObjectDelta(addressMap);
    clear();
    return result;
  }

  /**
   * This method is used to clear the entry for a specific key value associated with a given address
   * @param address The address of the containing domain object
   * @param key The attribute name
   */
  public void clearValue(HierarchyAddress address, String key)
  {
    HashMap<String, Object> v = addressMap.get(address);
    if (v != null)
    {
      v.remove(key);
    }
  }

}
