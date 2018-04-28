package org.mendybot.editor.system.model;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class SubsystemBlock
{
  private HashMap<String, UnitBlock> map = new HashMap<String, UnitBlock>();
  private DefaultMutableTreeNode parent;
  private String name;
  private DefaultMutableTreeNode me;

  public SubsystemBlock(DefaultMutableTreeNode parent, String name)
  {
    this.parent = parent;
    this.name = name;
    me = new DefaultMutableTreeNode(this);
    parent.add(me);
  }

  @Override
  public String toString()
  {
    return name;
  }

  public UnitBlock createUnit(String name)
  {
    UnitBlock u = new UnitBlock(me, name);
    map.put(name, u);
    return u;
  }
}
