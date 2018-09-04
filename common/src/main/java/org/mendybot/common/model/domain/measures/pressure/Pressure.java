package org.mendybot.common.model.domain.measures.pressure;

import java.text.DecimalFormat;

import org.mendybot.common.model.domain.measures.Precision;
import org.mendybot.common.util.TemperatureConversion;

public class Pressure
{
  private PressureType type;
  private Precision precision;
  private double rawValue;
  private double commonValue;
  private DecimalFormat formatter;

  public Pressure(PressureType type, Precision precision, double value)
  {
    this.type = type;
    this.precision = precision;
    this.rawValue = value;
    formatter = new DecimalFormat(precision.getFilter());
  }

  @Override
  public String toString()
  {
    if (type == PressureType.HECTOPASCALS)
    {
      return formatter.format((rawValue))+" "+type.getShorthand();
    }
    throw new RuntimeException("unknown temperature type "+type);
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof Pressure) {
      return ((Pressure)o).rawValue == rawValue;
    } else {
      return false;
    }
  }
}
