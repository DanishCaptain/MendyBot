package org.mendybot.common.model.domain.measures.pressure;

public enum PressureType
{
  HECTOPASCALS("hPa");

  private String shorthand;

  PressureType(String shorthand)
  {
    this.shorthand = shorthand;
  }
  
  public String getShorthand()
  {
    return shorthand;
  }
}
