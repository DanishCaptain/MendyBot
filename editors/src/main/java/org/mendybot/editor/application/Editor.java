package org.mendybot.editor.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.mendybot.editor.system.SystemEditor;

public class Editor
{
  private JFrame frame;

  public Editor()
  {
    frame = new JFrame("MendyBot - Editor");
    
    JTabbedPane tabs = new JTabbedPane();
    frame.add(tabs, BorderLayout.CENTER);
    
    tabs.add("System View", new SystemEditor());
    
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(d.width, d.height-50);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    Editor e = new Editor();
  }

}
