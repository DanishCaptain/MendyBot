package org.mendybot.common.model.domain.measures.temperature;

import java.text.DecimalFormat;

import org.mendybot.common.model.domain.measures.Precision;
import org.mendybot.common.util.TemperatureConversion;

public class Temperature
{
  private TemperatureType type;
  private Precision precision;
  private double rawValue;
  private DecimalFormat formatter;

  public Temperature(TemperatureType type, Precision precision, double value)
  {
    this.type = type;
    this.precision = precision;
    this.rawValue = value;
    formatter = new DecimalFormat(precision.getFilter());
  }

  @Override
  public String toString()
  {
    if (type == TemperatureType.KELVIN)
    {
      return formatter.format(TemperatureConversion.convertKelvinToFahrenheit(rawValue))+"\u00b0F";
    }
    else if (type == TemperatureType.FAHRENHEIT)
    {
      return formatter.format(rawValue)+"\u00b0F";
    }
    else if (type == TemperatureType.CELSIUS)
    {
      return formatter.format(TemperatureConversion.convertCelsiusToFahrenheit(rawValue))+"\u00b0F";
    }
    throw new RuntimeException("unknown temperature type "+type);
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof Temperature) {
      return ((Temperature)o).rawValue == rawValue && ((Temperature)o).type == type && ((Temperature)o).precision == precision;
    } else {
      return false;
    }
  }
}
