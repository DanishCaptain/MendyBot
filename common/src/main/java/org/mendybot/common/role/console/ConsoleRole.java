package org.mendybot.common.role.console;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.MendyBotRole;
import org.mendybot.common.role.console.clip.Clip;
import org.mendybot.common.role.console.glyph.Glyph;

public abstract class ConsoleRole implements MendyBotRole
{
  private static final Logger LOG = Logger.getInstance(ConsoleRole.class);
  private HashMap<String, Clip> clipsM = new HashMap<>();
  private ArrayList<Clip> clipsL = new ArrayList<>();
  private HashMap<String, Glyph> glyphsM = new HashMap<>();
  private ArrayList<Glyph> glyphsL = new ArrayList<>();
  private ApplicationModel model;

  public ConsoleRole(ApplicationModel model) {
    this.model = model;
  }
  
  public ApplicationModel getModel()
  {
    return model;
  }

  public final void init() throws ExecuteException
  {
    LOG.logInfo("init", "call");
    initClips(clipsM, clipsL);
    initGlyphs(glyphsM, glyphsL);
    initConsole();
  }

  protected abstract void initConsole() throws ExecuteException;

  public abstract void start() throws ExecuteException;

  public abstract void stop();
  
  public String getAppName()
  {
    return model.getName();
  }

  private void initClips(Map<String, Clip> map, List<Clip> list) throws ExecuteException
  {
    try
    {
      List<String> names = model.getProperties("clip-name");
      List<String> widgetNames = model.getProperties("clip-widget-name");
      List<String> classNames = model.getProperties("clip-class");

      for (int i=0; i<names.size(); i++)
      {
        String name = names.get(i);
        LOG.logInfo("initClips", name);
        String widgetName = widgetNames.get(i);
        String className = classNames.get(i);
        @SuppressWarnings("unchecked")
        Class<? extends Clip> c = (Class<? extends Clip>) Class.forName(className);
        Constructor<? extends Clip> cc = c.getConstructor(new Class[]{ConsoleRole.class, String.class, String.class,});
        Clip w = cc.newInstance(new Object[]{this, name, widgetName});
        map.put(name, w);
        list.add(w);
      }
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
  }

  public Clip lookupClip(String name)
  {
    return clipsM.get(name);
  }

  private void initGlyphs(Map<String, Glyph> map, List<Glyph> list) throws ExecuteException
  {
    try
    {
      List<String> names = model.getProperties("glyph-name");
      List<String> widgetNames = model.getProperties("glyph-widget-name");
      List<String> classNames = model.getProperties("glyph-class");

      for (int i=0; i<names.size(); i++)
      {
        String name = names.get(i);
        LOG.logInfo("initGlyphs", name);
        String widgetName = widgetNames.get(i);
        String className = classNames.get(i);
        @SuppressWarnings("unchecked")
        Class<? extends Glyph> c = (Class<? extends Glyph>) Class.forName(className);
        Constructor<? extends Glyph> cc = c.getConstructor(new Class[]{ConsoleRole.class, String.class, String.class,});
        Glyph w = cc.newInstance(new Object[]{this, name, widgetName});
        map.put(name, w);
        list.add(w);
      }
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
  }

  public Glyph lookupGlyphX(String name)
  {
    return glyphsM.get(name);
  }

  public void repaint()
  {
    BufferedImage image = getImage();
    synchronized(image) {
      Graphics g = image.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    
    for (Glyph gg : glyphsL)
    {
      gg.draw(g);
    }
    }
    repaintLocal();
  }

  protected abstract void repaintLocal();

  protected abstract BufferedImage getImage();

  public int getW()
  {
    return getImage().getWidth();
  }
  
  public int getH()
  {
    return getImage().getHeight();
  }
  
}
