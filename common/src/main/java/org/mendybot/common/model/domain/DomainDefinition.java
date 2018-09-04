package org.mendybot.common.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to associate a collection of DomainAttributes as a named definition
 * for a DomainObject
 * @see DomainObject
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class DomainDefinition implements Serializable
{
  private static final long          serialVersionUID = 9013626196969420810L;
  private String                     name;
  private ArrayList<DomainAttribute> aList            = new ArrayList<DomainAttribute>();

  /**
   * constructor
   * @param name
   * @param attributes
   */
  DomainDefinition(String name, List<DomainAttribute> attributes)
  {
    this.name = name;
    aList.addAll(attributes);
  }

  /**
   * This method returns the defined name;
   * @return String
   */
  public String getName()
  {
    return name;
  }

  /**
   * This method is used to populate a new DomainObject so it can store the values
   * as defined by this DomainDefinition and it's DomainAttributes. The values are then
   * initialized as appropriate for each of the attributes.
   * @param dj
   */
  void populateAndInit(DomainObject dj)
  {
    for (int i = 0; i < aList.size(); i++)
    {
      DomainAttribute attribute = aList.get(i);
      attribute.populate(dj);
      attribute.init(dj);
    }
  }

  /**
   * This method is used to initialize a given DomainObject as appropriate for it's
   * DomainAttributes
   * @param dj
   */
  void init(DomainObject dj)
  {
    for (int i = 0; i < aList.size(); i++)
    {
      DomainAttribute attribute = aList.get(i);
      attribute.init(dj);
    }
  }

  /**
   * This method returns a list of attributes for this definition
   * @return List of DomainAttribute
   */
  public List<DomainAttribute> getAttributes()
  {
    return aList;
  }

  @Override
  public String toString()
  {
    return name;
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
		DomainAttribute result = null;
		for (int i=0; i<aList.size(); i++)
		{
			if (aList.get(i).getName().equals(attributeName))
			{
				result = aList.get(i);
				break;
			}
		}
		if (result == null)
		{
			throw new AttributeNotFoundException(attributeName);
		}
	  return result;
  }
}
