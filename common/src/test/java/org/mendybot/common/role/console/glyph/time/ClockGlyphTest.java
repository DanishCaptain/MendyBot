package org.mendybot.common.role.console.glyph.time;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mendybot.common.application.Application;
import org.mendybot.common.application.model.ApplicationModel;
import org.mendybot.common.role.console.ConsoleRole;
import org.mendybot.common.role.console.glyph.Glyph;
import org.mendybot.common.role.console.glyph.GlyphTest;

public class ClockGlyphTest extends GlyphTest
{
  private static ClockGlyph glyph;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    System.setProperty("TEST-MODE", "true");
    Application application = new Application("clock-glyph_unittest");
    ApplicationModel model = new ApplicationModel(application);
    ConsoleRole role = new UnitTestConsoleRole(model);
    String name = "ut";
    String widgetName = "TIMEKEEPER";
    glyph = new ClockGlyph(role, name, widgetName);
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

  @Test
  public final void testTimeChange()
  {
    fail("Not yet implemented"); // TODO
  }

  @Override
  protected Glyph getTestGlyph()
  {
    return glyph;
  }

}
