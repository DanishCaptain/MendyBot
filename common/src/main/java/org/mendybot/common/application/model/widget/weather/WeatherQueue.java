package org.mendybot.common.application.model.widget.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;
import org.mendybot.common.model.domain.DomainDefinition;
import org.mendybot.common.model.domain.DomainModel;
import org.mendybot.common.model.domain.DomainObject;
import org.mendybot.common.model.domain.measures.Precision;
import org.mendybot.common.model.domain.measures.pressure.Pressure;
import org.mendybot.common.model.domain.measures.pressure.PressureType;
import org.mendybot.common.model.domain.measures.temperature.Temperature;
import org.mendybot.common.model.domain.measures.temperature.TemperatureType;

public class WeatherQueue extends TimerTask
{
  private ArrayList<WeatherListener> weatherListeners = new ArrayList<WeatherListener>();
  private static final long DELAY = 0;
  private static final long PERIOD = 1000 * 60 * 15;
  public static final String NAME = "WeatherQueue";
  private String ppid = "369d65abc09843c40acaa6bf244d79dd";
  private String zip = "85008";
  private Timer timer;
  private DomainModel domainModel;

  public WeatherQueue()
  {
    domainModel = DomainModel.lookup(WeatherWidget.MODEL_NAME);
    timer = new Timer();
  }

  public void start()
  {
    timer.schedule(this, DELAY, PERIOD);
  }

  public void stop()
  {
    timer.cancel();
  }

  @Override
  public void run()
  {
    DomainDefinition currentWeatherDef = domainModel.lookupDefinition(WeatherWidget.DD_CURRENT_WEATHER);
    DomainDefinition locationDef = domainModel.lookupDefinition(WeatherWidget.DD_LOCATION);

    String urlString = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&appid=" + ppid
        + getModeUrlAddition();
    try
    {
      URL url = new URL(urlString);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != 200)
      {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      StringBuilder sb = new StringBuilder();
      String output;
      while ((output = br.readLine()) != null)
      {
        sb.append(output + "\n");
      }
      conn.disconnect();
      DomainObject c = domainModel.create(currentWeatherDef, NAME);
      NumberFormat tempFormat = new DecimalFormat("#0.00");
      NumberFormat speedFormat = new DecimalFormat("#0.0");
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

      TimeZone timeZone = TimeZone.getDefault();
      Calendar cal = Calendar.getInstance();
      cal.setTimeZone(timeZone);
      SimpleDateFormat sdfSun = new SimpleDateFormat("HH:mm");
      sdfSun.setTimeZone(timeZone);

      JSONObject obj = new JSONObject(sb.toString());
      DomainObject loc = domainModel.create(locationDef, c.getAddress().toString(), WeatherWidget.WEATHER_REPORT_LOCATION);
      
      loc.setValue(WeatherWidget.LOCATION_CITY, obj.getString("name"));
      JSONObject sys = obj.getJSONObject("sys");
      loc.setValue(WeatherWidget.LOCATION_COUNTRY, sys.getString("country"));

      cal.setTimeInMillis(sys.getLong("sunrise") * 1000);
      c.setValue(WeatherWidget.WEATHER_REPORT_SUNRISE, sdfSun.format(cal.getTime()));
      cal.setTimeInMillis(sys.getLong("sunset") * 1000);
      c.setValue(WeatherWidget.WEATHER_REPORT_SUNSET, sdfSun.format(cal.getTime()));

      JSONObject coord = obj.getJSONObject("coord");
      loc.setValue(WeatherWidget.LOCATION_LONGITUDE, coord.getDouble("lon"));
      loc.setValue(WeatherWidget.LOCATION_LATITUDE, coord.getDouble("lat"));
      JSONObject main = obj.getJSONObject("main");
      JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);

      JSONObject wind = obj.getJSONObject("wind");
      c.setValue(WeatherWidget.WEATHER_REPORT_WIND_DIRECTION, wind.getInt("deg"));
      c.setValue(WeatherWidget.WEATHER_REPORT_WIND_SPEED, speedFormat.format(wind.getDouble("speed")));

      long dt = obj.getLong("dt") * 1000;
      c.setValue(WeatherWidget.WEATHER_REPORT_LAST_UPDATE, sdf.format(new Date(dt)));

//      c.setIconImage(weather.getString("icon") + ".png");
      c.setValue(WeatherWidget.WEATHER_REPORT_DESCRIPTION, weather.getString("description"));
      
      Temperature t = new Temperature(TemperatureType.KELVIN, Precision.TWO, main.getDouble("temp"));
      Temperature tMin = new Temperature(TemperatureType.KELVIN, Precision.TWO, main.getDouble("temp_min"));
      Temperature tMax = new Temperature(TemperatureType.KELVIN, Precision.TWO, main.getDouble("temp_max"));
      c.setValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE, t);
      c.setValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE_LOW, tMin);
      c.setValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE_HIGH, tMax);
      
//      c.setValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE, tempFormat.format(TemperatureConversion.convertKelvinToFahrenheit(main.getDouble("temp"))));
//      c.setValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE_LOW, tempFormat.format(TemperatureConversion.convertKelvinToFahrenheit(main.getDouble("temp_min"))));
//      c.setValue(WeatherWidget.WEATHER_REPORT_TEMPERATURE_HIGH, tempFormat.format(TemperatureConversion.convertKelvinToFahrenheit(main.getDouble("temp_max"))));
      c.setValue(WeatherWidget.WEATHER_REPORT_HUMIDITY, main.getInt("humidity"));
      
      Pressure p = new Pressure(PressureType.HECTOPASCALS, Precision.ZERO, main.getInt("humidity"));
      
//      c.setValue(WeatherWidget.WEATHER_REPORT_ATMOSPHERIC_PRESSURE, main.getInt("pressure"));
      c.setValue(WeatherWidget.WEATHER_REPORT_ATMOSPHERIC_PRESSURE, p);
      notifyWeatherListeners(c);
    }
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private String getModeUrlAddition()
  {
    return "";
  }

  public void addWeatherListener(WeatherListener lis)
  {
    weatherListeners.add(lis);
  }

  public void removeWeatherListener(WeatherListener lis)
  {
    weatherListeners.remove(lis);
  }

  private void notifyWeatherListeners(DomainObject current)
  {
    for (WeatherListener lis : weatherListeners)
    {
      lis.change(current);
    }
  }

}
