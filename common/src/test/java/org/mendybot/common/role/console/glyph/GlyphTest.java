package org.mendybot.common.role.console.glyph;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class GlyphTest
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

  @Test
  public final void testGetConsole()
  {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testGetName()
  {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testGetWIdgetName()
  {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testDraw()
  {
    Glyph glyph = getTestGlyph();
    BufferedImage img = new BufferedImage(400,400, BufferedImage.TYPE_INT_RGB);
    glyph.draw(img.getGraphics());
    File outputfile = new File("UT_"+glyph.getClass().getSimpleName()+"_test.jpg");
    System.out.println(outputfile.getAbsolutePath());
    try
    {
      ImageIO.write(img, "jpg", outputfile);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected abstract Glyph getTestGlyph();

}
