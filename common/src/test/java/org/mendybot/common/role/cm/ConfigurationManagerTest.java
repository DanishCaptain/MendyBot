package org.mendybot.common.role.cm;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mendybot.common.role.archive.Manifest;
import org.mendybot.common.role.archive.ManifestEntry;

public class ConfigurationManagerTest
{

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  //@Test
  public final void testFilterMissing()
  {
    Map<String, Manifest> master = new LinkedHashMap<>();
    Map<String, Manifest> local = new LinkedHashMap<>();
    Map<String, Manifest> r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{}");

    Manifest m1 = new Manifest("M1");
    master.put(m1.getName(), m1);
    r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{M1=M1 {}}");
    
    Manifest l1 = new Manifest("L1");
    local.put(l1.getName(), l1);
    r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{M1=M1 {}}");
    
    ManifestEntry meM1 = new ManifestEntry("ME1");
    m1.add(meM1);
    r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{M1=M1 {ME1=ME1 Dec 31 69 17:00}}");
    
    ManifestEntry meL1 = new ManifestEntry("LE1");
    l1.add(meL1);
    r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{M1=M1 {ME1=ME1 Dec 31 69 17:00}}");
    
    m1.remove(meM1);
    r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{M1=M1 {}}");
    
    l1.remove(meL1);
    r = ConfigurationManager.filterMissing(master, local);
    assertEquals(r.toString(), "{M1=M1 {}}");
    
  }

  //@Test
  public final void testFilterExtra()
  {
    Map<String, Manifest> master = new LinkedHashMap<>();
    Map<String, Manifest> local = new LinkedHashMap<>();
    Map<String, Manifest> r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{}");

    Manifest m1 = new Manifest("M1");
    master.put(m1.getName(), m1);
    r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{}");

    Manifest l1 = new Manifest("L1");
    local.put(l1.getName(), l1);
    r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{L1=L1 {}}");

    ManifestEntry meM1 = new ManifestEntry("ME1");
    m1.add(meM1);
    r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{L1=L1 {}}");
    
    ManifestEntry meL1 = new ManifestEntry("LE1");
    l1.add(meL1);
    r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{L1=L1 {LE1=LE1 Dec 31 69 17:00}}");
    
    m1.remove(meM1);
    r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{L1=L1 {LE1=LE1 Dec 31 69 17:00}}");
    
    l1.remove(meL1);
    r = ConfigurationManager.filterExtra(master, local);
    assertEquals(r.toString(), "{L1=L1 {}}");
    
  }

  @Test
  public final void testWrongfulDelete()
  {
    Map<String, Manifest> master = new LinkedHashMap<>();
    Map<String, Manifest> local = new LinkedHashMap<>();

    Manifest m1 = new Manifest("0.1");
    master.put(m1.getName(), m1);
    ManifestEntry me = new ManifestEntry("s");
    me.setLastModified(1000);
    m1.add(me);
    me = new ManifestEntry("c");
    me.setLastModified(1000);
    m1.add(me);
    
    Manifest m2 = new Manifest("0.2");
    master.put(m2.getName(), m2);
    me = new ManifestEntry("s");
    me.setLastModified(1000);
    m2.add(me);
    me = new ManifestEntry("c");
    me.setLastModified(1000);
    m2.add(me);
    
    Manifest m3 = new Manifest("0.3");
    master.put(m3.getName(), m3);
    me = new ManifestEntry("s");
    me.setLastModified(1000);
    m3.add(me);
    me = new ManifestEntry("c");
    me.setLastModified(1000);
    m3.add(me);

    Manifest l0 = new Manifest("0.0.1");
    local.put(l0.getName(), l0);
    me = new ManifestEntry("s");
    me.setLastModified(1000);
    l0.add(me);
    me = new ManifestEntry("t");
    me.setLastModified(1000);
    l0.add(me);
    Manifest l1 = new Manifest("0.1");
    local.put(l1.getName(), l1);
    me = new ManifestEntry("s");
    me.setLastModified(1000);
    l1.add(me);
    me = new ManifestEntry("y");
    me.setLastModified(1000);
    l1.add(me);
    
    Map<String, Manifest> rM = ConfigurationManager.filterMissing(master, local);
    assertEquals(rM.toString(), "{0.1=0.1 {c=c Dec 31 69 17:00}, 0.2=0.2 {s=s Dec 31 69 17:00, c=c Dec 31 69 17:00}, 0.3=0.3 {s=s Dec 31 69 17:00, c=c Dec 31 69 17:00}}");
    Map<String, Manifest> rE = ConfigurationManager.filterExtra(master, local);
    assertEquals(rE.toString(), "{0.0.1=0.0.1 {s=s Dec 31 69 17:00, t=t Dec 31 69 17:00}, 0.1=0.1 {y=y Dec 31 69 17:00}}");
    
  }

}
