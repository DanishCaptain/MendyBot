package org.mendybot.common.util;

public final class TemperatureConversion
{
  private TemperatureConversion()
  {
  }

  public static double convertKelvinToFahrenheit(double tK)
  {
    return tK * 9/5 - 459.67;
  }

  public static double convertCelsiusToFahrenheit(double tC)
  {
    return (1.8 * tC) + 32;
  }

  public static double convertFahrenheitToKelvin(double tF)
  {
    return (tF + 459.67) * 5/9;
  }

  public static double convertCelsiusToKelvin(double tC)
  {
    return tC + 273.15;
  }

  public static double convertFahrenheitToCelsius(double tF)
  {
    return (tF - 32) / 1.8;
  }

  public static double convertKelvinToCelsius(double tK)
  {
    return tK - 273.15;
  }

}
