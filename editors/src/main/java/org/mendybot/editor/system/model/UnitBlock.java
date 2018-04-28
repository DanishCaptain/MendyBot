package org.mendybot.editor.system.model;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class UnitBlock extends Block
{
  private DefaultMutableTreeNode parent;
  private String name;
  private DefaultMutableTreeNode me;

  public UnitBlock(DefaultMutableTreeNode parent, String name)
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

}
