package org.mendybot.common.application.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.mendybot.common.application.Application;
import org.mendybot.common.application.log.Logger;
import org.mendybot.common.application.model.platform.ApplicationPlatform;
import org.mendybot.common.application.model.widget.Widget;
import org.mendybot.common.exception.ExecuteException;
import org.mendybot.common.role.ApplicationRole;
import org.mendybot.common.role.MendyBotRole;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.headless.HeadlessConsole;
import org.mendybot.common.role.system.SingleServer;
import org.mendybot.common.role.system.SystemRole;

public class ApplicationModel
{
  private static final Logger LOG = Logger.getInstance(ApplicationModel.class);
  private Properties config = new Properties();
  private Properties properties = new Properties();
  private ArrayList<MendyBotRole> roles = new ArrayList<MendyBotRole>();
  private HashMap<String, ApplicationRole> appRolesM = new HashMap<String, ApplicationRole>();
  private HashMap<String, Widget> widgetsM = new HashMap<String, Widget>();
  private ArrayList<Widget> widgetsL = new ArrayList<Widget>();
  private Application application;
  private File dirEtc;
  private File dirOpt;
  private ApplicationPlatform applicationPlatform;
  private TimeZone timeZone;

  public ApplicationModel(Application application) throws ExecuteException
  {
    this.application=application;
    LOG.logDebug("()", "called");
    boolean testMode = System.getProperty("TEST-MODE") != null;
    loadConfig();
    loadProperties(application.getName(), testMode);
    File logFile;
    if (testMode)
    {
      logFile = new File("var/log/mendybot/MB_"+application.getName()+"_");
    } else {
      logFile = new File("/var/log/mendybot/MB_"+application.getName()+"_");
    }
    Logger.init(logFile.getAbsolutePath(), properties);
    LOG.logInfo("()", "test-mode: "+testMode);
    initSystemRole(roles);
    initConsoleRole(roles);
    initApplicationRoles(roles);
    initWidgets(widgetsM, widgetsL);
    initPlatform();
  }

  private void loadConfig() throws ExecuteException
  {
    InputStream is = getClass().getClassLoader().getResourceAsStream("mendy-config.properties");
    if (is != null) {
      try
      {
        config.load(is);
        is.close();
      }
      catch (IOException e)
      {
        throw new ExecuteException("mendy-config.properties", e);
      }
      
    } else {
      throw new ExecuteException("mendy-config.properties file is missing.");
    }
    String tzId = config.getProperty("timezone");
    if (tzId == null) {
      timeZone = TimeZone.getDefault();      
    } else {
      timeZone = TimeZone.getTimeZone(tzId);
    }
  }

  private void loadProperties(String name, boolean testMode) throws ExecuteException
  {
    if (testMode) {
      dirEtc = new File("etc/mendybot");
      dirOpt = new File("..");
    } else {
      dirEtc = new File("/etc/mendybot");
      dirOpt = new File("/opt/mendybot");
    }
    File file = new File(dirEtc, "MB_"+name+".properties");
    if (file.exists()) {
      try
      {
        InputStream is = new FileInputStream(file);
        properties.load(is);
        is.close();
      }
      catch (Exception e)
      {
        throw new ExecuteException(file.getName(), e);
      }
      
    } else {
      throw new ExecuteException("configuration .properties file is missing: "+file.getAbsolutePath());
    }
  }

  private void initSystemRole(List<MendyBotRole> roles) throws ExecuteException
  {
    try
    {
      String name = getConfigProperty("system-role-class");
      if (name != null)
      {
        @SuppressWarnings("unchecked")
        Class<? extends SystemRole> c = (Class<? extends SystemRole>) Class.forName(name);
        Constructor<? extends SystemRole> cc = c.getConstructor(new Class[]{ApplicationModel.class,});
        roles.add(cc.newInstance(new Object[]{this,}));
      }
      else
      {
        roles.add(new SingleServer(this));
      }
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
  }

  private void initConsoleRole(List<MendyBotRole> roles) throws ExecuteException
  {
    try
    {
      String name = getConfigProperty("console-role-class");
      if (name == null) 
      {
        name = getProperty("console-role-class");
      }
      if (name != null) 
      {
        @SuppressWarnings("unchecked")
        Class<? extends ConsoleRole> c = (Class<? extends ConsoleRole>) Class.forName(name);
        Constructor<? extends ConsoleRole> cc = c.getConstructor(new Class[]{ApplicationModel.class,});
        roles.add(cc.newInstance(new Object[]{this,}));
      }
      else
      {
        roles.add(new HeadlessConsole(this));
      }
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
  }

  private void initApplicationRoles(List<MendyBotRole> roles) throws ExecuteException
  {
    try
    {
      List<String> names = getConfigProperties("application-role-class");
      names.addAll(getProperties("application-role-class"));
      
      for (String className : names) {
        @SuppressWarnings("unchecked")
        Class<? extends ApplicationRole> c = (Class<? extends ApplicationRole>) Class.forName(className);
        Constructor<? extends ApplicationRole> cc = c.getConstructor(new Class[]{ApplicationModel.class,});
        ApplicationRole rr = cc.newInstance(new Object[]{this,});
        roles.add(rr);
        appRolesM.put(rr.getId(), rr);
      }
    }
    catch (Exception e)
    {
      LOG.logSevere("initApplicationRole", "classes: "+getConfigProperties("application-role-class"));
      throw new ExecuteException(e);
    }
  }

  private void initWidgets(Map<String, Widget> map, List<Widget> list) throws ExecuteException
  {
    try
    {
      List<String> names = getConfigProperties("widget-name");
      names.addAll(getProperties("widget-name"));
      List<String> classNames = getConfigProperties("widget-class");
      classNames.addAll(getProperties("widget-class"));

      for (int i=0; i<names.size(); i++)
      {
        String name = names.get(i);
        String className = classNames.get(i);
        @SuppressWarnings("unchecked")
        Class<? extends Widget> c = (Class<? extends Widget>) Class.forName(className);
        Constructor<? extends Widget> cc = c.getConstructor(new Class[]{ApplicationModel.class, String.class,});
        Widget w = cc.newInstance(new Object[]{this, name});
        map.put(name, w);
        list.add(w);
      }
    }
    catch (Exception e)
    {
      throw new ExecuteException(e);
    }
  }

  public Widget lookupWidget(String name)
  {
    return widgetsM.get(name);
  }

  private void initPlatform() throws ExecuteException
  {
    String className = getConfigProperty("application-platform-class", "org.mendybot.common.application.model.platform.UbuntuPlatform");
    try
    {
      @SuppressWarnings("unchecked")
      Class<? extends ApplicationPlatform> c = (Class<? extends ApplicationPlatform>) Class.forName(className);
      Constructor<? extends ApplicationPlatform> cc = c.getConstructor(new Class[]{ApplicationModel.class,});
      applicationPlatform = cc.newInstance(new Object[]{this,});
    }
    catch (Exception e)
    {
      LOG.logSevere("initPlatform", "class: "+className);
      throw new ExecuteException(e);
    }
  }
  
  public ApplicationPlatform getApplicationPlatform()
  {
    return applicationPlatform;
  }

  public void init() throws ExecuteException
  {
    for (MendyBotRole role : roles) {
      role.init();
    }
    for (Widget widget : widgetsL) {
      widget.init();
    }
  }

  public void start() throws ExecuteException
  {
    for (MendyBotRole role : roles) {
      role.start();
    }
    for (Widget widget : widgetsL) {
      widget.start();
    }
  }

  public void stop()
  {
    for (MendyBotRole role : roles) {
      role.stop();
    }
    for (Widget widget : widgetsL) {
      widget.stop();
    }
  }

  private String getConfigProperty(String key)
  {
    return config.getProperty(key);
  }

  private String getConfigProperty(String key, String defaultValue)
  {
    String v = getConfigProperty(key);
    if (v == null) {
      return defaultValue;
    } else {
      return v;
    }
  }

  private List<String> getConfigProperties(String key)
  {
    ArrayList<String> list = new ArrayList<String>();
    int index = 1;
    while (true) {
      String val = getConfigProperty(key+"_"+(index++));
      if (val == null) {
        break;
      } else {
        list.add(val);
      }
    }
    return list;
  }

  public String getProperty(String key)
  {
    return properties.getProperty(key);
  }

  public String getProperty(String key, String defaultValue)
  {
    String v = getProperty(key);
    if (v == null) {
      return defaultValue;
    } else {
      return v;
    }
  }

  public List<String> getProperties(String key)
  {
    ArrayList<String> list = new ArrayList<String>();
    int index = 1;
    while (true) {
      String val = getProperty(key+"_"+(index++));
      if (val == null) {
        break;
      } else {
        list.add(val);
      }
    }
    return list;
  }

  public String getName()
  {
    return application.getName();
  }

  public void stopApplication()
  {
    application.applicationStop();
  }

  public File getWorkingEtcDir()
  {
    return dirEtc;
  }

  public File getWorkingOptDir()
  {
    return dirOpt;
  }

  public ApplicationRole lookupApplicationModel(String id)
  {
    return appRolesM.get(id);
  }

  public List<Widget> getWidgets()
  {
    return this.widgetsL;
  }

  public TimeZone getTimeZone()
  {
    return timeZone;
  }
}
