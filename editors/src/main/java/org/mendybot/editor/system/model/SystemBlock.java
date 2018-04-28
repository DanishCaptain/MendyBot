package org.mendybot.editor.system.model;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class SystemBlock extends Block
{
  private HashMap<String, SubsystemBlock> map = new HashMap<String, SubsystemBlock>();
  private DefaultMutableTreeNode parent;
  private String name;
  private DefaultMutableTreeNode me;

  public SystemBlock(DefaultMutableTreeNode parent, String name)
  {
    this.parent = parent;
    this.name = name;
    me = new DefaultMutableTreeNode(this);
    parent.add(me);

  }
  
  public String getName()
  {
    return name;
  }
  
  @Override
  public String toString()
  {
    return name;
  }

  public SubsystemBlock createSubsystem(String name)
  {
    SubsystemBlock ss = new SubsystemBlock(me, name);
    map.put(name, ss);
    return ss;
  }
}
