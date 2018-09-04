package org.mendybot.common.model.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This object contains a set of key value pairs that represent changes made to DomainObject.
 * The address of the original DomainObject is maintained so it can be matched to a
 * representation later.
 * @see DomainObject
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class DomainObjectDelta implements Serializable
{
  private static final long serialVersionUID = 8174224675329511469L;
  private HashMap<HierarchyAddress, HashMap<String, Object>> map = new HashMap<HierarchyAddress, HashMap<String, Object>>();

  /**
   * constructor
   * @param addressMap
   */
  public DomainObjectDelta(
      HashMap<HierarchyAddress, HashMap<String, Object>> addressMap)
  {
    this.map.putAll(addressMap);
  }

  @Override
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    if (map.size() == 0)
    {
      sb.append("{}");
    }
    else
    {
      sb.append(map.toString());
    }
    return sb.toString();
  }

  /**
   * This method allows access to the delta data
   * @return a Hashmap keyed by HierarchyAddresses (DomainObject) that contains a HashMap of all the attributes delta
   * changes
   */
  public HashMap<HierarchyAddress, HashMap<String, Object>> getMap()
  {
    return map;
  }

}
