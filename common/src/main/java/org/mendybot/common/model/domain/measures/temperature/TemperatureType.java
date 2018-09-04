package org.mendybot.common.model.domain.measures.temperature;

public enum TemperatureType
{
  KELVIN("K"), FAHRENHEIT("F"), CELSIUS("C");

  private String key;

  TemperatureType(String key)
  {
    this.key = key;
  }
  
  public String getKey()
  {
    return key;
  }
  
  public static TemperatureType lookup(String key)
  {
    for (TemperatureType tt: TemperatureType.values()) {
      if (tt.getKey().equals(key)) {
        return tt;
      }
    }
    return null;
  }

}
