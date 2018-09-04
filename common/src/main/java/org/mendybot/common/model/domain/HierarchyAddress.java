package org.mendybot.common.model.domain;

import java.io.Serializable;

/**
 * This class holds the address value that may be chained to other HierarchyAddress objects.
 * <pre>
 * <B>History
 * Date     Author         Description/Justification</B>
 * 04/13/10 Brian Sorensen TO-104 GDIS-R - Initial code.
 * </pre>
 */
public class HierarchyAddress implements Comparable<HierarchyAddress>,
    Serializable
{
  private static final long serialVersionUID = -3101549620540615906L;
  private String            name;
  private HierarchyAddress  parent;

  /**
   * constructor
   * @param name
   * The base identifier for this address.
   */
  public HierarchyAddress(String name)
  {
    if (name == null)
    {
      throw new RuntimeException(name);
    }
    this.name = name;
  }

  /**
   * This method returns the base name of the address
   * @return String
   */
  public String getName()
  {
    return name;
  }

  /**
   * This method returns the String representation of this address.
   */
  @Override
  public String toString()
  {
    if (parent == null)
    {
      return name;
    }
    else
    {
      return parent.toString() + ":" + name;
    }
  }

  @Override
  public boolean equals(Object o)
  {
    boolean result = false;
    if (o instanceof HierarchyAddress)
    {
      result = toString().equals(o.toString());
    }
    return result;
  }

  @Override
  public int hashCode()
  {
    return toString().hashCode();
  }

  /**
   * This method sets the given address as the parent value. This will adjust the string
   * representation accordingly.
   * @param address
   */
  void setParent(HierarchyAddress address)
  {
    if (parent == null && address != this)
    {
      parent = address;
    }
  }

  @Override
  public int compareTo(HierarchyAddress obj)
  {
    return toString().compareTo("" + obj);
  }
}
