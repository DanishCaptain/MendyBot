package org.mendybot.console.view.glyph;

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public abstract class Glyph extends JPanel implements AncestorListener
{
  private static final long serialVersionUID = 8531725161069088115L;
  private PanelPosition pos;
  private int ratioWidth;
  private int ratioHeight;
  private int percent;

  public Glyph(PanelPosition pos, int ratioWidth, int ratioHeight, int percent)
  {
    this.pos=pos;
    this.ratioWidth = ratioWidth;
    this.ratioHeight = ratioHeight;
    this.percent = percent;
    setSize(100,100);
    addAncestorListener(this);
  }

  @Override
  public void ancestorAdded(AncestorEvent event)
  {
    adjustPosition();
  }

  private void adjustPosition()
  {
    int w = getParent().getWidth();
    int h = getParent().getHeight();
    
    
    int myW = Math.round(w);
    int myH = Math.round(h);
    System.out.println(myW+":"+myH);
    setSize(myW, myH);
    
//    System.out.println(w+":"+h);
    if (pos == PanelPosition.TOP_LEFT) {
      setLocation(0, 0);
    } else if (pos == PanelPosition.TOP_CENTER) {
      setLocation((w/2)-(getWidth()/2), 10);
    } else if (pos == PanelPosition.TOP_RIGHT) {
      setLocation(w-60-getWidth(), 10);
    } else if (pos == PanelPosition.BOTTOM_LEFT) {
      setLocation(10, h-10-getHeight());
    } else if (pos == PanelPosition.BOTTOM_CENTER) {
      setLocation((w/2)-(getWidth()/2), h-10-getHeight());
    } else if (pos == PanelPosition.BOTTOM_RIGHT) {
      setLocation(w-60-getWidth(), h-10-getHeight());
    } else if (pos == PanelPosition.CENTER) {
      setLocation((w/2)-(getWidth()/2), (h/2)-(getHeight()/2));
    } else if (pos == PanelPosition.CENTER_LEFT) {
      setLocation(10, (h/2)-(getHeight()/2));
    } else if (pos == PanelPosition.CENTER_RIGHT) {
      setLocation(w-60-getWidth(), (h/2)-(getHeight()/2));
    } else if (pos == PanelPosition.ORIGIN) {
      setLocation(0,0);
    }
  }

  @Override
  public void ancestorRemoved(AncestorEvent event)
  {
  }

  @Override
  public void ancestorMoved(AncestorEvent event)
  {
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()+" - ("+pos+") - visible: "+isVisible();
  }

}
