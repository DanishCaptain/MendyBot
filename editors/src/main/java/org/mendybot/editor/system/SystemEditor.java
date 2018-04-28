package org.mendybot.editor.system;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.mendybot.editor.system.model.SubsystemBlock;
import org.mendybot.editor.system.model.SystemBlock;
import org.mendybot.editor.system.model.UnitBlock;

public class SystemEditor extends JPanel
{
  private static final long serialVersionUID = 976967548014612134L;
  private TreeNode root;// = new TreeNode();

  public SystemEditor()
  {
    setLayout(new BorderLayout());
    DefaultMutableTreeNode top = new DefaultMutableTreeNode("MB Systems");
    JTree tree = new JTree(top);
    add(tree, BorderLayout.WEST);

    SystemBlock sHouse = new SystemBlock(top, "House");
    SubsystemBlock ssKitchen = sHouse.createSubsystem("Kitchen");
    SubsystemBlock ssBedroom1 = sHouse.createSubsystem("Bedroom1");
    UnitBlock environmentB1 = ssBedroom1.createUnit("EnvironmentBR1");
    UnitBlock windowControlB1 = ssBedroom1.createUnit("WindowControlBR1");
    
    SubsystemBlock ssBedroom2 = sHouse.createSubsystem("Bedroom2");
    
  }
}
